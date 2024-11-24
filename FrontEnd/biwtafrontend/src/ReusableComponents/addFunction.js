import { handleApiRequest } from "../utility/handleApiRequest";

export const addFunction=async(
    data,endpoint,method
) =>{
    console.log("inside add function",endpoint)
    try {
       
        await handleApiRequest({
            endpoint,
            data,
            method: method,
        });
    } catch (error) {
        console.error("Unexpected error:", error);
    }
}