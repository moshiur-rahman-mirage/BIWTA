

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
            title:'Salutation',
            to:'administrations/codes/salutation',
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
          {
            title:'Job Location',
            to:'administrations/codes/joblocation',
          },
          {
            title:'Marital Status',
            to:'administrations/codes/maritalstatus',
          },
          {
            title:'Religion',
            to:'administrations/codes/religion',
          },
          {
            title:'Job Title',
            to:'administrations/codes/jobtitle',
          },
          {
            title:'Employee Type',
            to:'administrations/codes/emptype',
          },
          {
            title:'Blood Group',
            to:'administrations/codes/bloodgroup',
          },
          {
            title:'Store Type',
            to:'administrations/codes/storetype',
          },
          {
            title:'Gender',
            to:'administrations/codes/gender',
          },

          {
            title:'Relation',
            to:'administrations/codes/relation',
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
