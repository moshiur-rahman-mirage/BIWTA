import React from 'react';

import Navbar from '../Shared/Navbar';
import { ToastContainer } from 'react-toastify';
import Sidebar from '../Shared/Sidebar';
import { Outlet } from 'react-router-dom';
import Subnav from '../Shared/Subnav';
import Sidenavnew from '../Shared/Sidenavnew';
import 'react-toastify/dist/ReactToastify.css';
const Main = () => {
  return (
    <div className="flex min-h-screen">
      <ToastContainer
        position="top-right"
        autoClose={3000}
        hideProgressBar={false}
        newestOnTop={false}
        closeOnClick
        rtl={false}
        pauseOnFocusLoss
        draggable
        pauseOnHover
      />

      {/* <Sidebar/> */}
      <Sidenavnew />

      <div className="flex flex-col flex-grow">
        <Navbar />
        <Subnav />
        <div className="flex-grow p-4">
          <Outlet />
        </div>
      </div>
    </div>
  );
};

export default Main;