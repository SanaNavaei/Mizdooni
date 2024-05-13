function CompleteReserveModal({ tableNumber, time, country, city, street }) {
  return (
    <div className="modal fade" id="modal-complete-reservation">
      <div className="modal-dialog p-0 rounded-3">
        <div className="modal-content">
          <div className="modal-header">
            <h4 className="modal-title">Reservation Detail</h4>
            <button type="button" className="btn-close border border-dark rounded-circle me-1" data-bs-dismiss="modal"></button>
          </div>
          <div className="modal-body" id="modal-restaurant">
            <p className="miz-text-grey mb-4">Note: Please Arrive at Least 15 Minutes Early.</p>
            <div className="d-flex justify-content-between px-2 mb-3">
              <p>Table Number</p>
              <p>{tableNumber}</p>
            </div>
            <div className="d-flex justify-content-between px-2 mb-3">
              <p>Time</p>
              <p>{time}</p>
            </div>
            <div className="px-2 mb-5">
              <p className="mb-3">Address</p>
              <p className="miz-text-grey">{`${country}, ${city}, ${street}`}</p>
            </div>
            <button id="close-reserve" type="button" className="w-100 rounded-3 py-1" data-bs-dismiss="modal">Close</button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default CompleteReserveModal;
