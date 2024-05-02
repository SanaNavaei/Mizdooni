const startHours = []
for (let i = 8; i < 18; i++) {
  const formattedHour = i.toString().padStart(2, '0');
  startHours.push(<option key={i} value={formattedHour}>{formattedHour}:00</option>);
}

const endHours = []
for (let i = 18; i < 24; i++) {
  const formattedHour = i.toString().padStart(2, '0');
  endHours.push(<option key={i} value={formattedHour}>{formattedHour}:00</option>);
}

function AddRestaurantModal() {
  return (
    <div className="modal fade" id="addRestaurant">
      <div className="modal-dialog">
        <div className="modal-content">
          <div className="modal-header">
            <h4 className="modal-title">Add Restaurant</h4>
            <button type="button" className="btn-close border border-dark rounded-circle me-1" data-bs-dismiss="modal"></button>
          </div>
          <div className="modal-body" id="modal-restaurant">
            <form>
              <div className="my-3 d-flex justify-content-between">
                <label htmlFor="name" className="form-label">Name</label>
                <input type="text" className="form-control w-50" id="name" name="name" required />
              </div>
              <div className="mb-3 d-flex justify-content-between">
                <label htmlFor="type" className="form-label">Type</label>
                <input type="text" className="form-control w-50" id="type" name="type" required />
              </div>
              <div className="mb-3">
                <label htmlFor="description" className="form-label">Description</label>
                <textarea className="form-control" rows="5" placeholder="Type about your restaurant..."></textarea>
              </div>
              <div className="mb-3 d-flex justify-content-between">
                <label htmlFor="country" className="form-label">Country</label>
                <input type="text" className="form-control w-50" id="country" name="country" required />
              </div>
              <div className="mb-3 d-flex justify-content-between">
                <label htmlFor="city" className="form-label">City</label>
                <input type="text" className="form-control w-50" id="city" name="city" required />
              </div>
              <div className="mb-3 d-flex justify-content-between">
                <label htmlFor="street" className="form-label">Street</label>
                <input type="text" className="form-control w-50" id="street" name="street" required />
              </div>
              <div className="d-flex justify-content-between mb-3">
                <label htmlFor="startHour">Start Hour</label>
                <select className="form-select mx-1 w-50" name="startHour" id="startHour">{startHours}</select>
              </div>
              <div className="d-flex justify-content-between mb-5">
                <label htmlFor="endHour">End Hour</label>
                <select className="form-select mx-1 w-50" name="endHour" id="endHour">{endHours}</select>
              </div>
              <button type="submit" className="miz-button w-100 mb-3" data-bs-dismiss="modal">Add</button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}

export default AddRestaurantModal;
