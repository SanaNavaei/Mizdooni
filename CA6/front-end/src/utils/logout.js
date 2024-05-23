import { useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';

function useLogout(toastMessage) {
  const navigate = useNavigate();

  const handleLogout = (expired = true) => {
    if (expired) {
      toast.info('Session expired. Please login again');
    }
    else {
      toast.info('You have been logged out')
    }
    localStorage.clear();
    navigate('/login');
  };

  return handleLogout;
}

export { useLogout };
