const menuitems = [
    {
      label: "Administrations",
      to: "/administrations",
    },
    {
      label: "Inventory",
      to: "/inventory",
    },
    {
      label: "Prescriptions",
      submenu: [
        { label: "General", to: "/settings/general" },
        { label: "Privacy", to: "/settings/privacy" },
        { label: "Notifications", to: "/settings/notifications" },
      ],
    },
    {
      label: "Reports",
      submenu: [
        { label: "Inventory", to: "/reports/inventory" },
        { label: "Prescription", to: "/reports/prescription" },
      ],
    },
    {
      label: "Profile",
      to: "/Profile",
    },
  ];
  
  export default menuitems;
  