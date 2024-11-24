import React, { useState, useEffect } from 'react';
import { FormControl, InputLabel, Select, MenuItem, CircularProgress } from '@mui/material';
import axiosInstance from '../Middleware/AxiosInstance';
import { useAuth } from '../Provider/AuthProvider';

const XcodesDropDown = ({ 
    variant = "outlined", 
    value, // Value passed down from parent
    label, 
    type, 
    onSelect, 
    defaultValue = '' 
}) => {
    const { zid } = useAuth();
    const [options, setOptions] = useState([]); // Store options fetched from API
    const [loading, setLoading] = useState(false); // Show loading indicator
console.log(value)
    // Fetch options dynamically based on type
    useEffect(() => {
        const fetchOptions = async () => {
            setLoading(true);
            try {
                const response = await axiosInstance.get(`api/xcodes/search?zid=${zid}&xtype=${type}`);
                const data = response.data || [];
                setOptions(data); // Update state with API response
            } catch (error) {
                console.error(`Error fetching ${type} options:`, error);
            } finally {
                setLoading(false); // Hide loading indicator
            }
        };

        if (type) fetchOptions(); // Fetch only if type is provided
    }, [type, zid]); // Add zid and type as dependencies

    // Update selected value when `value` prop changes
    useEffect(() => {
        if (value !== undefined) {
            // Ensure the `value` prop is applied correctly
            setSelectedValue(value);
        }
    }, [value]);

    const [selectedValue, setSelectedValue] = useState(defaultValue); // Track selected value

    // Handle selection change
    const handleChange = (event) => {
        const newValue = event.target.value;
        setSelectedValue(newValue); // Update selected value
        if (onSelect) onSelect(newValue); // Call parent callback
    };

    return (
        <FormControl fullWidth size="small" variant={variant}>
            <InputLabel>{label}</InputLabel>
            {loading ? (
                <CircularProgress size={24} sx={{ margin: 'auto' }} />
            ) : (
                <Select
                    value={selectedValue || ''} // Use selectedValue for the dropdown value
                    onChange={handleChange}
                    label={label}
                    displayEmpty
                >
                    <MenuItem value="">
                        
                    </MenuItem>
                    {options.map((option, index) => (
                        <MenuItem key={index} value={option.value || option.xcode}>
                            {option.xcode}
                        </MenuItem>
                    ))}
                </Select>
            )}
        </FormControl>
    );
};

export default XcodesDropDown;
