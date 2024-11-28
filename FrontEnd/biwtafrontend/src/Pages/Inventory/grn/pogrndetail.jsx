import React, { useCallback, useEffect, useState } from 'react';

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
import { handleApiRequest } from '../../../utility/handleApiRequest';
import SideButtons from '../../../Shared/SideButtons';
import Caption from '../../../utility/Caption';
import XcodesDropDown from '../../../ReusableComponents/XcodesDropDown';
import SortableList from '../../../ReusableComponents/SortableList';
import { useAuth } from '../../../Provider/AuthProvider';


const Pogrndetail = ({ xgrnnum }) => {
    const { zid } = useAuth();
    const variant = 'standard'
    const [isTyping, setIsTyping] = useState(false);
    const [refreshCallback, setRefreshCallback] = useState(null); // Store the refresh function
    const [refreshTrigger, setRefreshTrigger] = useState(false);
    const apiBaseUrl = `api/pogrndetail?zid=${zid}&xgrnnum=${xgrnnum}`;
    const [formData, setFormData] = useState({
        zid: zid,
        zauserid: '',
        xgrnnum: '',
        xrow: '',
        xitem: '',
        xqtygrn: '',
        xrate: '',
        xbatch: '',
        xdateexp: '',
        xlong: '',
        xlineamt: ''


    });


    const handleDropdownSelect = (fieldName, value) => {
        setFormData((prevState) => ({
            ...prevState,
            [fieldName]: value,
        }));
    };


    const handleRefresh = useCallback(() => {
        if (refreshCallback) {
            refreshCallback(); // Call the fetch function from the child
        }
    }, [refreshCallback]);

    useEffect(() => {

        if (refreshTrigger) {
            handleRefresh();
            setRefreshTrigger(false); // Reset trigger
        }
    }, [refreshTrigger, handleRefresh]);


    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prev) => {
            const updatedData = { ...prev, [name]: value };
            return updatedData;
        });
        setIsTyping(true);
    };



    const handleAction = async (method) => {
        console.log(zid);


        const data = {

            zid: zid,
            xgrnnum: formData.xgrnnum,
            zauserid: formData.zauserid,
            xrow: formData.xrow,
            xitem: formData.xitem,
            xqtygrn: formData.xqtygrn,
            xrate: formData.xrate,
            xbatch: formData.xbatch,
            xdateexp: formData.xdateexp,
            xlong: formData.xlong,
            xlineamt: formData.xlineamt

        };


        const endpoint = "/api/pogrndetail";

        await handleApiRequest({
            endpoint,
            data,
            method,
            onSuccess: (response) => {
                handleRefresh();
                setRefreshTrigger(true);
                if (method === 'DELETE') {

                    setFormData({
                        zid: zid,
                        zauserid: '',
                        xgrnnum: '',
                        xrow: '',
                        xitem: '',
                        xqtygrn: '',
                        xrate: '',
                        xbatch: '',
                        xdateexp: '',
                        xlong: '',
                        xlineamt: ''
                    });
                }

            },
        });
    };

    const handleOnRefresh = useCallback((refreshFn) => {
        setRefreshCallback(() => refreshFn);
    }, []);


    return (
        <div className='grid grid-cols-12 gap-5 z-40'>
            <div className="">
                <SideButtons
                    onAdd={() => handleAction('POST')}
                    onUpdate={() => handleAction('PUT')}
                    onDelete={() => handleAction('DELETE')}
                //  onClear={handleClear}
                // onShow={handleShow}
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
                                border: '1px solid #ccc', // Light gray border
                                borderRadius: '8px', // Optional: Rounded corners
                                padding: 2,
                            }}>
                                <Caption title={"Family Information Detail of " } />
                                <Box
                                    display="grid"

                                    gridTemplateColumns="repeat(2, 1fr)"
                                    gap={2}
                                    mb={2} // margin-bottom
                                >

                                    <TextField
                                        label="Family member Name"
                                        name='xname'
                                        variant={variant}
                                        size="small"
                                        onChange={handleChange}
                                        value={formData.xname}
                                        fullWidth
                                        required
                                        sx={{
                                            '& .MuiInputLabel-root': {
                                                fontSize: '0.85rem',
                                            },
                                            '& .MuiInputBase-input': {
                                                fontSize: '0.85rem',
                                            },
                                        }}
                                    />

                                    <TextField
                                        label="Date of Birth"
                                        type="date"
                                        name='xbirthdate'
                                        size='small'
                                        onChange={handleChange}
                                        InputLabelProps={{ shrink: true }}
                                        value={formData.xbirthdate}
                                        variant={variant}
                                        fullWidth

                                        sx={{
                                            '& .MuiInputLabel-root': {
                                                fontSize: '0.85rem',
                                            },
                                            '& .MuiInputBase-input': {
                                                fontSize: '0.85rem',
                                            },
                                        }}

                                    />
                                </Box>
                                <Box
                                    display="grid"
                                    gridTemplateColumns="repeat(2, 1fr)"
                                    gap={2}
                                    mb={2} // margin-bottom
                                >
                                    <XcodesDropDown
                                        id='xsex'
                                        name='xsex'
                                        variant={variant}
                                        label="Gender"
                                        size="small"
                                        type="Gender"
                                        apiUrl={apiBaseUrl}
                                        onSelect={(value) => handleDropdownSelect("xgender", value)}
                                        value={formData.xgender}
                                        fontSize="0.8rem" // Smaller font size for dropdown options
                                        captionSize="0.8rem"


                                    />



                                    <Stack direction={{ xs: 'column', sm: 'row' }} spacing={2} mb={2}>

                                        <XcodesDropDown
                                            variant={variant}
                                            label="Relation"
                                            size="small"
                                            type="Relation"
                                            name='xrelation'
                                            onSelect={(value) => handleDropdownSelect("xrelation", value)}
                                            value={formData.xrelation}
                                            defaultValue=""
                                        />

                                    </Stack>
                                </Box>

                                <Box
                                    display="grid"
                                    gridTemplateColumns="repeat(2, 1fr)"
                                    border
                                    gap={2}
                                    mb={2} // margin-bottom

                                >
                                    <TextField
                                        id='xnid'
                                        name='xnid'
                                        label="NID"
                                        size="small"
                                        onChange={handleChange}
                                        value={formData.xnid}
                                        variant={variant}
                                        fullWidth
                                        required
                                        sx={{
                                            '& .MuiInputLabel-root': {
                                                fontSize: '0.85rem',
                                            },
                                            '& .MuiInputBase-input': {
                                                fontSize: '0.85rem',
                                            },
                                            gridColumn: 'span 1'
                                        }}

                                        InputLabelProps={{
                                            shrink: true, // Ensures the label stays above the input field
                                        }}
                                    />

                                    <TextField
                                        label="Contact Number"
                                        variant={variant}
                                        size="small"
                                        fullWidth
                                        name='xcontact'
                                        onChange={handleChange}
                                        value={formData.xcontact}
                                        required
                                        sx={{
                                            '& .MuiInputLabel-root': {
                                                fontSize: '0.85rem',
                                            },
                                            '& .MuiInputBase-input': {
                                                fontSize: '0.85rem',
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
                                    apiUrl={apiBaseUrl}

                                    caption="Receive Entry List"
                                    columns={[
                                        { field: 'Row', title: 'Item Code', width: '10%', },
                                        { field: 'xitem', title: 'Item', width: '25%' },
                                        { field: 'xdesc', title: 'Item Code', width: '40%', align: 'center' },
                                        { field: 'xqtygrn', title: 'GRN Qty', width: '10%', align: 'center' },
                                    ]}
                                    onItemSelect={handleItemSelect}
                                    onRefresh={(refresh) => {
                                        if (refreshTrigger) {
                                            refresh();
                                            setRefreshTrigger(false);
                                        }
                                    }}
                                    pageSize={10}
                                    onSortChange={handleSortChange}
                                    sortField="xgrnnum"
                                    additionalParams={{ zid: zid, xstatus: 'Open' }}
                                    captionFont=".9rem"
                                    xclass="py-4 pl-2"
                                    bodyFont=".8rem"
                                    mt={0}
                                    page={1}
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

export default Pogrndetail;
