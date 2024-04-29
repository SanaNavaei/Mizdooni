import EmptyStar from '../assets/icons/star_empty.svg';
import FilledStar from '../assets/icons/star_filled.svg';

function AverageReview({ reviews, starCount, FoodRate, ServiceRate, AmbienceRate, OverallRate }) {
  return (
    <section id="average-review" className="row rounded-3 p-2 mt-5 mx-1">
      <div className="col-lg d-flex flex-column align-items-center align-items-lg-start pb-3 pb-lg-0">
        <h2 className="fw-normal lh-base fs-5">What {reviews} people are saying</h2>
        <div className="d-flex align-items-center gap-1">
          {[...Array(starCount)].map((_, index) => (
            <img key={index} src={FilledStar} alt="Star" width="15" height="18" />
          ))}
          {[...Array(5 - starCount)].map((_, index) => (
            <img key={`empty_${index}`} src={EmptyStar} alt="Empty Star" width="15" height="18" />
          ))}
          <p className="stars-text miz-text-grey ms-2">{starCount} based on recent ratings</p>
        </div>
      </div>
      <div className="col-lg">
        <div className="d-flex justify-content-around text-center">
          <div className="average-category">Food<br /><span className="fs-5">{FoodRate}</span></div>
          <div className="average-category">Service<br /><span className="fs-5">{ServiceRate}</span></div>
          <div className="average-category">Ambiance<br /><span className="fs-5">{AmbienceRate}</span></div>
          <div className="average-category">Overall<br /><span className="fs-5">{OverallRate}</span></div>
        </div>
      </div>
    </section>
  );
}

export default AverageReview;

