import React, { useEffect, useState } from 'react';
import { List, ListItem, ListItemText, Divider, Grid, Box, Typography } from '@mui/material';
import axios from 'axios';

const BasicList = ({ xtype, apiBaseUrl, zid, onItemSelect, onRefresh, xcode, xlong }) => {
    const [items, setItems] = useState([]);
    const [loading, setLoading] = useState(true);

    const fetchData = async () => {
        console.log("Date Fetching")
        try {
            const response = await axios.get(`${apiBaseUrl}/search?zid=${zid}&xtype=${xtype}`);
            setItems(response.data);
            console.log(response.data)
            setLoading(false);
        } catch (error) {
            console.error('Error fetching list items:', error);
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchData();
    }, [apiBaseUrl, zid, xtype, xlong, xcode]);

    useEffect(() => {
        if (onRefresh) {
            onRefresh(fetchData);
        }
    }, [onRefresh]);

    return (
        <div style={{ overflowY: 'auto', marginTop: '15px', borderRadius: '4px',  }}>
        {/* Heading */}
        <Box display="flex" justifyContent="space-between" mb={1} borderBottom={1}>
            {/* Column Names */}
            <Typography variant="subtitle1" style={{ fontWeight: 'bold', width: '20%' }}>
                Code
            </Typography>
            <Typography variant="subtitle1" style={{ fontWeight: 'bold', width: '55%' }}>
                Name
            </Typography>
            <Typography variant="subtitle1" style={{ fontWeight: 'bold', width: '10%' }}>
                Active?
            </Typography>
        </Box>
        
        {loading ? (
            <p>Loading...</p>
        ) : items.length === 0 ? (
            <p>No items available</p>
        ) : (
            <Box>
                {items.map((item, index) => (
                    <div key={index} onClick={() => onItemSelect(item)}>
                        <Box display="flex" justifyContent="space-between" mb={1}>
                            {/* Left side (item fields) */}
                            <Box flex="0 1 10%" style={{ width: 'auto' }}>
                                <Typography variant="subtitle1" align="left">{item.xcode || 'N/A'}</Typography>
                            </Box>
                            <Box flex="0 1 55%" style={{ width: 'auto' }}>
                                <Typography variant="subtitle1" align="left">{item.xlong || 'N/A'}</Typography>
                            </Box>
                            <Box flex="0 1 10%" style={{ width: 'auto' }}>
                                <Typography variant="subtitle1" align="left">{item.zactive}</Typography>
                            </Box>
                        </Box>
                        {index < items.length - 1 && <Divider />}
                    </div>
                ))}
            </Box>
        )}
    </div>
    );
};

export default BasicList;
