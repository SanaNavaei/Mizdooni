import { useEffect, useState } from 'react';

import PageLayout from 'components/PageLayout';
import UserInfo from 'components/UserInfo';
import CustomerReserve from 'components/CustomerReserve';
import { useLogout } from 'utils/logout';

import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.min.js';
import 'assets/stylesheets/global.css';
import 'assets/stylesheets/customer.css';

function Customer() {
  useEffect(() => { document.title = 'Customer'; }, []);
  const logout = useLogout();

  const id = localStorage.getItem('id');
  const [reservations, setReservations] = useState([]);

  useEffect(() => {
    const fetchReservations = async () => {
      const response = await fetch(`/api/reserves/customer/${id}`, {
        method: 'GET',
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('token')}`,
        },
      });
      if (response.ok) {
        const body = await response.json();
        setReservations(body.data);
      } else if (response.status === 401) {
        logout();
      } else {
        console.error('Error fetching reservations');
      }
    }
    fetchReservations();
  }, [id]);

  return (
    <PageLayout>
      <div className="container pt-4">
        <UserInfo />
        <CustomerReserve reserves={reservations} />
      </div>
    </PageLayout>
  );
}

export default Customer;
