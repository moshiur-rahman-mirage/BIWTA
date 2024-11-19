import React, { useState } from "react";
import { Link } from "react-router-dom";
import { menuitems } from "../Staticitems/Menuitems";

export default function Sidebar() {
  const [isOpen, setIsOpen] = useState(false);
  const [searchTerm, setSearchTerm] = useState("");
  const [expandedMenus, setExpandedMenus] = useState({});
  const sidebarWidth = 360;

  const toggleSubmenu = (title) => {
    setExpandedMenus((prevState) => ({
      ...prevState,
      [title]: !prevState[title],
    }));
  };



  const filterMenu = (items, searchTerm) => {
    return items
      .map((item) => {
        // Check if the item matches the search term
        const matches = item.title.toLowerCase().includes(searchTerm.toLowerCase());
  
        // Recursively filter submenus
        const filteredSubmenu = item.submenu ? filterMenu(item.submenu, searchTerm) : [];
  
        // Include the item if it matches or if its submenu has matches
        if (matches || filteredSubmenu.length > 0) {
          return {
            ...item,
            submenu: filteredSubmenu, // Include filtered submenu
          };
        }
  
        // Exclude the item if it doesn't match and has no matching submenus
        return null;
      })
      .filter(Boolean); // Remove null values
  };









  const renderMenu = (items) => {
    return items.map((item, index) => (
      <div key={`${item.title}-${index}`}>
        <div className="menu-item">
          <div className="flex justify-between items-center px-2 py-0  transition cursor-pointer">
            <Link
              to={item.submenu.length>0 ? "#" : item.to}
              
              className="menulist"
              onClick={(e) => {
                if (item.submenu.length>0) {
                  e.preventDefault();
                  toggleSubmenu(item.title);
                }else{
                  setIsOpen(false)
                }
              }}
            >
              {item.title}
            </Link>
            {item.submenu.length>0 && (
              
              <span
             
              onClick={() => {
                toggleSubmenu(item.title);
                setIsOpen(!isOpen);
              }}
                className="cursor-pointer"
              >
                {expandedMenus[item.title] ? "-" : "+"}
              </span>
            )}
          </div>
          {item.submenu && expandedMenus[item.title] && (
            <div className="submenu ml-4">{renderMenu(item.submenu)}</div>
          )}
        </div>
      </div>
    ));
  };


   const filteredItems = filterMenu(menuitems, searchTerm);
{{console.log(filteredItems)}}

  return (
    <div>
      <div
        className={`fixed z-30 top-0 left-0 h-full bg-zab-sidenav border-solid border-2 border-black transition-transform duration-1000`}
        style={{
          width: `${sidebarWidth}px`,
          transform: isOpen ? "translateX(0)" : `translateX(-${sidebarWidth}px)`,
        }}
      >
        <div className="px-4 pt-4 pb-2">
          <input
            type="text"
            placeholder="Search Items..."
            className="w-full px-2 border rounded transition-transform duration-1000"
            style={{
              transform: isOpen ? "translateX(0)" : "translateX(-50px)",
            }}
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
        </div>
        <div
          className="transition-transform duration-1000"
          style={{
            transform: isOpen ? "translateX(0)" : "translateX(-50px)",
          }}
        >
          {filteredItems.length > 0 ? (
            renderMenu(filteredItems)
          ) : (
            <div className="px-4 py-2 text-gray-500">No matching items found</div>
          )}
        </div>
      </div>
      <button
        className="fixed top-1 z-50 p-2 bg-transparent text-white transition-all duration-1000"
        style={{
          left: isOpen ? `${sidebarWidth + 10}px` : "10px",
        }}
        onClick={() => setIsOpen(!isOpen)}
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          className="h-8 w-10 text-zab-hombtn text-3xl"
          fill="none"
          viewBox="0 1 24 22"
          stroke="currentColor"
        >
          <path
            strokeLinecap="round"
            strokeLinejoin="round"
            strokeWidth={4}
            d="M4 6h16M4 12h16M4 18h16"
          />
        </svg>
      </button>
    </div>
  );
}
