import { useRouteError } from "react-router-dom";

import "../../assets/stylesheets/error.css";
import Tree from "../../assets/images/errors/tree.png";

function Error() {
  const { error } = useRouteError();
  console.error(error);

  return (
    <div class="min-vh-100 d-flex flex-column justify-content-center">
      <main class="container">
        <div class="row align-items-center py-2">
          <div class="col-md text-center text-md-start">
            <h1 class="pe-2 pe-md-0">:(</h1>
            <h2>Error!</h2>
            <p class="mb-4">{error.statusText || error.message}</p>
            <a class="miz-button text-decoration-none" href="./home.html">Home Page</a>
          </div>
          <div class="col-md d-none d-md-flex justify-content-end">
            <img class="img-fluid" src={Tree} alt="Tree" height="460" width="300" />
          </div>
        </div>
      </main>
    </div>
  );
}

export default Error;
