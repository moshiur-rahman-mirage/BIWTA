import React from 'react';
import SideButtons from '../Shared/SideButtons';
import HelmetTitle from '../utility/HelmetTitle';
import { useState } from 'react';  // Make sure to import useState
import Caption from '../utility/Caption';

import SelectField from '../formfield/SelectField';
import Checkbox from '../formfield/Checkbox';

import { Box, FormControl, FormControlLabel, InputLabel, List, ListItem, ListItemText, MenuItem, Select, TextField } from '@mui/material';
import axios from 'axios';

const zid=100000;

const Store = () => {

    const [formData, setFormData] = useState({
        zid:'',
        xtype: 'Store',
        xcode: '',
        xlong: '',
        xemail:'',
        xmadd: '',
        xtypeobj:'',
        xphone: '',
        

    });


    const [searchResults, setSearchResults] = useState([]); // For search results
    const [isTyping, setIsTyping] = useState(false); // To handle typing state
    const [selectedCode, setSelectedCode] = useState(''); // To store selected code

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
            return;
        }

        try {
            const response = await axios.get(
                `http://localhost:8080/api/xcodes/search?zid=${zid}&xtype=Store&xcode=${query}`
            );

            setSearchResults(response.data); // Assuming response is an array of results
            setIsTyping(false);
        } catch (error) {
            console.error('Error fetching search results:', error);
            setIsTyping(false);
        }
    };

    const handleResultClick = (result) => {
        setFormData((prev) => ({
            ...prev,
            xcode: result.code, // Assuming `result.code` is the field
        }));
        setSelectedCode(result.code);
        setSearchResults([]); // Clear the dropdown after selection
    };



    const [type, setType] = React.useState('');

    const handleTypeChange = (e) => {
        setType(e.target.value);
    };

    const [checked, setChecked] = useState(false);

    const handleCheckboxChange = (event) => {
        setChecked(event.target.checked);
    };




    // Button Handlers 

    const handleAdd = async () => {
        try {
            const response = await axios.post('http://localhost:8080/api/xcodes', formData);
            alert('Store added successfully!');
            console.log('Data added:', response.data);

            // Optionally reset the form
            setFormData({
                xcode: '',
                xlong: '',
                xmadd: '',
                xemail:'',
                xtypeobj:'',
                xphone: '',
                xtype: 'Store',
            });
            setChecked(false);
        } catch (error) {
            console.error('Error adding store:', error);
            alert('Failed to add store. Please try again.');
        }
    };

    // Update Store
    const handleUpdate = async () => {
        try {
            console.log(formData)
            const response = await axios.put(`http://localhost:8080/api/xcodes?zid=${zid}&xtype=Store&xcode=${formData.xcode}`, formData);
            alert('Store updated successfully!');
            console.log('Data updated:', response.data);
        } catch (error) {
            console.error('Error updating store:', error);
            alert('Failed to update store. Please try again.');
        }
    };

    // Delete Store
    const handleDelete = async () => {
        try {
            await axios.delete(`http://localhost:8080/api/xcodes?zid=${zid}&xtype=Store&xcode=${formData.xcode}`);
            alert('Store deleted successfully!');
            console.log('Deleted store with code:', formData.xcode);

            // Optionally reset the form
            setFormData({
                xcode: '',
                xlong: '',
                xmadd: '',
                xphone: '',
                xtypeobj:'',
                xtype: 'Store',
            });
            setChecked(false);
        } catch (error) {
            console.error('Error deleting store:', error);
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
            xtypeobj:'',
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
            console.log('Fetched data:', response.data);

            // Populate the form with the fetched data
            setFormData(response.data);
        } catch (error) {
            console.error('Error fetching data:', error);
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
                            <div className="w-full px-2  py-2 mx-auto  ">
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
                                        gridTemplateColumns: 'repeat(3, 1fr)',
                                        borderRadius: 2,
                                        
                                        bgcolor: 'white',
                                    }}
                                    noValidate
                                    autoComplete="off"
                                    
                                >

{isTyping && searchResults.length > 0 && (
                <List
                    sx={{
                        maxHeight: '200px',
                        overflowY: 'auto',
                        border: '1px solid #ccc',
                        borderRadius: '4px',
                        backgroundColor: '#fff',
                        position: 'absolute',
                        zIndex: 1,
                    }}
                >
                    {searchResults.map((result, index) => (
                        <ListItem
                            key={index}
                            button
                            onClick={() => handleResultClick(result)}
                        >
                            <ListItemText primary={result.code} />
                        </ListItem>
                    ))}
                </List>
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