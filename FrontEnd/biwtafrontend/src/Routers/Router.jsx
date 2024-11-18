import { createBrowserRouter } from "react-router-dom";
import Main from "../Layout/Main"
import Home from "../pages/Home";
import Login from "../Login/Login";
import About from "../pages/About";
import Contact from "../Pages/Contact";



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
                path: "login",
                element: <Login />
            },
            {
                path: "about",
                element: <About />
            },
            {
                path: "contact",
                element: <Contact />
            }

        ]
    }
])

export default Router