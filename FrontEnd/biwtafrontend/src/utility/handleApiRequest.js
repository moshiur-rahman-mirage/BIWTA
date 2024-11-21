import axios from 'axios';
import Swal from 'sweetalert2';

export const handleApiRequest = async ({ 
    endpoint, 
    method='POST',
    data={},
    params={},
    headers={}, 
    onSuccess, 
    onError, 
    onValidationError 
}) => {
    try {
        const response = await axios({
            url: endpoint,
            method: method.toUpperCase(), // Ensure the method is uppercase
            data, // Data for POST, PUT, PATCH
            params, // Query parameters for GET, DELETE
            headers, // Custom headers
        });
        
        if (onSuccess) onSuccess(response);
        
        Swal.fire('Success!', 'Operation completed successfully', 'success');
    } catch (error) {
        // console.log(error)
        if (error.response && error.response.status === 400) {
            const errorMessages = error.response.data;
            if (onValidationError) onValidationError(errorMessages);

            Swal.fire({
                icon: 'error',
                title: 'Validation Errors',
                html: errorMessages,
                confirmButtonText: 'Okay',
            });
        } else  {
            const errorMessages=error.message

            Swal.fire({
                icon: 'error',
                title: error.code,
                html: errorMessages,
                confirmButtonText: 'Okay',
            });
            // onError(error);
        }
    }
};
