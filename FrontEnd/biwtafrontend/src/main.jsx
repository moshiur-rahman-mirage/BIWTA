import React from 'react'
import ReactDOM from 'react-dom/client'

import './index.css'
import './style.css'
import { RouterProvider } from 'react-router-dom'
import Router from './Routers/Router.jsx'
import { AuthProvider } from './Provider/AuthProvider.jsx'
// import AuthProvider from './Provider/AuthProvider.jsx'

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <AuthProvider>
      <RouterProvider router={Router} />
    </AuthProvider>
  </React.StrictMode>,
)