import React, { useEffect, useRef } from 'react';
import SideButtons from '../Shared/SideButtons';
import HelmetTitle from '../utility/HelmetTitle';
import { useState } from 'react';  // Make sure to import useState
import Caption from '../utility/Caption';
import Swal from 'sweetalert2';

import SelectField from '../formfield/SelectField';
import Checkbox from '../formfield/Checkbox';

import { Box, FormControl, FormControlLabel, InputLabel, List, ListItem, ListItemText, MenuItem, Select, TextField } from '@mui/material';
import axios from 'axios';



const Store = () => {
    const [searchResults, setSearchResults] = useState([]); // For search results
    const [isTyping, setIsTyping] = useState(false); // To handle typing state
    const [selectedCode, setSelectedCode] = useState(''); // To store selected code
    const [checked, setChecked] = useState(false);
    const [xtypeobj, setXtypeobj] = useState('');
    const [isListOpen, setListOpen] = useState(false)
    const listRef = useRef(null);
    const formRef = useRef(null);
    const [errors, setErrors] = useState({});
    const [zid, setZid] = useState(100000);
    const [xtype, setXtype] = useState('Store');
    
    const [formData, setFormData] = useState({
        zid: zid,
        xtype: 'Store',
        xcode: '',
        xlong: '',
        xemail: '',
        xmadd: '',
        zactive: false,
        xtypeobj: '',
        xphone: '',


    });




    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prev) => ({
            ...prev,
            [name]: value
        }));
        if (name === 'xcode') {

            setIsTyping(true);
            fetchSearchResults(value);
        }
    };



    const fetchSearchResults = async (query) => {
        if (!query) {
            setSearchResults([]);
            setListOpen(false);
            return;
        }
        try {
            const response = await axios.get(
                `http://localhost:8080/api/xcodes/searchtext?zid=${zid}&xtype=${xtype}&searchText=${query}`
            );
            setSearchResults(response.data);
            setListOpen(true); // Open dropdown
        } catch (error) {
            console.error('Error fetching search results:', error);
        }
    };



    const handleResultClick = (result) => {

        const updatedZactive = result.zactive == 1 ? true : false;
        setChecked(updatedZactive)
        console.log(checked)
        setFormData({
            xcode: result.xcode,
            xlong: result.xlong,
            xemail: result.xemail,
            xtype:result.xtype,
            xphone: result.xphone,
            xmadd: result.xmadd,
            xtypeobj: result.xtypeobj,
            zactive: checked
        });
        setListOpen(false);
    };

    useEffect(() => {
        const handleClickOutside = (event) => {
            // if (listRef.current && !listRef.current.contains(event.target)) {
            if (formRef.current && !formRef.current.contains(event.target)) {
                setListOpen(false);
            }
        };

        document.addEventListener('mousedown', handleClickOutside);
        return () => {
            document.removeEventListener('mousedown', handleClickOutside);
        };
    }, []);



    const handleTypeChange = (e) => {

        setFormData({
            ...formData,
            xtypeobj: e.target.value,
        });
    };






    const handleCheckboxChange = (event) => {
        setChecked(event.target.checked ? 1 : 0);

    };




    // Button Handlers 

    const handleAdd = async () => {
        try {
            const response = await axios.post('http://localhost:8080/api/xcodes', { ...formData, zid: zid,xtype:'Store', zactive: checked });
            setErrors({});
            Swal.fire('Success!', 'Store Inserted successfully', 'success');

            // Optionally reset the form
            setFormData({
                xcode: '',
                xlong: '',
                xmadd: '',
                xemail: '',
                xtypeobj: '',
                xphone: '',
               
            });
            setChecked(false);
        } catch (error) {
            if (error.response && error.response.status === 400) {
                setErrors(error.response.data);
                const errorMessages = error.response.data;
               
                Swal.fire({
                    icon: 'error',
                    title: 'Validation Errors',
                    html: errorMessages,
                    confirmButtonText: 'Okay',
                });
            }
        }
    };

    // Update Store
    const handleUpdate = async () => {
        try {
            console.log(formData)
            const response = await axios.put(`http://localhost:8080/api/xcodes?zid=${zid}&xtype=Store&xcode=${formData.xcode}`, {...formData, zid: zid,xtype:'Store', zactive: checked});
            setErrors({});
            Swal.fire('Success!', 'Updated successfully', 'success');

        } catch (error) {

            if (error.response && error.response.status === 400) {
                setErrors(error.response.data);
                const errorMessages = errors
                console.log(errorMessages)
                Swal.fire({
                    icon: 'error',
                    title: 'Validation Errors',
                    html: errorMessages,
                    confirmButtonText: 'Okay',
                });
            }
        }
    };

    // Delete Store
    const handleDelete = async () => {
        try {
            await axios.delete(`http://localhost:8080/api/xcodes?zid=${zid}&xtype=Store&xcode=${formData.xcode}`);
            alert('Store deleted successfully!');
            // console.log('Deleted store with code:', formData.xcode);

            // Optionally reset the form
            setFormData({
                xcode: '',
                xlong: '',
                xmadd: '',
                xphone: '',
                xtypeobj: '',
                xtype: 'Store',
            });
            setChecked(false);
        } catch (error) {
            // console.error('Error deleting store:', error);
            alert('Failed to delete store. Please try again.');
        }
    };

    // Clear Form
    const handleClear = () => {
        setFormData({
            xcode: '',
            xlong: '',
            xmadd: '',
            xphone: '',
            zactive: '',
            xemail:'',
            xtypeobj: '',
            xtype: 'Store',
        });
        setChecked(false);
        alert('Form cleared.');
    };

    // Show Data
    const handleShow = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/api/xcodes/${formData.xcode}`);
            alert('Store data fetched successfully!');
            // console.log('Fetched data:', response.data);

            // Populate the form with the fetched data
            setFormData(response.data);
        } catch (error) {
            // console.error('Error fetching data:', error);
            alert('Failed to fetch store data. Please try again.');
        }
    };










    return (
        <div className=''>
            <HelmetTitle title="Store" />
            <div className='grid grid-cols-12'>
                <div className="">
                    <SideButtons
                        onAdd={handleAdd}
                        onUpdate={handleUpdate}
                        onDelete={handleDelete}
                        onClear={handleClear}
                        onShow={handleShow}
                    />
                </div>



                <div className='col-span-11 '>

                    <div className='grid grid-cols-2'>
                        <div className='border shadow-lg border-black rounded'>
                            <div className="w-full px-2  py-2  mx-auto  ">
                                <Caption title={"Store Entry"} />
                                <Box
                                    component="form"
                                    sx={{
                                        '& > :not(style)': { my: 1 },
                                        // maxWidth: 500,
                                        mx: 'auto',
                                        // p: 3,
                                        // boxShadow: 3,
                                        display: 'grid',
                                        gap: 2,
                                        mt:1,
                                        gridTemplateColumns: 'repeat(3, 1fr)',
                                        borderRadius: 2,

                                        bgcolor: 'white',
                                    }}
                                    noValidate
                                    autoComplete="off"

                                >

                                    {/* {console.log(isListOpen)} */}
                                    {/* Search Results Dropdown */}
                                    {isListOpen && searchResults.length > 0 && (
                                        <div ref={formRef}
                                            style={{
                                                position: 'absolute',
                                                maxHeight: '400px',
                                                width: '600px',
                                                maxWidth: '500px',
                                                overflowY: 'auto',
                                                border: '2px solid black',
                                                borderRadius: '4px',
                                                backgroundColor: '#fff', // Solid white background
                                                zIndex: 100,
                                                boxShadow: '0px 4px 6px rgba(0, 0, 0, 0.1)',
                                            }}
                                        >
                                            {/* Column Headers */}
                                            <div
                                                style={{
                                                    display: 'flex',
                                                    padding: '10px',
                                                    fontWeight: 'bold',
                                                    backgroundColor: '#f0f0f0',
                                                    borderBottom: '1px solid gray',
                                                }}
                                            >
                                                <div style={{ flex: 1, textAlign: 'left' }}>Code</div>
                                                <div style={{ flex: 2, textAlign: 'left' }}>Name</div>
                                                <div style={{ flex: 1, textAlign: 'left' }}>Type</div>
                                            </div>

                                            {/* List Items */}
                                            <List>
                                                {searchResults.map((result, index) => (
                                                    <ListItem
                                                        key={index}
                                                        button
                                                        onClick={() => handleResultClick(result)}
                                                        style={{ display: 'flex' }}
                                                    >
                                                        {/* Columns in List */}
                                                        <div style={{ flex: 1, textAlign: 'left' }}>{result.xcode}</div>
                                                        <div style={{ flex: 2, textAlign: 'left' }}>{result.xlong}</div>
                                                        <div style={{ flex: 1, textAlign: 'left' }}>{result.xtype}</div>
                                                    </ListItem>
                                                ))}
                                            </List>
                                        </div>
                                    )}



                                    <TextField
                                        id="xcode"
                                        name="xcode"
                                        label="Store Code"
                                        value={formData.xcode}
                                        onChange={handleChange}
                                        variant="outlined"
                                        // fullWidth
                                        sx={{ gridColumn: 'span 1' }}

                                    />
                                    <TextField
                                        id="xlong"
                                        name="xlong"
                                        label="Store Name"
                                        value={formData.xlong}
                                        onChange={handleChange}
                                        variant="outlined"
                                        sx={{ gridColumn: 'span 2' }}
                                    />
                                    <TextField
                                        id="xphone"
                                        name="xphone"
                                        label="Phone"
                                        value={formData.xphone}
                                        onChange={handleChange}
                                        variant="outlined"
                                        sx={{ gridColumn: 'span 1' }}
                                    />
                                    <TextField
                                        id="xmadd"
                                        name="xmadd"
                                        label="Address"
                                        value={formData.xmadd}
                                        onChange={handleChange}
                                        variant="outlined"
                                        sx={{ gridColumn: 'span 2' }}
                                    />

                                    <FormControl fullWidth sx={{ gridColumn: 'span 1' }}>
                                        <InputLabel id="demo-simple-select-label">Store Type</InputLabel>
                                        <Select
                                            labelId="demo-simple-select-label"
                                            id="demo-simple-select"
                                            name="xtypeobj"
                                            value={formData.xtypeobj}
                                            label="Store Type"
                                            onChange={handleTypeChange}
                                            // sx={{ gridColumn: 'span 1' }}
                                            sx={{ width: '100%' }}
                                        >
                                            <MenuItem value={''}>Select</MenuItem>
                                            <MenuItem value={'Physical'}>Physical</MenuItem>
                                            <MenuItem value={'Virtual'}>Virtual</MenuItem>

                                        </Select>
                                    </FormControl>

                                    <TextField
                                        id="xemail"
                                        name="xemail"
                                        label="Email"
                                        value={formData.xemail}
                                        onChange={handleChange}
                                        variant="outlined"
                                        sx={{ gridColumn: 'span 2' }}
                                    />



                                    <Checkbox
                                        checked={checked}
                                        onChange={handleCheckboxChange}
                                        name="Activate?"
                                        color="primary"  // You can use 'primary', 'secondary', 'default' or 'error'
                                    />




                                </Box>

                            </div>
                        </div>
                        <div>

                        </div>
                    </div>


                </div>
            </div>
        </div>
    );
};

export default Store;