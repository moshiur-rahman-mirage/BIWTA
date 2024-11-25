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
        console.log("Data received by handleApiRequest:", data);
        const config = {
            method: method.toUpperCase(), // Ensure the method is uppercase
            url: endpoint,
            headers,
            params, // Only for GET, DELETE
            data, // Only for POST, PUT, PATCH
        };

        console.log("Axios config before sending:", endpoint);

        // Make the API request using axiosInstance
        const response = await axiosInstance(config);

        // Handle success response
        if (onSuccess) onSuccess(response);

        // Display success SweetAlert with custom z-index
        Swal.fire({
            icon: 'success',
            title: 'Success!',
            text: 'Operation completed successfully',
            // Set z-index here to ensure it appears above other modals
        });
        
    } catch (error) {
        // Handle error
        if (error.response && error.response.status === 400) {
            const errorMessages = error.response.data;

            if (typeof errorMessages === 'object') {
                console.log(errorMessages)
                let formattedErrors = '';
                for (const field in errorMessages) {
                    
                    formattedErrors += `${field}: ${errorMessages[field].join(', ')}<br>`;
                }
                if (onValidationError) onValidationError(formattedErrors);
                
                Swal.fire({
                    icon: 'error',
                    title: 'Validation Errors',
                    html: formattedErrors,
                    confirmButtonText: 'Okay',
                     // Set z-index here to ensure it appears above other modals
                });
            } else {
                Swal.fire({
                    icon: 'error',
                    title: 'Validation Errors',
                    text: errorMessages,
                    confirmButtonText: 'Okay',
                     // Set z-index here to ensure it appears above other modals
                });
            }
        } else {
            const errorMessages = error.message;

            if (error.code === 'ECONNABORTED') {
                Swal.fire({
                    icon: 'error',
                    title: 'Network Error',
                    text: 'The request took too long to respond. Please try again later.',
                    confirmButtonText: 'Okay',
                     // Set z-index here to ensure it appears above other modals
                });
            } else if (error.response && error.response.status >= 500) {
                Swal.fire({
                    icon: 'error',
                    title: 'Server Error',
                    text: 'There was an issue with the server. Please try again later.',
                    confirmButtonText: 'Okay',
                     // Set z-index here to ensure it appears above other modals
                });
            } else {
                Swal.fire({
                    icon: 'error',
                    title: error.code || 'Error',
                    html: errorMessages,
                    confirmButtonText: 'Okay',
                     // Set z-index here to ensure it appears above other modals
                });
            }

            if (onError) onError(error);
        }
    }
};
