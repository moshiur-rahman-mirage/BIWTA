
import axiosInstance from '../Middleware/AxiosInstance';


export const fetchSearchResults = async (query, api, fieldConfig) => {
    if (!query) return []; // Return empty results if no query

    try {
        const url = api.replace('{query}', query); // Replace query placeholder in the API if needed
        const response = await axiosInstance.get(url);
        console.log(response)
        // Dynamically map the response data to the field configuration
        const filteredResults = response.data.map((item) => {
            const result = {};
            fieldConfig.forEach(({ field }) => {
                result[field] = item[field];
            });
            return result;
        });

        return filteredResults;
    } catch (error) {
        console.error('Error fetching search results:', error);
        throw error; // Re-throw to handle in the calling component
    }
};
