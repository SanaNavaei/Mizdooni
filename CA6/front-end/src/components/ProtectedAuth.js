import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

function ProtectedAuth({ children }) {
  const navigate = useNavigate();

  const isAuthenticated = localStorage.getItem('token');

  useEffect(() => {
    if (isAuthenticated) {
      navigate('/');
    }
  }, []);

  return children;
}

export default ProtectedAuth;
