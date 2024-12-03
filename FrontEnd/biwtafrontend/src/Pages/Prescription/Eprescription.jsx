import * as React from 'react';
import { styled } from '@mui/material/styles';
import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Grid';
import TextField from '@mui/material/TextField';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import IconButton from '@mui/material/IconButton';
import DeleteIcon from '@mui/icons-material/Delete';

// Styled Paper for items
const Item = styled(Paper)(({ theme }) => ({
    backgroundColor: '#fff',
    ...theme.typography.body2,
    padding: theme.spacing(1),
    textAlign: 'center',
    color: theme.palette.text.secondary,
    ...theme.applyStyles('dark', {
        backgroundColor: '#1A2027',
    }),
}));

const Eprescription = () => {
    const [medications, setMedications] = React.useState([{ medication: '', dosage: '', frequency: '' }]);

    const handleAddMedication = () => {
        setMedications([...medications, { medication: '', dosage: '', frequency: '' }]);
    };

    const handleRemoveMedication = (index) => {
        const newMedications = medications.filter((_, i) => i !== index);
        setMedications(newMedications);
    };

    const handleChangeMedication = (index, field, value) => {
        const newMedications = [...medications];
        newMedications[index][field] = value;
        setMedications(newMedications);
    };

    return (
        <div style={{ padding: '15px' }}>
            <h1 style={{ display: 'flex', alignItems: 'center', fontSize: '18px' }}>
                <img
                    src="/logo/prescriptionlogo.png"
                    alt="Logo"
                    style={{ width: '30px', height: '30px', marginRight: '10px' }}
                />
                বাংলাদেশ অভ্যন্তরীণ নৌ-পরিবহন কর্তৃপক্ষ
            </h1>
            <Grid container spacing={2}>
                {/* Patient Details Section */}
                <Grid item xs={12}>
                    <Item style={{ padding: '10px' }}>
                        <Typography variant="body2">Patient Details</Typography>
                        <Grid container spacing={1}>
                            <Grid item xs={6}>
                                <TextField fullWidth label="Name" variant="outlined" size="small" />
                            </Grid>
                            <Grid item xs={3}>
                                <TextField fullWidth label="Age" variant="outlined" size="small" />
                            </Grid>
                            <Grid item xs={3}>
                                <TextField fullWidth label="Gender" variant="outlined" size="small" />
                            </Grid>
                        </Grid>
                    </Item>
                </Grid>

                {/* Prescription Section */}
                <Grid item xs={12}>
                    <Item style={{ padding: '10px' }}>
                        <Typography variant="body2">Prescription</Typography>
                        {medications.map((medication, index) => (
                            <Grid container spacing={1} key={index}>
                                <Grid item xs={6}>
                                    <TextField
                                        fullWidth
                                        label="Medication"
                                        variant="outlined"
                                        size="small"
                                        value={medication.medication}
                                        onChange={(e) => handleChangeMedication(index, 'medication', e.target.value)}
                                    />
                                </Grid>
                                <Grid item xs={3}>
                                    <TextField
                                        fullWidth
                                        label="Dosage"
                                        variant="outlined"
                                        size="small"
                                        value={medication.dosage}
                                        onChange={(e) => handleChangeMedication(index, 'dosage', e.target.value)}
                                    />
                                </Grid>
                                <Grid item xs={3}>
                                    <TextField
                                        fullWidth
                                        label="Frequency"
                                        variant="outlined"
                                        size="small"
                                        value={medication.frequency}
                                        onChange={(e) => handleChangeMedication(index, 'frequency', e.target.value)}
                                    />
                                </Grid>
                                {/* Remove Button */}
                                <Grid item xs={12} style={{ display: 'flex', justifyContent: 'flex-end' }}>
                                    <IconButton onClick={() => handleRemoveMedication(index)}>
                                        <DeleteIcon />
                                    </IconButton>
                                </Grid>
                            </Grid>
                        ))}
                        <Button variant="outlined" color="secondary" size="small" onClick={handleAddMedication}>
                            Add Medication
                        </Button>
                    </Item>
                </Grid>

                {/* Additional Instructions Section */}
                <Grid item xs={12}>
                    <Item style={{ padding: '10px' }}>
                        <Typography variant="body2">Additional Instructions</Typography>
                        <TextField
                            fullWidth
                            label="Instructions"
                            variant="outlined"
                            size="small"
                            multiline
                            rows={2}
                        />
                    </Item>
                </Grid>

                {/* Submit Button Section */}
                <Grid item xs={12}>
                    <Item style={{ padding: '10px' }}>
                        <Button variant="contained" color="primary" size="small">
                            Submit Prescription
                        </Button>
                    </Item>
                </Grid>
            </Grid>
        </div>
    );
};

export default Eprescription;
