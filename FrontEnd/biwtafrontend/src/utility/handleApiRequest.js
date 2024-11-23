import axios from 'axios';
import Swal from 'sweetalert2';
import axiosInstance from '../Middleware/AxiosInstance';


export const handleApiRequest = async ({ 
    endpoint, 
    method = 'POST',
    data = {},
    params = {},
    headers = {},
    onSuccess,
    onError,
    onValidationError 
}) => {
    try {
        // Prepare the request configuration
        const config = {
            method: method.toUpperCase(), // Ensure the method is uppercase
            url: endpoint,
            headers,
            params, // Only for GET, DELETE
            data, // Only for POST, PUT, PATCH
        };

        // Make the API request using axiosInstance
        const response = await axiosInstance(config);

        // Handle success response
        if (onSuccess) onSuccess(response);

        Swal.fire('Success!', 'Operation completed successfully', 'success');
        
    } catch (error) {
        // Handle error
        if (error.response && error.response.status === 400) {
            const errorMessages = error.response.data;
            if (onValidationError) onValidationError(errorMessages);

            Swal.fire({
                icon: 'error',
                title: 'Validation Errors',
                html: errorMessages,
                confirmButtonText: 'Okay',
            });
        } else {
            const errorMessages = error.message;

            Swal.fire({
                icon: 'error',
                title: error.code || 'Error',
                html: errorMessages,
                confirmButtonText: 'Okay',
            });

            // Call onError callback if available
            if (onError) onError(error);
        }
    }
};
