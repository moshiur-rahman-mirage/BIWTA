import React, { useEffect, useRef, useState } from 'react';
import SideButtons from '../../Shared/SideButtons';
import HelmetTitle from '../../utility/HelmetTitle';
import Caption from '../../utility/Caption';
import Swal from 'sweetalert2';
import SelectField from '../../formfield/SelectField';
import Checkbox from '../../formfield/Checkbox';
import { Box, TextField, List, ListItem } from '@mui/material';
import axios from 'axios';
import { handleApiRequest } from '../../utility/handleApiRequest';
import BasicList from './BasicList';

const BesicXcodes = ({ title, xtype, apiBaseUrl }) => {
    const [searchResults, setSearchResults] = useState([]);
    const [isTyping, setIsTyping] = useState(false);
    const [isListOpen, setListOpen] = useState(false);
    const [checked, setChecked] = useState(false);
    const [refreshList, setRefreshList] = useState(() => () => {});
    const [zid] = useState(100000);
    const listRef = useRef(null);
    const formRef = useRef(null);
    const [formData, setFormData] = useState({
        zid: zid,
        xtype: xtype,
        xcode: '',
        xlong: '',
        zactive: false,
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prev) => ({
            ...prev,
            [name]: value,
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
                `${apiBaseUrl}/searchtext?zid=${zid}&xtype=${xtype}&searchText=${query}`
            );
            setSearchResults(response.data);
            setListOpen(true);
        } catch (error) {
            console.error('Error fetching search results:', error);
        }
    };

    const handleResultClick = (result) => {
        console.log(result)
        if (result.zactive==1){
            result.zactive=true
        }else if(result.zactive==0){
           result.zactive=false
        }

        setFormData({
            xcode: result.xcode,
            xlong: result.xlong,
            xtype: result.xtype,
            zactive: result.zactive
        });
        setListOpen(false);
    };

    useEffect(() => {
        const handleClickOutside = (event) => {
            if (formRef.current && !formRef.current.contains(event.target)) {
                setListOpen(false);
            }
        };

        document.addEventListener('mousedown', handleClickOutside);
        return () => {
            document.removeEventListener('mousedown', handleClickOutside);
        };
    }, []);


    const handleCheckboxChange = (event) => {
        console.log(event)
        setFormData((prevState) => ({
            ...prevState,
            zactive: event.target.checked, 
        }));
    };

    const handleAction = async (method) => {
        const endpoint = `${apiBaseUrl}?zid=${zid}&xtype=${xtype}&xcode=${formData.xcode}`;
       
        const data = { ...formData};
        console.log("Data Updating")
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

    const handleClear = () => {
        setFormData({ xcode: '', xlong: '', xtype: xtype });
        setChecked(false);
        alert('Form cleared.');
    };


    const handleItemSelect = (item) => {
        setFormData({
            xcode: item.xcode,
            xlong: item.xlong,
            xtype: item.xtype,
            zactive: item.zactive
        });
    };

    return (
        <div className=''>
            <HelmetTitle title={title} />
            <div className='grid grid-cols-12'>
                <div>
                    <SideButtons
                        onAdd={() => handleAction('POST')}
                        onUpdate={() => handleAction('PUT')}
                        onDelete={() => handleAction('DELETE')}
                        onClear={handleClear}
                    />
                </div>

                <div className='col-span-11 '>
                    <div className='grid grid-cols-2 gap-2'>
                        <div className='border shadow-lg border-gray-500 rounded max-h-[200px]' >
                            <div className="w-full px-2 py-2 mx-auto">
                                <Caption title={title} />
                                <Box
                                    component="form"
                                    sx={{
                                        '& > :not(style)': { my: 1 },
                                        mx: 'auto',
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
                                    {isListOpen && searchResults.length > 0 && (
                                        <div
                                            ref={formRef}
                                            style={{
                                                position: 'absolute',
                                                maxHeight: '400px',
                                                width: '600px',
                                                overflowY: 'auto',
                                                border: '1px solid black',
                                                borderRadius: '4px',
                                                backgroundColor: '#fff',
                                                zIndex: 100,
                                                boxShadow: '0px 4px 6px rgba(0, 0, 0, 0.1)',
                                            }}
                                        >
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

                                            <List>
                                                {searchResults.map((result, index) => (
                                                    <ListItem
                                                        key={index}
                                                        button={true}
                                                        onClick={() => handleResultClick(result)}
                                                        style={{ display: 'flex' }}
                                                    >
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
                                        label="Code"
                                        value={formData.xcode}
                                        onChange={handleChange}
                                        variant="outlined"
                                        sx={{ gridColumn: 'span 1' }}
                                    />
                                    <TextField
                                        id="xlong"
                                        name="xlong"
                                        label="Name"
                                        value={formData.xlong}
                                        onChange={handleChange}
                                        variant="outlined"
                                        sx={{ gridColumn: 'span 2' }}
                                    />
                                    <Checkbox
                                        // checked={checked}
                                        checked={formData.zactive}
                                        onChange={handleCheckboxChange}
                                        name="Activate?"
                                        color="primary"
                                    />
                                </Box>
                            </div>
                        </div>
                        <div className='border shadow-lg border-gray-500 rounded p-2'>
                            
                        <Caption title={`List of ${xtype}`} />
                            <BasicList
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

export default BesicXcodes;
