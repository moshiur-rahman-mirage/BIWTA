

export const menuitems = [
  {
    title: 'Dashboard',
    to: '/dashboard',
  },
  {
    title: 'Profile',
    to: '/profile',
  },
  {
    title: 'Settings',
    to: '/settings',
    submenu: [
      {
        title: 'General Settings',
        to: '/settings/general',
        submenu: [
          {
            title: 'Privacy',
            to: '/settings/general/privacy',
          },
          {
            title: 'Security',
            to: '/settings/general/security',
          },
        ],
      },
      {
        title: 'Account Settings',
        to: '/settings/account',
      },
    ],
  },
  {
    title: 'Notifications',
    to: '/notifications',
  },
  {
    title: 'Logout',
    to: '/logout',
  },
];
