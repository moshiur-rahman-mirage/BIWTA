import React, { useState } from 'react';
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
import HelmetTitle from '../../utility/HelmetTitle';
import SideButtons from '../../Shared/SideButtons';
import Caption from '../../utility/Caption';
import XcodesDropDown from '../../ReusableComponents/XcodesDropDown';
import { useAuth } from '../../Provider/AuthProvider';

const Pdmsthrd = () => {
    const { zid } = useAuth();
    const apiBaseUrl = "http://localhost:8080/api/xcodes";


    return (
        <div className='grid grid-cols-12'>
            <div className="">
                <SideButtons
                // onAdd={handleAdd}
                // onUpdate={handleUpdate}
                // onDelete={handleDelete}
                // onClear={handleClear}
                // onShow={handleShow}
                />
            </div>
            <div className='col-span-11 '>
                <Button
                    variant="contained"
                    sx={{
                       marginLeft:1,
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
                    Family Information
                </Button>
                <div className=' shadow-lg  rounded'>
                    <div className="w-full px-2  py-2  mx-auto  ">
                        <Caption title={"Employee Entry"} />


                        <Box
                            component="form"
                            sx={{
                                '& > :not(style)': { my: 1 },
                                // maxWidth: 500,
                                mx: 'auto',
                                // p: 3,
                                // boxShadow: 3,
                                // display: 'grid',
                                gap: 2,
                                mt: 1,
                                // gridTemplateColumns: 'repeat(3, 1fr)',
                                borderRadius: 2,

                                bgcolor: 'white',
                            }}
                            noValidate
                            autoComplete="off"

                        >
                            {/* Row 1 */}
                            <Box
                                display="grid"
                                gridTemplateColumns="repeat(4, 1fr)"
                                gap={2}
                                mb={2} // margin-bottom
                            >
                                <TextField
                                    label="Employee ID"
                                    size="small"
                                    variant="filled"
                                    fullWidth
                                    required
                                    sx={{ gridColumn: 'span 1' }}
                                />
                                <div className="col-span-3"></div>
                            </Box>


                            <Box
                                display="grid"
                                gridTemplateColumns="repeat(4, 1fr)"
                                gap={2}
                                mb={2} // margin-bottom
                            >
                                <Stack direction={{ xs: 'column', sm: 'row' }} spacing={2} mb={2}>

                                    <XcodesDropDown

                                        label="Salutation"
                                        size="small"
                                        type="salutation"
                                        apiUrl={apiBaseUrl} // Replace with your API endpoint
                                        // onSelect={handleSalutationSelect}
                                        defaultValue=""
                                    />

                                </Stack>
                                <TextField
                                    label="First Name"
                                    variant="filled"
                                    size="small"
                                    fullWidth
                                    required
                                />
                                <TextField
                                    label="Middle Name"
                                    variant="filled"
                                    size="small"
                                    fullWidth
                                    required
                                />
                                <TextField
                                    label="Last Name"
                                    variant="filled"
                                    size="small"
                                    fullWidth
                                    required
                                />
                            </Box>

                            {/* <Box
                                display="grid"
                                gridTemplateColumns="repeat(4, 1fr)"
                                gap={2}
                                mb={2} 
                            > */}
                            <Stack
                                direction={{ xs: 'column', sm: 'row' }}
                                // spacing={2} 
                                mb={2}
                                display="grid"
                                gap={2}
                                gridTemplateColumns="repeat(4, 1fr)"
                            >
                                <FormControl variant="filled" component="fieldset" sx={{ display: 'flex', flexDirection: 'row', alignItems: 'center', gap: 1 }}>
                                    <FormLabel id="gender-label">Gender</FormLabel>
                                    <RadioGroup
                                        row
                                        aria-labelledby="gender-label"
                                        name="gender"
                                        defaultValue="Male"
                                    >
                                        <FormControlLabel value="Male" control={<Radio size="small" />} label="Male" />
                                        <FormControlLabel value="Female" control={<Radio size="small" />} label="Female" />
                                        <FormControlLabel value="Other" control={<Radio size="small" />} label="Other" />
                                    </RadioGroup>
                                </FormControl>


                                <TextField
                                    label="Date of Birth"
                                    type="date"
                                    size='small'
                                    InputLabelProps={{ shrink: true }}
                                    variant='filled'
                                    fullWidth
                                />
                                <TextField size='small' variant='filled' label="National ID" fullWidth required />
                                <XcodesDropDown

                                    label="Designation"
                                    size="small"
                                    type="Designation"
                                    apiUrl={apiBaseUrl} // Replace with your API endpoint
                                    // onSelect={handleSalutationSelect}
                                    defaultValue=""
                                />
                            </Stack>
                            {/* </Box> */}
                            {/* Row 4 */}
                            <Stack
                                direction={{ xs: 'column', sm: 'row' }}
                                // spacing={2} 
                                mb={2}
                                display="grid"
                                gap={2}
                                gridTemplateColumns="repeat(4, 1fr)"
                            >
                                <XcodesDropDown

                                    label="Department"
                                    size="small"
                                    type="Department"
                                    apiUrl={apiBaseUrl} // Replace with your API endpoint
                                    // onSelect={handleSalutationSelect}
                                    defaultValue=""
                                />
                                <XcodesDropDown

                                    label="Religion"
                                    size="small"
                                    type="Religion"
                                    apiUrl={apiBaseUrl} // Replace with your API endpoint
                                    // onSelect={handleSalutationSelect}
                                    defaultValue=""
                                />
                                <XcodesDropDown

                                    label="Blood Group"
                                    size="small"
                                    type="Blood Group"
                                    apiUrl={apiBaseUrl} // Replace with your API endpoint
                                    // onSelect={handleSalutationSelect}
                                    defaultValue=""
                                />
                                <XcodesDropDown

                                    label="Marital Status"
                                    size="small"
                                    type="Marital Status"
                                    apiUrl={apiBaseUrl} // Replace with your API endpoint
                                    // onSelect={handleSalutationSelect}
                                    defaultValue=""
                                />
                            </Stack>

                            {/* Row 5 */}
                            <Stack
                                direction={{ xs: 'column', sm: 'row' }}
                                // spacing={2} 
                                mb={2}
                                display="grid"
                                gap={2}
                                gridTemplateColumns="repeat(4, 1fr)"
                            >
                                <TextField label="Personal Mobile No."
                                    size='small'
                                    variant="filled" fullWidth required />
                                <TextField label="Email"
                                    size='small'
                                    variant="filled" fullWidth required />
                                <XcodesDropDown

                                    label="Job Title"
                                    size="small"
                                    type="Job Title"
                                    apiUrl={apiBaseUrl} // Replace with your API endpoint
                                    // onSelect={handleSalutationSelect}
                                    defaultValue=""
                                />

                                <XcodesDropDown

                                    label="Job Location"
                                    size="small"
                                    type="Job Location"
                                    apiUrl={apiBaseUrl} // Replace with your API endpoint
                                    // onSelect={handleSalutationSelect}
                                    defaultValue=""
                                />
                            </Stack>

                            {/* Row 6 */}
                            <Stack
                                direction={{ xs: 'column', sm: 'row' }}
                                // spacing={2} 
                                mb={2}
                                display="grid"
                                gap={2}
                                gridTemplateColumns="repeat(4, 1fr)"
                            >
                                <XcodesDropDown

                                    label="Employee Type"
                                    size="small"
                                    type="Employee Type"
                                    apiUrl={apiBaseUrl} // Replace with your API endpoint
                                    // onSelect={handleSalutationSelect}
                                    defaultValue=""
                                />

                                <TextField
                                    label="BMDC Registration No"

                                    size='small'
                                    InputLabelProps={{ shrink: true }}
                                    variant='filled'
                                    fullWidth
                                />

                                <TextField
                                    label="Credentials"
                                    size='small'
                                    InputLabelProps={{ shrink: true }}
                                    variant='filled'
                                    fullWidth
                                    multiline
                                    sx={{ gridColumn: 'span 2' }}
                                />
                            </Stack>


                        </Box>


                    </div>
                </div>
                <div>

                </div>



            </div>
        </div>
    );
};

export default Pdmsthrd;
