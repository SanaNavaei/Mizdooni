import { Link } from 'react-router-dom';

import Stars from './Stars'
import { isRestaurantOpen } from 'utils/date';

import Location from 'assets/icons/location.svg';
import Dot from 'assets/icons/dot.svg';

function Card({ id, name, totalReviews, type, address, endTime, startTime, starCount, image }) {
  const isOpen = isRestaurantOpen(startTime, endTime)

  return (
    <section className="col">
      <div className="restaurant-card card border-0 h-100 rounded-3 position-relative">
        <Link to={`/restaurants/${id}`}>
          <div className="card-stars position-absolute rounded-end-4 d-flex px-2 py-1 justify-content-around">
            <Stars count={starCount} size={12} />
          </div>
          <img className="restaurant-card-pic card-img-top rounded-top-3 w-100 object-fit-cover" src={image} alt={name} />
        </Link>
        <div className="card-body py-2">
          <h3 className="card-title fs-6 lh-base">{name}</h3>
          <p className="miz-text-grey fw-medium">{totalReviews} reviews</p>
          <p className="text-black fw-light">{type}</p>
          <div className="d-flex">
            <img className="me-2" src={Location} alt="Location" />
            <p>{address.city}</p>
          </div>
          <div className="d-flex">
            <p className={`miz-text-${isOpen ? 'green' : 'red'} fw-medium`}>{isOpen ? 'Open' : 'Closed'}</p>
            <img className="mx-1" src={Dot} alt="" />
            <p>{isOpen ? `Closes at ${endTime}` : `Opens at ${startTime}`}</p>
          </div>
        </div>
      </div>
    </section>
  );
}

export default Card;
