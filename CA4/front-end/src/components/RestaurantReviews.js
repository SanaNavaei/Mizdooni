import AverageReview from "./AverageReview";
import Review from "./Review";

function RestaurantReviews({ restaurant, reviews }) {
  return (
    <div>
      <AverageReview 
        reviews={restaurant.reviews}
        starCount={restaurant.starCount}
        FoodRate={restaurant.FoodRate} 
        ServiceRate={restaurant.ServiceRate} 
        AmbienceRate={restaurant.AmbienceRate} 
        OverallRate={restaurant.OverallRate} 
      />

      <article class="mt-4">
        <div class="d-flex justify-content-between align-items-center pb-4">
          <h2 class="fw-normal fs-6">{restaurant.reviews} Reviews</h2>
          <button class="miz-button">Add Review</button>
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

      <nav class="mt-5">
        <ul class="review-pagination d-flex justify-content-center align-items-center gap-2">
          <li class="active"><a href="#">1</a></li>
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
