import { useState, useEffect } from 'react';

import PageLayout from 'components/PageLayout';
import HomeSearch from 'components/HomeSearch';
import Cards from 'components/Cards';
import About from 'components/About';
import { useLogout } from 'utils/logout';

import 'bootstrap/dist/css/bootstrap.min.css';
import 'assets/stylesheets/global.css';
import 'assets/stylesheets/cards.css';
import 'assets/stylesheets/home.css';

function Home() {
  useEffect(() => { document.title = 'Home'; }, []);
  const logout = useLogout();

  const [restaurantsTop, setRestaurantsTop] = useState([]);
  const [restaurantsLike, setRestaurantsLike] = useState([]);

  const fetchRestaurants = async (query) => {
    const response = await fetch('/api/restaurants?' + query);
    if (response.ok) {
      const body = await response.json();
      return body;
    } else if (response.status === 401) {
      logout();
    } else {
      console.error('Error fetching restaurants');
    }
  }

  useEffect(() => {
    const queryTop = new URLSearchParams({ page: 1, sort: 'rating' });
    const queryLike = new URLSearchParams({ page: 1, sort: 'rating', location: localStorage.getItem('city') });
    const fetchLists = async () => {
      const bodyTop = await fetchRestaurants(queryTop);
      setRestaurantsTop(bodyTop.data.pageList.slice(0, 6));
      const bodyLike = await fetchRestaurants(queryLike);
      setRestaurantsLike(bodyLike.data.pageList.slice(0, 6));
    }
    fetchLists();
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
