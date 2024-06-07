import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';

import Logo from 'assets/images/logo.png'

function HomeSearch() {
  const navigate = useNavigate();
  const searchParams = {};

  const [locations, setLocations] = useState({});
  const [restaurantTypes, setRestaurantTypes] = useState([]);
  const [formData, setFormData] = useState({
    location: '',
    type: '',
    name: ''
  });

  useEffect(() => {
    if (formData.location) {
      searchParams.location = formData.location;
    }
    if (formData.type) {
      searchParams.type = formData.type;
    }
    if (formData.name) {
      searchParams.name = formData.name;
    }
  }, [formData]);

  useEffect(() => {
    const fetchRestaurantLocations = async () => {
      const response = await fetch(process.env.REACT_APP_API_URL + '/api/restaurants/locations');
      if (response.ok) {
        const body = await response.json();
        setLocations(body.data);
      } else {
        toast.error('Failed to fetch restaurant locations');
      }
    }

    const fetchRestaurantTypes = async () => {
      const response = await fetch(process.env.REACT_APP_API_URL + '/api/restaurants/types');
      if (response.ok) {
        const body = await response.json();
        setRestaurantTypes(body.data);
      } else {
        toast.error('Failed to fetch restaurant types');
      }
    }

    fetchRestaurantLocations();
    fetchRestaurantTypes();
  }, []);

  const changePage = () => {
    navigate({
      pathname: '/restaurants',
      search: new URLSearchParams(searchParams).toString(),
    })
  }

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  return (
    <div id="main-search">
      <div className="container h-100">
        <div className="row h-100">
          <div className="col col-xl-7 col-xxl-6 d-flex flex-column justify-content-center align-items-center">
            <img src={Logo} alt="Mizdooni" height="200" width="248" />
            <form id="search-form" className="d-flex flex-wrap w-100 mt-3 mb-5">
              <select className="form-select flex-grow-1" name="location" value={formData.location} onChange={handleChange}>
                <option value="">Location</option>
                {(() => {
                  const options = [];
                  for (const [country, cities] of Object.entries(locations)) {
                    const cityOptions = cities.map(city => (
                      <option key={city} value={city}>{city}</option>
                    ));
                    options.push(
                      <optgroup key={country} label={country}>
                        {cityOptions}
                      </optgroup>
                    );
                  }
                  return options;
                })()}
              </select>

              <select className="form-select flex-grow-1" name="type" value={formData.type} onChange={handleChange}>
                <option value="">Restaurant</option>
                {restaurantTypes.map(type => (
                  <option key={type} value={type}>{type}</option>
                ))}
              </select>
              <div className="flex-break d-md-none pt-3"></div>
              <input className="form-control" type="text" name="name" value={formData.name} onChange={handleChange} placeholder="Type Restaurant..." />
              <button className="miz-button disabled-button" type="submit" onClick={changePage} disabled={!formData.location && !formData.type && !formData.name}>Search</button>
            </form>
          </div>
          <div className="col col-xl-5 col-xxl-6 d-none d-xl-block"></div>
        </div>
      </div>
    </div>
  );
}

export default HomeSearch;
