import { useEffect, useState } from 'react';
import { toast } from 'react-toastify';
import { Link, useNavigate } from 'react-router-dom';
import 'react-toastify/dist/ReactToastify.css';

import AuthenticationHeader from 'components/AuthenticationHeader';
import FormItem from 'components/FormItem';

import 'bootstrap/dist/css/bootstrap.min.css';
import 'assets/stylesheets/global.css';
import 'assets/stylesheets/authentication.css';

function Signup() {
  useEffect(() => { document.title = 'Signup'; }, []);
  const navigate = useNavigate();

  const [userError, setUserError] = useState('');
  const [emailError, setEmailError] = useState('');
  const [isFormValid, setIsFormValid] = useState(false);
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

  useEffect(() => {
    const usernameValid = formData.username.trim() !== '' && userError === '';
    const passwordValid = formData.password.trim() !== '';
    const emailValid = formData.email.trim() !== '' && emailError === '';
    const countryValid = formData.address.country.trim() !== '';
    const cityValid = formData.address.city.trim() !== '';
    const roleValid = formData.role !== '';
    setIsFormValid(usernameValid && passwordValid && emailValid && countryValid && cityValid && roleValid);
  }, [formData]);

  const validateUsername = async () => {
    const response = await fetch(`/api/validate/username?data=${formData.username}`);
    if (response.ok) {
      setUserError('');
    } else {
      const data = await response.json();
      setUserError(data.message);
    }
  };

  const validateEmail = async () => {
    const response = await fetch(`/api/validate/email?data=${formData.email}`);
    if (response.ok) {
      setEmailError('');
    } else {
      const data = await response.json();
      setEmailError(data.message);
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
      setFormData({ ...formData, [name]: value });
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const response = await fetch('/api/signup', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(formData),
    });

    if (response.ok) {
      let res = await response.json();
      localStorage.setItem('token', res.token);
      localStorage.setItem('id', res.data.id);
      localStorage.setItem('username', res.data.username);
      localStorage.setItem('email', res.data.email);
      localStorage.setItem('role', res.data.role);
      localStorage.setItem('country', res.data.address.country);
      localStorage.setItem('city', res.data.address.city);

      if (res.data.role === 'manager') {
        navigate('/manager');
      } else {
        navigate('/customer');
      }
    } else {
      toast.error('Failed to Signup!', {
        position: 'top-right',
        autoClose: 3000,
      });
    }
  };

  return (
    <div className="min-vh-100 d-flex flex-column justify-content-center background-auth">
      <main className="container-fluid px-0 py-4">
        <div id="container" className="mx-auto px-4 px-sm-5 rounded-4">
          <AuthenticationHeader text="Welcome to Mizdooni!" />
          <div className="px-3 px-sm-4 px-md-3 py-3" >
            <form onSubmit={handleSubmit}>
              <FormItem label="Username" type="text" name="username" value={formData.username} onChange={handleInputChange} onBlur={validateUsername} error={userError} />
              <FormItem label="Password" type="password" name="password" value={formData.password} onChange={handleInputChange} />
              <FormItem label="Email" type="email" name="email" value={formData.email} onChange={handleInputChange} onBlur={validateEmail} error={emailError} />
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
              <button type="submit" className="miz-button disabled-button w-100 mb-3" disabled={!isFormValid}>Signup</button>
            </form>
            <p className="bottom-text text-center">Already have an account? <Link to="/login" className="miz-text-red text-decoration-none">Login here</Link></p>
          </div>
        </div>
      </main>
    </div>
  );
}

export default Signup;
