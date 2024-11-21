import { createBrowserRouter, Navigate } from "react-router-dom";
import Main from "../Layout/Main";
import Login from "../Pages/Login";
import Home from "../Pages/Home";
import Store from "../Pages/Store";
import Personalinfo from "../Pages/Personalinfo";
import Dashboard from "../Pages/Dashboard";
import { useAuth } from "../Provider/AuthProvider";
import ErrorPage from "../ErrorPage";
import Department from "../Pages/Xcodes/Department";
import Designation from "../Pages/Xcodes/Designation";
import Section from "../Pages/Xcodes/Section";
import ItemGroup from "../Pages/ItemGroup/ItemGroup";


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
                path: "/main/administrations/codes/store",
                element: <ProtectedRoute element={<Store />} />,
            },
            {
                path: "/main/administrations/codes/department",
                element: <ProtectedRoute element={<Department />} />,
            },
            {
                path: "/main/administrations/codes/designation",
                element: <ProtectedRoute element={<Designation />} />,
            },
            {
                path: "/main/administrations/codes/section",
                element: <ProtectedRoute element={<Section />} />,
            },
            {
                path: "/main/administrations/codes/itemgroup",
                element: <ProtectedRoute element={<ItemGroup />} />,
            },
        ],
    },
    {
        path: "*", // Catch-all for unmatched routes
        element: <ErrorPage />,
    },
]);

export default Router;
