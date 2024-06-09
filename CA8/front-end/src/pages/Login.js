import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { toast } from 'react-toastify';

import AuthenticationHeader from 'components/AuthenticationHeader';
import FormItem from 'components/FormItem';
import { useLogin } from 'utils/login';

import 'bootstrap/dist/css/bootstrap.min.css';
import 'assets/stylesheets/global.css';
import 'assets/stylesheets/authentication.css';

const googleOAuthLink = 'https://accounts.google.com/o/oauth2/auth?' + new URLSearchParams({
  client_id: '899573287578-4u3cgovpkipi13v3t5c75jurjg8jfttd.apps.googleusercontent.com',
  response_type: 'code',
  scope: 'profile email',
  redirect_uri: 'http://localhost:3000/login/google',
});

function Login() {
  useEffect(() => { document.title = 'Login'; }, []);
  const login = useLogin();

  const [formData, setFormData] = useState({
    username: '',
    password: '',
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const response = await fetch('/api/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(formData),
    });

    if (response.ok) {
      const body = await response.json();
      login(body);
    } else {
      toast.error('Invalid username or password');
    }
  }

  return (
    <div className="min-vh-100 d-flex flex-column justify-content-center background-auth">
      <main className="container-fluid px-0 py-4">
        <div id="container" className="mx-auto px-4 px-sm-5 rounded-4">
          <AuthenticationHeader text="Welcome Back!" />
          <div className="px-4 px-sm-5 py-4">
            <form onSubmit={handleSubmit}>
              <FormItem label="Username" type="text" name="username" value={formData.username} onChange={handleInputChange} />
              <FormItem label="Password" type="password" name="password" value={formData.password} onChange={handleInputChange} />
              <button type="submit" className="miz-button w-100 mt-4 mb-3">Login</button>
            </form>
            <a href={googleOAuthLink}>
              <button className="miz-button w-100 mb-3">Login with Google</button>
            </a>
            <p className="bottom-text text-center">Don't have an account? <Link to="/signup" className="miz-text-red text-decoration-none">Sign up here</Link></p>
          </div>
        </div>
      </main>
    </div>
  );
}

export default Login;
