import FullStar from '../assets/icons/star_filled.svg';
import EmptyStar from '../assets/icons/star_empty.svg';
import Location from '../assets/icons/location.svg';
import Dot from '../assets/icons/dot.svg';

function Card({ name, reviews, type, location, isOpen, closingTime, startingTime, starCount, image }) {
  const totalStars = 5;

  return (
    <section className="col">
      <div className="restaurant-card card border-0 h-100 rounded-3 position-relative">
        <a href="#">
          <div className="card-stars position-absolute rounded-end-4 d-flex px-2 py-1 justify-content-around">
            {[...Array(starCount)].map((_, index) => (
              <img key={index} src={FullStar} alt="Star" width="12" height="15" />
            ))}
            {[...Array(totalStars - starCount)].map((_, index) => (
              <img key={`empty_${index}`} src={EmptyStar} alt="Empty Star" width="12" height="15" />
            ))}
          </div>
          <img className="restaurant-card-pic card-img-top rounded-top-3 w-100 object-fit-cover" src={image} alt={name} />
        </a>
        <div className="card-body py-2">
          <h3 className="card-title fs-6 lh-base">{name}</h3>
          <p className="miz-text-grey fw-medium">{reviews} reviews</p>
          <p className="text-black fw-light">{type}</p>
          <div className="d-flex">
            <img className="me-2" src={Location} alt="Location" />
            <p>{location}</p>
          </div>
          <div className="d-flex">
            <p className={`miz-text-${isOpen ? 'green' : 'red'} fw-medium`}>{isOpen ? 'Open' : 'Closed'}</p>
            <img className="mx-1" src={Dot} alt="" />
            <p>{isOpen ? `Closes at ${closingTime}` : `Opens at ${startingTime}`}</p>
          </div>
        </div>
      </div>
    </section>
  )
}

export default Card;
