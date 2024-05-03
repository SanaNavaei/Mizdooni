import AddRestaurantModal from "./AddRestaurantModal";

function ManagerRestaurants({ restaurants }) {
  return (
    <div id="restaurants-list" className="mx-auto bg-white rounded-3 pt-3 pb-1">
      <div className="d-flex justify-content-between align-items-center px-3">
        <h2 className="fw-semibold m-0 fs-6">My Restaurants</h2>
        <button className="miz-button" data-bs-toggle="modal" data-bs-target="#addRestaurant">Add</button>
      </div>
      <hr />
      {restaurants.length === 0 &&
        <div className="table-responsive">
          <p className="miz-text-grey text-center fs-5">Add your first restaurant!</p>
        </div>
      }

      <div className="table-responsive">
        <table className="table table-borderless align-middle">
          <tbody>
            {restaurants.map((restaurant, index) => (
              <tr key={index}>
                <td className="ps-3">{restaurant.name}</td>
                <td className="text-center">{restaurant.location}</td>
                <td className="text-end pe-3">
                  <button className="miz-button">Manage</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      <AddRestaurantModal />
    </div>
  );
}

export default ManagerRestaurants;
