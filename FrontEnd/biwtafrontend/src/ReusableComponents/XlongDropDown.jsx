import React, { useState, useEffect } from 'react';
import { FormControl, InputLabel, Select, MenuItem, CircularProgress } from '@mui/material';
import axiosInstance from '../Middleware/AxiosInstance';
import { useAuth } from '../Provider/AuthProvider';

const XlongDropDown = ({
    variant = "outlined",
    value, // Value passed down from parent
    label,
    type,
    onSelect,
    withXlong,
    defaultValue = '',
    fontSize = '0.875rem', // Default font size for options
    captionSize = '0.875rem', // Default font size for the label
}) => {
    const { zid } = useAuth();
    const [options, setOptions] = useState([]); // Store options fetched from API
    const [loading, setLoading] = useState(false); // Show loading indicator
    const [selectedValue, setSelectedValue] = useState(defaultValue); // Track selected value

    // Fetch options dynamically based on type
    useEffect(() => {
        const fetchOptions = async () => {
            setLoading(true);
            try {
                const response = await axiosInstance.get(`api/xcodes/search?zid=${zid}&xtype=${type}`);
                console.log(response)
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
            setSelectedValue(value); // Ensure the `value` prop is applied correctly
        }
    }, [value]);

    // Handle selection change
    const handleChange = (event) => {
        // const newValue = event.target.value;
        // setSelectedValue(newValue); // Update selected value
        // if (onSelect) onSelect(newValue); // Call parent callback
        const selectedXcode = event.target.value;
        const selectedOption = options.find(option => option.xcode === selectedXcode);
        setSelectedValue(selectedXcode); // Update selected value
        if (onSelect && selectedOption) {
            onSelect({ xcode: selectedXcode, xlong: selectedOption.xlong }); // Pass both xcode and xlong
        }
    };

    return (
        <FormControl fullWidth variant={variant} size="small">
            <InputLabel
                sx={{
                    fontSize: captionSize,
                }}
            >
                {label}

            </InputLabel>
            {loading ? (
                <CircularProgress size={24} sx={{ margin: 'auto' }} />
            ) : (
                <Select
                    value={selectedValue || ''} // Use selectedValue for the dropdown value
                    onChange={handleChange}
                    label={label}
                    // displayEmpty
                    sx={{
                        '& .MuiMenuItem-root': {
                            fontSize, // Apply fontSize to menu items
                        },
                        fontSize, // Apply fontSize to selected value in dropdown
                    }}
                >
                    <MenuItem value="">
                        <span style={{ fontSize }}>

                        </span>
                    </MenuItem>
                    {options.map((option, index) => (
                        <MenuItem
                            key={index}
                            value={option.xcode} // Use xcode as the value
                            sx={{
                                fontSize, // Apply fontSize to each menu item
                            }}
                            data-xlong={option.xlong} // Attach xlong to the menu item
                        >
                            {option.xcode}  
                           
                        </MenuItem>
                    ))}
                </Select>
            )}
        </FormControl>
    );
};

export default XlongDropDown;
