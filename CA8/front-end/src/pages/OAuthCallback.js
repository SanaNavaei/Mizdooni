import { useEffect } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { toast } from 'react-toastify';

import { useLogin } from 'utils/login';

function OAuthCallback() {
  useEffect(() => { document.title = 'Redirecting...'; }, []);
  const navigate = useNavigate();
  const login = useLogin();

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
      const body = await response.json();
      login(body);
    } else {
      toast.error('Google Authentication Failed');
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
