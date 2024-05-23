import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

function ProtectedRoute({ children }) {
  const navigate = useNavigate();

  const isAuthenticated = localStorage.getItem('token');

  useEffect(() => {
    if (!isAuthenticated) {
      navigate('/login');
    }
  }, []);

  return children;
}

export default ProtectedRoute;
