import { useState, useEffect } from 'react';

import AverageReview from './AverageReview';
import Review from './Review';
import AddReviewModal from './AddReviewModal';

function RestaurantReviews({ restaurant }) {
  const [reviews, setReviews] = useState([]);
  const [totalPages, setTotalPages] = useState(0);
  const [currentPage, setCurrentPage] = useState(1);

  useEffect(() => {
    fetch(`/api/reviews/${restaurant.id}?page=${currentPage}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error('Failed to fetch restaurant reviews');
        }
        return response.json();
      })
      .then((data) => {
        setReviews(data.pageList);
        setTotalPages(data.totalPages);
      })
      .catch(error => {
        console.error('Error fetching restaurant reviews:', error);
      });
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
        ambienceRate={restaurant.averageRating.ambience}
        overallRate={restaurant.averageRating.overall}
      />

      <article className="mt-4">
        <div className="d-flex justify-content-between align-items-center pb-4">
          <h2 className="fw-normal fs-6">{restaurant.totalReviews} Reviews</h2>
          <button className="miz-button" data-bs-toggle="modal" data-bs-target="#addReview">Add Review</button>
        </div>
        {reviews.map((review, index) => (
          <div key={index}>
            <Review
              reviewerName={review.reviewerName}
              foodRate={review.foodRate}
              serviceRate={review.serviceRate}
              ambianceRate={review.ambianceRate}
              overallRate={review.overallRate}
              date={review.date}
              comment={review.comment}
              starCount={review.starCount}
            />
            <hr />
          </div>
        ))}
      </article>
      <AddReviewModal restaurantName={restaurant.name} />
      <Pagination totalPages={totalPages} currentPage={currentPage} onPageChange={handlePageChange} />
    </div>
  )
}

export default RestaurantReviews;
