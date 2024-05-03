import { useEffect, useState } from 'react';

import PageLayout from 'components/PageLayout';
import RestaurantDetails from 'components/RestaurantDetails';
import Reserve from 'components/Reserve';
import RestaurantReviews from 'components/RestaurantReviews';

import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.min.js';
import 'assets/stylesheets/global.css';
import 'assets/stylesheets/restaurant.css';

function Restaurant({ restaurantId }) {
  useEffect(() => {
    document.title = 'Restaurant';
  }, []);
  const [restaurant, setRestaurant] = useState({});

  useEffect(() => {
    fetch(`/api/restaurant/${restaurantId}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error('Failed to fetch restaurant data');
        }
        return response.json();
      })
      .then((data) => {
        setRestaurant(data);
        console.log('Success:', restaurant);
      })
      .catch(error => {
        console.error('Error fetching restaurant data:', error);
      });
  }, [restaurantId]);

  return (
    <PageLayout>
      <div className="container pt-5">
        <div className="row gap-4">
          <RestaurantDetails {...restaurant} />
          <Reserve {...restaurant} />
          <RestaurantReviews {...{ restaurant }} />
        </div>
      </div>
    </PageLayout>
  );
}

export default Restaurant;
