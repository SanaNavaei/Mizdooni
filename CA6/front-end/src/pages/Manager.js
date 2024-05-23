import { useState, useEffect } from 'react';

import PageLayout from "components/PageLayout";
import Logout from 'components/Logout';
import ManagerRestaurants from "components/ManagerRestaurants";

import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.min.js';
import 'assets/stylesheets/global.css';
import 'assets/stylesheets/manager_restaurants.css';

function Manager() {
  useEffect(() => { document.title = 'Manager Restaurants'; }, []);

  const [restaurants, setRestaurants] = useState([]);
  const id = localStorage.getItem('id');

  const reloadRestaurants = () => {
    fetch(`/api/restaurants/manager/${id}`)
      .then(async response => {
        if (response.ok) {
          return response.json();
        }
        const resp = await response.json();
        throw new Error(resp.message);
      })
      .then(data => {
        setRestaurants(data.data);
      })
      .catch(error => {
        console.error('Error getting restaurants:', error.message);
      });
  };

  useEffect(() => {
    reloadRestaurants();
  }, [id]);

  return (
    <PageLayout>
      <div className="container pt-4">
        <Logout />
        <ManagerRestaurants restaurants={restaurants} reloadRestaurants={reloadRestaurants} />
      </div>
    </PageLayout>
  );
}

export default Manager;
