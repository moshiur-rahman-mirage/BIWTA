import React, { useCallback, useEffect, useRef, useState } from 'react';

import {
    TextField,
    MenuItem,
    FormControl,
    InputLabel,
    Select,
    RadioGroup,
    FormControlLabel,
    Radio,
    Button,
    Box,
    Stack,
    FormLabel,
} from '@mui/material';

import Swal from 'sweetalert2';
import SideButtons from "../../Shared/SideButtons"
import Caption from "../../utility/Caption"
const ApprovalLayer = ({ xgrnnum = '' }) => {

    const variant = 'standard'








    return (
        <div className='grid grid-cols-12 gap-5 z-40'>
            <div className="">
                <SideButtons
                    onAdd={''}
                    onUpdate={''}
                    onDelete={''}
                    onClear={''}
                />
            </div>
            <div className='col-span-11 '>
                <div className='   rounded'>
                    <div className="w-full px-2  mx-auto  ">



                        <Box
                            display="grid"
                            gridTemplateColumns="repeat(2, 1fr)"
                            component="form"
                            sx={{
                                '& > :not(style)': { my: 1 },

                                mx: 'auto',
                                gap: 2,
                                mt: 1,
                                borderRadius: 2,
                                bgcolor: 'white',
                            }}
                            noValidate
                            autoComplete="off"

                        >
                            <Box sx={{
                                gridColumn: 'span 1',
                                border: '1px solid #ccc',
                                borderRadius: '8px',
                                padding: 2,
                            }}>
                                <Caption title={"Approval Layer"} />
                                <Box
                                    display="grid"

                                    gridTemplateColumns="repeat(2, 1fr)"
                                    gap={2}
                                    mb={2} // margin-bottom
                                >

                                    <TextField
                                        label="Row Number"
                                        name='xrow'
                                        variant={variant}
                                        size="small"
                                        // onChange={handleChange}
                                        // value={formData.xrow}
                                        fullWidth
                                        sx={{
                                            '& .MuiInputBase-input': {
                                                // Remove unnecessary padding
                                                // Ensure the input spans the full height
                                                fontSize: '.9rem'
                                            },
                                        }}
                                        InputLabelProps={{
                                            shrink: true,
                                            sx: {
                                                fontWeight: 600,
                                            },
                                        }}
                                    />

                                    <TextField
                                        label="Unit"
                                        name='xunitpur'
                                        variant={variant}
                                        size="small"
                                        // onChange={handleChange}
                                        // value={formData.xunitpur}
                                        fullWidth
                                        sx={{
                                            '& .MuiInputBase-input': {
                                                // Remove unnecessary padding
                                                // Ensure the input spans the full height
                                                fontSize: '.9rem'
                                            },
                                        }}
                                        InputLabelProps={{
                                            shrink: true,
                                            sx: {
                                                fontWeight: 600, // Adjust font size here
                                            },
                                        }}
                                        InputProps={{
                                            sx: {
                                                fontWeight: 600
                                            }
                                        }}
                                    />

                                </Box>
                                <Box
                                    display="grid"
                                    gridTemplateColumns="repeat(2, 1fr)"
                                    gap={2}
                                    mb={2} // margin-bottom
                                >
                                    <DynamicDropdown
                                        isOpen={itemDropdownOpen}
                                        onClose={() => setItemDropdownOpen(false)}
                                        triggerRef={itemRef}
                                        data={searchResults}
                                        headers={itemConfig.map((config) => config.header)}
                                        onSelect={handleItemClick}
                                        dropdownWidth={400}
                                        dropdownHeight={300}
                                        rect={rect}
                                    />
                                    {/* Supplier ID Field */}
                                    <TextField
                                        inputRef={itemRef}
                                        id="xitem"
                                        name="xitem"
                                        required
                                        label="Item Code"
                                        error={!!formErrors.xitem} 
                                        helperText={formErrors.xitem}
                                        InputLabelProps={{
                                            shrink: true,
                                            sx: {
                                                fontWeight: 600, // Adjust font size here
                                            },
                                        }}
                                        size="small"
                                        value={formData.xitem || ''}
                                        variant={variant}
                                        fullWidth
                                        onChange={(e) => {
                                            handleChange(e);
                                            const query = e.target.value;
                                            const apiSearchUrl = `http://localhost:8080/api/products/search?zid=${zid}&text=${query}`;
                                            handleSearch(
                                                e.target.value,
                                                apiSearchUrl,
                                                itemConfig,
                                                setSearchResults,
                                                setItemDropdownOpen,
                                                itemRef,
                                                setDropdownPosition,
                                                { zid }
                                            );
                                        }}
                                        sx={{
                                            gridColumn: 'span 1',
                                            '& .MuiInputBase-input': {
                                                fontSize: '.9rem'
                                            },
                                        }}
                                    />



                                    <TextField
                                        label="Item Name"
                                        variant={variant}
                                        size="small"
                                        fullWidth
                                        name='xdesc'
                                        InputLabelProps={{
                                            shrink: true,
                                            sx: {
                                                fontWeight: 600, // Adjust font size here
                                            },
                                        }}
                                        InputProps={{
                                            sx: {
                                                fontSize: '.8rem',
                                            }
                                        }}
                                        sx={{
                                            gridColumn: 'span 1',
                                            '& .MuiInputBase-input': {
                                                fontSize: '.9rem'
                                            },
                                        }}
                                        onChange={handleChange}
                                        value={formData.xdesc}


                                    />
                                </Box>

                                <Box
                                    display="grid"
                                    gridTemplateColumns="repeat(2, 1fr)"
                                    border
                                    gap={2}
                                    mb={2} // margin-bottom

                                >
                                    <TextField
                                        label="Receive Qty"
                                        variant={variant}
                                        size="small"
                                        fullWidth
                                        name='xqtygrn'
                                        onChange={handleChange}
                                        error={!!formErrors.xqtygrn} 
                                        helperText={formErrors.xqtygrn}
                                        value={formData.xqtygrn}
                                        required
                                        sx={{
                                            gridColumn: 'span 1',
                                            '& .MuiInputBase-input': {
                                                fontSize: '.9rem'
                                            },
                                        }}
                                        InputLabelProps={{
                                            shrink: true,
                                            sx: {
                                                fontWeight: 600, // Adjust font size here
                                            },
                                        }}
                                    />

                                    <TextField
                                        label="Rate"
                                        variant={variant}
                                        size="small"
                                        fullWidth
                                        name='xrategrn'
                                        error={!!formErrors.xrategrn} 
                                        helperText={formErrors.xrategrn}
                                        onChange={handleChange}
                                        sx={{
                                            gridColumn: 'span 1',
                                            '& .MuiInputBase-input': {
                                                fontSize: '.9rem'
                                            },
                                        }}
                                        value={formData.xrategrn}
                                        InputLabelProps={{
                                            shrink: true,
                                            sx: {
                                                fontWeight: 600, // Adjust font size here
                                            },
                                        }}
                                    />

                                </Box>
                                <Box
                                    display="grid"
                                    gridTemplateColumns="repeat(2, 1fr)"
                                    border
                                    gap={2}
                                    mb={2} // margin-bottom

                                >
                                    <TextField
                                        label="Batch"
                                        variant={variant}
                                        size="small"
                                        fullWidth
                                        name='xbatch'
                                        onChange={handleChange}
                                        value={formData.xbatch}
                                        sx={{
                                            gridColumn: 'span 1',
                                            '& .MuiInputBase-input': {
                                                fontSize: '.9rem'
                                            },
                                        }}
                                        InputLabelProps={{
                                            shrink: true,
                                            sx: {
                                                fontWeight: 600, // Adjust font size here
                                            },
                                        }}
                                    />

                                    <TextField
                                        label="Expiration Date"
                                        type='date'
                                        variant={variant}
                                        size="small"
                                        fullWidth
                                        name='xdateexp'
                                        onChange={handleChange}
                                        sx={{
                                            gridColumn: 'span 1',
                                            '& .MuiInputBase-input': {
                                                fontSize: '.9rem'
                                            },
                                        }}
                                        value={formData.xdateexp}
                                        InputLabelProps={{
                                            shrink: true,
                                            sx: {
                                                fontWeight: 600, // Adjust font size here
                                            },
                                        }}
                                    />

                                </Box>
                            </Box>
                            <Box sx={{
                                gridColumn: 'span 1',
                                border: '1px solid #ccc', // Light gray border
                                borderRadius: '8px', // Optional: Rounded corners
                                padding: 2,
                            }}>

                                <SortableList
                                    apiUrl={apiListUrl}

                                    caption="Receive Entry Detail List"
                                    columns={[
                                        { field: 'xrow', title: 'Serial', width: '5%', },
                                        { field: 'xitem', title: 'Item', width: '10%' },
                                        { field: 'xdesc', title: 'Item Code', width: '65%', align: 'center' },
                                        { field: 'xqtygrn', title: 'GRN Qty', width: '10%', align: 'center' },
                                        { field: 'xrategrn', title: 'Rate', width: '10%', align: 'center' },
                                    ]}
                                    onItemSelect={handleItemSelect}
                                    onRefresh={(refresh) => {
                                        if (refreshTrigger) {
                                            refresh();
                                            setRefreshTrigger(false);
                                        }
                                    }}
                                    pageSize={10}
                                    // onSortChange={handleSortChange}
                                    sortField="xgrnnum"
                                    additionalParams={{}}
                                    captionFont=".9rem"
                                    xclass="py-4 pl-2"
                                    bodyFont=".7rem"
                                    mt={0}
                                    page={1}
                                    isModal
                                />

                            </Box>



                            {/* Row 1 */}



                        </Box>

                    </div>
                </div>
                <div>

                </div>



            </div>
        </div >
    );
};

export default ApprovalLayer;
