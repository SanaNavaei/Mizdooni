import React from 'react';

import AddReviewModal from './AddReviewModal';
import CancelReserveModal from './CancelReserveModal';

function CustomerReserve({ reserves }) {
  return (
    <>
      <div className="table-responsive mt-4 rounded-3">
        <table id="reservations" className="table align-middle m-0">
          <thead>
            <tr>
              <th className="fw-semibold fs-5 px-3 py-2" colSpan="5">My Reservations</th>
            </tr>
          </thead>
          <tbody>
            {reserves.length === 0 ? (
              <tr>
                <td id="no-reserve" className="miz-text-grey text-center p-4">No Reservations yet!</td>
              </tr>
            ) : (
              reserves.map((reserve, index) => (
                <React.Fragment key={index}>
                  <tr key={index} className={reserve.cancelled && reserve.isPastTime ? "cancelled-reservation past-reservation" : reserve.cancelled ? "cancelled-reservation" : reserve.isPastTime ? "past-reservation" : ""}>
                    <td className="ps-3">{reserve.datetime}</td>
                    <td>{reserve.restaurant.name}</td>
                    <td>Table-{reserve.table.tableNumber}</td>
                    <td>{reserve.table.seatsNumber} Seats</td>
                    <td className="pe-3 text-end">
                      {reserve.cancelled ? (
                        <button className="miz-link-button" disabled>Canceled</button>
                      ) : (
                        reserve.isPastTime ? (
                          <button className="miz-link-button" data-bs-toggle="modal" data-bs-target="#modal-add-review">
                            Add Comment
                          </button>
                        ) : (
                          <button className="miz-link-button" data-bs-toggle="modal" data-bs-target="#modal-cancel-reservation">
                            Cancel
                          </button>
                        )
                      )}
                    </td>
                  </tr>
                  <AddReviewModal restaurantName={reserve.restaurant.name} restaurantId={reserve.restaurant.id} />
                  <CancelReserveModal restaurantName={reserve.restaurant.name} reserveId={reserve.reservationNumber} />
                </React.Fragment>
              ))
            )}
          </tbody>
        </table>
      </div>
    </>
  );
}

export default CustomerReserve;
