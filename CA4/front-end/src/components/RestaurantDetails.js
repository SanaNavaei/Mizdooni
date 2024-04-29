import Time from '../assets/icons/time.svg';
import ReviewStar from '../assets/icons/review_star.svg';
import Restaurant from '../assets/icons/restaurant.svg';
import Location from '../assets/icons/location.svg';

function RestaurantDetails({ name, reviews, type, country, city, street, isOpen, closingTime, startingTime, image, description }) {
  return (
    <section class="col-lg">
      <div class="position-relative">
        <img id="restaurant-image" class="w-100 object-fit-cover rounded-3 border-end-3" src={image} alt={name} />
        <div id="restaurant" class="border-bottom w-100 d-flex justify-content-between align-items-center position-absolute bottom-0 rounded-bottom-3 pt-3 pb-2 ps-2 pe-4">
          <h2 class="fw-semibold">{name}</h2>
          <p class="open text-white rounded-3 py-1 px-3">{isOpen ? 'Open!' : 'Closed!'}</p>
        </div>
      </div>
      <div class="px-2">
        <table class="table table-borderless align-middle m-0">
          <tbody>
            <tr>
              <td class="ps-0">
                <div class="d-flex align-items-center gap-1">
                  <img src={Time} alt="Time" width="20" height="20" />
                    <p class="fw-light">From {startingTime} to {closingTime}</p>
                </div>
              </td>
              <td>
                <div class="d-flex align-items-center gap-1">
                  <img src={ReviewStar} alt="Review" width="21" height="22" />
                    <p class="fw-light">{reviews} Reviews</p>
                </div>
              </td>
              <td>
                <div class="d-flex align-items-center gap-1">
                  <img src={Restaurant} alt="Restaurant" width="11" height="17" />
                    <p class="fw-light">{type}</p>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
        <div class="d-flex align-items-center pt-2 pb-3">
          <img class="me-2" src={Location} alt="Location" />
            <p class="miz-text-grey fw-light">{country}, {city}, {street}</p>
        </div>
        <p class="text-justify fw-light lh-sm">{description}</p>
      </div>
    </section>
  );
}

export default RestaurantDetails;
