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
  const [restaurants, setRestaurants] = useState([]);

  useEffect(() => {
    document.title = 'Home';
  }, []);

  useEffect(() => {
    fetch('/api/restaurants?page=1&sort=rate', {
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
        console.log('Success:', data);
        setRestaurants(data.data.pageList);
      })
      .catch(error => {
        console.error('Error fetching restaurants:', error);
      });
  }, []);

  return (
    <PageLayout>
      <HomeSearch />
      <Cards topText="Top Restaurants in Mizdooni" restaurants={restaurants} />
      <About />
    </PageLayout>
  );
}

export default Home;
