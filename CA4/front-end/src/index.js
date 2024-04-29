import React from 'react';
import ReactDOM from 'react-dom/client';
import { RouterProvider, createBrowserRouter } from "react-router-dom";

import routeArray from './route';

import 'bootstrap/dist/css/bootstrap.min.css';
import './assets/stylesheets/global.css';

const router = createBrowserRouter(routeArray);

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);
