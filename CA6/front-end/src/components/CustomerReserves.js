import { useState } from 'react';
import { Link } from 'react-router-dom';

import AddReviewModal from './AddReviewModal';
import CancelReserveModal from './CancelReserveModal';

function CustomerReserves({ reserves, reloadReserves }) {
  const [modalData, setModalData] = useState({
    restaurant: {
      id: 0,
      name: '',
    },
    reservationNumber: 0,
  });

  const handleModals = (index) => {
    setModalData({
      restaurant: {
        id: reserves[index].restaurant.id,
        name: reserves[index].restaurant.name,
      },
      reservationNumber: reserves[index].reservationNumber,
    });
  }

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
                <tr key={index} className={reserve.cancelled && reserve.isPastTime ? "cancelled-reservation past-reservation" : reserve.cancelled ? "cancelled-reservation" : reserve.isPastTime ? "past-reservation" : ""}>
                  <td className="ps-3">{reserve.datetime}</td>
                  <td><Link className="miz-link-button text-decoration-none" to={`/restaurants/${reserve.restaurant.id}`}>{reserve.restaurant.name}</Link></td>
                  <td>Table-{reserve.table.tableNumber}</td>
                  <td>{reserve.table.seatsNumber} Seats</td>
                  <td className="pe-3 text-end">
                    {reserve.cancelled ? (
                      <button className="miz-link-button" disabled>Canceled</button>
                    ) : (
                      reserve.isPastTime ? (
                        <button className="miz-link-button" onClick={() => handleModals(index)} data-bs-toggle="modal" data-bs-target="#modal-add-review">
                          Add Comment
                        </button>
                      ) : (
                        <button className="miz-link-button" onClick={() => handleModals(index)} data-bs-toggle="modal" data-bs-target="#modal-cancel-reservation">
                          Cancel
                        </button>
                      )
                    )}
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>
      <AddReviewModal restaurantName={modalData.restaurant.name} restaurantId={modalData.restaurant.id} />
      <CancelReserveModal restaurantName={modalData.restaurant.name} reserveId={modalData.reservationNumber} reloadReserves={reloadReserves} />
    </>
  );
}

export default CustomerReserves;
