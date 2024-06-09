import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { toast } from 'react-toastify';

import PageLayout from 'components/PageLayout';
import RestaurantDetails from 'components/RestaurantDetails';
import Reserve from 'components/Reserve';
import RestaurantReviews from 'components/RestaurantReviews';

import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.min.js';
import 'assets/stylesheets/global.css';
import 'assets/stylesheets/restaurant.css';

function Restaurant() {
  useEffect(() => { document.title = 'Restaurant'; }, []);

  const restaurantId = useParams().id;
  const [restaurant, setRestaurant] = useState({
    id: -1,
    name: '',
    type: '',
    startTime: '',
    endTime: '',
    description: '',
    address: {
      country: '',
      city: '',
      street: '',
    },
    averageRating: {
      food: 0,
      service: 0,
      ambiance: 0,
      overall: 0,
    },
    maxSeatsNumber: 0,
    starCount: 0,
    managerUsername: '',
    image: '',
    totalReviews: 0,
  });

  useEffect(() => {
    const fetchRestaurant = async () => {
      const response = await fetch(`/api/restaurants/${restaurantId}`);
      if (response.ok) {
        const body = await response.json();
        setRestaurant(body.data);
      } else {
        toast.error('Error fetching restaurant info');
      }
    }
    fetchRestaurant();
  }, [restaurantId]);

  return (
    <PageLayout>
      <div className="container pt-5">
        <div className="row gap-4">
          <RestaurantDetails {...restaurant} />
          <Reserve {...restaurant} />
          <RestaurantReviews restaurant={restaurant} />
        </div>
      </div>
    </PageLayout>
  );
}

export default Restaurant;
