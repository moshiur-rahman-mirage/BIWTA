import { createBrowserRouter, Navigate } from "react-router-dom";
import Main from "../Layout/Main";
import Home from "../Pages/Home";
import { useAuth } from "../Provider/AuthProvider";
import ErrorPage from "../ErrorPage";
import Department from "../Pages/Xcodes/Department";
import Designation from "../Pages/Xcodes/Designation";
import Section from "../Pages/Xcodes/Section";
import ItemGroup from "../Pages/ItemGroup/ItemGroup";
import Pdmsthrd from "../Pages/Personalinfo/Pdmsthrd";
import Login from "../Pages/Login/Login";
import Store from "../Pages/Xcodes/Store";
import Salutation from "../Pages/Xcodes/Salutation";
import PrivateRoutes from "../PrivateRoutes";
import JobLocation from "../Pages/Xcodes/JobLocation";
import BloodGroup from "../Pages/Xcodes/BloodGroup";
import MaritalStatus from "../Pages/Xcodes/MaritalStatus";
import EmpType from "../Pages/Xcodes/EmpType";
import JobTitle from "../Pages/Xcodes/JobTitle";
import Religion from "../Pages/Xcodes/Religion";
import StoreType from "../Pages/Xcodes/StoreType";


// Protected Route wrapper


// Define the routes
const Router = createBrowserRouter([
    {
        path: "/",
        element: <Login />, // Login is the default route
    },
    {
        path: "/main",
        element: <PrivateRoutes element={<Main />} />,
        children: [
            {
                path: "/main/home",
                element: <PrivateRoutes element={<Home />} />,
            },
            {
                path: "/main/personalinfo",
                element: <PrivateRoutes element={<Pdmsthrd />} />,
            },
            {
                path: "/main/administrations/codes/store",
                element: <PrivateRoutes element={<Store />} />,
            },
            {
                path: "/main/administrations/codes/department",
                element: <PrivateRoutes element={<Department />} />,
            },
            {
                path: "/main/administrations/codes/salutation",
                element: <PrivateRoutes element={<Salutation />} />,
            },
            {
                path: "/main/administrations/codes/designation",
                element: <PrivateRoutes element={<Designation />} />,
            },
            {
                path: "/main/administrations/codes/section",
                element: <PrivateRoutes element={<Section />} />,
            },
            {
                path: "/main/administrations/codes/itemgroup",
                element: <PrivateRoutes element={<ItemGroup />} />,
            },
            {
                path: "/main/administrations/codes/joblocation",
                element: <PrivateRoutes element={<JobLocation />} />,
            },
            {
                path: "/main/administrations/codes/bloodgroup",
                element: <PrivateRoutes element={<BloodGroup />} />,
            },
            {
                path: "/main/administrations/codes/maritalstatus",
                element: <PrivateRoutes element={<MaritalStatus />} />,
            },
            {
                path: "/main/administrations/codes/emptype",
                element: <PrivateRoutes element={<EmpType />} />,
            },
            {
                path: "/main/administrations/codes/jobtitle",
                element: <PrivateRoutes element={<JobTitle />} />,
            },
            {
                path: "/main/administrations/codes/religion",
                element: <PrivateRoutes element={<Religion />} />,
            },
            {
                path: "/main/administrations/codes/storetype",
                element: <PrivateRoutes element={<StoreType />} />,
            },
        ],
    },
    {
        path: "*", // Catch-all for unmatched routes
        element: <ErrorPage />,
    },
]);

export default Router;
