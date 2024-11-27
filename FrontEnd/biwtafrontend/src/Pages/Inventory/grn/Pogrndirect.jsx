import React, { useCallback, useEffect, useRef, useState } from 'react';
import {
    TextField,
    Box,
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
        xstatus: '',
        xnote: '',


    });
    const [refreshTrigger, setRefreshTrigger] = useState(false);
    const [searchResults, setSearchResults] = useState([]);
    const [isDropdownOpen, setDropdownOpen] = useState(false);
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

    const variant = 'standard';
    const apiBaseUrl = `http://localhost:8080/api/pogrnheader`;
    console.log(apiBaseUrl)
    const fieldConfig = [
        { header: 'GRN Number', field: 'xgrnnum' },
        { header: 'Date', field: 'xdate' },
        { header: 'Supplier', field: 'xcus' },
        { header: 'Challan', field: 'xref' },
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
        setDropdownOpen(false);
    };

    const handleDropdownSelect = (fieldName, value) => {
        setFormData((prevState) => ({
            ...prevState,
            [fieldName]: value,
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
            if (response && response.xitem) {
                console.log("POSt called")
                console.log(response)
                setFormData((prev) => ({ ...prev, xitem: response.xitem }));
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
            xlong:'',
            xorg:''

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
                    xlong:'',
                    xorg:''

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
                    <div className="w-full px-4 py-4 pt-0 mx-auto">
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
                                    isOpen={isDropdownOpen}
                                    onClose={() => setDropdownOpen(false)}
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
                                    value={formData.xgrnnum}
                                    variant={variant}
                                    fullWidth
                                    onChange={(e) => {
                                        handleChange(e);
                                        handleSearch(
                                            e.target.value,
                                            apiBaseUrl,
                                            fieldConfig,
                                            setSearchResults,
                                            setDropdownOpen,
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
                                {/* Mailing Address */}
                                <TextField
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
                                    onChange={handleChange}
                                    sx={{ gridColumn: 'span 1' }}
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

                                <TextField
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

                                <TextField
                                    id="xstatus"
                                    name="xstatus"
                                    label="Approval Status"
                                    size="small"
                                    value={formData.xstatus}
                                    InputLabelProps={{
                                        shrink: true, // Ensure the label shrinks above the input
                                      }}
                                    variant={variant}
                                    fullWidth
                                    onChange={handleChange}
                                />

                                <TextField
                                    label="GRN Status"
                                    name='xstatusdoc'
                                    variant={variant}
                                    size="small"
                                    onChange={handleChange}
                                    InputLabelProps={{
                                        shrink: true, // Ensure the label shrinks above the input
                                      }}
                                    value={formData.xstatusdoc}
                                    fullWidth

                                />
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
                                    sx={{ gridColumn: 'span 1' }}

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
                    caption="Item List"
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
                    additionalParams={{zid:zid, xstatus: 'Confirmed' }}
                    captionFont=".9rem"
                    xclass="py-4 pl-2"
                    bodyFont=".8rem"
                    mt={0}
                    page={1}
                />
            </Box>
        </div >
    );
};

export default Pogrndirect;
