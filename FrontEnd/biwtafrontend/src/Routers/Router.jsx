import { createBrowserRouter } from "react-router-dom";
import Main from "../Layout/Main";
import Home from "../Pages/Home";
import { useAuth } from "../Provider/AuthProvider";
import ErrorPage from "../ErrorPage";
import Login from "../Pages/Login/Login";
import PrivateRoutes from "../PrivateRoutes";

// Admin Codes Pages
import Department from "../Pages/Xcodes/Department";
import Designation from "../Pages/Xcodes/Designation";
import Section from "../Pages/Xcodes/Section";
import Store from "../Pages/Xcodes/Store";
import Salutation from "../Pages/Xcodes/Salutation";
import JobLocation from "../Pages/Xcodes/JobLocation";
import BloodGroup from "../Pages/Xcodes/BloodGroup";
import MaritalStatus from "../Pages/Xcodes/MaritalStatus";
import EmpType from "../Pages/Xcodes/EmpType";
import JobTitle from "../Pages/Xcodes/JobTitle";
import Religion from "../Pages/Xcodes/Religion";
import StoreType from "../Pages/Xcodes/StoreType";
import Gender from "../Pages/Xcodes/Gender";
import Relation from "../Pages/Xcodes/Relation";

// Other Pages
import ItemGroup from "../Pages/ItemGroup/ItemGroup";
import Pdmsthrd from "../Pages/Personalinfo/Pdmsthrd";
import Supplier from "../Pages/Suppliers/supplier";
import PaymentType from "../Pages/Xcodes/PaymentType";

// Define the routes
const Router = createBrowserRouter([
    // Default Route (Login)
    {
        path: "/",
        element: <Login />,
    },

    // Main Layout with protected routes
    {
        path: "/main",
        element: <PrivateRoutes element={<Main />} />,
        children: [
            {
                path: "home",
                element: <Home />,
            },
            {
                path: "personalinfo",
                element: <Pdmsthrd />,
            },

            // Administration Codes Group
            {
                path: "administrations/codes",
                children: [
                    { path: "store", element: <Store /> },
                    { path: "department", element: <Department /> },
                    { path: "salutation", element: <Salutation /> },
                    { path: "designation", element: <Designation /> },
                    { path: "section", element: <Section /> },
                    { path: "itemgroup", element: <ItemGroup /> },
                    { path: "joblocation", element: <JobLocation /> },
                    { path: "bloodgroup", element: <BloodGroup /> },
                    { path: "maritalstatus", element: <MaritalStatus /> },
                    { path: "emptype", element: <EmpType /> },
                    { path: "jobtitle", element: <JobTitle /> },
                    { path: "religion", element: <Religion /> },
                    { path: "storetype", element: <StoreType /> },
                    { path: "gender", element: <Gender /> },
                    { path: "relation", element: <Relation /> },
                    { path: "paymenttype", element: <PaymentType /> },
                ],
            },
            {
                path: "administrations/master",
                children: [
                    { path: "supplier", element: <Supplier /> },
                   
                ],
            },
        ],
    },

    // Catch-all for unmatched routes
    {
        path: "*",
        element: <ErrorPage />,
    },
]);

export default Router;
