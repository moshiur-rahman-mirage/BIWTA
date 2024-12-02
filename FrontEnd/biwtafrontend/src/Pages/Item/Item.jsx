import React, { useCallback, useEffect, useRef, useState } from 'react';
import {
    TextField,
    Box,

    Checkbox,
} from '@mui/material';
import HelmetTitle from '../../utility/HelmetTitle';
import SideButtons from '../../Shared/SideButtons';
import Caption from '../../utility/Caption';
import DynamicDropdown from '../../ReusableComponents/DynamicDropdown';
import { useAuth } from '../../Provider/AuthProvider';
import { handleApiRequest } from '../../utility/handleApiRequest';
import { addFunction } from '../../ReusableComponents/addFunction';
import { handleSearch } from '../../ReusableComponents/handleSearch';
import LoadingPage from '../Loading/Loading';
import XcodesDropDown from '../../ReusableComponents/XcodesDropDown';
import GenericList from '../../ReusableComponents/GenericList';
import { dark } from '@mui/material/styles/createPalette';
import SearchableList from '../../ReusableComponents/SearchableList';
import SortableList from '../../ReusableComponents/SortableList';

const Item = () => {
    // Authentication Context
    const { zid, zemail } = useAuth();
    // State Management
    const [formData, setFormData] = useState({
        zid: 100000,
        zauserid: '',
        xitem: '',
        xdesc: '',
        xunit: '',
        xunitpur: '',
        xcfpur: '',
        xgitem: '',
        xcatitem: '',
        xrate: '',
        xprodnature: '',
        xgenericname: '',
        xgenericdesc: '',
        xdrugtype: '',
        xstrength: '',
        xroute: '',
        xbatmg: '',
        xreordqty: ''


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

    // Configuration
    const variant = 'standard';
    const apiBaseUrl = `api/products/items/${zid}`;
    
    const fieldConfig = [
        { header: 'ID', field: 'xitem' },
        { header: 'Name', field: 'xdesc' },
        { header: 'Generic Name', field: 'xgenericname' },
        { header: 'Unit', field: 'xunit' },
    ];






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

    const handleSortChange = (field) => {
        // Toggle sorting order if the same field is clicked
        setSortOrder((prevOrder) => (field === sortField && prevOrder === 'asc' ? 'desc' : 'asc'));
        setSortField(field);
    };

    const handleDropdownSelect = (fieldName, value) => {
        setFormData((prevState) => ({
            ...prevState,
            [fieldName]: value,
        }));
    };



    useEffect(() => {
        if (selectedItem) {
            
            setFormData({
                ...selectedItem
            });
        }
    }, [selectedItem]);


    useEffect(() => {
        setRefreshTrigger(true);
    }, [updateCount]);




    const handleAdd = async () => {

        const endpoint = 'api/products';
        const data = {
            ...formData,
            zauserid: zemail,
            zid: zid
        };
        addFunction(data, endpoint, 'POST', (response) => {
            if (response && response.xitem) {
              
                setFormData((prev) => ({ ...prev, xitem: response.xitem }));
                setUpdateCount(prevCount => prevCount + 1);
            } else {
                // alert('Supplier added successfully.');
            }
        });
    };






    const handleItemSelect = useCallback((item) => {
      
        setSelectedItem(item);
    }, []);

    const handleClear = () => {
        setFormData({
            zid: zid,
            zauserid: '',
            xitem: '',
            xdesc: '',
            xunit: '',
            xunitpur: '',
            xcfpur: '',
            xgitem: '',
            xcatitem: '',
            xrate: '',
            xprodnature: '',
            xgenericname: '',
            xgenericdesc: '',
            xdrugtype: '',
            xstrength: '',
            xroute: '',
            xbatmg: '',
            xreordqty: ''

        });
        alert('Form cleared.');
    };

    const handleDelete = async () => {
     
        const endpoint = `api/products/${zid}/${formData.xitem}`;
        await handleApiRequest({
            endpoint,
            method: 'DELETE',
            onSuccess: (response) => {
                setFormData({
                    zid: zid,
                    zauserid: '',
                    xitem: '',
                    xdesc: '',
                    xunit: '',
                    xunitpur: '',
                    xcfpur: '',
                    xgitem: '',
                    xcatitem: '',
                    xrate: '',
                    xprodnature: '',
                    xgenericname: '',
                    xgenericdesc: '',
                    xdrugtype: '',
                    xstrength: '',
                    xroute: '',
                    xbatmg: '',
                    xreordqty: ''

                });
                setUpdateCount(prevCount => prevCount + 1);

            },
        });
    };


    const handleUpdate = async () => {
        setUpdateCount(prevCount => prevCount + 1);
        const endpoint = `api/products/${zid}/${formData.xitem}`;
        const data = {
            ...formData,
            zid: zid
        };
     

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
            <HelmetTitle title="Product Entry" />

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
                gridColumn: 'span 6',
                // border: '1px solid #ccc', // Light gray border
                borderRadius: '8px', // Optional: Rounded corners
                // padding: 2,
            }}>
                <div className="shadow-lg rounded">
                    <div className="w-full px-4 pt-0 py-4 mx-auto">
                        <Caption title="Product Entry" />
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
                                gridTemplateColumns="repeat(3, 1fr)"
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
                                    id="xitem"
                                    name="xitem"
                                    label="Item Code"
                                    size="small"
                                    value={formData.xitem}
                                    variant={variant}
                                    fullWidth
                                    onChange={(e) => {
                                        handleChange(e);
                                        const query = e.target.value;
                                        const apiSearchUrl = `api/products/search?zid=${zid}&text=${query}`;
                                        handleSearch(
                                            e.target.value,
                                            apiSearchUrl,
                                            fieldConfig,
                                            setSearchResults,
                                            setDropdownOpen,
                                            triggerRef,
                                            setDropdownPosition,
                                            query
                                        );
                                    }}
                                    sx={{ gridColumn: 'span 1' }}
                                />
                                {/* Company Field */}
                                <TextField
                                    id="xdesc"
                                    name="xdesc"
                                    label="Name"
                                    size="small"
                                    value={formData.xdesc}
                                    variant={variant}
                                    fullWidth
                                    onChange={handleChange}
                                    sx={{ gridColumn: 'span 2' }}
                                />
                            </Box>

                            {/* Row 2 */}
                            <Box
                                display="grid"
                                gridTemplateColumns="repeat(3, 1fr)"
                                gap={2}
                                mb={2}
                            >
                                {/* Mailing Address */}
                                <TextField
                                    id="xgenericname"
                                    name="xgenericname"
                                    label="Generic Name"
                                    size="small"
                                    value={formData.xgenericname}
                                    variant={variant}
                                    fullWidth
                                    onChange={handleChange}
                                    sx={{ gridColumn: 'span 1' }}
                                />
                                {/* Email */}
                                <TextField
                                    id="xgenericdesc"
                                    name="xgenericdesc"
                                    label="Generic Description"
                                    size="small"
                                    value={formData.xgenericdesc}
                                    variant={variant}
                                    fullWidth
                                    onChange={handleChange}
                                    sx={{ gridColumn: 'span 2' }}
                                />
                                {/* Phone */}

                            </Box>

                            {/* Row 3 */}
                            <Box
                                display="grid"
                                gridTemplateColumns="repeat(3, 1fr)"
                                gap={2}
                                mb={2}
                            >

                                <TextField
                                    id="xdrugtype"
                                    name="xdrugtype"
                                    label="Drug Type"
                                    size="small"
                                    value={formData.xdrugtype}
                                    variant={variant}
                                    fullWidth
                                    onChange={handleChange}
                                />
                                {/* Mobile */}
                                <TextField
                                    id="xroute"
                                    name="xroute"
                                    label="Route"
                                    size="small"
                                    value={formData.xroute}
                                    variant={variant}
                                    fullWidth
                                    onChange={handleChange}
                                />

                                <TextField
                                    id="xstrength"
                                    name="xstrength"
                                    label="Strength"
                                    size="small"
                                    value={formData.xstrength}
                                    variant={variant}
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
                                    label="Group"
                                    name='xgitem'
                                    variant={variant}
                                    size="small"
                                    onChange={handleChange}
                                    value={formData.xgitem}
                                    fullWidth
                                    required
                                />
                                <TextField

                                    id='xcatitem'
                                    name='xcatitem'
                                    label="Category"
                                    size="small"
                                    value={formData.xcatitem}
                                    variant={variant}
                                    onChange={handleChange}
                                    fullWidth
                                    // disabled
                                    required
                                    sx={{ gridColumn: 'span 1' }}

                                />

                                <TextField
                                    label="Conversion Factor"
                                    name='xcfpur'
                                    variant={variant}
                                    size="small"
                                    onChange={handleChange}
                                    value={formData.xcfpur}
                                    fullWidth
                                    required
                                />

                            </Box>

                            <Box
                                display="grid"
                                gridTemplateColumns="repeat(3, 1fr)"
                                gap={2}
                                mb={2} // margin-bottom
                            >
                                <TextField
                                    label="Unit"
                                    name='xunit'
                                    variant={variant}
                                    size="small"
                                    onChange={handleChange}
                                    value={formData.xunit}
                                    fullWidth
                                    required
                                />
                                <TextField
                                    label="Purchase Unit"
                                    name='xunitpur'
                                    variant={variant}
                                    size="small"
                                    onChange={handleChange}
                                    value={formData.xunitpur}
                                    fullWidth

                                />

                                {/* <Checkbox
                                    // checked={checked}
                                    //onChange={handleCheckboxChange}
                                    name="Activate?"
                                    color="primary"  // You can use 'primary', 'secondary', 'default' or 'error'
                                /> */}

                            </Box>










                        </Box>
                    </div>
                </div>
            </Box >
            <Box sx={{
                gridColumn: 'span 5',

                // border: '1px solid #ccc', // Light gray border
                borderRadius: '8px', // Optional: Rounded corners
                // padding: 2,
            }}>

                <SortableList
                    apiUrl={apiBaseUrl}
                    caption="Item List"
                    columns={[
                        { field: 'xitem', title: 'Item Code', width: '35%', },
                        { field: 'xdesc', title: 'Name', width: '45%' },
                        { field: 'xroute', title: 'Route', width: '20%', align: 'center' },
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
                    sortField="xitem"
                    captionFont=".9rem"
                    bodyFont=".8rem"
                    xclass="py-4 pl-2"
                    mt={0}
                    page={1}
                  
                    
                />
            </Box>
        </div >
    );
};

export default Item;
