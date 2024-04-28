import App from './pages/App';
import Error from './pages/errors/Error'

const routeArray = [
  {
    path: "/",
    element: <App />,
    errorElement: <Error />,
  }
]

export default routeArray;
