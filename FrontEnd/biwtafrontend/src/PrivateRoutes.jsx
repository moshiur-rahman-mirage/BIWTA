import React from 'react';
import { Navigate } from 'react-router-dom';
import { useAuth } from './Provider/AuthProvider';
import LoadingPage from './Pages/Loading/Loading';
 // Make sure you're importing the auth context

const PrivateRoutes = ({ element }) => {
    const { zid,token } = useAuth();  

   
    if (!zid || !token) {
        return <LoadingPage/>
    }

    
        return element;
    

     
};

export default PrivateRoutes;
