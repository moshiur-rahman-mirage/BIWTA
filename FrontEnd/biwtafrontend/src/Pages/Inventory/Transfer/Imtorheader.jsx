import React, { useCallback, useEffect, useRef, useState } from 'react';
import {
    TextField,
    Box,
    Typography,
    Button,
    Modal,
} from '@mui/material';
import { useAuth } from '../../../Provider/AuthProvider';
import LoadingPage from '../../Loading/Loading';
import HelmetTitle from '../../../utility/HelmetTitle';
import SideButtons from '../../../Shared/SideButtons';
import Caption from '../../../utility/Caption';
import DynamicDropdown from '../../../ReusableComponents/DynamicDropdown';
import XlongDropDown from '../../../ReusableComponents/XlongDropDown';
import { handleApiRequest } from '../../../utility/handleApiRequest';
import { addFunction } from '../../../ReusableComponents/addFunction';
import { handleSearch } from '../../../ReusableComponents/handleSearch';
import { convertDate } from '../../../utility/convertDate';
import axiosInstance from '../../../Middleware/AxiosInstance';
import Swal from 'sweetalert2';
import { validateForm } from '../../../ReusableComponents/validateForm';
import SortableList from '../../../ReusableComponents/SortableList';
import Imtordetail from './Imtordetail';


const Imtorheader = () => {
    // Authentication Context
    const { zid, zemail } = useAuth();
    console.log(zemail)
    const xwh = localStorage.getItem("xwh");
    console.log(zid, zemail)
    const [formData, setFormData] = useState({
        zid: zid,
        xtornum: '',
        xstatustor: '',
        xdate: new Date().toISOString().split('T')[0],
        xfwh: xwh,
        xfwhdesc: '',
        xtwh: '',
        xtwhdesc: '',
        xnote: '',
        xlong: ''


    });
    const [formErrors, setFormErrors] = useState({});

    const [refreshTrigger, setRefreshTrigger] = useState(false);
    const [searchResults, setSearchResults] = useState([]);
    const [isDropdownOpen, setDropdownOpen] = useState(false);

    const [supDropdownOpen, setSupDropdownOpen] = useState(false);
    const [grnDropdownOpen, setGrnDropdownOpen] = useState(false);
    const [directFetch, setDirectFetch] = useState('');
    const [dropdownPosition, setDropdownPosition] = useState({ top: 0, left: 0 });
    const [loading, setLoading] = useState(true);
    const [status, setStatus] = useState("Inactive");
    const [refreshCallback, setRefreshCallback] = useState(null);
    const [selectedItem, setSelectedItem] = useState(null);
    const [updateCount, setUpdateCount] = useState(0);
    const [sortField, setSortField] = useState('name'); // Default sorting field
    const [sortOrder, setSortOrder] = useState('asc');
    const [open, setOpen] = useState(false);
    const apiListUrl = `api/imtordetails?action=requisition/${zid}/${formData.xtornum}`


    // Handle dropdown value change
    const handleStatusChange = (event) => {
        setStatus(event.target.value);
    };

    // References
    const triggerRef = useRef(null);
    const supplierRef = useRef(null);
    const variant = 'standard';
    const apiBaseUrl = `api/imtorheader`;

    const fieldConfig = [
        { header: 'Requisition Number', field: 'xtornum' },
        { header: 'Date', field: 'xdate' },
        { header: 'From Store', field: 'xfwh' },
        { header: 'Store Name', field: 'xfwhdesc' },
        { header: 'To Store', field: 'xtwh' },
        { header: 'Store Name', field: 'xtwhdesc' },
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
        // console.log(e.target)
        const { name, value } = e.target;
        setFormData((prev) => {
            if (prev[name] !== value) {
                return { ...prev, [name]: value };
            }
            return prev;
        });
    };

    const handleResultClick = (result) => {
        setDirectFetch('Yes')
        setFormData((prev) => ({
            ...prev,
            ...result,
            zid,
        }));
        setSupDropdownOpen(false);
        setGrnDropdownOpen(false);

    };

    const handleDropdownSelect = (fieldName, value) => {
        console.log(fieldName);

        setFormData((prevState) => {
            const updatedState = { ...prevState, [fieldName]: value };

            if (fieldName === 'xfwh') {
                updatedState.xfwh = value.xcode;
                updatedState.xfwhdesc = value.xlong;
            }

            if (fieldName === 'xtwh') {
                updatedState.xtwh = value.xcode;
                updatedState.xtwhdesc = value.xlong;
            }

            return updatedState;
        });
    };


    console.log(directFetch)
    useEffect(() => {
        if (selectedItem) {
            // console.log(convertDate(selectedItem.xdate))
            setFormData({
                ...selectedItem,
                xdate: convertDate(selectedItem.xdate)
            });
        }
    }, [selectedItem]);


    useEffect(() => {
        if (refreshCallback && formData.xtornum) {
            refreshCallback(); // Trigger the refresh callback from SortableList
        }
    }, [formData.xtornum, refreshCallback]);


    useEffect(() => {
        setRefreshTrigger(true);
    }, [updateCount]);


    const handleAdd = async () => {
        const errors = validateForm(formData, ['xfwh']);
        if (Object.keys(errors).length > 0) {
            setFormErrors(errors);
            Swal.fire({
                icon: 'error',
                title: 'Invalid Input',
                text: 'Please fix the errors before proceeding.',
            });
            return;
        }

        const endpoint = 'api/imtorheader?action=requisition';
        const data = {
            ...formData,
            zauserid: zemail,
            zid: zid
        };

        addFunction(data, endpoint, 'POST', (response) => {
            if (response && response.xtornum) {

                setFormData((prev) => ({ ...prev, xtornum: response.xtornum, xstatustor: response.xstatustor }));
                setUpdateCount(prevCount => prevCount + 1);
                setFormErrors({});
            } else {
                // alert('Supplier added successfully.');
            }
        });
    };



    const handleItemSelect = useCallback((item) => {
        console.log(item)
        setFormData((prev) => ({
            ...prev,
            xtornum: item.xtornum, xfwh: item.xfwh, xlong: item.xlong, xfwhdesc: item.xfwhdesc, xstatustor: item.xstatustor, xtwh: item.xtwh
        }));
    }, []);

    const handleClear = () => {
        setFormData({
            zid: zid,
            xtornum: '',
            xstatustor: '',
            xdate: new Date().toISOString().split('T')[0],
            xfwh: xwh,
            xfwhdesc: '',
            xtwh: '',
            xtwhdesc: '',
            xnote: '',
            xlong: ''

        });
        alert('Form cleared.');
    };

    const handleDelete = async () => {
        // console.log(formData)
        const endpoint = `api/imtorheader/${zid}/${formData.xtornum}`;
        await handleApiRequest({
            endpoint,
            method: 'DELETE',
            onSuccess: (response) => {
                setFormData({
                    zid: zid,
                    xtornum: '',
                    xstatustor: '',
                    xdate: new Date().toISOString().split('T')[0],
                    xfwh: xwh,
                    xfwhdesc: '',
                    xnote: '',
                    xlong: ''

                });
                setUpdateCount(prevCount => prevCount + 1);

            },
        });
    };


    const handleUpdate = async () => {
        const errors = validateForm(formData, ['xfwh']);
        if (Object.keys(errors).length > 0) {
            setFormErrors(errors);
            Swal.fire({
                icon: 'error',
                title: 'Invalid Input',
                text: 'Please fix the errors before proceeding.',
            });
            return;
        }
        setUpdateCount(prevCount => prevCount + 1);
        const endpoint = `api/imtorheader/${zid}/${formData.xtornum}`;
        const data = {
            ...formData,
            zid: zid
        };

        await handleApiRequest({
            endpoint,
            data,
            method: 'PATCH',
        });
        setFormErrors({});
    };

    const handleOpen = () => {
        document.body.style.paddingRight = `${window.innerWidth - document.documentElement.clientWidth}px`;
        document.body.style.overflow = "hidden";
        setOpen(true);
    };

    const handleClose = () => {
        document.body.style.paddingRight = "";
        document.body.style.overflow = "";
        setOpen(false);
    };



    const handleConfirm = async () => {
        if (window.confirm('Confirm This GRN?')) {
            setStatus("Processing...");
            const params = {
                zid: 100000,
                zemail: zemail,
                xtornum: formData.xtornum,
                xdate: formData.xdate,
                xfwh: formData.xfwh,
                len: 8
            };

            try {

                const response = await axiosInstance.post("/api/imtorheader/confirmSR", params);
                setStatus(response.data);

                Swal.fire({
                    icon: 'success',
                    title: 'Success!',
                    text: 'Operation completed successfully'
                });

            } catch (error) {
                // Handle error response
                setStatus("Error: " + (error.response?.data || error.message));

                Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: 'Something went wrong. Please try again.'
                });
            }
        }
    };




    if (loading) {
        return <LoadingPage />;
    }

    return (

        <div>

            <div className='grid grid-cols-12 gap-1 mb-2'>
                <div className='col-span-1'>
                </div>
                <Button
                    onClick={handleOpen}
                    variant='outlined'
                    sx={{
                        marginLeft: 1,
                        paddingX: 1,
                        paddingY: 0.5,
                        height: '2.5rem',
                        '&:hover': {
                            backgroundColor: '#F59E0B', // Yellow-600
                        },
                    }}
                    size="medium"
                >
                    Detail
                </Button>
                <Button
                    onClick={handleConfirm}
                    variant='outlined'
                    sx={{
                        marginLeft: 1,
                        paddingX: 2, // equivalent to Tailwind's px-2
                        paddingY: 0.5, // equivalent to Tailwind's py-0.5
                      
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
                <HelmetTitle title="Store Requisition(Transfer)" />

                {/* Sidebar with Action Buttons */}
                <div className="col-span-1">
                    <SideButtons
                        onAdd={handleAdd}
                        onClear={handleClear}
                        onUpdate={handleUpdate}
                        onDelete={handleDelete}
                    />
                </div>


                <Modal
                    open={open}
                    onClose={handleClose}
                    disablePortal
                    disableEnforceFocus
                    disableAutoFocus
                    disableScrollLock
                >
                    <Box sx={{
                        position: "absolute",
                        top: "50%",
                        left: "50%",
                        transform: "translate(-50%, -50%)",
                        width: "1300px", // Fixed width
                        height: "500px", // Fixed height
                        bgcolor: "background.paper",
                        border: "2px solid #000",
                        boxShadow: 24,
                        borderRadius: "5px",
                        p: 4,
                        zIndex: 10,
                    }}>
                        <Imtordetail xtornum={formData.xtornum} />
                    </Box>
                </Modal>
                {/* Modal */}

                <Box sx={{
                    gridColumn: 'span 5',
                    // border: '1px solid #ccc', // Light gray border
                    borderRadius: '8px', // Optional: Rounded corners
                    // padding: 2,
                }}>



                    <div className="shadow-lg rounded">
                        <div className="w-full  py-2 pt-0 mx-auto ">
                            <Caption title="Store Requisition(Transfer)" />
                            <Box
                                component="form"
                                sx={{
                                    '& > :not(style)': { my: 1 },
                                    mx: 'auto',
                                    gap: 2,
                                    px: 1,
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
                                        id="xtornum"
                                        name="xtornum"
                                        label="SR Number"
                                        InputLabelProps={{
                                            shrink: true,
                                            sx: {
                                                fontWeight: 600,
                                            },
                                        }}
                                        size="small"
                                        value={formData.xtornum || ''}
                                        variant={variant}
                                        fullWidth
                                        onChange={(e) => {
                                            handleChange(e);
                                            const query = e.target.value;
                                            const apiSearchUrl = `api/imtorheader/search?action=Requisition&zid=${zid}&text=${query}`;
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
                                        sx={{
                                            gridColumn: 'span 1',
                                            '& .MuiInputBase-input': {
                                                // Remove unnecessary padding
                                                // Ensure the input spans the full height
                                                fontSize: '.9rem'
                                            },
                                        }}
                                    />
                                    {/* Company Field */}
                                    <TextField
                                        id="xdate"
                                        name="xdate"
                                        label="SR Date"
                                        InputLabelProps={{
                                            shrink: true,
                                            sx: {
                                                fontWeight: 600,
                                            },
                                        }}
                                        type="date"
                                        size="small"
                                        value={formData.xdate}
                                        variant={variant}
                                        fullWidth
                                        onChange={handleChange}
                                        sx={{
                                            gridColumn: 'span 1',
                                            '& .MuiInputBase-input': {
                                                // Remove unnecessary padding
                                                // Ensure the input spans the full height
                                                fontSize: '.9rem'
                                            },
                                        }}
                                    />

                                </Box>


                                <Box
                                    display="grid"
                                    gridTemplateColumns="repeat(2, 1fr)"
                                    gap={2}
                                    mb={2}
                                >



                                    <XlongDropDown
                                        variant={variant}
                                        label="From Store"
                                        size="small"
                                        name="xfwh"
                                        type="Branch"
                                        onSelect={(value) => handleDropdownSelect("xfwh", value)}
                                        value={formData.xfwh}
                                        defaultValue=""
                                        error={!!formErrors.xfwh}  // Check if there's an error for this field
                                        helperText={formErrors.xfwh}
                                        withXlong="false"
                                        InputLabelProps={{
                                            shrink: true,
                                            sx: {
                                                fontWeight: 600,
                                            },
                                        }}
                                        sx={{
                                            pointerEvents: 'none', // Disables interaction with the dropdown
                                        }}
                                    />




                                    {/* Mobile */}
                                    <TextField
                                        id="xfwhdesc"
                                        name="xfwhdesc"
                                        label="Store Name"
                                        size="small"
                                        value={formData.xfwhdesc}
                                        variant={variant}
                                        hidden
                                        InputLabelProps={{
                                            shrink: true,
                                            sx: {
                                                fontWeight: 600,
                                            },
                                        }}
                                        inputProps={{
                                            readOnly: true,
                                        }}
                                        onChange={handleChange}
                                        sx={{
                                            display: 'none', // Completely hides the TextField
                                            '& .MuiInputBase-input': {
                                                fontSize: '.9rem',
                                            },
                                        }}
                                    />





                                    <XlongDropDown
                                        variant={variant}
                                        label="To Store"
                                        size="small"
                                        name="xtwh"
                                        type="Branch"
                                        onSelect={(value) => handleDropdownSelect("xtwh", value)}
                                        value={formData.xtwh}
                                        defaultValue=""
                                        error={!!formErrors.xtwh}  // Check if there's an error for this field
                                        helperText={formErrors.xtwh}
                                        withXlong="false"
                                        InputLabelProps={{
                                            shrink: true,
                                            sx: {
                                                fontWeight: 600,
                                            },
                                        }}
                                    />


                                    {/* Mobile */}
                                    <TextField
                                        id="xtwhdesc"
                                        name="xtwhdesc"
                                        label="Store Name"
                                        size="small"
                                        value={formData.xtwhdesc}
                                        variant={variant}
                                        hidden
                                        InputLabelProps={{
                                            shrink: true,
                                            sx: {
                                                fontWeight: 600,
                                            },
                                        }}
                                        inputProps={{
                                            readOnly: true,
                                        }}
                                        onChange={handleChange}
                                        sx={{
                                            display: 'none', // Completely hides the TextField
                                            '& .MuiInputBase-input': {
                                                fontSize: '.9rem',
                                            },
                                        }}
                                    />

                                </Box>
                                <Box
                                    display="grid"
                                    gridTemplateColumns="repeat(3, 1fr)"
                                    gap={2}
                                    mb={2} // margin-bottom
                                >


                                    <Box sx={{ display: 'flex', alignItems: 'center', mt: 2, gridColumn: 'span 1' }}>
                                        <Typography variant="subtitle1" sx={{ fontWeight: 400, fontSize: '1rem' }}>
                                            Status:
                                        </Typography>
                                        <Typography
                                            variant="subtitle1"
                                            sx={{
                                                marginLeft: 1,
                                                color: status === 'Confirmed' ? 'green' : 'red', // Conditional styling
                                            }}
                                        >
                                            {formData.xstatustor}
                                        </Typography>
                                    </Box>
                                    <div>

                                    </div>



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
                                            shrink: true,
                                            sx: {
                                                fontWeight: 600,
                                            },
                                        }}
                                        fullWidth
                                        required
                                        multiline
                                        sx={{
                                            gridColumn: 'span 3',
                                            '& .MuiInputBase-input': {
                                                // Remove unnecessary padding
                                                // Ensure the input spans the full height
                                                fontSize: '.9rem'
                                            },
                                        }}
                                    />


                                </Box>

                            </Box>
                        </div>
                    </div>
                </Box >
                <Box sx={{
                    gridColumn: 'span 6',
                    px: 0,
                    // border: '1px solid #ccc', // Light gray border
                    borderRadius: '8px', // Optional: Rounded corners
                    // padding: 2,
                }}>

                    <SortableList
                        directFetch='Yes'
                        apiUrl={apiBaseUrl}
                        isFolded={false}
                        caption="Store Requisition List"
                        columns={[
                            { field: 'xtornum', title: 'Requisition Number', width: '25%', align: 'left' },
                            { field: 'xfwh', title: 'Store', width: '25%', align: 'left' },
                            { field: 'xfwhdesc', title: 'Store Name', width: '40%', align: 'left' },
                            { field: 'xdate', title: 'Date', width: '10%', align: 'left' },
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
                        sortField="xtornum"
                        additionalParams={{ zid: zid, xstatustor: 'Approved', user: zemail,xtrn:'SR--' }}
                        captionFont=".9rem"
                        xclass="py-4 pl-2"
                        bodyFont=".8rem"
                        mt={0}
                        page={1}
                    />
                    <SortableList



                        apiUrl={`api/imtordetails/${zid}/${formData.xtornum}`}
                        isFolded={false}
                        caption="Store Requisition Detail List"
                        columns={[
                            { field: 'xrow', title: 'Serial', width: '5%', align: 'left' },
                            { field: 'xitem', title: 'Item', width: '10%', align: 'left' },
                            { field: 'xdesc', title: 'Item Code', width: '65%', align: 'left' },
                            { field: 'xqtyord', title: 'Required Quantity', width: '65%', align: 'left' },

                        ]}
                        // onItemSelect={handleItemSelect}
                        onRefresh={(refresh) => {
                            if (formData.xtornum) {
                                refresh(); // Trigger the refresh only when xtornum is available
                            }
                        }}
                        pageSize={10}
                        // onSortChange={handleSortChange}
                        sortField="xtornum"
                        additionalParams={{}}
                        captionFont=".9rem"
                        xclass="py-0 pl-2"
                        bodyFont=".7rem"
                        mt={2}
                        page={1}
                    // isModal
                    />
                </Box>
            </div >
        </div>
    );
};

export default Imtorheader;
