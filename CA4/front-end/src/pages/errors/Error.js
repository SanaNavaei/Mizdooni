import { useRouteError } from 'react-router-dom';

import Tree from 'assets/images/errors/tree.png';

import 'assets/stylesheets/error.css';

function Error() {
  const error = useRouteError();

  return (
    <div className="min-vh-100 d-flex flex-column justify-content-center background">
      <main className="container">
        <div className="row align-items-center py-2">
          <div className="col-md text-center text-md-start">
            <h1 className="pe-2 pe-md-0">:(</h1>
            <h2>Error!</h2>
            <p className="mb-4">{error.statusText || error.message}</p>
            <a className="miz-button text-decoration-none" href="./home.html">Home Page</a>
          </div>
          <div className="col-md d-none d-md-flex justify-content-end">
            <img className="img-fluid" src={Tree} alt="Tree" height="460" width="300" />
          </div>
        </div>
      </main>
    </div>
  );
}

export default Error;
