import Logo from '../assets/images/logo.png'

function HomeSearch() {
  return (
    <div id="main-search">
      <div class="container h-100">
        <div class="row h-100">
          <div class="col col-xl-7 col-xxl-6 d-flex flex-column justify-content-center align-items-center">
            <img src={Logo} alt="Mizdooni" height="200" width="248" />
            <form id="search-form" class="d-flex flex-wrap w-100 mt-3 mb-5">
              <select class="form-select flex-grow-1" name="location">
                <option value="" selected>Location</option>
                <option value="Tehran">Tehran</option>
                <option value="Karaj">Karaj</option>
                <option value="Sari">Sari</option>
              </select>
              <select class="form-select flex-grow-1" name="type">
                <option value="" selected>Restaurant</option>
                <option value="Fast Food">Fast Food</option>
                <option value="Sea Food">Sea Food</option>
                <option value="Healthy Food">Healthy Food</option>
              </select>
              <div class="flex-break d-md-none pt-3"></div>
              <input class="form-control" type="text" name="name" placeholder="Type Restaurant..." />
              <button class="miz-button" type="submit">Search</button>
            </form>
          </div>
          <div class="col col-xl-5 col-xxl-6 d-none d-xl-block"></div>
        </div>
      </div>
    </div>
  )
}

export default HomeSearch;
