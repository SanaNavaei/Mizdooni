import Logo from '../assets/images/logo.png'

function Header({ button, user}) {
  return (
    <header className="sticky-top">
      <nav id="navbar" className="navbar navbar-expand bg-white">
        <div className="container-fluid mx-1 ms-sm-4 me-sm-5">
          <div className="d-flex align-items-center">
            <a className="navbar-brand" href="/">
              <img src={Logo} height="70" width="87" alt="Mizdooni" />
            </a>
            <p className="miz-text-red fs-5 m-0 d-none d-md-block">Reserve Table From Anywhere!</p>
          </div>
          <div className="d-flex align-items-center">
            {user && (
              <p className="m-0 pe-1 pe-sm-3 text-center">Welcome, {user}!</p>
            )}
            <button className="rounded-3 border-0 text-white">{button}</button>
          </div>
        </div>
      </nav>
    </header>
  )
}

export default Header;
