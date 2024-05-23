import React, { useState, useEffect } from 'react';
import { createSearchParams, useNavigate } from 'react-router-dom';

import Logo from 'assets/images/logo.png'
import { useLogout } from 'utils/logout';

function HomeSearch() {
  const [locations, setLocations] = useState({
    Canada: [],
    Germany: [],
    Japan: [],
    US: [],
  });
  const countries = ['Canada', 'Germany', 'Japan', 'US'];
  const [CanadaData, setCanadaData] = useState([]);
  const [GermanyData, setGermanyData] = useState([]);
  const [JapanData, setJapanData] = useState([]);
  const [USData, setUSData] = useState([]);
  const logout = useLogout();

  useEffect(() => {
    setLocations({
      Canada: CanadaData,
      Germany: GermanyData,
      Japan: JapanData,
      US: USData,
    });
  }, [CanadaData, GermanyData, JapanData, USData]);

  const navigate = useNavigate();
  const [restaurantTypes, setRestaurantTypes] = useState([]);

  const [formData, setFormData] = useState({
    location: '',
    type: '',
    name: ''
  });

  useEffect(() => {
    const fetchRestaurantLocations = async () => {
      const response = await fetch('/api/restaurants/locations');
      if (response.ok) {
        const body = await response.json();
        setCanadaData(body.data.Canada);
        setGermanyData(body.data.Germany);
        setJapanData(body.data.Japan);
        setUSData(body.data.US);
      } else if (response.status === 401) {
        logout();
      } else {
        console.error('Failed to fetch restaurant locations');
      }
    }

    const fetchRestaurantTypes = async () => {
      const response = await fetch('/api/restaurants/types');
      if (response.ok) {
        const body = await response.json();
        setRestaurantTypes(body.data);
      } else if (response.status === 401) {
        logout();
      } else {
        console.error('Failed to fetch restaurant types');
      }
    }

    fetchRestaurantLocations();
    fetchRestaurantTypes();
  }, []);
  const params = {};

  useEffect(() => {
    if (formData.location) {
      params.location = formData.location;
    }
    if (formData.type) {
      params.type = formData.type;
    }
    if (formData.name) {
      params.name = formData.name;
    }
  }, [formData]);

  const changePage = () => {
    navigate({
      pathname: '/restaurants',
      search: `?${createSearchParams(params)}`,
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
                  for (let i = 0; i < countries.length; i++) {
                    const country = countries[i];
                    const cities = locations[country] || [];
                    const cityOptions = [];
                    for (let j = 0; j < cities.length; j++) {
                      const city = cities[j];
                      cityOptions.push(<option key={city} value={city}>{city}</option>);
                    }
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
