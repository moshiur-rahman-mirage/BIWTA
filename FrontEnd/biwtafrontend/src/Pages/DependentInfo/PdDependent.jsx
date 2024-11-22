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

const PdDependent = () => {
    const { zid } = useAuth();
    const variant = 'standard'
    const apiBaseUrl = "http://localhost:8080/api/xcodes";


    return (
        <div className='grid grid-cols-12 gap-5'>
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
                <div className='   rounded'>
                    <div className="w-full px-2  mx-auto  ">
                        <Caption title={"Family Information Detail"} />


                        <Box
                            component="form"
                            sx={{
                                '& > :not(style)': { my: 1 },

                                mx: 'auto',
                                gap: 2,
                                mt: 1,
                                borderRadius: 2,
                                bgcolor: 'white',
                            }}
                            noValidate
                            autoComplete="off"

                        >
                            {/* Row 1 */}
                            <Box
                                display="grid"
                                gridTemplateColumns="repeat(2, 1fr)"
                                gap={2}
                                mb={2} // margin-bottom
                            >
                                <TextField
                                    label="Family member Name"
                                    size="small"
                                   variant={variant}
                                    fullWidth
                                    required
                                    sx={{ gridColumn: 'span 1' }}
                                />

                                <TextField
                                    label="Date of Birth"
                                    type="date"
                                    size='small'
                                    InputLabelProps={{ shrink: true }}
                                    variant={variant}
                                    fullWidth
                                />
                            </Box>
                            <Box
                                display="grid"
                                gridTemplateColumns="repeat(2, 1fr)"
                                gap={2}
                                mb={2} // margin-bottom
                            >
                                <FormControl variant={variant} component="fieldset" sx={{ display: 'flex', flexDirection: 'row', alignItems: 'center', gap: 1 }}>
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



                                <Stack direction={{ xs: 'column', sm: 'row' }} spacing={2} mb={2}>

                                    <XcodesDropDown
                                        variant={variant}
                                        label="Relation"
                                        size="small"
                                        type="Relation"
                                        apiUrl={apiBaseUrl} // Replace with your API endpoint
                                        // onSelect={handleSalutationSelect}
                                        defaultValue=""
                                    />

                                </Stack>
                            </Box>

                            <Box
                                display="grid"
                                gridTemplateColumns="repeat(2, 1fr)"
                                gap={2}
                                mb={2} // margin-bottom
                            >
                                <TextField
                                    label="National ID"
                                   variant={variant}
                                    size="small"
                                    fullWidth
                                    required
                                />
                                <TextField
                                    label="Contact Number"
                                   variant={variant}
                                    size="small"
                                    fullWidth
                                    required
                                />

                            </Box>


                        </Box>

                    </div>
                </div>
                <div>

                </div>



            </div>
        </div >
    );
};

export default PdDependent;
