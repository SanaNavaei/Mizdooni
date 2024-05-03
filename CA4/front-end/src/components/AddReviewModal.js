import { useState, useEffect } from 'react';

import EmptyStar from 'assets/icons/star_empty.svg'
import FullStar from 'assets/icons/star_filled.svg'

function AddReviewModal({ restaurantName }) {
  const [qualityRating, setQualityRating] = useState(0);
  const [serviceRating, setServiceRating] = useState(0);
  const [ambienceRating, setAmbienceRating] = useState(0);
  const [overAllRating, setOverAllRating] = useState(0);
  const [isSubmitDisabled, setIsSubmitDisabled] = useState(true);

  useEffect(() => {
    if (qualityRating !== 0 && serviceRating !== 0 && ambienceRating !== 0 && overAllRating !== 0) {
      setIsSubmitDisabled(false);
    } else {
      setIsSubmitDisabled(true);
    }
  }, [qualityRating, serviceRating, ambienceRating, overAllRating]);


  const handleQualityClick = (starValue) => {
    setQualityRating(starValue);
  };

  const handleServiceClick = (starValue) => {
    setServiceRating(starValue);
  }

  const handleAmbienceClick = (starValue) => {
    setAmbienceRating(starValue);
  }

  const handleOverAllClick = (starValue) => {
    setOverAllRating(starValue);
  }

  return (
    <div className="modal fade" id="addReview" tabIndex="-1" aria-labelledby="addReviewLabel" aria-hidden="true">
      <div className="modal-dialog modal-dialog-centered">
        <div className="modal-content">
          <div className="modal-header">
            <h5 className="modal-title" id="addReviewLabel">Add Review for <span className="miz-text-red">{restaurantName}</span></h5>
            <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div className="modal-body">
            <p className="miz-text-grey mb-3">Note: Reviews can only be made by diners who have eaten at this restaurant</p>
            <div className="d-flex justify-content-between mb-3">
              <p>Food Quality</p>
              <div>
                {[...Array(5)].map((_, index) => (
                  <img
                    key={index}
                    className='addReviewStar'
                    src={index < qualityRating ? FullStar : EmptyStar}
                    alt={index < qualityRating ? 'Full Star' : 'Empty Star'}
                    onClick={() => handleQualityClick(index + 1)}
                  />
                ))}
              </div>
            </div>
            <div className="d-flex justify-content-between mb-3">
              <p>Servie</p>
              <div>
                {[...Array(5)].map((_, index) => (
                  <img
                    key={index}
                    className='addReviewStar'
                    src={index < serviceRating ? FullStar : EmptyStar}
                    alt={index < serviceRating ? 'Full Star' : 'Empty Star'}
                    onClick={() => handleServiceClick(index + 1)}
                  />
                ))}
              </div>
            </div>
            <div className="d-flex justify-content-between mb-3">
              <p>Ambience</p>
              <div>
                {[...Array(5)].map((_, index) => (
                  <img
                    key={index}
                    className='addReviewStar'
                    src={index < ambienceRating ? FullStar : EmptyStar}
                    alt={index < ambienceRating ? 'Full Star' : 'Empty Star'}
                    onClick={() => handleAmbienceClick(index + 1)}
                  />
                ))}
              </div>
            </div>
            <div className="d-flex justify-content-between mb-3">
              <p>Overall</p>
              <div>
                {[...Array(5)].map((_, index) => (
                  <img
                    key={index}
                    className='addReviewStar'
                    src={index < overAllRating ? FullStar : EmptyStar}
                    alt={index < overAllRating ? 'Full Star' : 'Empty Star'}
                    onClick={() => handleOverAllClick(index + 1)}
                  />
                ))}
              </div>
            </div>
            <div className="mb-3">
              <label htmlFor="reviewComment" className="form-label">Comment</label>
              <textarea className="form-control" id="reviewComment" rows="5" placeholder="Type your review..."></textarea>
            </div>

            <button type="button" className="miz-button disabled-button w-100" disabled={isSubmitDisabled}>Submit Review</button>
            <button type="button" className="miz-button w-100 mt-3" id="close-reserve" data-bs-dismiss="modal">Cancel</button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default AddReviewModal;
