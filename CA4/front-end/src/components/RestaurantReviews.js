import AverageReview from './AverageReview';
import Review from './Review';
import AddReviewModal from './AddReviewModal';

function RestaurantReviews({ restaurant, reviews }) {
  return (
    <div>
      <AverageReview
        reviews={restaurant.reviews}
        starCount={restaurant.starCount}
        foodRate={restaurant.foodRate}
        serviceRate={restaurant.serviceRate}
        ambienceRate={restaurant.ambienceRate}
        overallRate={restaurant.overallRate}
      />

      <article className="mt-4">
        <div className="d-flex justify-content-between align-items-center pb-4">
          <h2 className="fw-normal fs-6">{restaurant.reviews} Reviews</h2>
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
      <AddReviewModal restaurantName={restaurant.name}/>

      <nav className="mt-5">
        <ul className="review-pagination d-flex justify-content-center align-items-center gap-2">
          <li className="active"><a href="#">1</a></li>
          <li><a href="#">2</a></li>
          <li><a href="#">3</a></li>
          <li>&middot; &middot; &middot;</li>
          <li><a href="#">19</a></li>
        </ul>
      </nav>

    </div>
  )
}

export default RestaurantReviews;