import React, { createContext, useState, useContext } from 'react';

// Create a context to store authentication state
const AuthContext = createContext();

// Custom hook to use auth context
export const useAuth = () => {
    return useContext(AuthContext);
};

// AuthProvider component to wrap the app and provide the auth context
export const AuthProvider = ({ children }) => {
    const [isAuthenticated, setIsAuthenticated] = useState(false);
    const [userId, setUserId] = useState(null);
    const [zid, setZid] = useState(null);
    const [token,setToken]=useState(null)

    const login = (id,zid,token) => {
        console.log("login invoked with id:", id, "zid:", zid,'token:',token);
        setIsAuthenticated(true);
        setUserId(id);
        setZid(zid);
        setToken(token);
        sessionStorage.setItem('zid', zid);
        sessionStorage.setItem('token', token);
        // console.log(zid)
    };

    const logout = () => {
        setIsAuthenticated(false);
        setUserId(null);
        setZid(null);
        sessionStorage.removeItem('zid');
        sessionStorage.removeItem('token');
      };
// console.log(userId)
    return (
        <AuthContext.Provider value={{ isAuthenticated, login, logout,userId ,zid, setZid,token  }}>
            {children}
        </AuthContext.Provider>
    );
};
