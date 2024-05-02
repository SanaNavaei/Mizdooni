import { useEffect } from 'react';

import PageLayout from 'components/PageLayout';
import Logout from 'components/Logout';
import CustomerReserve from 'components/CustomerReserve';

import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.min.js';
import 'assets/stylesheets/global.css';
import 'assets/stylesheets/customer.css';

const user = {
  username: "Parna",
  email: "Tom_holland@ut.ac.ir",
  country: "Iran",
  city: "Tehran"
}

const reserves = [
  {
    date: "2024-06-22 16:00",
    restaurant: "Ali Daei Dizy",
    table: "12",
    seats: "4",
    isCancel: false,
    isPastTime: false
  },
  {
    date: "2024-02-22 16:00",
    restaurant: "Ali Daei Dizy",
    table: "12",
    seats: "4",
    isCancel: false,
    isPastTime: true
  },
  {
    date: "2024-02-22 16:00",
    restaurant: "Ali Daei Dizy",
    table: "12",
    seats: "4",
    isCancel: false,
    isPastTime: true
  },
  {
    date: "2024-02-22 16:00",
    restaurant: "Ali Daei Dizy",
    table: "12",
    seats: "4",
    isCancel: true,
    isPastTime: true
  }
]

function Customer() {
  useEffect(() => {
    document.title = 'Customer';
  }, []);

  return (
    <PageLayout>
      <div class="container pt-4">
        <Logout email={user.email} country={user.country} city={user.city} />
        <CustomerReserve reserves={reserves} />
      </div>
    </PageLayout>
  );
}

export default Customer;
