import { useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';

function useLogin() {
  const navigate = useNavigate();

  const handleLogin = (body) => {
    toast.info('Successfully logged in', { autoClose: 1500 });

    localStorage.setItem('token', body.token);
    localStorage.setItem('id', body.data.id);
    localStorage.setItem('username', body.data.username);
    localStorage.setItem('email', body.data.email);
    localStorage.setItem('role', body.data.role);
    localStorage.setItem('country', body.data.address.country);
    localStorage.setItem('city', body.data.address.city);

    if (body.data.role === 'manager') {
      navigate('/manager');
    } else {
      navigate('/customer');
    }
  };

  return handleLogin;
}

export { useLogin };
