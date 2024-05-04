import { useState } from 'react';

function CancelReserveModal({ restaurantName, reserveId }) {
  const [isChecked, setIsChecked] = useState(false);
  const userId = localStorage.getItem('id');

  const handleCheckboxChange = () => {
    setIsChecked(!isChecked);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    fetch(`/api/reserves/:${reserveId}/cancel`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ reserveId: reserveId, userId: userId }),
    })
      .then(async (response) => {
        if (response.ok) {
          return response.json();
        } else {
          const resp = await response.json();
          throw new Error(resp.message);
        }
      })
      .then((data) => {
        console.log(data);
      })
      .catch((error) => {
        console.error('Error:', error);
      });
  }


return (
  <div className="modal fade" id="cancelReservation">
    <div className="modal-dialog p-0 rounded-3">
      <div className="modal-content">
        <div className="modal-header">
          <h4 className="modal-title">Cancel Reservation at <span className="miz-text-red">{restaurantName}</span></h4>
          <button type="button" className="btn-close border border-dark rounded-circle me-1" data-bs-dismiss="modal"></button>
        </div>
        <div className="modal-body" id="modal-restaurant">
          <p className="miz-text-grey mb-2">Note: Once you hit the Cancel button, your reserve will be canceled</p>
          <div className="d-flex mb-2">
            <input type="checkbox" className="custom-checkbox me-2" id="agree" name="agree" checked={isChecked} onChange={handleCheckboxChange} />
            <label for="agree">I agree</label>
          </div>
          <form onSubmit={handleSubmit}>
            <button type="submit" className="miz-button disabled-button w-100" disabled={!isChecked} data-bs-dismiss="modal">Cancel</button>
          </form>
        </div>
      </div>
    </div>
  </div>
);
}

export default CancelReserveModal;
