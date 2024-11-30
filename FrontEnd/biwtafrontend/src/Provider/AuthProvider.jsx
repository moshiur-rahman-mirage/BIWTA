import { createContext, useContext, useEffect, useState } from "react";
import axios from "axios";
import axiosInstance from "../Middleware/AxiosInstance";

// Create Context for Authentication
export const AuthContext = createContext(null);

export const useAuth = () => {
    return useContext(AuthContext);
};

export const AuthProvider = ({ children }) => {
    const [authState, setAuthState] = useState({
        zid: null,
        zemail: null,
    });

    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);
    const [token, setToken] = useState(null);
    const [zid, setZid] = useState(null);
    const [zemail, setZemail] = useState(null);
    const [xname,setXname]=useState(null);
    const [zorg,setZorg]=useState(null);

    // Function to handle user login (using JWT)
    const login = (id, zid, token,xname,zorg) => {
       

        // Set state using passed arguments
        setAuthState({ zid, zemail: id,xname:xname,zorg:zorg });
        setToken(token);
        setZid(zid);
        setZemail(id);
        setZorg(zorg);
        setXname(xname);
        console.log(zorg)
        // Store in localStorage
        localStorage.setItem("zid", zid);
        localStorage.setItem("zemail", id);
        localStorage.setItem("token", token);
        localStorage.setItem("xname", xname);
        localStorage.setItem("zorg",zorg)

       
    };

    // Function to fetch user data after login
    const fetchUserData = async (token) => {
        try {
            const response = await axios.get(
                `api/pdmst/search?zid=${authState.zid}&xstaff=${authState.zemail}`,
                {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                }
            );
            setUser(response.data);
        } catch (error) {
            console.error("Error fetching user data:", error);
        }
    };

    // Function to handle user logout
    const logout = () => {
        setLoading(true);
        localStorage.removeItem("token");
        localStorage.removeItem("zid");
        localStorage.removeItem("zemail");

        setAuthState({ zid: null, zemail: null });
        setToken(null);
        setUser(null);
        setLoading(false);

      
    };

    // Validate user session and token on load
    useEffect(() => {
        const storedZid = localStorage.getItem("zid");
        const storedZemail = localStorage.getItem("zemail");
        const storedToken = localStorage.getItem("token");

        if (storedZid && storedZemail && storedToken) {
            setAuthState({ zid: storedZid, zemail: storedZemail });
            setToken(storedToken);
        } else {
            setAuthState({ zid: null, zemail: null });
        }
    }, []);

    useEffect(() => {
        const validateUser = async () => {
            try {
                const response = await axiosInstance.get("/auth/validate", {
                    headers: { Authorization: `Bearer ${token}` },
                });
             
                if(localStorage.getItem('zid')!=response.data.zid || localStorage.getItem('zemail')!=response.data.zemail ){
                    logout();
                }
            } catch (error) {
                console.error("User validation failed:", error);
                // setAuthState({ zid: null, zemail: null });
                localStorage.clear();
                logout();
            }
        };

        if (token && zid && zemail) {
            validateUser();
        }
    }, [token, zid, zemail]);



    useEffect(() => {
        const handleStorageChange = () => {
            const storedToken = localStorage.getItem("token");
            const storedZid = localStorage.getItem("zid");
            const storedZemail = localStorage.getItem("zemail");

            if (!storedToken || !storedZid || !storedZemail) {
                logout(); // Logout if any critical value is missing
            } 
            // else {
            //     setToken(storedToken);
            //     setZid(storedZid);
            //     setZemail(storedZemail);
            // }
        };

        window.addEventListener("storage", handleStorageChange);

        return () => {
            window.removeEventListener("storage", handleStorageChange);
        };
    }, []);


    const authInfo = {
        login,
        logout,
        zemail: authState.zemail,
        zid: authState.zid,
        token,
        loading,
        user,
        setAuthState,
        fetchUserData,
        xname:authState.xname,
        zorg:authState.zorg
    };

    return (
        <AuthContext.Provider value={authInfo}>
            {children}
        </AuthContext.Provider>
    );
};
