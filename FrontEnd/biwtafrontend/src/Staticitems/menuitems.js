export const menuitems = [
  {
    title: 'Administration',
    to: 'main/administrations',
    submenu: [
      {
        title: 'Master Setup',
        to: 'administrations/master',
        submenu: [
          { title: 'Item', to: 'administrations/master/item' },
          { title: 'Supplier', to: 'administrations/master/supplier' },
          // { title: 'Customer', to: 'administrations/master/customer' },
          { title: 'User', to: 'administrations/master/user' },
        ],
      },
      {
        title: 'Codes & Parameters',
        to: 'administrations/codes',
        submenu: [
          // Store-related codes
          { title: 'Store', to: 'administrations/codes/store' },
          { title: 'Store Type', to: 'administrations/codes/storetype' },

          // Department and organization
          { title: 'Department', to: 'administrations/codes/department' },
          { title: 'Designation', to: 'administrations/codes/designation' },
          { title: 'Section', to: 'administrations/codes/section' },

          // Personal information
          { title: 'Salutation', to: 'administrations/codes/salutation' },
          { title: 'Marital Status', to: 'administrations/codes/maritalstatus' },
          { title: 'Religion', to: 'administrations/codes/religion' },
          { title: 'Blood Group', to: 'administrations/codes/bloodgroup' },
          { title: 'Gender', to: 'administrations/codes/gender' },
          { title: 'Relation', to: 'administrations/codes/relation' },

          // Job and employee-related codes
          { title: 'Job Location', to: 'administrations/codes/joblocation' },
          { title: 'Job Title', to: 'administrations/codes/jobtitle' },
          { title: 'Employee Type', to: 'administrations/codes/emptype' },

          // Group-related codes
          { title: 'Product Group', to: 'administrations/codes/itemgroup' },
          { title: 'Supplier Group', to: 'administrations/codes/supgroup' },
          { title: 'Customer Group', to: 'administrations/codes/cusgroup' },
          { title: 'Payment Type', to: 'administrations/codes/paymenttype'},
        ],
      },
    ],
  },
  { title: 'Personal Info', to: 'personalinfo' },
  { title: 'Prescription', to: 'prescription' },
  { title: 'Dashboard', to: 'dashboard' },
];
