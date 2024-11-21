import React, { useEffect, useRef } from 'react';
import SideButtons from '../../Shared/SideButtons';
import HelmetTitle from '../../utility/HelmetTitle';
import { useState } from 'react';  // Make sure to import useState
import Caption from '../../utility/Caption';
import Swal from 'sweetalert2';

import SelectField from '../../formfield/SelectField';
import Checkbox from '../../formfield/Checkbox';

import { Box, FormControl, FormControlLabel, InputLabel, List, ListItem, ListItemText, MenuItem, Select, TextField } from '@mui/material';
import axios from 'axios';
import { handleApiRequest } from '../../utility/handleApiRequest';
import BasicList from '../Xcodes/BasicList';
import ItemGroupList from './ItemGroupList';



const ItemGroup = () => {
    const [searchResults, setSearchResults] = useState([]); // For search results
    const [isTyping, setIsTyping] = useState(false); // To handle typing state
    const [selectedCode, setSelectedCode] = useState(''); // To store selected code
    const [checked, setChecked] = useState(false);
    const [xtypeobj, setXtypeobj] = useState('');
    const [isListOpen, setListOpen] = useState(false)
    const listRef = useRef(null);
    const formRef = useRef(null);
    const [errors, setErrors] = useState({});
    const [zid] = useState(100000);
    const [xtype, setXtype] = useState('Item Group');
    const apiBaseUrl = "http://localhost:8080/api/xcodes"
    const [refreshList, setRefreshList] = useState(() => () => { });
    const [formData, setFormData] = useState({
        zid: zid,
        xtype: xtype,
        xcode: '',
        xlong: '',
        xemail: '',
        xmadd: '',
        zactive: false,
        xtypeobj: '',
        xphone: '',
        xgtype: '',


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

        
        setFormData({
            xcode: result.xcode,
            xlong: result.xlong,
            xemail: result.xemail,
            xtype: result.xtype,
            xphone: result.xphone,
            xmadd: result.xmadd,
            xtypeobj: result.xtypeobj,
            xgtype: result.xgtype,
            zactive: result.zactive
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
            xtypeobj: e.target.value || '',
        });
    };

    const handleGtypeChange = (e) => {
        console.log(e.target.value)
        setFormData({
            ...formData,
            xgtype: e.target.value || '',
        });
    };







    const handleCheckboxChange = (event) => {
        console.log(event.target.value)
        setFormData((prevState) => ({
            ...prevState,
            zactive: event.target.checked, 
        }));
    };



    const handleAdd = async () => {
        const endpoint = 'http://localhost:8080/api/xcodes';
        const data = {
            ...formData,
            zid: zid,
            xtype: 'Item Group',
            zactive: checked
        };


        await handleApiRequest({
            endpoint,
            data,
            method: 'POST',
            onSuccess: (response) => {
                setErrors({});
                // setFormData({
                //     xcode: '',
                //     xlong: '',
                //     xmadd: '',
                //     xemail: '',
                //     xtypeobj: '',
                //     xphone: '',
                // });
                // setChecked(false);
            },
        });
    };

    const handleItemSelect = (item) => {
        console.log(item.zactive)
        setFormData({
            xcode: item.xcode,
            xlong: item.xlong,
            xtype: item.xtype,
            zactive: item.zactive,
            xgtype:item.xgtype,
            xtypeobj:item.xtypeobj
        });
    };





    const handleAction = async (method) => {
        console.log(zid)
        const endpoint = `${apiBaseUrl}?zid=${zid}&xtype=${xtype}&xcode=${formData.xcode}`;
        
        const data = { ...formData,zid:zid };
        console.log(data)
        await handleApiRequest({
            endpoint,
            data,
            method,
            onSuccess: (response) => {
                if (method === 'DELETE') {
                    setFormData({ xcode: '', xlong: '', xtype: xtype });
                    setChecked(false);
                }
                refreshList();
            },
        });
    };

    const handleDelete = async () => {
        const endpoint = `http://localhost:8080/api/xcodes?zid=${zid}&xtype=${xtype}&xcode=${formData.xcode}`;
        await handleApiRequest({
            endpoint,
            method: 'DELETE',
            onSuccess: (response) => {
                setErrors({});
                setFormData({
                    xcode: '',
                    xlong: '',
                    xmadd: '',
                    xphone: '',
                    xtypeobj: '',
                    xtype: 'Item Group',
                });
                setChecked(false);
            },
        });
    };


    const handleClear = () => {
        setFormData({
            xcode: '',
            xlong: '',
            xmadd: '',
            xphone: '',
            zactive: '',
            xemail: '',
            xtypeobj: '',
            xtype: xtype,
        });
        setChecked(false);
        alert('Form cleared.');
    };





    return (
        <div className=''>
            <HelmetTitle title="Item Group" />
            <div className='grid grid-cols-12'>
                <div className="">
                    <SideButtons
                        onAdd={() => handleAction('POST')}
                        onUpdate={() => handleAction('PUT')}
                        onDelete={() => handleAction('DELETE')}
                        onClear={handleClear}
                    // onShow={handleShow}
                    />
                </div>



                <div className='col-span-11 '>

                    <div className='grid grid-cols-2  gap-2'>
                        <div className='border shadow-lg border-black rounded  max-h-[300px]'>
                            <div className="w-full px-2  py-2  mx-auto  ">
                                <Caption title={"Item Group Entry"} />
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
                                        mt: 1,
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
                                                border: '1px solid black',
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
                                                        button={true}
                                                        onClick={() => handleResultClick(result)}
                                                        style={{ display: 'flex' }}
                                                    // sx={{border:1 }}
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
                                        label="Item Group Code"
                                        value={formData.xcode}
                                        onChange={handleChange}
                                        variant="outlined"
                                        // fullWidth
                                        sx={{ gridColumn: 'span 1' }}

                                    />
                                    <TextField
                                        id="xlong"
                                        name="xlong"
                                        label="Item Group Name"
                                        value={formData.xlong}
                                        onChange={handleChange}
                                        variant="outlined"
                                        sx={{ gridColumn: 'span 2' }}
                                    />

                                    <FormControl fullWidth sx={{ gridColumn: 'span 1' }}>
                                        <InputLabel id="demo-simple-select-label">Item Group Type</InputLabel>
                                        <Select
                                            labelId="demo-simple-select-label"
                                            id="demo-simple-select"
                                            name="xtypeobj"
                                            value={formData.xtypeobj || ''}
                                            label="Item Group Type"
                                            onChange={handleTypeChange}
                                            // sx={{ gridColumn: 'span 1' }}
                                            sx={{ width: '100%' }}
                                        >
                                            <MenuItem value={''}>Select</MenuItem>
                                            <MenuItem value={'Product'}>Product</MenuItem>
                                            <MenuItem value={'Service'}>Service</MenuItem>

                                        </Select>
                                    </FormControl>

                                    <FormControl fullWidth sx={{ gridColumn: 'span 1' }}>
                                        <InputLabel id="demo-simple-select-label">Item Group Nature</InputLabel>
                                        <Select
                                            labelId="demo-simple-select-label"
                                            id="demo-simple-select"
                                            name="xgtype"
                                            value={formData.xgtype || ''}
                                            label="Item Group Nature"
                                            onChange={handleGtypeChange}
                                            // sx={{ gridColumn: 'span 1' }}
                                            sx={{ width: '100%' }}
                                        >
                                            <MenuItem value={''}>Select</MenuItem>
                                            <MenuItem value={'General'}>General</MenuItem>
                                            <MenuItem value={'Fixed Asset'}>Fixed Asset</MenuItem>

                                        </Select>
                                    </FormControl>


                                    <FormControl fullWidth sx={{ gridColumn: 'span 1', display: 'flex', justifyContent: 'center' }}>
                                        <Checkbox

                                            checked={formData.zactive}
                                            onChange={handleCheckboxChange}
                                            name="Activate?"
                                            color="primary"
                                        />
                                    </FormControl>




                                </Box>

                            </div>
                        </div>
                        <div className='border shadow-lg border-gray-500 rounded p-2'>
                            <Caption title={`List of ${xtype}`} />
                            <ItemGroupList
                                xtype={xtype}
                                apiBaseUrl={apiBaseUrl}
                                zid={zid}
                                onItemSelect={handleItemSelect}
                                style={{ marginTop: '1px' }}
                                onRefresh={(fetchData) => setRefreshList(() => fetchData)}
                            />
                        </div>
                    </div>


                </div>
            </div>
        </div>
    );
};

export default ItemGroup;