import { useState, useEffect } from 'react';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

import EmptyStar from 'assets/icons/star_empty.svg'
import FullStar from 'assets/icons/star_filled.svg'

function AddReviewModal({ restaurantName, restaurantId, reloadReviews }) {
  const [isSubmitDisabled, setIsSubmitDisabled] = useState(true);
  const [error, setError] = useState('');
  const [formData, setFormData] = useState({
    rating: {
      food: 0,
      service: 0,
      ambiance: 0,
      overall: 0,
    },
    comment: '',
  });

  useEffect(() => {
    if (formData.rating.food > 0 && formData.rating.service > 0 && formData.rating.ambiance > 0 && formData.rating.overall > 0) {
      setIsSubmitDisabled(false);
    } else {
      setIsSubmitDisabled(true);
    }
  }, [formData]);

  const handleSubmit = (e) => {
    e.preventDefault();
    fetch(`/api/reviews/${restaurantId}`, {
      method: 'POST',
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`,
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(formData),
    })
      .then(async response => {
        if (response.ok) {
          return response.json();
        }
        const resp = await response.json();
        throw new Error(resp.message);
      })
      .then(data => {
        console.debug(data);
        setError('');
        reloadReviews();
        toast.success('Review added successfully', {
          position: 'top-right',
          autoClose: 3000,
        });
      })
      .catch(error => {
        toast.error(error.message, {
          position: 'top-right',
          autoClose: 3000,
        })
        setError(error.message);
      });
  }

  const handleCommentChange = (e) => {
    setFormData({ ...formData, comment: e.target.value });
  }

  const handleFoodClick = (starValue) => {
    setFormData({ ...formData, rating: { ...formData.rating, food: starValue } });
  };

  const handleServiceClick = (starValue) => {
    setFormData({ ...formData, rating: { ...formData.rating, service: starValue } });
  }

  const handleAmbianceClick = (starValue) => {
    setFormData({ ...formData, rating: { ...formData.rating, ambiance: starValue } });
  }

  const handleOverallClick = (starValue) => {
    setFormData({ ...formData, rating: { ...formData.rating, overall: starValue } });
  }

  return (
    <div className="modal fade" id="modal-add-review" tabIndex="-1" aria-labelledby="add-review-label" aria-hidden="true">
      <div className="modal-dialog modal-dialog-centered">
        <div className="modal-content">
          <div className="modal-header">
            <h5 className="modal-title" id="add-review-label">Add Review for <span className="miz-text-red">{restaurantName}</span></h5>
            <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div className="modal-body">
            <p className="miz-text-grey mb-3">Note: Reviews can only be made by diners who have eaten at this restaurant</p>
            <form onSubmit={handleSubmit}>
              <div className="d-flex justify-content-between mb-3">
                <p>Food Quality</p>
                <div>
                  {[...Array(5)].map((_, index) => (
                    <img
                      key={index}
                      className='add-review-star'
                      src={index < formData.rating.food ? FullStar : EmptyStar}
                      alt={index < formData.rating.food ? 'Full Star' : 'Empty Star'}
                      onClick={() => handleFoodClick(index + 1)}
                    />
                  ))}
                </div>
              </div>
              <div className="d-flex justify-content-between mb-3">
                <p>Service</p>
                <div>
                  {[...Array(5)].map((_, index) => (
                    <img
                      key={index}
                      className='add-review-star'
                      src={index < formData.rating.service ? FullStar : EmptyStar}
                      alt={index < formData.rating.service ? 'Full Star' : 'Empty Star'}
                      onClick={() => handleServiceClick(index + 1)}
                    />
                  ))}
                </div>
              </div>
              <div className="d-flex justify-content-between mb-3">
                <p>Ambiance</p>
                <div>
                  {[...Array(5)].map((_, index) => (
                    <img
                      key={index}
                      className='add-review-star'
                      src={index < formData.rating.ambiance ? FullStar : EmptyStar}
                      alt={index < formData.rating.ambiance ? 'Full Star' : 'Empty Star'}
                      onClick={() => handleAmbianceClick(index + 1)}
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
                      className='add-review-star'
                      src={index < formData.rating.overall ? FullStar : EmptyStar}
                      alt={index < formData.rating.overall ? 'Full Star' : 'Empty Star'}
                      onClick={() => handleOverallClick(index + 1)}
                    />
                  ))}
                </div>
              </div>
              <div className="mb-3">
                <label htmlFor="reviewComment" className="form-label">Comment</label>
                <textarea className="form-control" id="reviewComment" rows="5" onChange={handleCommentChange} placeholder="Type your review..."></textarea>
              </div>
              {error && <p className="miz-text-red text-center">{error}</p>}
              <button type="submit" className="miz-button disabled-button w-100" disabled={isSubmitDisabled}>Submit Review</button>
              <button type="button" className="miz-button w-100 mt-3" id="close-reserve" data-bs-dismiss="modal">Cancel</button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}

export default AddReviewModal;
