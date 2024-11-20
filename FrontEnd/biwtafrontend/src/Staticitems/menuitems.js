

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
            title:'Store',
            to:'administrations/master/store',
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
