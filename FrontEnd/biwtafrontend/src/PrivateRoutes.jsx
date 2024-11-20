import React from 'react';
import { Navigate } from 'react-router-dom';
import { useAuth } from '../AuthContext';

const PrivateRoutes = ({ children }) => {
    const { userId } = useAuth();

    if (!userId) {
        // If userId is not present, redirect to login
        return <Navigate to="/login" />;
    }

    // If userId exists, render the protected component
    return children;
};

export default PrivateRoutes;
