import React from 'react';

import Navbar from '../Shared/Navbar';

import Sidebar from '../Shared/Sidebar';
import { Outlet } from 'react-router-dom';

const Main = () => {
    return (
      <div className="flex min-h-screen">

      <Sidebar/>

      <div className="flex flex-col flex-grow">
        <Navbar />
        <div className="flex-grow p-4">
          <Outlet />
        </div>
      </div>
    </div>
    );
};

export default Main;