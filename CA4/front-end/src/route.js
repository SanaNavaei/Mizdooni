import Home from 'pages/Home';
import SearchResult from 'pages/SearchResult';
import Restaurant from 'pages/Restaurant';
import Login from 'pages/Login';
import Signup from 'pages/Signup';
import Customer from 'pages/Customer';
import Error from 'pages/errors/Error';

const routeArray = [
  {
    path: '/',
    element: <Home />,
    errorElement: <Error />,
  },
  {
    path: '/restaurants',
    element: <SearchResult />,
  },
  {
    path: '/restaurant',
    element: <Restaurant />
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
    element: <Customer />,
  }
];

export default routeArray;
