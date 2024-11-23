import React, { useEffect, useRef, useState } from 'react';
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
    Modal,
    Typography,
} from '@mui/material';
import HelmetTitle from '../../utility/HelmetTitle';
import SideButtons from '../../Shared/SideButtons';
import Caption from '../../utility/Caption';
import XcodesDropDown from '../../ReusableComponents/XcodesDropDown';
import { useAuth } from '../../Provider/AuthProvider';
import PdDependent from '../DependentInfo/PdDependent';
import { handleApiRequest } from '../../utility/handleApiRequest';
import LoadingPage from '../Loading/Loading';


const Pdmsthrd = () => {
    const { zid,zemail } = useAuth();
    console.log(zid)
    const [loading, setLoading] = useState(true);
    useEffect(() => {
        if (zid && zemail) {
          setLoading(false);
        }
      }, [zid,zemail]);
    
      if (loading && !zid && !zemail) {
        return <LoadingPage />; 
      }
    const [open, setOpen] = useState(false);
    // const handleOpen = () => setOpen(true);
    // const handleClose = () => setOpen(false);
    const [searchResults, setSearchResults] = useState([]); // For search results
    const [isTyping, setIsTyping] = useState(false); // To handle typing state
    const [selectedCode, setSelectedCode] = useState(''); // To store selected code
    const [checked, setChecked] = useState(false);
    const [xtypeobj, setXtypeobj] = useState('');
    const [isListOpen, setListOpen] = useState(false)
    const listRef = useRef(null);
    const formRef = useRef(null);
    const [errors, setErrors] = useState({});
    const apiBaseUrl = "http://localhost:8080/api/pdmst";
    const variant = 'standard'
    const [dropdownValues, setDropdownValues] = useState({
        xdesignation: "",
        xdepartment: "",
        xsalutation:'',
    });


    const [formData, setFormData] = useState({
        zid: zid,
        xstaff:'',
        xname:'',
        xbirthdate:'',
        xlastname:'',
        xdepartment:'',
        xdesignation:'',
        xmname:''


    });


    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prev) => ({
            ...prev,
            [name]: value
        }));
        

            setIsTyping(true);
            if (name === 'xstaff') {
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
            const response = await axiosInstance.get(`api/pdmst?zid=${zid}&searchText=${query}`)
            setSearchResults(response.data);
            setListOpen(true);
            if (inputRef.current) {
                const rect = inputRef.current.getBoundingClientRect();
                setDropdownPosition({
                    top: rect.bottom + window.scrollY,  // Position below the input field
                    left: rect.left + window.scrollX,   // Align it with the input field
                });
            }
        } catch (error) {
            console.error('Error fetching search results:', error);
        }
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







      const handleAdd = async () => {
        const endpoint = 'http://localhost:8080/api/pdmst';
        const data = { 
            ...formData, 
            zid: zid,
        };
    
        await handleApiRequest({
            endpoint,
            data,
            method:'POST',
            onSuccess: (response) => {
                setErrors({});
            },
        });
    };

    const handleDropdownSelect = (key, value) => {
        console.log(value)
        setDropdownValues((prev) => ({
            ...prev,
            [key]: value,
         
        }));
        
    };
    console.log(formData)

    return (
        <div className='grid grid-cols-12'>
            <HelmetTitle title="Employee Information" />
            <div className="">
                <SideButtons
                onAdd={handleAdd}
                // onUpdate={handleUpdate}
                // onDelete={handleDelete}
                // onClear={handleClear}
                // onShow={handleShow}
                />
            </div>
            <div className='col-span-11 '>
                <Button
                    onClick={handleOpen}
                    variant='outlined'
                    sx={{
                        marginLeft: 1,
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

                {/* Modal */}
                <Modal
                    open={open}
                    onClose={handleClose}
                    disablePortal
                    disableEnforceFocus
                    disableAutoFocus
                >
                    <Box sx={{
                          position: "absolute",
                          top: "50%",
                          left: "50%",
                          transform: "translate(-50%, -50%)",
                          width: "1200px", // Fixed width
                          height: "500px", // Fixed height
                          bgcolor: "background.paper",
                          border: "2px solid #000",
                          boxShadow: 24,
                          p: 4,
                    }}>
                        <PdDependent />
                    </Box>
                </Modal>
                {/* Modal */}

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
                                    id='xstaff'
                                    name='xstaff'
                                    label="Employee ID"
                                    size="small"
                                     value={formData.xstaff}
                                    variant={variant}
                                    fullWidth
                                    onChange={handleChange}
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
                                        id='xsalutation'
                                        name='xsalutation'
                                        variant={variant}
                                        label="Salutation"
                                        size="small"
                                        type="salutation"
                                        apiUrl={apiBaseUrl} // Replace with your API endpoint
                                        onSelect={(value) => handleDropdownSelect("xsalutation", value)} 
                                        value={dropdownValues.xsalutation} 
                                    />

                                </Stack>
                                <TextField
                                    label="First Name"
                                    name='xfname'
                                    variant={variant}
                                    size="small"
                                    onChange={handleChange}
                                    value={formData.xfname}
                                    fullWidth
                                    required
                                />
                                <TextField
                                    label="Middle Name"
                                    name='xmname'
                                    variant={variant}
                                    size="small"
                                    onChange={handleChange}
                                    value={formData.xmname}
                                    fullWidth
                                    required
                                />
                                <TextField
                                    label="Last Name"
                                    name='xlastname'
                                    variant={variant}
                                    size="small"
                                    onChange={handleChange}
                                    value={formData.xlastname}
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
                                <FormControl variant={variant} component="fieldset" sx={{ display: 'flex', flexDirection: 'row', alignItems: 'center', gap: 1 }}>
                                    <FormLabel id="gender-label">Gender</FormLabel>
                                    <RadioGroup
                                        row
                                        aria-labelledby="gender-label"
                                        name="xsex"
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
                                    onChange={handleChange}
                                    InputLabelProps={{ shrink: true }}
                                    value={formData.xbirthdate}
                                    variant={variant}
                                    fullWidth
                                />
                                <TextField size='small' onChange={handleChange} variant={variant} label="National ID" fullWidth required />
                                <XcodesDropDown
                                    variant={variant}
                                    label="Designation"
                                    size="small"
                                    type="Designation"
                                    apiUrl={apiBaseUrl} // Replace with your API endpoint
                                    onSelect={(value) => handleDropdownSelect("xdesignation", value)} 
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
                                    variant={variant}
                                    label="Department"
                                    size="small"
                                    type="Department"
                                    apiUrl={apiBaseUrl} // Replace with your API endpoint
                                    onSelect={(value) => handleDropdownSelect("xdepartment", value)} 
                                    defaultValue=""
                                />
                                <XcodesDropDown
                                    id="xreligion"
                                    name="xreligion"
                                    variant={variant}
                                    label="Religion"
                                    size="small"
                                    type="Religion"
                                    apiUrl={apiBaseUrl} // Replace with your API endpoint
                                    onSelect={(value) => handleDropdownSelect("xreligion", value)} 
                                    defaultValue=""
                                />
                                <XcodesDropDown
                                    id="xbloodgroup"
                                    name="xbloodgroup"
                                    variant={variant}
                                    label="Blood Group"
                                    size="small"
                                    type="Blood Group"
                                    apiUrl={apiBaseUrl} // Replace with your API endpoint
                                    onSelect={(value) => handleDropdownSelect("xbloodgroup", value)}
                                    defaultValue=""
                                />
                                <XcodesDropDown
                                    id="xmstat"
                                    name="xmstat"
                                    variant={variant}
                                    label="Marital Status"
                                    size="small"
                                    type="Marital Status"
                                    apiUrl={apiBaseUrl} // Replace with your API endpoint
                                    onSelect={(value) => handleDropdownSelect("xmstat", value)}
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
                                    onChange={handleChange}
                                    variant={variant} fullWidth required />
                                <TextField label="Email"
                                    size='small'
                                    onChange={handleChange}
                                    variant={variant} fullWidth required />
                                <XcodesDropDown
                                    variant={variant}
                                    label="Job Title"
                                    size="small"
                                    type="Job Title"
                                    apiUrl={apiBaseUrl} // Replace with your API endpoint
                                    // onSelect={handleSalutationSelect}
                                    defaultValue=""
                                />

                                <XcodesDropDown
                                    variant={variant}
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
                                    variant={variant}
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
                                    // InputLabelProps={{ shrink: true }}
                                    variant={variant}
                                    fullWidth
                                />

                                <TextField
                                    label="Credentials"
                                    size='small'

                                    variant={variant}
                                    fullWidth
                                    multiline
                                    sx={{ gridColumn: 'span 2' }}
                                />
                                {/* <TextField
                                    label="Employee ID"
                                    size="small"
                                    variant={variant}
                                    fullWidth
                                    required
                                    sx={{ gridColumn: 'span 1' }}
                                /> */}

                            </Stack>


                        </Box>


                    </div>
                </div>
                <div>

                </div>



            </div>
        </div >
    );
};

export default Pdmsthrd;
