import { Link } from 'react-router-dom';

import Logo from 'assets/images/logo.png'
import HeaderInfo from './HeaderInfo';

function Header() {
  return (
    <header className="sticky-top">
      <nav id="navbar" className="navbar navbar-expand bg-white">
        <div className="container-fluid mx-1 ms-sm-4 me-sm-5">
          <div className="d-flex align-items-center">
            <Link to="/" className="navbar-brand">
              <img src={Logo} height="70" width="87" alt="Mizdooni" />
            </Link>
            <p className="miz-text-red fs-5 m-0 d-none d-md-block">Reserve Table From Anywhere!</p>
          </div>
          <HeaderInfo />
        </div>
      </nav>
    </header>
  );
}

export default Header;
