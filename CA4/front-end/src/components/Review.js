import Stars from './Stars'

import Dot from 'assets/icons/dot.svg';

function getInitials(name) {
  const words = name.split(' ');
  let initials = '';

  for (const word of words) {
    initials += word.charAt(0);
  }

  return initials.toUpperCase();
}

function Review({ reviewerName='', foodRate, serviceRate, ambianceRate, overallRate, date, comment, starCount }) {
  const initials = getInitials(reviewerName);

  return (
    <section className="d-flex align-items-start gap-2 gap-sm-3">
      <div className="flex-grow-0">
        <div className="profile-picture fs-5 d-flex justify-content-center align-items-center">{initials}</div>
      </div>
      <div className="flex-grow-1 pt-sm-2">
        <div className="d-md-flex justify-content-between align-items-start">
          <div className="pb-1 pb-md-0">
            <h3 className="fw-normal lh-base fs-5">{reviewerName}</h3>
            <div className="review-rates d-flex align-items-center">
              <div>Food <span className="miz-text-red">{foodRate}</span></div>
              <img className="mx-1" src={Dot} alt="" />
              <div>Service <span className="miz-text-red">{serviceRate}</span></div>
              <img className="mx-1" src={Dot} alt="" />
              <div>Ambiance <span className="miz-text-red">{ambianceRate}</span></div>
              <img className="mx-1" src={Dot} alt="" />
              <div>Overall <span className="miz-text-red">{overallRate}</span></div>
            </div>
          </div>
          <div className="d-flex align-items-center gap-1">
            <Stars count={starCount} size={15} />
            <p className="stars-text miz-text-grey ms-2 my-0">Dined on {date}</p>
          </div>
        </div>
        <p className="pt-2">{comment}</p>
      </div>
    </section>
  );
}

export default Review;
