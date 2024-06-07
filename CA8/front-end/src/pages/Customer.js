import { useEffect, useState } from 'react';
import { toast } from 'react-toastify';

import PageLayout from 'components/PageLayout';
import UserInfo from 'components/UserInfo';
import CustomerReserves from 'components/CustomerReserves';
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

  const reloadReservations = async () => {
    const response = await fetch(process.env.REACT_APP_API_URL + `/api/reserves/customer/${id}`, {
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
      toast.error('Error fetching customer reservations');
    }
  }

  useEffect(() => {
    reloadReservations();
  }, [id]);

  return (
    <PageLayout>
      <div className="container pt-4">
        <UserInfo />
        <CustomerReserves reserves={reservations} reloadReserves={reloadReservations} />
      </div>
    </PageLayout>
  );
}

export default Customer;
