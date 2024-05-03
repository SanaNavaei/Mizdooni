import React, { useState, useEffect } from 'react';

import Logo from 'assets/images/logo.png'

function HomeSearch() {
  const [locations, setLocations] = useState([]);
  const [restaurantTypes, setRestaurantTypes] = useState([]);

  const [formData, setFormData] = useState({
    location: '',
    type: '',
    name: ''
  });

  useEffect(() => {
    fetch('/api/locations')
      .then(response => response.json())
      .then(data => setLocations(data))
      .catch(error => console.error('Error fetching locations:', error));

    fetch('/api/restaurantTypes')
      .then(response => response.json())
      .then(data => setRestaurantTypes(data))
      .catch(error => console.error('Error fetching restaurant types:', error));
  }, []);

  const handleSubmit = (e) => {
    e.preventDefault();
    let { location, type, name } = formData;
    let queryParams = new URLSearchParams({ location, type, name });
    let url = '/api/restaurants?' + queryParams;
    let restaurantName = "";
    if (name !== "") {
      restaurantName += name + ",";
    } else if (type !== "") {
      restaurantName += type + ",";
    } else if (location !== "") {
      restaurantName += location + ",";
    }

    fetch(url)
      .then(response => {
        if (!response.ok) {
          throw new Error('Failed to search for restaurantNames');
        }
        return response.json();
      })
      .then(data => {
        console.log('Search results:', data);
        window.location.href = `/restaurants/${restaurantName}`;
      })
      .catch(error => {
        console.error('Error searching for restaurants:', error);
      });
  };

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  return (
    <div id="main-search">
      <div className="container h-100">
        <div className="row h-100">
          <div className="col col-xl-7 col-xxl-6 d-flex flex-column justify-content-center align-items-center">
            <img src={Logo} alt="Mizdooni" height="200" width="248" />
            <form id="search-form" className="d-flex flex-wrap w-100 mt-3 mb-5" onSubmit={handleSubmit}>
              <select className="form-select flex-grow-1" name="location" value={formData.location} onChange={handleChange}>
                <option value="" selected>Location</option>
                {locations.map(location => (
                  <option key={location.id} value={location.name}>{location.name}</option>
                ))}
              </select>
              <select className="form-select flex-grow-1" name="type" value={formData.type} onChange={handleChange}>
                <option value="" selected>Restaurant</option>
                {restaurantTypes.map(type => (
                  <option key={type.id} value={type.name}>{type.name}</option>
                ))}
              </select>
              <div className="flex-break d-md-none pt-3"></div>
              <input className="form-control" type="text" name="name" value={formData.name} onChange={handleChange} placeholder="Type Restaurant..." />
              <button className="miz-button disabled-button" type="submit" disabled={!formData.location && !formData.type && !formData.name}>Search</button>
            </form>
          </div>
          <div className="col col-xl-5 col-xxl-6 d-none d-xl-block"></div>
        </div>
      </div>
    </div>
  );
}

export default HomeSearch;
