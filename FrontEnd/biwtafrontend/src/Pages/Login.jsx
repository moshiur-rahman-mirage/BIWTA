import React, { useState } from 'react';
import { TextField, Button, Typography, Box } from '@mui/material';
import { FaUser, FaLock } from 'react-icons/fa'; // Material icons for UserID and Password
import axios from "axios";

import { useNavigate } from 'react-router-dom';
import { useAuth } from '../Provider/AuthProvider';

function Login() {

    const [userId, setUserId] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const { login } = useAuth();
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
      e.preventDefault();
  
      console.log("called")
      try {
        const response = await axios.post('http://localhost:8080/api/xusers/login', {
            zemail: userId,
            xpassword: password,
          });
        console.log(response)
        if (response.status === 200) {
          alert('Login successful');
          login(userId);
          navigate('/main');
        }
        else if (response.status === 401) {
          console.log("first")
          alert('Invalid credentials');
        }
      } catch (error) {
        console.log("error")
        console.log(error)
        if (error.response) {
          alert('Invalid credentials');
        } else {
          alert('An Error Occured');
        }
      }
    };

  return (
    <div className="flex justify-center items-center h-screen bg-gray-100">
      <Box 
        className="bg-white p-8 rounded-lg shadow-xl w-80"
        component="form" 
        onSubmit={handleSubmit}
      >
        <Typography variant="h5" className="text-center mb-4">Login</Typography>
        
        <div className="mb-4">
          <div className="flex items-center border border-gray-300 p-2 rounded-md">
            <FaUser className="text-gray-500 mr-2" />
            <TextField
              variant="standard"
              label="User ID"
              fullWidth
              value={userId}
              onChange={(e) => setUserId(e.target.value)}
              required
            />
          </div>
        </div>

        <div className="mb-6">
          <div className="flex items-center border border-gray-300 p-2 rounded-md">
            <FaLock className="text-gray-500 mr-2" />
            <TextField
              variant="standard"
              label="Password"
              type="password"
              fullWidth
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>
        </div>

        <Button 
          variant="contained" 
          type="submit" 
          fullWidth
          className="bg-blue-500 hover:bg-blue-600"
        >
          Login
        </Button>
      </Box>
    </div>
  );
}

export default Login;
