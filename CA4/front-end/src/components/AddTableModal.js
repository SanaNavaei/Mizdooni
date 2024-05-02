import { useState } from 'react';

function AddTableModal() {
  const [inputValue, setInputValue] = useState('');

  const handleInputChange = (event) => {
    setInputValue(event.target.value);
  };

  return (
    <div className="modal fade" id="addTable" tabIndex="-1" aria-labelledby="addTableLabel" aria-hidden="true">
      <div className="modal-dialog modal-dialog-centered">
        <div className="modal-content">
          <div className="modal-header">
            <h5 className="modal-title" id="addTableLabel">Add Table</h5>
            <button type="button" className="btn-close border border-dark rounded-circle me-1" data-bs-dismiss="modal"></button>
          </div>
          <div className="modal-body">
            <form id="addTableForm">
              <div className="d-flex justify-content-between mb-5 mt-3">
                <label htmlFor="tableNumber" className="form-label">Number of Seats</label>
                <input type="number" className="form-control w-50" id="tableNumber" min="1" required value={inputValue} onChange={handleInputChange} />
              </div>
              <button type="submit" className="miz-button disabled-button w-100" disabled={!inputValue.trim()}>Add</button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}

export default AddTableModal;
