import React, { useState } from "react";
import { Link } from "react-router-dom";
import menuitems from "../Staticitems/Menuitems";

export default function Sidebar() {
  const [isOpen, setIsOpen] = useState(false);
  const [searchTerm, setSearchTerm] = useState("");
  const [expandedMenu, setExpandedMenu] = useState(null);
  // const sidebarWidth = isOpen ? 260 : 0;
  const sidebarWidth = 360;

  const filteredItems = menuitems.filter((item) =>
    item.label.toLowerCase().includes(searchTerm.toLowerCase())
  );

  const toggleSubmenu = (label) => {
    setExpandedMenu(expandedMenu === label ? null : label);
  };

  return (
    <div>
      <div
        className={`fixed top-0 left-0 h-full bg-zab-sidenav border-solid border-2 border-black transition-transform duration-1000`}
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
            onChange={(e) => setSearchTerm(e.target.value)} // Update search term
          />
        </div>

        <div
          className={` transition-transform  duration-1000`}
          style={{
            transform: isOpen ? "translateX(0)" : "translateX(-50px)",

          }}
        >
          <div className="flex flex-col px-4 ">
            {filteredItems.length > 0 ? (
              filteredItems.map((item, index) => (
                <div key={index}>

                  <div
                    className="flex justify-between items-center px-0 py-0 hover:underline transition cursor-pointer"
                    onClick={() =>
                      item.submenu ? toggleSubmenu(item.label) : null
                    }
                  >
                    <Link to={item.to || "#"} className="block">
                      {item.label}
                    </Link>

                    {item.submenu && (
                      <span className="no-underline">
                        {expandedMenu === item.label ? "-" : "+"}
                      </span>
                    )}
                  </div>


                  {item.submenu && expandedMenu === item.label && (
                    <div className="ml-4">
                      {item.submenu.map((submenuItem, subIndex) => (
                        <Link
                          key={subIndex}
                          to={submenuItem.to}
                          className="block px-4  hover:underline"
                        >
                          {submenuItem.label}
                        </Link>
                      ))}
                    </div>
                  )}
                </div>
              ))
            ) : (
              <div className="px-4 py-2 text-gray-500">No matching items found</div>
            )}

          </div>
        </div>
      </div>

      {/* Toggle Button */}
      <button
        className="fixed top-1 z-50 p-2 bg-transparent text-white transition-all duration-1000"
        style={{
          left: isOpen ? `${sidebarWidth + 10}px` : "10px", // Offset the button to the right edge of the sidebar
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
