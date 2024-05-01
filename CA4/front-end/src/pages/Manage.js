import PageLayout from 'components/PageLayout';
import RestaurantReservations from 'components/RestaurantReservations';
import RestaurantTables from 'components/RestaurantTables';

import 'bootstrap/dist/css/bootstrap.min.css';
import 'assets/stylesheets/global.css';
import 'assets/stylesheets/manager_manage.css';

const reservations = [
  { time: '17:00 Jul 24th', bookedBy: 'Tom Holland', table: 2, cancelled: false },
  { time: '17:00 Jul 24th', bookedBy: 'Tom Holland', table: 3, cancelled: false },
  { time: '17:00 Jul 24th', bookedBy: 'Tom Holland', table: 5, cancelled: true },
  { time: '17:00 Jul 24th', bookedBy: 'Tom Holland', table: 8, cancelled: true },
  { time: '17:00 Jul 24th', bookedBy: 'Tom Holland', table: 10, cancelled: true },
  { time: '17:00 Jul 24th', bookedBy: 'Tom Holland', table: 15, cancelled: true },
  { time: '17:00 Jul 24th', bookedBy: 'Tom Holland', table: 1, cancelled: true }
];

const tables = [
  { number: 1, seats: 4 },
  { number: 2, seats: 4 },
  { number: 3, seats: 4 },
  { number: 4, seats: 4 },
  { number: 5, seats: 4 },
  { number: 6, seats: 4 },
  { number: 7, seats: 4 },
  { number: 8, seats: 4 },
  { number: 9, seats: 6 },
  { number: 10, seats: 4 },
  { number: 11, seats: 4 },
  { number: 12, seats: 4 },
  { number: 13, seats: 6 },
  { number: 14, seats: 6 },
  { number: 15, seats: 8 },
  { number: 16, seats: 10 }
];

const restaurantInfo = {
  name: 'Ali Daei Dizy',
  country: 'Iran',
  city: 'Boshehr',
  street: 'Vali-e-Asr Square',
};

function Manage() {
  const beforeMain = (
    <section id="restaurant" className="container-fluid">
      <div className="d-flex justify-content-between align-items-center flex-wrap py-2 ps-3 pe-3 pe-sm-5">
        <h2 className="miz-text-red-light m-0 fw-normal fs-5">{restaurantInfo.name}</h2>
        <p className="miz-text-red-light m-0">{`Address: ${restaurantInfo.street}, ${restaurantInfo.city}, ${restaurantInfo.country}`}</p>
      </div>
    </section>
  );
  return (
    <PageLayout beforeMain={beforeMain} footerMargin={0}>
      <div className="container-fluid h-100">
        <div className="row h-100 flex-column-reverse">
          <RestaurantReservations reservations={reservations}/>
          <RestaurantTables tables={[]}/>
        </div>
      </div>
    </PageLayout>
  );
}

export default Manage;
