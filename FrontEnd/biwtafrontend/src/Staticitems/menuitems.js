

export const menuitems = [
  {
    title: 'Administration',
    to: 'main/administrations',
    submenu: [

      {
        title: 'Master Setup',
        to: 'administrations/master',
        submenu:[
          
          {
            title:'Item',
            to:'administrations/master/item',
          },
          {
            title:'Supplier',
            to:'administrations/master/supplier',
          },
          {
            title:'Customer',
            to:'administrations/master/customer',
          },
          {
            title:'User',
            to:'administrations/master/user',
          },
          
        ]
      },
      {
        title: 'Codes & Parameters',
        to: 'administrations/codes',
        submenu:[
          {
            title:'Store',
            to:'administrations/codes/store',
          },
          {
            title:'Department',
            to:'administrations/codes/department',
          },
          {
            title:'Designation',
            to:'administrations/codes/designation',
          },
          {
            title:'Section',
            to:'administrations/codes/section',
          },
          {
            title:'Product Group',
            to:'administrations/codes/itemgroup',
          },
          {
            title:'Supplier Group',
            to:'administrations/codes/supgroup',
          },
          {
            title:'Customer Group',
            to:'administrations/codes/cusgroup',
          },
          
        ]
      }
    ]
  },
  {
    title: 'Personal Info',
    to: 'personalinfo',
  },
  {
    title:'Prescription',
    to:'prescription',
  },
  {
    title:'product',
    to:'product'
  },
  {
    title:'dashboard',
    to:'dashboard'
  }
];
