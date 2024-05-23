import { useState, useEffect } from 'react';

import PageLayout from 'components/PageLayout';
import UserInfo from 'components/UserInfo';
import ManagerRestaurants from 'components/ManagerRestaurants';
import { useLogout } from 'utils/logout';

import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.min.js';
import 'assets/stylesheets/global.css';
import 'assets/stylesheets/manager_restaurants.css';

function Manager() {
  useEffect(() => { document.title = 'Manager Restaurants'; }, []);
  const logout = useLogout();

  const [restaurants, setRestaurants] = useState([]);
  const id = localStorage.getItem('id');

  const reloadRestaurants = async () => {
    const response = await fetch(`/api/restaurants/manager/${id}`);
    if (response.ok) {
      const body = await response.json();
      setRestaurants(body.data);
    } else if (response.status === 401) {
      logout();
    } else {
      console.error('Error fetching restaurants');
    }
  }

  useEffect(() => {
    reloadRestaurants();
  }, [id]);

  return (
    <PageLayout>
      <div className="container pt-4">
        <UserInfo />
        <ManagerRestaurants restaurants={restaurants} reloadRestaurants={reloadRestaurants} />
      </div>
    </PageLayout>
  );
}

export default Manager;
