import { createBrowserRouter } from "react-router-dom";
import Main from "../Layout/Main";
import Home from "../Pages/Home";
import Store from "../Pages/Store";
import Personalinfo from "../Pages/Personalinfo";
// import PersonalInfo from "../Pages/PersonalInfo";
// import Prescription from "../Pages/Prescription";
// import Product from "../Pages/Product";
// import Dashboard from "../Pages/Dashboard";

const Router = createBrowserRouter([
    {
        path: "/",
        element: <Main />,
        children: [
            {
                path: "/",
                element: <Home />
            },
            {
                path: "/administrations/master/store",
                element: <Store />
            },
            {
                path: "/personalinfo",
                element: <Personalinfo />
            },
            // {
            //     path: "/personalinfo",
            //     element: <PersonalInfo />
            // },
            // {
            //     path: "/prescription",
            //     element: <Prescription />
            // },
            // {
            //     path: "/product",
            //     element: <Product />
            // },
            // {
            //     path: "/dashboard",
            //     element: <Dashboard />
            // }
        ]
    }
]);

export default Router;
