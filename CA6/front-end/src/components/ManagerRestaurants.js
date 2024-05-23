import { Link } from 'react-router-dom';

import AddRestaurantModal from './AddRestaurantModal';

function ManagerRestaurants({ restaurants, reloadRestaurants }) {
  const id = localStorage.getItem('id');

  return (
    <div id="restaurants-list" className="mx-auto bg-white rounded-3 pt-3 pb-1">
      <div className="d-flex justify-content-between align-items-center px-3">
        <h2 className="fw-semibold m-0 fs-6">My Restaurants</h2>
        <button className="miz-button" data-bs-toggle="modal" data-bs-target="#modal-add-restaurant">Add</button>
      </div>
      <hr />
      {restaurants == null &&
        <div className="table-responsive">
          <p className="miz-text-grey text-center fs-5">Add your first restaurant!</p>
        </div>
      }

      <div className="table-responsive">
        <table className="table table-borderless align-middle">
          <tbody>
            {restaurants && restaurants.length > 0 &&
              restaurants.map((restaurant, index) => (
                <tr key={index}>
                  <td className="ps-3">{restaurant.name}</td>
                  <td className="text-center">{restaurant.location}</td>
                  <td className="text-end pe-3">
                    <Link to={`/manage/${restaurant.id}`}>
                      <button className="miz-button">Manage</button>
                    </Link>
                  </td>
                </tr>
              ))
            }

          </tbody>
        </table>
      </div>
      <AddRestaurantModal reloadRestaurants={reloadRestaurants} />
    </div>
  );
}

export default ManagerRestaurants;
