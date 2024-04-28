import Logo from '../assets/images/logo.png'

function Header({ button, user}) {
  return (
    <header class="sticky-top">
      <nav id="navbar" class="navbar navbar-expand bg-white">
        <div class="container-fluid mx-1 ms-sm-4 me-sm-5">
          <div class="d-flex align-items-center">
            <a class="navbar-brand" href="#">
              <img src={Logo} height="70" width="87" alt="Mizdooni" />
            </a>
            <p class="miz-text-red fs-5 m-0 d-none d-md-block">Reserve Table From Anywhere!</p>
          </div>
          <div class="d-flex align-items-center">
            {user && (
              <p class="m-0 pe-1 pe-sm-3 text-center">Welcome, {user}!</p>
            )}
            <button class="rounded-3 border-0 text-white">{button}</button>
          </div>
        </div>
      </nav>
    </header>
  )
}

export default Header;
