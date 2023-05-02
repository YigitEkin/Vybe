/* eslint-disable react/jsx-filename-extension */
import { Navigate, useRoutes } from 'react-router-dom';
// layouts
import DashboardLayout from './layouts/dashboard';
import SimpleLayout from './layouts/simple';
//
import BlogPage from './pages/BlogPage';
import UserPage from './pages/UserPage';
import LoginPage from './pages/LoginPage';
import Page404 from './pages/Page404';
import ProductsPage from './pages/ProductsPage';
import DashboardAppPage from './pages/DashboardAppPage';
import useAuthStore from './stores/auth';

// ----------------------------------------------------------------------

export default function Router() {
  const { isLoggedIn } = useAuthStore((state) => state.isLoggedIn);
  const routesArray = isLoggedIn
    ? [
        {
          path: '/dashboard',
          element: <DashboardLayout />,
          children: [
            { element: <Navigate to="/dashboard/app" />, index: true },
            { path: 'app', element: <DashboardAppPage /> },
            { path: 'user', element: <UserPage /> },
            { path: 'products', element: <ProductsPage /> },
            { path: 'blog', element: <BlogPage /> },
          ],
        },
        {
          element: <SimpleLayout />,
          children: [
            { element: <Navigate to="/dashboard/app" />, index: true },
            { path: '404', element: <Page404 /> },
            { path: '*', element: <Navigate to="/404" /> },
          ],
        },
      ]
    : [
        {
          path: '/',
          element: <LoginPage />,
        },
      ];

  const routes = useRoutes(routesArray);

  return routes;
}
