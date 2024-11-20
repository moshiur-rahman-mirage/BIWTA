import { createBrowserRouter, Navigate } from "react-router-dom";
import Main from "../Layout/Main";
import Login from "../Pages/Login";
import Home from "../Pages/Home";
import Store from "../Pages/Store";
import Personalinfo from "../Pages/Personalinfo";
import Dashboard from "../Pages/Dashboard";
import { useAuth } from "../Provider/AuthProvider";
import ErrorPage from "../ErrorPage";

// Protected Route wrapper
const ProtectedRoute = ({ element }) => {
    const { userId } = useAuth(); // Use useAuth hook here
    return userId ? element : <Navigate to="/" replace />;
};

// Define the routes
const Router = createBrowserRouter([
    {
        path: "/",
        element: <Login />, // Login is the default route
    },
    {
        path: "/main",
        element: <ProtectedRoute element={<Main />} />,
        children: [
            {
                path: "/main/home",
                element: <ProtectedRoute element={<Home />} />,
            },
            {
                path: "/main/dashboard",
                element: <ProtectedRoute element={<Dashboard />} />,
            },
            {
                path: "/main/administrations/master/store",
                element: <ProtectedRoute element={<Store />} />,
            },
            {
                path: "/main/personalinfo",
                element: <ProtectedRoute element={<Personalinfo />} />,
            },
        ],
    },
    {
        path: "*", // Catch-all for unmatched routes
        element: <ErrorPage />,
    },
]);

export default Router;
