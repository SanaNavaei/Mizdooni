import { useEffect } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { toast } from 'react-toastify';

function OAuthCallback() {
  useEffect(() => { document.title = 'Redirecting...'; }, []);
  const navigate = useNavigate();

  const [searchParams, setSearchParams] = useSearchParams();

  const handleOAuthCallback = async () => {
    const response = await fetch('/api/login/google', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ code: searchParams.get('code') }),
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
        navigate('/manager')
      } else {
        navigate('/customer')
      }
    } else {
      toast.error('Google Authentication Failed', {
        position: 'top-right',
        autoClose: 3000,
      });
      navigate('/login');
    }
  }

  useEffect(() => {
    handleOAuthCallback();
  }, []);

  return (
    <div className="mt-5 ms-5">
      <h1 className="fs-4">Redirecting . . .</h1>
    </div>
  );
}

export default OAuthCallback;
