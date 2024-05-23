import { useEffect, useState } from 'react';

import PageLayout from 'components/PageLayout';
import Logout from 'components/Logout';
import CustomerReserve from 'components/CustomerReserve';

import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.min.js';
import 'assets/stylesheets/global.css';
import 'assets/stylesheets/customer.css';

function Customer() {
  useEffect(() => { document.title = 'Customer'; }, []);

  const id = localStorage.getItem('id');
  const [reservations, setReservations] = useState([]);

  useEffect(() => {
    fetch(`/api/reserves/customer/${id}`, {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`,
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
        setReservations(data.data);
      })
      .catch((error) => {
        console.error('Error getting reservations:', error.message);
      });
  }, [id]);

  return (
    <PageLayout>
      <div className="container pt-4">
        <Logout />
        <CustomerReserve reserves={reservations} />
      </div>
    </PageLayout>
  );
}

export default Customer;
