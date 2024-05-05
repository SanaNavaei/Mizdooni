import { useState, useEffect } from 'react';

import PageLayout from "components/PageLayout";
import Logout from 'components/Logout';
import ManagerRestaurants from "components/ManagerRestaurants";

import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.min.js';
import 'assets/stylesheets/global.css';
import 'assets/stylesheets/manager_restaurants.css';

function Manager() {
  const [restaurants, setRestaurants] = useState([]);
  const id = localStorage.getItem('id');

  useEffect(() => {
    document.title = 'Manager Restaurants';
  }, []);

  useEffect(() => {
    fetch(`/api/restaurants/manager/${id}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
    })
      .then(async (response) => {
        if (response.ok) {
          return response.json();
        } else {
          const resp = await response.json();
          throw new Error(resp.message);
        }
      })
      .then((data) => {
        console.log('Data:', data);
        setRestaurants(data.data);
      })
      .catch((error) => {
        console.error('Error getting restaurants:', error.message);
      });
  }, [id]);

  return (
    <PageLayout>
      <div className="container pt-4">
        <Logout />
        <ManagerRestaurants restaurants={restaurants} />
      </div>
    </PageLayout>
  );
}

export default Manager;
