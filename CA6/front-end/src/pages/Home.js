import { useState, useEffect } from 'react';

import PageLayout from 'components/PageLayout';
import HomeSearch from 'components/HomeSearch';
import Cards from 'components/Cards';
import About from 'components/About';

import 'bootstrap/dist/css/bootstrap.min.css';
import 'assets/stylesheets/global.css';
import 'assets/stylesheets/cards.css';
import 'assets/stylesheets/home.css';

function Home() {
  useEffect(() => { document.title = 'Home'; }, []);

  const [restaurantsTop, setRestaurantsTop] = useState([]);
  const [restaurantsLike, setRestaurantsLike] = useState([]);
  const [city, setCity] = useState(localStorage.getItem('city'));

  const fetchRestaurants = (query) => {
    return fetch('/api/restaurants?' + query)
      .then(response => {
        if (!response.ok) {
          throw new Error('Failed to fetch restaurants');
        }
        return response.json();
      }, (error) => {
        console.error('Error fetching restaurants:', error);
      });
  }

  useEffect(() => {
    const queryTop = new URLSearchParams({ page: 1, sort: 'rating' });
    const queryLike = new URLSearchParams({ page: 1, sort: 'rating', location: city });
    fetchRestaurants(queryTop).then(data => setRestaurantsTop(data.data.pageList.slice(0, 6)));
    fetchRestaurants(queryLike).then(data => setRestaurantsLike(data.data.pageList.slice(0, 6)));
  }, []);

  return (
    <PageLayout>
      <HomeSearch />
      <Cards topText="Top Restaurants in Mizdooni" restaurants={restaurantsTop} />
      {restaurantsLike.length !== 0 && (
        <Cards topText="You Might Also Like" restaurants={restaurantsLike} />
      )}
      <About />
    </PageLayout>
  );
}

export default Home;
