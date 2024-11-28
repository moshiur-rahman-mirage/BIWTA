import React, { useEffect, useMemo, useState } from 'react';
import {
    Box,
    Typography,
    Divider,
    TextField,
    Button,
    Grid,
    Pagination,
    Select,
    MenuItem,
    FormControl,
    InputLabel,
} from '@mui/material';
import Caption from '../utility/Caption';
import axiosInstance from '../Middleware/AxiosInstance';

const SortableList = ({
    apiUrl,
    caption,
    columns,
    onItemSelect,
    onRefresh,
    pageSize: defaultPageSize = 10,
    onSortChange,
    sortField,
    additionalParams = {},
    captionFont,
    bodyFont,
    xclass,
    mt,
    isModal = false,
}) => {
    const [items, setItems] = useState([]);
    const [filteredItems, setFilteredItems] = useState([]);
    const [loading, setLoading] = useState(true);
    const [hoveredIndex, setHoveredIndex] = useState(null);
    const [folded, setFolded] = useState(true);
    const [searchTerm, setSearchTerm] = useState('');
    const [page, setPage] = useState(1); // Current page (Material-UI pagination is 1-based)
    const [pageSize, setPageSize] = useState(defaultPageSize); // Items per page
    const [totalPages, setTotalPages] = useState(0); // Total pages
    const [xsortField, setSortField] = useState(sortField); // Current field to sort by
    const [sortOrder, setSortOrder] = useState('asc'); // Current sort order
    
    const handleMouseEnter = (index) => setHoveredIndex(index);
    const handleMouseLeave = () => setHoveredIndex(null);


    const handleSortChange = (field) => {
        if (xsortField === field) {
            // Toggle sort order if the same column is clicked
            setSortOrder((prevOrder) => (prevOrder === 'asc' ? 'desc' : 'asc'));
        } else {
            // Set new column to sort by and default to ascending order
            setSortField(field);
            setSortOrder('asc');
        }
        setPage(1); // Reset to the first page when sorting changes
    };
    

    const constructApiUrl = useMemo(() => {
        return `${apiUrl}?page=${page - 1}&size=${pageSize}&sortBy=${xsortField}&ascending=${sortOrder === 'asc'}`;
    }, [apiUrl, page, pageSize, xsortField, sortOrder]);


    const fetchData = async () => {
        setLoading(true);
        try {
            const apiUrlWithParams = constructApiUrl;


           

            const response = await axiosInstance.get(apiUrlWithParams, { params: additionalParams });
          
            setItems(response.data.content || []); // Update items based on API response
            setFilteredItems(response.data.content || []);
            setTotalPages(response.data.totalPages || 1);
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
    }, [page, pageSize, xsortField, sortOrder]);

    const handleSearch = (event) => {
        setFolded(false);
        const value = event.target.value.toLowerCase();
        setSearchTerm(value);
        setFilteredItems(
            items.filter((item) =>
                columns.some((col) =>
                    String(item[col.field] || '').toLowerCase().includes(value)


                )
            )
        );
    };

    const toggleFold = () => setFolded((prev) => !prev);

    const handlePageChange = (event, newPage) => {
        setPage(newPage);
    };


    const handlePageSizeChange = (event) => {
        setPageSize(event.target.value);
        setPage(1);
        setFolded(false);
    };

    return (
        <div className={`${xclass} shadow-lg pt-0 rounded`} style={{  maxHeight: isModal ? 'calc(80vh - 100px)' : 'calc(100vh - 100px)', overflowY: 'auto', borderRadius: '4px' }}>
            <Box display="flex" alignItems="left" justifyContent="space-between" mt={mt}>
                <Caption title={caption}  />
            </Box>
            <Box display="flex" alignItems="left" justifyContent="space-between" gap={2}>
                <TextField
                    sx={{ height: '40px', marginTop: '2px' }}
                    size="small"
                    placeholder="Search..."
                    value={searchTerm}
                    onChange={handleSearch}
                />
                <Box display="flex" alignItems="left" justifyContent="space-between" gap={2}>


                    <FormControl size="small" sx={{
                        minWidth: 102, // Increase width by 2px (100 -> 102)
                        height: '40px',
                        marginTop: '2px',
                        position: 'relative',
                    }}>
                        <InputLabel sx={{
                            position: 'absolute',
                            top: '50%', // Vertically center
                            transform: 'translateY(-50%)',
                            left:'35px',

                            fontSize: '0.85rem', // Adjust font size if necessary
                            paddingLeft: '4px', // Optional: Add padding for aesthetics
                        }}>
                            Show
                        </InputLabel>

                        <Select value={pageSize} onChange={handlePageSizeChange}>
                            {[ 10, 15, 20].map((size) => (
                                <MenuItem key={size} value={size}>
                                    {size}
                                </MenuItem>
                            ))}
                        </Select>
                    </FormControl>
                    <Button onClick={toggleFold} variant="outlined" size="small" sx={{ height: '40px', marginTop: '2px' }}>
                        {folded ? `Expand (${filteredItems.length})` : 'Collapse'}
                    </Button>
                </Box>
            </Box>

            {!folded && (
                <>
                    {/* Table Header */}
                    <Grid container spacing={2} mt={0} mb={1}>
                        {columns.map((col, idx) => (
                            <Grid item xs={12 / columns.length} key={idx}>
                                <Typography
                                    variant="subtitle1"
                                    style={{ fontWeight: 'bold', fontSize: captionFont || '1rem', cursor: 'pointer' }}
                                    onClick={() => onSortChange(col.field)}
                                >
                                    {col.title}{' '}
                                    {xsortField === col.field ? (sortOrder === 'asc' ? '↑' : '↓') : ''}
                                </Typography>
                            </Grid>
                        ))}
                    </Grid>

                    {/* Table Content */}
                    {loading ? (
                        <Typography>Loading...</Typography>
                    ) : filteredItems.length === 0 ? (
                        <Typography>No items available</Typography>
                    ) : (
                        <Box>
                            {filteredItems.map((item, index) => (
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
                                    <Grid container spacing={2}>
                                        {columns.map((col, idx) => (
                                            <Grid item xs={12 / columns.length} key={idx}>
                                                <Typography
                                                    style={{
                                                        fontSize: bodyFont || '0.875rem',
                                                        textAlign: 'left',
                                                    }}
                                                    onClick={() => handleSortChange(col.field)} 
                                                >
                                                    {item[col.field] || 'N/A'}
                                                </Typography>
                                            </Grid>
                                        ))}
                                    </Grid>
                                    {index < filteredItems.length - 1 && <Divider />}
                                </div>
                            ))}
                        </Box>
                    )}


                    {/* Pagination Controls */}
                    <Box mt={2} display="flex" justifyContent="space-between" alignItems="center">
                        <Pagination
                            count={totalPages}
                            page={page}
                            onChange={handlePageChange}
                            // color="#8979EE"
                            size="small"
                        />
                    </Box>
                </>
            )}
        </div>
    );
};

export default SortableList;
