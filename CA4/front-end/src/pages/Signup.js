import { useEffect, useState } from 'react';

import AuthenticationHeader from 'components/AuthenticationHeader';
import FormItem from 'components/FormItem';

import 'bootstrap/dist/css/bootstrap.min.css';
import 'assets/stylesheets/global.css';
import 'assets/stylesheets/authentication.css';

function Signup() {
  useEffect(() => {
    document.title = 'Signup';
  }, []);

  const [userError, setUserError] = useState('');
  const [emailError, setEmailError] = useState('');

  const [formData, setFormData] = useState({
    username: '',
    password: '',
    email: '',
    address: {
      country: '',
      city: '',
    },
    role: '',
  });

  const validateUsername = async(value) => {
    try {
      const response = await fetch(`/validate/username?date=${value}`);
      if (response.ok) {
        setFormData({ ...formData, username: value });
      } else {
        const data = await response.json();
        setFormData({ ...formData, username: ''});
        setUserError(data.message);
      }
    } catch (error) {
      console.error('Error validating username:', error);
    }
  };

  const validateEmail = async(value) => {
    try {
      const response = await fetch(`/validate/email?date=${value}`);
      if (response.ok) {
        setFormData({ ...formData, email: value });
      } else {
        const data = await response.json();
        setFormData({ ...formData, email: ''});
        setEmailError(data.message);
      }
    } catch (error) {
      console.error('Error validating email:', error);
    }
  };

  const handleInputChange = (e) => {
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
    } else {
      if (name === 'username') {
        validateUsername(value);
      } else if (name === 'email') {
        validateEmail(value);
      } else {
        setFormData({ ...formData, [name]: value });
      }
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch('/signup', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData),
      });
      if (response.ok) {
        if (formData.role === 'client') {
          window.location.href = '/cusotmer';
        } else if (formData.role === 'manager') {
          window.location.href = '/manager';
        }
      } else {
        console.log('Failed to signup');
      }
    } catch (error) {
      console.log(error.message)
    }
  };

  return (
    <div className="min-vh-100 d-flex flex-column justify-content-center background-auth">
      <main className="container-fluid px-0 py-4">
        <div id="container" className="mx-auto px-4 px-sm-5 rounded-4">
          <AuthenticationHeader text="Welcome to Mizdooni!" />
          <form className="px-3 px-sm-4 px-md-3 py-3" onSubmit={handleSubmit}>
            <FormItem label="Username" type="text" name="username" value={formData.username} onChange={handleInputChange} error={userError}/>
            <FormItem label="Password" type="password" name="password" value={formData.password} onChange={handleInputChange} />
            <FormItem label="Email" type="email" name="email" value={formData.email} onChange={handleInputChange} error={emailError}/>
            <FormItem label="Country" type="text" name="address.country" value={formData.address.country} onChange={handleInputChange} />
            <FormItem label="City" type="text" name="address.city" value={formData.address.city} onChange={handleInputChange} />
            <div className="mb-5">
              <label htmlFor="role" className="miz-text-red form-label pe-2">Role:</label>
              <select className="form-select" id="role" name="role" value={formData.role} onChange={handleInputChange} required>
                <option value="" disabled hidden>Choose a Role</option>
                <option value="client">Client</option>
                <option value="manager">Manager</option>
              </select>
            </div>
            <button type="submit" className="miz-button w-100 mb-3">Signup</button>
            <p className="bottom-text text-center">Already have an account? <a href="/login" className="miz-text-red text-decoration-none">Login here</a></p>
          </form>
        </div>
      </main>
    </div>
  );
}

export default Signup;
