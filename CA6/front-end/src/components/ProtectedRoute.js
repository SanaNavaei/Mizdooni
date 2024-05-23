import { useNavigate } from 'react-router-dom';

function ProtectedRoute({ children }) {
  const navigate = useNavigate();

  const isAuthenticated = localStorage.getItem('id');

  if (!isAuthenticated) {
    navigate('/login');
  }

  return children;
}

export default ProtectedRoute;
