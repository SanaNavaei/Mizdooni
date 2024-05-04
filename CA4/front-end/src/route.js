import Home from 'pages/Home';
import SearchResult from 'pages/SearchResult';
import Restaurant from 'pages/Restaurant';
import Login from 'pages/Login';
import Signup from 'pages/Signup';
import Customer from 'pages/Customer';
import Manage from 'pages/Manage';
import Manager from 'pages/Manager';
import Error from 'pages/errors/Error';

const routeArray = [
  {
    path: '/',
    element: <Home />,
    errorElement: <Error />,
  },
  {
    path: '/restaurants/:restaurantName',
    element: <SearchResult />,
  },
  {
    path: '/restaurants/:id',
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
  },
  {
    path: "/manage/:id",
    element: <Manage />,
  },
  {
    path: "/manager",
    element: <Manager />,
  }
];

export default routeArray;
