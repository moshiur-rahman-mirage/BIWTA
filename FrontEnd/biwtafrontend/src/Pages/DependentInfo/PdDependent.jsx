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
import { handleApiRequest } from '../../utility/handleApiRequest';

const PdDependent = ({ xstaff, xname }) => {
    const { zid } = useAuth();
    const variant = 'standard'
    const [isTyping, setIsTyping] = useState(false);
    const apiBaseUrl = "http://localhost:8080/api/xcodes";
    const [formData, setFormData] = useState({
        zid: zid,
        zauserid: '',
        xstaff: '',
        xgender: '',
        xnid: '',
        xcontact: '',
        xrelation: '',
        xbirthdate: '',
        xname: ''


    });


    const handleDropdownSelect = (fieldName, value) => {
        setFormData((prevState) => ({
            ...prevState,
            [fieldName]: value,
        }));
    };


    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prev) => {
            const updatedData = { ...prev, [name]: value };
            return updatedData;
        });
        setIsTyping(true);
    };



    const handleAction = async (method) => {
        console.log(zid);


        const data = {
            
            zid: zid,
            xstaff: xstaff,
            zauserid: formData.zauserid,
            xname: formData.xname,
            xbirthdate: formData.xbirthdate,
            xgender: formData.xgender,
            xnid: formData.xnid,
            xcontact: formData.xcontact,
            xrelation: formData.xrelation,
        };

        console.log("Data Updating");
        console.log(data);

        const endpoint = "/api/pddependent";

        await handleApiRequest({
            endpoint,
            data,
            method,
            onSuccess: (response) => {
                if (method === 'DELETE') {

                    setFormData({
                        zid: zid,
                        zauserid: '',
                        xstaff: '',
                        xgender: '',
                        xnid: '',
                        xcontact: '',
                        xrelation: '',
                        xbirthdate: '',
                        xname: '',
                    });
                }

            },
        });
    };


    return (
        <div className='grid grid-cols-12 gap-5'>
            <div className="">
                <SideButtons
                    onAdd={() => handleAction('POST')}
                    onUpdate={() => handleAction('PUT')}
                    onDelete={() => handleAction('DELETE')}
                //  onClear={handleClear}
                // onShow={handleShow}
                />
            </div>
            <div className='col-span-11 '>
                <div className='   rounded'>
                    <div className="w-full px-2  mx-auto  ">
                        <Caption title={"Family Information Detail of " + xname} />


                        <Box
                            display="grid"
                            gridTemplateColumns="repeat(2, 1fr)"
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
                            <Box sx={{ gridColumn: 'span 1' }}>
                                <Box
                                    display="grid"
                                    gridTemplateColumns="repeat(2, 1fr)"
                                    gap={2}
                                    mb={2} // margin-bottom
                                >
                                    <TextField
                                        label="Family member Name"
                                        name='xname'
                                        variant={variant}
                                        size="small"
                                        onChange={handleChange}
                                        value={formData.xname}
                                        fullWidth
                                        required
                                    />

                                    <TextField
                                        label="Date of Birth"
                                        type="date"
                                        name='xbirthdate'
                                        value={formData.xbirthdate}
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
                                    <XcodesDropDown
                                        id='xsex'
                                        name='xsex'
                                        variant={variant}
                                        label="Gender"
                                        size="small"
                                        type="Gender"
                                        apiUrl={apiBaseUrl}
                                        onSelect={(value) => handleDropdownSelect("xgender", value)}
                                        value={formData.xgender}


                                    />



                                    <Stack direction={{ xs: 'column', sm: 'row' }} spacing={2} mb={2}>

                                        <XcodesDropDown
                                            variant={variant}
                                            label="Relation"
                                            size="small"
                                            type="Relation"
                                            name='xrelation'
                                            onSelect={(value) => handleDropdownSelect("xrelation", value)}
                                            value={formData.xrelation}
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
                                        id='xnid'
                                        name='xnid'
                                        label="NID"
                                        size="small"
                                        onChange={handleChange}
                                        value={formData.xnid}
                                        variant={variant}
                                        fullWidth
                                        required
                                        sx={{ gridColumn: 'span 1' }}
                                        InputLabelProps={{
                                            shrink: true, // Ensures the label stays above the input field
                                        }}
                                    />

                                    <TextField
                                        label="Contact Number"
                                        variant={variant}
                                        size="small"
                                        fullWidth
                                        name='xcontact'
                                        onChange={handleChange}
                                        value={formData.xcontact}
                                        required
                                    />

                                </Box>
                            </Box>
                            <Box sx={{ gridColumn: 'span 1' }}>

                            </Box>



                            {/* Row 1 */}



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
