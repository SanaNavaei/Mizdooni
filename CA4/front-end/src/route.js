import Home from 'pages/Home';
import SearchResult from 'pages/SearchResult';
import Restaurant from 'pages/Restaurant';
import Login from 'pages/Login';
import Signup from 'pages/Signup';
import Customer from 'pages/Customer';
import Manage from 'pages/Manage';
import Manager from 'pages/Manager';
import Error from 'pages/errors/Error';
import ProtectedRoute from 'components/ProtectedRoute';

const routeArray = [
  {
    path: '/',
    element: <ProtectedRoute><Home /></ProtectedRoute>,
    errorElement: <Error />,
  },
  {
    path: '/restaurants',
    element: <ProtectedRoute><SearchResult /></ProtectedRoute>,
  },
  {
    path: '/restaurants/:id',
    element: <ProtectedRoute><Restaurant /></ProtectedRoute>,
  },
  {
    path: '/login',
    element: <Login />,
  },
  {
    path: '/signup',
    element: <Signup />,
  },
  {
    path: "/customer",
    element: <ProtectedRoute><Customer /></ProtectedRoute>,
  },
  {
    path: "/manage/:id",
    element: <ProtectedRoute><Manage /></ProtectedRoute>,
  },
  {
    path: "/manager",
    element: <ProtectedRoute><Manager /></ProtectedRoute>,
  }
];

export default routeArray;
