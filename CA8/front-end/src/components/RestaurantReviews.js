import { useState, useEffect } from 'react';
import { toast } from 'react-toastify';

import AverageReview from './AverageReview';
import Review from './Review';
import AddReviewModal from './AddReviewModal';
import Pagination from './Pagination';

function RestaurantReviews({ restaurant }) {
  const [reviews, setReviews] = useState([{
    rating: {
      food: 0,
      service: 0,
      ambiance: 0,
      overall: 0,
    },
    starCount: 0,
    comment: '',
    datetime: '',
    user: {
      id: 0,
      username: '',
      email: '',
    },
  }]);
  const [totalPages, setTotalPages] = useState(0);
  const [currentPage, setCurrentPage] = useState(1);

  const reloadReviews = async () => {
    const response = await fetch(`/api/reviews/${restaurant.id}?page=${currentPage}`);
    if (response.ok) {
      const body = await response.json();
      setReviews(body.data.pageList);
      setTotalPages(body.data.totalPages);
    } else {
      toast.error('Failed to fetch restaurant reviews');
    }
  }

  useEffect(() => {
    if (restaurant.id !== -1) {
      reloadReviews();
    }
  }, [restaurant.id, currentPage]);

  const handlePageChange = (page) => {
    setCurrentPage(page);
  };

  return (
    <div>
      <AverageReview
        reviews={restaurant.totalReviews}
        starCount={restaurant.starCount}
        foodRate={restaurant.averageRating.food}
        serviceRate={restaurant.averageRating.service}
        ambianceRate={restaurant.averageRating.ambiance}
        overallRate={restaurant.averageRating.overall}
      />

      <article className="mt-4">
        <div className="d-flex justify-content-between align-items-center pb-4">
          <h2 className="fw-normal fs-6">{restaurant.totalReviews} Reviews</h2>
          <button className="miz-button" data-bs-toggle="modal" data-bs-target="#modal-add-review">Add Review</button>
        </div>
        {reviews.map((review, index) => (
          <div key={index}>
            <Review
              reviewerName={review.user.username}
              foodRate={review.rating.food}
              serviceRate={review.rating.service}
              ambianceRate={review.rating.ambiance}
              overallRate={review.rating.overall}
              date={review.datetime}
              comment={review.comment}
              starCount={review.starCount}
            />
            <hr />
          </div>
        ))}
      </article>

      <AddReviewModal restaurantName={restaurant.name} restaurantId={restaurant.id} reloadReviews={reloadReviews} />
      <Pagination totalPages={totalPages} currentPage={currentPage} onPageChange={handlePageChange} />
    </div>
  )
}

export default RestaurantReviews;
