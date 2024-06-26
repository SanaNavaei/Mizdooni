import { useState } from 'react';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

function CancelReserveModal({ restaurantName, reserveId }) {
  const [isChecked, setIsChecked] = useState(false);

  const handleCheckboxChange = () => {
    setIsChecked(true);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    fetch(`/api/reserves/cancel/${reserveId}`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
    })
      .then(async (response) => {
        if (response.ok) {
          return response.json();
        } else {
          const resp = await response.json();
          throw new Error(resp.message);
        }
      })
      .then(data => {
        toast.success('Reservation canceled successfully', {
          position: 'top-right',
          autoClose: 3000,
        })
        setIsChecked(false);
        window.location.reload();
      })
      .catch(error => {
        toast.error(error.message, {
          position: 'top-right',
          autoClose: 3000,
        });
        setIsChecked(false);
      });
  }

  return (
    <div className="modal fade" id="modal-cancel-reservation">
      <div className="modal-dialog p-0 rounded-3">
        <div className="modal-content">
          <div className="modal-header">
            <h4 className="modal-title">Cancel Reservation at <span className="miz-text-red">{restaurantName}</span></h4>
            <button type="button" className="btn-close border border-dark rounded-circle me-1" data-bs-dismiss="modal"></button>
          </div>
          <div className="modal-body" id="modal-restaurant">
            <p className="miz-text-grey mb-2">Note: Once you hit the Cancel button, your reservation will be canceled</p>
            <div className="d-flex mb-2">
              <input type="checkbox" className="custom-checkbox me-2" id="agree" name="agree" checked={isChecked} onChange={handleCheckboxChange} />
              <label htmlFor="agree">I agree</label>
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
