import Home from './pages/Home';
import Error from './pages/errors/Error';

const routeArray = [
  {
    path: "/",
    element: <Home />,
    errorElement: <Error />,
  }
]

export default routeArray;
