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


    const login = (id) => {
        setIsAuthenticated(true);
        setUserId(id);
    };

    const logout = () => {
        setIsAuthenticated(false);
        setUserId(null); // Clear userId on logout
      };
console.log(userId)
    return (
        <AuthContext.Provider value={{ isAuthenticated, login, logout,userId }}>
            {children}
        </AuthContext.Provider>
    );
};
