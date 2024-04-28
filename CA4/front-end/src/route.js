import Home from './pages/Home';
import SearchResult from './pages/SearchResult';
import Error from './pages/errors/Error';

const routeArray = [
  {
    path: "/",
    element: <Home />,
    errorElement: <Error />,
  },
  {
    path: "/restaurants",
    element: <SearchResult />,
  }
]

export default routeArray;
