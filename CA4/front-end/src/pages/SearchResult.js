import { useEffect, useState } from 'react';

import PageLayout from 'components/PageLayout';
import Cards from 'components/Cards';

import 'bootstrap/dist/css/bootstrap.min.css';
import 'assets/stylesheets/global.css';
import 'assets/stylesheets/cards.css';

function SearchResult({ restaurantName }) {
  const [restaurants, setRestaurants] = useState([]);

  useEffect(() => {
    document.title = 'Search Result';
  }, []);

  useEffect(() => {
    fetch('/api/restaurants', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error('Failed to fetch restaurants');
        }
        return response.json();
      })
      .then((data) => {
        setRestaurants(data);
        console.log('Success:', data);
      })
      .catch(error => {
        console.error('Error fetching restaurants:', error);
      });
  }, [restaurantName]);


  return (
    <PageLayout>
      <Cards topText={`Results for #${restaurantName}`} restaurants={restaurants} />
    </PageLayout>
  );
}

export default SearchResult;
