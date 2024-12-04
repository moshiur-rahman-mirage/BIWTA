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
    directFetch,
    apiUrl,
    caption,
    columns,
    onItemSelect,
    onRefresh,
    pageSize: defaultPageSize = 10,
    sortField,
    additionalParams = {},
    captionFont,
    bodyFont,
    isFolded = true,
    xclass,
    mt,
    isModal = false,
}) => {
    const [items, setItems] = useState([]);
    const [filteredItems, setFilteredItems] = useState([]);
    const [loading, setLoading] = useState(false);
    const [hoveredIndex, setHoveredIndex] = useState(null);
    const [folded, setFolded] = useState(isFolded);
    const [searchTerm, setSearchTerm] = useState('');
    const [page, setPage] = useState(1);
    const [pageSize, setPageSize] = useState(defaultPageSize);
    const [xtotalPages, setxTotalPages] = useState(0);
    const [xsortField, setSortField] = useState(sortField);
    const [sortOrder, setSortOrder] = useState('asc');

    const handleMouseEnter = (index) => setHoveredIndex(index);
    const handleMouseLeave = () => setHoveredIndex(null);
    console.log(apiUrl)
    const constructApiUrl = useMemo(() => {
        return `${apiUrl}?page=${page - 1}&size=${pageSize}&sortBy=${xsortField}&ascending=${sortOrder === 'asc'}`;
    }, [apiUrl, page, pageSize, xsortField, sortOrder]);

    const fetchData = async () => {
        setLoading(true);
        try {
            const response = await axiosInstance.get(constructApiUrl, { params: additionalParams });
            console.log(response)
            console.log(constructApiUrl)
            setItems(response.data.content || []);
            setFilteredItems(response.data.content || []);
            setxTotalPages(response.data.page.totalPages || 1);
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
    }, [page, pageSize, xsortField]);

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
        <div className={`${xclass} shadow-lg pt-0 rounded`} style={{ maxHeight: isModal ? 'calc(80vh - 100px)' : 'calc(100vh - 100px)', overflowY: 'auto', borderRadius: '4px' }}>
            <Box display="flex" justifyContent="space-between" mt={mt}>
                <Caption title={caption} />
            </Box>
            <Box display="flex" justifyContent="space-between" gap={2}>
            <TextField
                    sx={{
                        height: '30px', // Adjust overall height
                        marginTop: '2px',
                        '& .MuiInputBase-root': {
                            height: '100%',
                            display: 'flex',
                            alignItems: 'center', // Ensures the input content (including placeholder) is vertically centered
                        },
                        '& .MuiInputBase-input': {
                            padding: '0', // Remove unnecessary padding
                            height: '100%', // Ensure the input spans the full height
                            lineHeight: '30px', // Match the line height to the TextField height
                            paddingLeft: '5px'
                        },
                        '& .MuiInputBase-input::placeholder': {
                            color: 'gray', // Adjust placeholder color
                            fontSize: '0.85rem', // Adjust placeholder font size
                            verticalAlign: 'middle',
                            paddingLeft: '5px'
                        },
                    }}
                    size="small"
                    placeholder="Search..."
                    value={searchTerm}
                    onChange={handleSearch}
						 
									  
																	 
					  
                />
                <Box display="flex" alignItems="left" justifyContent="space-between" gap={2} >


                    <FormControl size="small" sx={{
                        minWidth: 102, // Increase width by 2px (100 -> 102)
                        height: '40px',
                        marginTop: '2px',
                        position: 'relative',
                        '& .MuiInputBase-root': {
                            height: '30px', // Ensure the Select input matches the parent height
                            lineHeight: '30px', // Align the text vertically
                        },
                        '& .MuiSelect-select': {
                            padding: '0 8px', // Adjust padding for consistent appearance
                            display: 'flex',
                            alignItems: 'center', // Vertically center the dropdown content
                        },
                    }}>
                        <InputLabel sx={{
                            position: 'absolute',
                            top: '50%', // Vertically center
                            transform: 'translateY(-75%)',
                            left: '35px',

                            fontSize: '0.8rem', // Adjust font size if necessary
                            paddingLeft: '4px', // Optional: Add padding for aesthetics
                        }}>
                            Show
                        </InputLabel>

                        <Select value={pageSize} onChange={handlePageSizeChange} sx={{ fontSize: '0.8rem' }}>
                            {[10, 15, 20].map((size) => (
                                <MenuItem key={size} value={size} sx={{ fontSize: '0.8rem' }}>
                                    {size}
                                </MenuItem>
                            ))}
                        </Select>
                    </FormControl>
                    <Button onClick={toggleFold} variant="outlined" size="small" sx={{ height: '30px', marginTop: '2px', fontSize: '0.8rem' }}>
                        {folded ? `Expand (${filteredItems.length})` : 'Collapse'}
                    </Button>
                </Box>
            </Box>

            {!folded && (
                <>
                    <Grid container spacing={1} sx={{ borderBottom: '1px solid #ccc', padding: '8px 0' }}>
                        {columns.map((col, idx) => (
                            <Grid item xs={12 / columns.length} key={idx} style={{ textAlign: col.align || 'left' }}>
                                <Typography
                                    variant="subtitle1"
                                    sx={{
                                        fontWeight: '600',
                                        cursor: 'pointer',
                                        fontSize:'14px'
                                    }}
                                    onClick={() => setSortField(col.field)}
                                >
                                    {col.title}{' '}
                                    {xsortField === col.field ? (sortOrder === 'asc' ? '↑' : '↓') : ''}
                                </Typography>
                            </Grid>
                        ))}
                    </Grid>
                    {loading ? (
                        <Typography>Loading...</Typography>
                    ) : filteredItems.length === 0 ? (
                        <Typography>No items available</Typography>
                    ) : (
                        filteredItems.map((item, index) => (
                            <Box
                                key={index}
                                onClick={() => onItemSelect(item)}
                                onMouseEnter={() => handleMouseEnter(index)}
                                onMouseLeave={handleMouseLeave}
                                sx={{
                                    backgroundColor: hoveredIndex === index ? '#f0f0f0' : 'transparent',
                                    cursor: 'pointer',
                                    padding: '8px 0',
                                }}
                            >
                                <Grid container spacing={1}>
                                    {columns.map((col, idx) => (
                                        <Grid item xs={12 / columns.length} key={idx} style={{ textAlign: col.align || 'left' }}>
                                            <Typography sx={{ fontSize: bodyFont || '0.875rem' }}>
                                                {item[col.field] || 'N/A'}
                                            </Typography>
                                        </Grid>
                                    ))}
                                </Grid>
                                {index < filteredItems.length - 1 && <Divider />}
                            </Box>
                        ))
                    )}
                    <Box mt={2} display="flex" justifyContent="space-between" alignItems="center">
                        <Pagination
                            count={xtotalPages}
                            page={page}
                            onChange={handlePageChange}
                            size="small"
                        />
                    </Box>
                </>
            )}
        </div>
    );
};

export default SortableList;
