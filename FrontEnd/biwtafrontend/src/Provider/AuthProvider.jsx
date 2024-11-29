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

    // Function to handle user login (using JWT)
    const login = (id, zid, token) => {
       

        // Set state using passed arguments
        setAuthState({ zid, zemail: id });
        setToken(token);
        setZid(zid);
        setZemail(id);
        // Store in sessionStorage
        sessionStorage.setItem("zid", zid);
        sessionStorage.setItem("zemail", id);
        sessionStorage.setItem("token", token);

       
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
        sessionStorage.removeItem("token");
        sessionStorage.removeItem("zid");
        sessionStorage.removeItem("zemail");

        setAuthState({ zid: null, zemail: null });
        setToken(null);
        setUser(null);
        setLoading(false);

      
    };

    // Validate user session and token on load
    useEffect(() => {
        const storedZid = sessionStorage.getItem("zid");
        const storedZemail = sessionStorage.getItem("zemail");
        const storedToken = sessionStorage.getItem("token");

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
             
                if(sessionStorage.getItem('zid')!=response.data.zid || sessionStorage.getItem('zemail')!=response.data.zemail ){
                    logout();
                }
            } catch (error) {
                console.error("User validation failed:", error);
                // setAuthState({ zid: null, zemail: null });
                sessionStorage.clear();
                logout();
            }
        };

        if (token && zid && zemail) {
            validateUser();
        }
    }, [token, zid, zemail]);



    useEffect(() => {
        const handleStorageChange = () => {
            const storedToken = sessionStorage.getItem("token");
            const storedZid = sessionStorage.getItem("zid");
            const storedZemail = sessionStorage.getItem("zemail");

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
        fetchUserData
    };

    return (
        <AuthContext.Provider value={authInfo}>
            {children}
        </AuthContext.Provider>
    );
};
