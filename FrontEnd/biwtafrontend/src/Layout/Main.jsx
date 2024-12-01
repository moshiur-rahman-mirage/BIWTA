import React from 'react';

import Navbar from '../Shared/Navbar';

import Sidebar from '../Shared/Sidebar';
import { Outlet } from 'react-router-dom';
import Subnav from '../Shared/Subnav';
import Sidenavnew from '../Shared/Sidenavnew';

const Main = () => {
    return (
      <div className="flex min-h-screen">

      {/* <Sidebar/> */}
      <Sidenavnew/>

      <div className="flex flex-col flex-grow">
        <Navbar />
        <Subnav/>
        <div className="flex-grow p-4">
          <Outlet />
        </div>
      </div>
    </div>
    );
};

export default Main;