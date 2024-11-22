import React, { useState, useEffect } from 'react';
import { FormControl, InputLabel, Select, MenuItem, CircularProgress } from '@mui/material';
import axios from 'axios';
import { useAuth } from '../Provider/AuthProvider';

const XcodesDropDown = ({ label, type, apiUrl, onSelect, defaultValue = '' }) => {

    const { zid } = useAuth();
    const [options, setOptions] = useState([]); // Store options fetched from API
    const [loading, setLoading] = useState(true); // Show loading indicator
    const [selectedValue, setSelectedValue] = useState(defaultValue); // Track selected value

    // Fetch options dynamically based on type
    useEffect(() => {

        const fetchOptions = async () => {
            setLoading(true);
            try {
                const response = await axios.get(`${apiUrl}/search?zid=${zid}&xtype=${type}`); // Pass type as a query parameter
                setOptions(response.data); // Update state with API response
            } catch (error) {
                console.error(`Error fetching ${type} options:`, error);
            } finally {
                setLoading(false); // Hide loading indicator
            }
        };
        console.log(options)
        if (type) fetchOptions(); // Fetch only if type is provided
    }, [type, apiUrl]);

    // Handle selection change
    const handleChange = (event) => {
        setSelectedValue(event.target.value);
        if (onSelect) onSelect(event.target.value); // Pass selected value to parent component
    };

    return (
        <FormControl fullWidth size="small" variant="filled" >
            <InputLabel>{label}</InputLabel>
            {loading ? (
                <CircularProgress size={24} sx={{ margin: 'auto' }} />
            ) : (
                <Select value={selectedValue} onChange={handleChange} label={label}>
                    <MenuItem value="">
                        Pick
                    </MenuItem>
                    {options.map((option, index) => (
                        <MenuItem key={index} value={option.value}>
                            {option.xlong}

                        </MenuItem>
                    ))}
                </Select>
            )}
        </FormControl>
    );
};

export default XcodesDropDown;
