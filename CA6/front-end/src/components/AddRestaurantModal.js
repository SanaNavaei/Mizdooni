import { useEffect, useState } from 'react';
import { toast } from 'react-toastify';

import { useLogout } from 'utils/logout';

const startHours = [<option key={0}></option>]
for (let i = 8; i < 18; i++) {
  const formattedHour = i.toString().padStart(2, '0');
  startHours.push(<option key={i} value={formattedHour}>{formattedHour}:00</option>);
}

const endHours = [<option key={0}></option>]
for (let i = 18; i < 24; i++) {
  const formattedHour = i.toString().padStart(2, '0');
  endHours.push(<option key={i} value={formattedHour}>{formattedHour}:00</option>);
}

function AddRestaurantModal({ reloadRestaurants }) {
  const [nameError, setNameError] = useState('');
  const [isFormFilled, setIsFormFilled] = useState(false);
  const [formData, setFormData] = useState({
    name: '',
    type: '',
    description: '',
    startTime: '',
    endTime: '',
    address: {
      country: '',
      city: '',
      street: ''
    },
  });
  const logout = useLogout();

  const handleChange = (e) => {
    const { name, value } = e.target;
    if (name.startsWith('address.')) {
      const [parentKey, childKey] = name.split('.');
      setFormData({
        ...formData,
        [parentKey]: {
          ...formData[parentKey],
          [childKey]: value,
        },
      });
    }
    else if (name === 'startTime' || name === 'endTime') {
      setFormData({
        ...formData,
        [name]: value + ':00',
      });
    }
    else {
      setFormData({ ...formData, [name]: value });
    }
  };

  const validateName = async () => {
    const response = await fetch(`/api/validate/restaurant-name?data=${formData.name}`);
    if (response.ok) {
      setNameError('');
    } else {
      const body = await response.json();
      setNameError(body.message);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const response = await fetch('/api/restaurants', {
      method: 'POST',
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`,
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(formData),
    });
    if (response.ok) {
      setNameError('');
      reloadRestaurants();
      toast.success('Restaurant added successfully');
    } else if (response.status === 401) {
      logout();
    } else {
      const body = await response.json();
      toast.error(body.message);
    }
  };

  useEffect(() => {
    const isNameValid = formData.name.trim() !== '' && nameError === '';
    const isTypeValid = formData.type.trim() !== '';
    const isCountryValid = formData.address.country.trim() !== '';
    const isCityValid = formData.address.city.trim() !== '';
    const isStreetValid = formData.address.street.trim() !== '';
    setIsFormFilled(isNameValid && isTypeValid && isCountryValid && isCityValid && isStreetValid);
  }, [formData, nameError]);

  return (
    <div className="modal fade" id="modal-add-restaurant">
      <div className="modal-dialog">
        <div className="modal-content">
          <div className="modal-header">
            <h4 className="modal-title">Add Restaurant</h4>
            <button type="button" className="btn-close border border-dark rounded-circle me-1" data-bs-dismiss="modal"></button>
          </div>
          <div className="modal-body" id="modal-restaurant">
            <form onSubmit={handleSubmit}>
              <div className="my-3 d-flex justify-content-between">
                <label htmlFor="name" className="form-label">Name</label>
                <input type="text" className="form-control w-50" id="name" name="name" value={formData.name} onChange={handleChange} onBlur={validateName} required />
                {nameError && <p className="miz-text-red py-1 fw-bold">{nameError}</p>}
              </div>
              <div className="mb-3 d-flex justify-content-between">
                <label htmlFor="type" className="form-label">Type</label>
                <input type="text" className="form-control w-50" id="type" name="type" value={formData.type} onChange={handleChange} required />
              </div>
              <div className="mb-3">
                <label htmlFor="description" className="form-label">Description</label>
                <textarea className="form-control" rows="5" name="description" onChange={handleChange} placeholder="Type about your restaurant..."></textarea>
              </div>
              <div className="mb-3 d-flex justify-content-between">
                <label htmlFor="country" className="form-label">Country</label>
                <input type="text" className="form-control w-50" id="country" name="address.country" value={formData.address.country} onChange={handleChange} required />
              </div>
              <div className="mb-3 d-flex justify-content-between">
                <label htmlFor="city" className="form-label">City</label>
                <input type="text" className="form-control w-50" id="city" name="address.city" value={formData.address.city} onChange={handleChange} required />
              </div>
              <div className="mb-3 d-flex justify-content-between">
                <label htmlFor="street" className="form-label">Street</label>
                <input type="text" className="form-control w-50" id="street" name="address.street" value={formData.address.street} onChange={handleChange} required />
              </div>
              <div className="d-flex justify-content-between mb-3">
                <label htmlFor="startHour">Start Hour</label>
                <select className="form-select mx-1 w-50" name="startTime" id="startHour" onChange={handleChange}>{startHours}</select>
              </div>
              <div className="d-flex justify-content-between mb-5">
                <label htmlFor="endHour">End Hour</label>
                <select className="form-select mx-1 w-50" name="endTime" id="endHour" onChange={handleChange}>{endHours}</select>
              </div>
              <button type="submit" className="miz-button disabled-button w-100 mb-3" data-bs-dismiss="modal" disabled={!isFormFilled}>Add</button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}

export default AddRestaurantModal;
