import React, { useEffect, useState } from 'react';
import { Box, Typography, Divider, TextField, Button, Grid } from '@mui/material';
import Caption from '../utility/Caption';

const SortableList = ({
    apiUrl,
    caption,
    columns,
    onItemSelect,
    onRefresh,
    pageSize,
    sortField,
    sortOrder,
    onSortChange,
    additionalParams = {},
    captionFont,
    bodyFont,
    xclass,
    mt,
}) => {
    const [items, setItems] = useState([]);
    const [filteredItems, setFilteredItems] = useState([]);
    const [loading, setLoading] = useState(true);
    const [hoveredIndex, setHoveredIndex] = useState(null);
    const [folded, setFolded] = useState(true);
    const [searchTerm, setSearchTerm] = useState('');
    const [page, setPage] = useState(0); // Current page
    const [totalPages, setTotalPages] = useState(0); // Total pages

    const handleMouseEnter = (index) => setHoveredIndex(index);
    const handleMouseLeave = () => setHoveredIndex(null);

    const fetchData = async () => {
        setLoading(true);
        try {
            const params = {
                page,
                size: pageSize,
                sort: sortField ? `${sortField},${sortOrder}` : undefined,
                ...additionalParams,
            };
            const response = await apiUrl(params); // Parent provides API function
            setItems(response.data.content);
            setFilteredItems(response.data.content); // Initialize filtered items
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
    }, [page, pageSize, sortField, sortOrder]);

    const handleSearch = (event) => {
        setFolded(false);
        const value = event.target.value.toLowerCase();
        setSearchTerm(value);
        setFilteredItems(
            items.filter((item) =>
                columns.some((col) =>
                    String(item[col.field] || '')
                        .toLowerCase()
                        .includes(value)
                )
            )
        );
    };

    const toggleFold = () => setFolded((prev) => !prev);

    const handlePageChange = (newPage) => {
        if (newPage >= 0 && newPage < totalPages) {
            setPage(newPage);
        }
    };

    return (
        <div className={`${xclass} shadow-lg pt-0 rounded`} style={{ overflowY: 'auto', borderRadius: '4px' }}>
            <Box display="flex" alignItems="left" justifyContent="space-between" mt={mt}>
                <Caption title={caption} />
            </Box>
            <Box display="flex" alignItems="left" justifyContent="space-between" gap={2}>
                <TextField
                    sx={{ height: '40px', marginTop: '2px' }}
                    size="small"
                    placeholder="Search..."
                    value={searchTerm}
                    onChange={handleSearch}
                />
                <Button onClick={toggleFold} variant="outlined" size="small" sx={{ height: '40px', marginTop: '2px' }}>
                    {folded ? `Expand (${filteredItems.length})` : 'Collapse'}
                </Button>
            </Box>

            {!folded && (
                <>
                    {/* Table Header */}
                    <Grid container spacing={2} mt={2} mb={1}>
                        {columns.map((col, idx) => (
                            <Grid item xs={12 / columns.length} key={idx}>
                                <Typography
                                    variant="subtitle1"
                                    style={{ fontWeight: 'bold', fontSize: captionFont || '1rem', cursor: 'pointer' }}
                                    onClick={() => onSortChange(col.field)}
                                >
                                    {col.title}{' '}
                                    {sortField === col.field ? (sortOrder === 'asc' ? '↑' : '↓') : ''}
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
                    <Box display="flex" justifyContent="center" mt={2}>
                        <Button
                            variant="contained"
                            disabled={page === 0}
                            onClick={() => handlePageChange(page - 1)}
                            size="small"
                        >
                            Previous
                        </Button>
                        <Typography variant="body1" style={{ margin: '0 8px' }}>
                            Page {page + 1} of {totalPages}
                        </Typography>
                        <Button
                            variant="contained"
                            disabled={page === totalPages - 1}
                            onClick={() => handlePageChange(page + 1)}
                            size="small"
                        >
                            Next
                        </Button>
                    </Box>
                </>
            )}
        </div>
    );
};

export default SortableList;
