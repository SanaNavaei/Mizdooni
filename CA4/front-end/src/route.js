import Home from './pages/Home';
import SearchResult from './pages/SearchResult';
import Restaurant from './pages/Restaurant';
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
  },
  {
    path: "/restaurant",
    element: <Restaurant />
  }
]

export default routeArray;
