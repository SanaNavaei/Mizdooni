import { useEffect } from 'react';

import PageLayout from "components/PageLayout";
import Logout from 'components/Logout';
import ManagerRestaurants from "components/ManagerRestaurants";

import 'bootstrap/dist/css/bootstrap.min.css';
import 'assets/stylesheets/global.css';
import 'assets/stylesheets/manager_manage.css';

const managerEmail = "Tom_Holland@ut.ac.ir";
const restaurants = [
  {
    name: "Ali Daei Dizy",
    location: "Boshehr, Iran",
  },
  {
    name: "Ali Daei Dizy",
    location: "Boshehr, Iran",
  }
];

function Manager() {
  useEffect(() => {
    document.title = 'Manager Restaurants';
  }, []);

  return (
    <PageLayout>
      <div class="container pt-4">
        <Logout email={managerEmail} />
        <ManagerRestaurants restaurants={restaurants} />
      </div>
    </PageLayout>
  );
}

export default Manager;
