import { handleApiRequest } from "../utility/handleApiRequest";

export const addFunction=async(
    data,endpoint,method,onSuccess
) =>{
   
    try {
       
        await handleApiRequest({
            endpoint,
            data,
            method: method,
            onSuccess: (response) => {
                if (onSuccess) onSuccess(response.data); // Pass the response data to the callback
            },
        });
    } catch (error) {
        console.error("Unexpected error:", error);
    }
}