import Logo from '../assets/images/logo.png'

function HomeSearch() {
  return (
    <div id="main-search">
      <div className="container h-100">
        <div className="row h-100">
          <div className="col col-xl-7 col-xxl-6 d-flex flex-column justify-content-center align-items-center">
            <img src={Logo} alt="Mizdooni" height="200" width="248" />
            <form id="search-form" className="d-flex flex-wrap w-100 mt-3 mb-5">
              <select className="form-select flex-grow-1" name="location">
                <option value="" selected>Location</option>
                <option value="Tehran">Tehran</option>
                <option value="Karaj">Karaj</option>
                <option value="Sari">Sari</option>
              </select>
              <select className="form-select flex-grow-1" name="type">
                <option value="" selected>Restaurant</option>
                <option value="Fast Food">Fast Food</option>
                <option value="Sea Food">Sea Food</option>
                <option value="Healthy Food">Healthy Food</option>
              </select>
              <div className="flex-break d-md-none pt-3"></div>
              <input className="form-control" type="text" name="name" placeholder="Type Restaurant..." />
              <button className="miz-button" type="submit">Search</button>
            </form>
          </div>
          <div className="col col-xl-5 col-xxl-6 d-none d-xl-block"></div>
        </div>
      </div>
    </div>
  )
}

export default HomeSearch;
