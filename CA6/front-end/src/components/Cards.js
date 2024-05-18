import Card from './Card';

function Cards({ topText, restaurants }) {
  return (
    <article className="container pt-5">
      <h2 className="fs-6 fw-normal mb-3">{topText}</h2>
      <div className="row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 row-cols-xl-5 row-cols-xxl-6 g-4">
        {restaurants.map((restaurant, index) => (
          <Card key={index} {...restaurant} />
        ))}
      </div>
    </article>
  );
}

export default Cards;
