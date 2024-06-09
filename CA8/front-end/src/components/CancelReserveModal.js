import { useState } from 'react';
import { toast } from 'react-toastify';

import { useLogout } from 'utils/logout';

function CancelReserveModal({ restaurantName, reserveId, reloadReserves }) {
  const [isChecked, setIsChecked] = useState(false);
  const logout = useLogout();

  const handleCheckboxChange = () => {
    setIsChecked(true);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const response = await fetch(`/api/reserves/cancel/${reserveId}`, {
      method: 'POST',
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`,
        'Content-Type': 'application/json',
      },
    });
    setIsChecked(false);
    if (response.ok) {
      toast.success('Reservation canceled successfully');
      reloadReserves();
    } else if (response.status === 401) {
      logout();
    } else {
      const body = await response.json();
      toast.error(body.message);
    }
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
