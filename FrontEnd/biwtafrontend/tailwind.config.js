/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,jsx,ts,tsx}",
  ],
  theme:{
  extend: {
    colors: {
      'zab-navbar': '#0480BA', // custom color name
      'zab-sidenav': '#ecf5fb', // another custom color
      'zab-hombtn':'#ffD014'
    },
  },
},
  plugins: [],
}

