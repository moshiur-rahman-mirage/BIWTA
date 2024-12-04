
import { useAuth } from './Provider/AuthProvider';
import LoadingPage from './Pages/Loading/Loading';
 // Make sure you're importing the auth context

const PrivateRoutes = ({ element }) => {
    const { zid,token,loading,logout } = useAuth();  
    
    console.log(zid,token,loading)
   
    if (loading) {
        return <LoadingPage />; // Show loading while authentication data is being fetched
    }
    
    // if (!zid || !token) {
    //     logout();
    // }
    
    return element;

     
};

export default PrivateRoutes;
