import { useEffect, useState } from 'react';
import { toast } from 'react-toastify';

import { useLogout } from 'utils/logout';

function AddTableModal({ restaurantId, reloadTables }) {
  const [inputValue, setInputValue] = useState(0);
  const [isDisabled, setIsDisabled] = useState(true);
  const logout = useLogout();

  useEffect(() => {
    if (inputValue > 0) {
      setIsDisabled(false);
    } else {
      setIsDisabled(true);
    }
  }, [inputValue]);

  const handleInputChange = (event) => {
    setInputValue(event.target.value);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    const response = await fetch(`/api/tables/${restaurantId}`, {
      method: 'POST',
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`,
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ seatsNumber: inputValue }),
    });
    if (response.ok) {
      reloadTables();
      toast.success('Table added successfully');
    } else if (response.status === 401) {
      logout();
    } else {
      toast.error('Failed to add table');
    }
  }

  return (
    <div className="modal fade" id="modal-add-table" tabIndex="-1" aria-labelledby="add-table-label" aria-hidden="true">
      <div className="modal-dialog modal-dialog-centered">
        <div className="modal-content">
          <div className="modal-header">
            <h5 className="modal-title" id="add-table-label">Add Table</h5>
            <button type="button" className="btn-close border border-dark rounded-circle me-1" data-bs-dismiss="modal"></button>
          </div>
          <div className="modal-body">
            <form id="add-table-form" onSubmit={handleSubmit}>
              <div className="d-flex justify-content-between mb-5 mt-3">
                <label htmlFor="tableNumber" className="form-label">Number of Seats</label>
                <input type="number" className="form-control w-50" id="tableNumber" min="1" required value={inputValue} onChange={handleInputChange} />
              </div>
              <button type="submit" className="miz-button disabled-button w-100" disabled={isDisabled} data-bs-dismiss="modal">Add</button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}

export default AddTableModal;
