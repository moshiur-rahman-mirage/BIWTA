import React, { useEffect, useState } from 'react';
import { Box, Typography, Divider } from '@mui/material';
import axiosInstance from '../Middleware/AxiosInstance';
import Caption from '../utility/Caption';

const GenericList = ({
    apiUrl,        // API endpoint for fetching data
    caption,       // Caption for the list
    columns,       // Array of column configurations (field and title)
    onItemSelect,  // Callback when an item is selected
    onRefresh,     // Callback for refreshing data
    additionalParams = {},
    captionFont,
    bodyFont,
    xclass
}) => {
    const [items, setItems] = useState([]);
    const [loading, setLoading] = useState(true);
    const [hoveredIndex, setHoveredIndex] = useState(null);

    const handleMouseEnter = (index) => setHoveredIndex(index);
    const handleMouseLeave = () => setHoveredIndex(null);
    // console.log(captionFont)
    const fetchData = async () => {
        console.log("fetchData is called")
        try {
            console.log(apiUrl);
            setLoading(true);
            console.log(apiUrl)
            const response = await axiosInstance.get(apiUrl, { params: additionalParams });
            
            console.log("Response from api is "+response)
            console.log(response.data)
            setItems(response.data);
        } catch (error) {
            console.error('Error fetching list items:', error);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        if (onRefresh) {
            onRefresh(() => fetchData());
        }
    }, [onRefresh]);

    useEffect(() => {
        fetchData();

    }, []);

    return (
        <div className={`${xclass} shadow-lg rounded`} style={{ overflowY: 'auto', borderRadius: '4px' }}>
            {/* Caption */}
            <Caption title={caption} />
            {/* {caption && (
                <Typography variant="h6" gutterBottom>
                    {caption}
                </Typography>
            )} */}

            {/* Table Header */}
            <Box display="flex" justifyContent="space-between" mt={2} mb={1} borderBottom={1}>
                {columns.map((col, idx) => (
                    <Typography
                        key={idx}

                        variant="subtitle1" style={{ fontWeight: '', width: '20%' }}
                    >
                        {col.title}
                    </Typography>
                ))}
            </Box>

            {/* Table Content */}
            {loading ? (
                <p>Loading...</p>
            ) : items.length === 0 ? (
                <p>No items available</p>
            ) : (
                <Box>
                    {items.map((item, index) => (
                        <div
                            key={index}
                            onClick={() => onItemSelect(item)}
                            onMouseEnter={() => handleMouseEnter(index)}
                            onMouseLeave={handleMouseLeave}
                            style={{
                                backgroundColor: hoveredIndex === index ? '#f0f0f0' : 'transparent',
                                cursor: 'pointer',
                                padding: '8px 0',
                            }}
                        >
                            <Box display="flex" justifyContent="space-between" mb={1}>
                                {columns.map((col, idx) => (
                                    <Box
                                        key={idx}
                                        flex={col.flex || '1'}
                                        // style={{ width: col.width || 'auto',fontSize:captionFont }}
                                        sx={{
                                            width: col.width || 'auto',
                                            fontSize: captionFont || '0.875rem', // Ensure captionFont is defined
                                        }}

                                    >
                                        <Typography
                                            sx={{ fontSize: bodyFont }}
                                            
                                            variant="subtitle1"
                                            align={col.align || 'left'}
                                        >
                                            {item[col.field] || 'N/A'}
                                        </Typography>
                                    </Box>
                                ))}
                            </Box>
                            {index < items.length - 1 && <Divider />}
                        </div>
                    ))}
                </Box>
            )}
        </div>
    );
};

export default GenericList;
