import React, { useState, useEffect } from 'react';
import { FormControl, InputLabel, Select, MenuItem, CircularProgress } from '@mui/material';
import axiosInstance from '../Middleware/AxiosInstance';
import { useAuth } from '../Provider/AuthProvider';

const XcodesDropDown = ({ variant = "outlined", label, type, onSelect, defaultValue = '' }) => {
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

    useEffect(() => {
        setSelectedValue(defaultValue);
    }, [defaultValue]);

    // Handle selection change
    const handleChange = (event) => {
        const value = event.target.value;
        setSelectedValue(value); // Update selected value
        if (onSelect) onSelect(value); // Call parent callback
    };

    return (
        <FormControl fullWidth size="small" variant={variant}>
            <InputLabel>{label}</InputLabel>
            {loading ? (
                <CircularProgress size={24} sx={{ margin: 'auto' }} />
            ) : (
                <Select
                    value={selectedValue}
                    onChange={handleChange}
                    label={label}
                    displayEmpty
                >
                    <MenuItem value="N/A">
                        Pick
                    </MenuItem>
                    {options.map((option, index) => (
                        <MenuItem key={index} value={option.value || option.xcode}>
                            {option.xlong}
                        </MenuItem>
                    ))}
                </Select>
            )}
        </FormControl>
    );
};

export default XcodesDropDown;
