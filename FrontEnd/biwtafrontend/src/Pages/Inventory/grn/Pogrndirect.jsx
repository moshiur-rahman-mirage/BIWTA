import React, { useCallback, useEffect, useRef, useState } from 'react';
import {
    TextField,
    Box,
    Typography,
    Button,
} from '@mui/material';
import { useAuth } from '../../../Provider/AuthProvider';

import HelmetTitle from '../../../utility/HelmetTitle';
import SideButtons from '../../../Shared/SideButtons';
import Caption from '../../../utility/Caption';
import DynamicDropdown from '../../../ReusableComponents/DynamicDropdown';

import { handleApiRequest } from '../../../utility/handleApiRequest';
import { addFunction } from '../../../ReusableComponents/addFunction';
import { handleSearch } from '../../../ReusableComponents/handleSearch';
import LoadingPage from '../../Loading/Loading';
import SortableList from '../../../ReusableComponents/SortableList';
import XcodesDropDown from '../../../ReusableComponents/XcodesDropDown';


const Pogrndirect = () => {
    // Authentication Context
    const { zid, zemail } = useAuth();
    // State Management
    const [formData, setFormData] = useState({
        zid: zid,
        xgrnnum: '',
        xstatusgrn: '',
        xdate: '',
        xcus: '',
        xwh: '',
        xref: '',
        xstatus: 'Open',
        xnote: '',


    });
    const [refreshTrigger, setRefreshTrigger] = useState(false);
    const [searchResults, setSearchResults] = useState([]);
    const [isDropdownOpen, setDropdownOpen] = useState(false);

    const [supDropdownOpen, setSupDropdownOpen] = useState(false);
    const [grnDropdownOpen, setGrnDropdownOpen] = useState(false);

    const [dropdownPosition, setDropdownPosition] = useState({ top: 0, left: 0 });
    const [loading, setLoading] = useState(true);
    const [status, setStatus] = useState("Inactive");
    const [refreshCallback, setRefreshCallback] = useState(null);
    const [selectedItem, setSelectedItem] = useState(null);
    const [updateCount, setUpdateCount] = useState(0);
    const [sortField, setSortField] = useState('name'); // Default sorting field
    const [sortOrder, setSortOrder] = useState('asc');




    // Handle dropdown value change
    const handleStatusChange = (event) => {
        setStatus(event.target.value);
    };
    // References
    const triggerRef = useRef(null);
    const supplierRef = useRef(null);
    const variant = 'standard';
    const apiBaseUrl = `http://localhost:8080/api/pogrnheader`;
    console.log(apiBaseUrl)
    const fieldConfig = [
        { header: 'GRN Number', field: 'xgrnnum' },
        { header: 'Date', field: 'xdate' },
        { header: 'Supplier', field: 'xorg' },
        { header: 'Challan', field: 'xref' },
    ];


    const supConfig = [
        { header: 'Supplier ID', field: 'xcus' },
        { header: 'Name', field: 'xorg' },
        { header: 'Address', field: 'xmadd' },
    ];

    const handleSortChange = (field) => {
        // Toggle sorting order if the same field is clicked
        setSortOrder((prevOrder) => (field === sortField && prevOrder === 'asc' ? 'desc' : 'asc'));
        setSortField(field);
    };


    useEffect(() => {
        if (zid && zemail) setLoading(false);
    }, [zid, zemail]);

    // Handlers
    const handleChange = (e) => {
        const { name, value } = e.target;
        // setFormData((prev) => ({ ...prev, [name]: value }));
        setFormData((prev) => {
            if (prev[name] !== value) {
                return { ...prev, [name]: value };
            }
            return prev;
        });
    };

    const handleResultClick = (result) => {
        setFormData((prev) => ({
            ...prev,
            ...result,
            zid,
        }));
        setSupDropdownOpen(false);
        setGrnDropdownOpen(false);

    };

    const handleDropdownSelect = (fieldName, value) => {
        console.log(value)
        setFormData((prevState) => ({
            ...prevState,
            [fieldName]: value,
            // xlong:xlong
        }));
    };


    useEffect(() => {
        if (selectedItem) {
            console.log(selectedItem)
            setFormData({
                ...selectedItem
            });
        }
    }, [selectedItem]);


    useEffect(() => {
        setRefreshTrigger(true);
    }, [updateCount]);


    const handleAdd = async () => {

        const endpoint = 'api/pogrnheader';
        const data = {
            ...formData,
            zauserid: zemail,
            zid: zid
        };
        addFunction(data, endpoint, 'POST', (response) => {
            if (response && response.xgrnnum) {
                console.log("POSt called")
                console.log(response)
                setFormData((prev) => ({ ...prev, xgrnnum: response.xgrnnum }));
                setUpdateCount(prevCount => prevCount + 1);
            } else {
                // alert('Supplier added successfully.');
            }
        });
    };


    const handleItemSelect = useCallback((item) => {
        console.log('Selected Item:', item);
        setSelectedItem(item);
    }, []);

    const handleClear = () => {
        setFormData({
            zid: zid,
            xgrnnum: '',
            xstatusgrn: '',
            xdate: '',
            xcus: '',
            xwh: '',
            xref: '',
            xstatus: '',
            xnote: '',
            xlong: '',
            xorg: ''

        });
        alert('Form cleared.');
    };

    const handleDelete = async () => {
        console.log(formData)
        const endpoint = `api/pogrnheader/${zid}/${formData.xgrnnum}`;
        await handleApiRequest({
            endpoint,
            method: 'DELETE',
            onSuccess: (response) => {
                setFormData({
                    zid: zid,
                    xgrnnum: '',
                    xstatusgrn: '',
                    xdate: '',
                    xcus: '',
                    xwh: '',
                    xref: '',
                    xstatus: '',
                    xnote: '',
                    xlong: '',
                    xorg: ''

                });
                setUpdateCount(prevCount => prevCount + 1);

            },
        });
    };


    const handleUpdate = async () => {
        setUpdateCount(prevCount => prevCount + 1);
        const endpoint = `api/pogrnheader/${zid}/${formData.xgrnnum}`;
        const data = {
            ...formData,
            zid: zid
        };
        console.log(data)

        await handleApiRequest({
            endpoint,
            data,
            method: 'PUT',
            // onSuccess: (response) => {
            //     setErrors({});
            // },
        });
    };

    // Render Loading Page if Necessary
    if (loading) {
        return <LoadingPage />;
    }

    return (

        <div>

            <div className='grid grid-cols-12 gap-1 mb-2'>
                <div className='col-span-1'>
                </div>
                <Button
                    onClick={''}
                    variant='outlined'
                    sx={{
                        marginLeft: 1,
                        paddingX: 1, // equivalent to Tailwind's px-2
                        paddingY: 0.5, // equivalent to Tailwind's py-0.5
                        // equivalent to Tailwind's w-24 (6rem = 24 * 0.25rem)
                        height: '2.5rem', // equivalent to Tailwind's h-10 (2.5rem = 10 * 0.25rem)
                        '&:hover': {
                            backgroundColor: '#F59E0B', // Yellow-600
                        },
                    }}
                    size="medium"

                >
                    Detail
                </Button>
                <Button
                    onClick={''}
                    variant='outlined'
                    sx={{
                        marginLeft: 1,
                        paddingX: 2, // equivalent to Tailwind's px-2
                        paddingY: 0.5, // equivalent to Tailwind's py-0.5
                        // equivalent to Tailwind's w-24 (6rem = 24 * 0.25rem)
                        height: '2.5rem', // equivalent to Tailwind's h-10 (2.5rem = 10 * 0.25rem)
                        '&:hover': {
                            backgroundColor: '#F59E0B', // Yellow-600
                        },
                    }}
                    size="medium"

                >
                    Confirm
                </Button>
            </div>


            <div className="grid grid-cols-12">




                {/* Helmet Title for Page */}
                <HelmetTitle title="Product Receive Entry" />

                {/* Sidebar with Action Buttons */}
                <div className="col-span-1">
                    <SideButtons
                        onAdd={handleAdd}
                        onClear={handleClear}
                        onUpdate={handleUpdate}
                        onDelete={handleDelete}
                    />
                </div>

                {/* Main Form Section */}
                {/* <div className="col-span-6"> */}
                <Box sx={{
                    gridColumn: 'span 5',
                    // border: '1px solid #ccc', // Light gray border
                    borderRadius: '8px', // Optional: Rounded corners
                    // padding: 2,
                }}>



                    <div className="shadow-lg rounded">
                        <div className="w-full px-2 py-2 pt-0 mx-auto ">
                            <Caption title="Product Receive Entry" />
                            <Box
                                component="form"
                                sx={{
                                    '& > :not(style)': { my: 1 },
                                    mx: 'auto',
                                    gap: 2,
                                    borderRadius: 2,
                                    bgcolor: 'white',
                                }}
                                autoComplete="off"
                            >
                                {/* Row 1 */}
                                <Box
                                    display="grid"
                                    gridTemplateColumns="repeat(2, 1fr)"
                                    gap={2}
                                    mb={2}
                                >
                                    {/* Dropdown for Search Results */}
                                    <DynamicDropdown
                                        isOpen={grnDropdownOpen}
                                        onClose={() => setGrnDropdownOpen(false)}
                                        triggerRef={triggerRef}
                                        data={searchResults}
                                        headers={fieldConfig.map((config) => config.header)}
                                        onSelect={handleResultClick}
                                        dropdownWidth={800}
                                        dropdownHeight={400}
                                    />
                                    {/* Supplier ID Field */}
                                    <TextField
                                        ref={triggerRef}
                                        id="xgrnnum"
                                        name="xgrnnum"
                                        label="GRN Number"
                                        InputLabelProps={{
                                            shrink: true, // Ensure the label shrinks above the input
                                        }}
                                        size="small"
                                        value={formData.xgrnnum || ''}
                                        variant={variant}
                                        fullWidth
                                        onChange={(e) => {
                                            handleChange(e);
                                            const query = e.target.value;
                                            const apiSearchUrl = `http://localhost:8080/api/pogrnheader/search?zid=${zid}&text=${query}`;
                                            handleSearch(
                                                e.target.value,
                                                apiSearchUrl,
                                                fieldConfig,
                                                setSearchResults,
                                                setGrnDropdownOpen,
                                                triggerRef,
                                                setDropdownPosition,
                                                { zid }
                                            );
                                        }}
                                        sx={{ gridColumn: 'span 1' }}
                                    />
                                    {/* Company Field */}
                                    <TextField
                                        id="xdate"
                                        name="xdate"
                                        label="Date"
                                        InputLabelProps={{
                                            shrink: true, // Ensure the label shrinks above the input
                                        }}
                                        type="date"
                                        size="small"
                                        value={formData.xdate}
                                        variant={variant}
                                        fullWidth
                                        onChange={handleChange}
                                        sx={{ gridColumn: 'span 1' }}
                                    />

                                </Box>

                                {/* Row 2 */}
                                <Box
                                    display="grid"
                                    gridTemplateColumns="repeat(2, 1fr)"
                                    gap={2}
                                    mb={2}
                                >


                                    <DynamicDropdown
                                        isOpen={supDropdownOpen}
                                        onClose={() => setSupDropdownOpen(false)}
                                        triggerRef={supplierRef}
                                        data={searchResults}
                                        headers={supConfig.map((config) => config.header)}
                                        onSelect={handleResultClick}
                                        dropdownWidth={600}
                                        dropdownHeight={400}
                                    />
                                    {/* Mailing Address */}
                                    <TextField
                                        ref={supplierRef}
                                        id="xcus"
                                        name="xcus"
                                        label="Supplier"
                                        size="small"
                                        value={formData.xcus}
                                        InputLabelProps={{
                                            shrink: true, // Ensure the label shrinks above the input
                                        }}
                                        variant={variant}
                                        fullWidth
                                        sx={{ gridColumn: 'span 1' }}
                                        onChange={(e) => {
                                            handleChange(e);
                                            const query = e.target.value;
                                            const apiSupUrl = `http://localhost:8080/api/cacus/search?zid=${zid}&text=${query}`;
                                            handleSearch(
                                                e.target.value,
                                                apiSupUrl,
                                                supConfig,
                                                setSearchResults,
                                                setSupDropdownOpen,
                                                supplierRef,
                                                setDropdownPosition,
                                                { zid }
                                            );
                                        }}
                                    />
                                    {/* Email */}
                                    <TextField
                                        id="xorg"
                                        name="xorg"
                                        label="Supplier Name"
                                        size="small"
                                        value={formData.xorg}
                                        variant={variant}
                                        InputLabelProps={{
                                            shrink: true, // Ensure the label shrinks above the input
                                        }}
                                        inputProps={{
                                            readOnly: true,
                                        }}
                                        fullWidth
                                        onChange={handleChange}
                                        sx={{ gridColumn: 'span 1' }}
                                    />
                                    {/* Phone */}

                                </Box>

                                {/* Row 3 */}
                                <Box
                                    display="grid"
                                    gridTemplateColumns="repeat(2, 1fr)"
                                    gap={2}
                                    mb={2}
                                >

                                    {/* <TextField
                                    id="xwh"
                                    name="xwh"
                                    label="Store Code"
                                    size="small"
                                    value={formData.xwh}
                                    InputLabelProps={{
                                        shrink: true, // Ensure the label shrinks above the input
                                    }}
                                    variant={variant}
                                    fullWidth
                                    onChange={handleChange}
                                /> */}

                                    <XcodesDropDown
                                        variant={variant}
                                        label="Store"
                                        size="small"
                                        name="xwh"
                                        type="Branch"
                                        onSelect={(value) => handleDropdownSelect("xwh", value)}
                                        value={formData.xwh}
                                        defaultValue=""
                                        InputLabelProps={{
                                            shrink: true, // Ensure the label shrinks above the input
                                        }}
                                    />


                                    {/* Mobile */}
                                    <TextField
                                        id="xlong"
                                        name="xlong"
                                        label="Store Name"
                                        size="small"
                                        value={formData.xlong}
                                        variant={variant}
                                        InputLabelProps={{
                                            shrink: true, // Ensure the label shrinks above the input
                                        }}
                                        inputProps={{
                                            readOnly: true,
                                        }}
                                        fullWidth
                                        onChange={handleChange}
                                    />


                                    {/* Fax */}


                                </Box>
                                <Box
                                    display="grid"
                                    gridTemplateColumns="repeat(3, 1fr)"
                                    gap={2}
                                    mb={2} // margin-bottom
                                >


                                    <Box sx={{ display: 'flex', alignItems: 'center', mt: 2, gridColumn: 'span 1' }}>
                                        <Typography variant="subtitle1" sx={{ fontWeight: 'bold' }}>
                                            Status:
                                        </Typography>
                                        <Typography
                                            variant="subtitle1"
                                            sx={{
                                                marginLeft: 1,
                                                color: status === 'Confirmed' ? 'green' : 'red', // Conditional styling
                                            }}
                                        >
                                            {formData.xstatus}
                                        </Typography>
                                    </Box>

                                    <TextField
                                        id='xref'
                                        name='xref'
                                        label="Challan No"
                                        size="small"
                                        value={formData.xref}
                                        variant={variant}
                                        onChange={handleChange}
                                        InputLabelProps={{
                                            shrink: true, // Ensure the label shrinks above the input
                                        }}
                                        fullWidth
                                        // disabled
                                        required
                                        sx={{ gridColumn: 'span 2' }}

                                    />



                                </Box>

                                <Box
                                    display="grid"
                                    gridTemplateColumns="repeat(3, 1fr)"
                                    gap={2}
                                    mb={2} // margin-bottom
                                >
                                    <TextField
                                        label="Note"
                                        name='xnote'
                                        variant={variant}
                                        size="small"
                                        onChange={handleChange}
                                        value={formData.xnote}
                                        InputLabelProps={{
                                            shrink: true, // Ensure the label shrinks above the input
                                        }}
                                        fullWidth
                                        required
                                        multiline
                                        sx={{ gridColumn: 'span 3' }}
                                    />


                                </Box>

                            </Box>
                        </div>
                    </div>
                </Box >
                <Box sx={{
                    gridColumn: 'span 6',

                    // border: '1px solid #ccc', // Light gray border
                    borderRadius: '8px', // Optional: Rounded corners
                    // padding: 2,
                }}>

                    <SortableList
                        apiUrl={apiBaseUrl}

                        caption="Receive Entry List"
                        columns={[
                            { field: 'xgrnnum', title: 'Item Code', width: '25%', },
                            { field: 'xcus', title: 'Name', width: '25%' },
                            { field: 'xorg', title: 'Supplier Name', width: '40%', align: 'center' },
                            { field: 'xdate', title: 'GRN Date', width: '10%', align: 'center' },
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
            </div >
        </div>
    );
};

export default Pogrndirect;
