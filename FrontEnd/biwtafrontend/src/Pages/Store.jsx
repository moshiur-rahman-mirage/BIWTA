import React from 'react';
import SideButtons from '../Shared/SideButtons';
import HelmetTitle from '../utility/HelmetTitle';
import { useState } from 'react';  // Make sure to import useState
import Caption from '../utility/Caption';

import SelectField from '../formfield/SelectField';
import Checkbox from '../formfield/Checkbox';

import { Box, FormControl, FormControlLabel, InputLabel, MenuItem, Select, TextField } from '@mui/material';

const Store = () => {

    const [formData, setFormData] = useState({

        xcode: '',
        xlong: '',
        xmadd: '',
        xphone: '',
        xtype: ''

    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prev) => ({
            ...prev,
            [name]: value
        }));
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        // handle form submission logic
        console.log(formData);
    };

    const [type, setType] = React.useState('');

    const handleTypeChange = (e) => {
        setType(e.target.value);
    };

    const [checked, setChecked] = useState(false);

    const handleCheckboxChange = (event) => {
        setChecked(event.target.checked);
    };

    return (
        <div className=''>
            <HelmetTitle title="Store" />
            <div className='grid grid-cols-12'>
                <div className="">
                    <SideButtons />
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
                                    onSubmit={handleSubmit}
                                >
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
                                            value={type}
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