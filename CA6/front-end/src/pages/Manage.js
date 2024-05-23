import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';

import PageLayout from 'components/PageLayout';
import RestaurantReservations from 'components/RestaurantReservations';
import RestaurantTables from 'components/RestaurantTables';
import { useLogout } from 'utils/logout';

import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.min.js';
import 'assets/stylesheets/global.css';
import 'assets/stylesheets/manager_manage.css';

function Manage() {
  useEffect(() => { document.title = 'Manager Manage'; }, []);
  const logout = useLogout();

  const restaurantId = useParams().id;
  const [tableNumber, setTableNumber] = useState(0);
  const [restaurantInfo, setRestaurantInfo] = useState({
    address: {
      street: '',
      city: '',
      country: '',
    },
    name: '',
  });

  useEffect(() => {
    const fetchRestaurant = async () => {
      const response = await fetch(`/api/restaurants/${restaurantId}`);
      if (response.ok) {
        const body = await response.json();
        setRestaurantInfo(body.data);
      } else if (response.status === 401) {
        logout();
      } else {
        console.error('Error fetching restaurant');
      }
    }
    fetchRestaurant();
  }, [restaurantId]);

  const beforeMain = (
    <section id="restaurant_manage" className="container-fluid">
      <div className="d-flex justify-content-between align-items-center flex-wrap py-2 ps-3 pe-3 pe-sm-5">
        <h2 className="miz-text-red-light m-0 fw-normal fs-5">{restaurantInfo.name}</h2>
        <p className="miz-text-red-light m-0">{`Address: ${restaurantInfo.address.street}, ${restaurantInfo.address.city}, ${restaurantInfo.address.country}`}</p>
      </div>
    </section>
  );
  return (
    <PageLayout beforeMain={beforeMain} footerMargin={0} mainId="main_manage">
      <div className="container-fluid h-100">
        <div className="row h-100 flex-column-reverse">
          <RestaurantReservations restaurantId={restaurantId} tableNumber={tableNumber} />
          <RestaurantTables restaurantId={restaurantId} setTableNumber={setTableNumber} />
        </div>
      </div>
    </PageLayout>
  );
}

export default Manage;
