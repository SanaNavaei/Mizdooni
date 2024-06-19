import { isRestaurantOpen } from 'utils/date';

import Time from 'assets/icons/time.svg';
import ReviewStar from 'assets/icons/review_star.svg';
import Restaurant from 'assets/icons/restaurant.svg';
import Location from 'assets/icons/location.svg';

function RestaurantDetails({ name, totalReviews, type, address, endTime, startTime, image, description }) {
  const isOpen = isRestaurantOpen(startTime, endTime);

  return (
    <section className="col-lg">
      <div className="position-relative">
        <img id="restaurant-image" className="w-100 object-fit-cover rounded-3 border-end-3" src={image} alt={name} />
        <div id="restaurant" className="border-bottom w-100 d-flex justify-content-between align-items-center position-absolute bottom-0 rounded-bottom-3 pt-3 pb-2 ps-2 pe-4">
          <h2 className="fw-semibold">{name}</h2>
          <p className={`text-white rounded-3 py-1 px-3 ${isOpen ? 'open' : 'closed'}`}>{isOpen ? 'Open!' : 'Closed!'}</p>
        </div>
      </div>
      <div className="px-2">
        <table className="table table-borderless align-middle m-0">
          <tbody>
            <tr>
              <td className="ps-0">
                <div className="d-flex align-items-center gap-1">
                  <img src={Time} alt="Time" width="20" height="20" />
                  <p className="fw-light ">From {startTime} to {endTime}</p>
                </div>
              </td>
              <td>
                <div className="d-flex align-items-center gap-1">
                  <img src={ReviewStar} alt="Review" width="21" height="22" />
                  <p className="fw-light ">{totalReviews} Reviews</p>
                </div>
              </td>
              <td>
                <div className="d-flex align-items-center gap-1">
                  <img src={Restaurant} alt="Restaurant" width="11" height="17" />
                  <p className="fw-light ">{type}</p>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
        <div className="d-flex align-items-center pt-2 pb-3">
          <img className="me-2" src={Location} alt="Location" />
          <p className="miz-text-grey fw-light ">{address.country}, {address.city}, {address.street}</p>
        </div>
        <p className="text-justify fw-light lh-sm">{description}</p>
      </div>
    </section>
  );
}

export default RestaurantDetails;
