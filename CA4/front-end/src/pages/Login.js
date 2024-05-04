import { useEffect, useState } from 'react';

import AuthenticationHeader from 'components/AuthenticationHeader';
import FormItem from 'components/FormItem';
import { useAuthContext } from 'components/AuthProvider';

import 'bootstrap/dist/css/bootstrap.min.css';
import 'assets/stylesheets/global.css';
import 'assets/stylesheets/authentication.css';

function Login() {
  useEffect(() => {
    document.title = 'Login';
  }, []);

  const user = useAuthContext().user;

  const [formData, setFormData] = useState({
    username: '',
    password: '',
  });
  const [error, setError] = useState('');

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch('/api/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData),
      });
      if (response.ok) {
        window.location.href = '/customer';
        user.setUser(formData.username);
      } else {
        console.log('Failed to login');
        setError('Invalid username or password');
      }
    } catch (err) {
      console.error(err);
    }
  }

  return (
    <div className="min-vh-100 d-flex flex-column justify-content-center background-auth">
      <main className="container-fluid px-0 py-4">
        <div id="container" className="mx-auto px-4 px-sm-5 rounded-4">
          <AuthenticationHeader text="Welcome Back!" />
          <form className="px-4 px-sm-5 py-4" onSubmit={handleSubmit}>
            <FormItem label="Username" type="text" name="username" value={formData.username} onChange={handleInputChange}/>
            <FormItem label="Password" type="password" name="password" value={formData.password} onChange={handleInputChange}/>
            <button type="submit" className="miz-button w-100 mt-4 mb-3">Login</button>
            {error && <p className="miz-text-red text-center fw-bold">{error}</p>}
            <p className="bottom-text text-center">Don't have an account? <a href="/signup" className="miz-text-red text-decoration-none">Sign up here</a></p>
          </form>
        </div>
      </main>
    </div>
  );
}

export default Login;
