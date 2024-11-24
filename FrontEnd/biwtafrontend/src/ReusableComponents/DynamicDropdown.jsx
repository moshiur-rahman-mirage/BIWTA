import React, { useState, useEffect, useRef } from 'react';

const DynamicDropdown = ({
    isOpen, // Controls visibility
    onClose, // Callback to close dropdown
    triggerRef, // Ref for positioning
    data = [], // Data array to display
    headers = [], // Header titles for columns
    onSelect, // Callback for item selection
    dropdownWidth = 600, // Default width
    dropdownHeight = 400, // Default max height
}) => {
    const dropdownRef = useRef(null);
    const [position, setPosition] = useState({ top: 0, left: 0 });
    const [hoveredIndex, setHoveredIndex] = useState(null); // Track hovered item index

    useEffect(() => {
        if (isOpen && triggerRef?.current) {
            const rect = triggerRef.current.getBoundingClientRect();
            setPosition({
                top: rect.bottom + window.scrollY,
                left: rect.left + window.scrollX,
            });
        }
    }, [isOpen, triggerRef]);

    // Close dropdown on outside click
    useEffect(() => {
        const handleOutsideClick = (event) => {
            if (dropdownRef.current && !dropdownRef.current.contains(event.target)) {
                onClose();
            }
        };

        if (isOpen) {
            document.addEventListener('mousedown', handleOutsideClick);
        }
        return () => document.removeEventListener('mousedown', handleOutsideClick);
    }, [isOpen, onClose]);

    if (!isOpen) return null;

    return (
        <div
            ref={dropdownRef}
            style={{
                position: 'absolute',
                top: position.top,
                left: position.left,
                maxHeight: `${dropdownHeight}px`,
                width: `${dropdownWidth}px`,
                overflowY: 'auto',
                border: '1px solid black',
                borderRadius: '4px',
                backgroundColor: '#fff',
                zIndex: 1000,
                boxShadow: '0px 4px 6px rgba(0, 0, 0, 0.1)',
            }}
        >
            {/* Header Row */}
            <div
                style={{
                    display: 'flex',
                    padding: '10px',
                    fontWeight: 'bold',
                    backgroundColor: '#f0f0f0',
                    borderBottom: '1px solid gray',
                }}
            >
                {headers.map((header, index) => (
                    <div key={index} style={{ flex: 1, textAlign: 'left' }}>
                        {header}
                    </div>
                ))}
            </div>

            {/* Data Rows */}
            <div>
                {data.map((item, index) => (
                    <div
                        key={index}
                        style={{
                            display: 'flex',
                            padding: '10px',
                            cursor: 'pointer',
                            backgroundColor: index % 2 === 0 ? '#fff' : '#f9f9f9',
                            transition: 'background-color 0.3s ease', // Smooth transition for hover effect
                            ...(hoveredIndex === index
                                ? { backgroundColor: '#e0e0e0' } // Highlight on hover
                                : {}),
                        }}
                        onClick={() => {
                            console.log("Dropdown item selected:::::: ", item);
                            onSelect(item);
                        }}
                        onMouseEnter={() => setHoveredIndex(index)} // Set hover state
                        onMouseLeave={() => setHoveredIndex(null)} // Remove hover state
                    >
                        {Object.values(item).map((value, idx) => (
                            <div key={idx} style={{ flex: 1, textAlign: 'left' }}>
                                {value}
                            </div>
                        ))}
                    </div>
                ))}
            </div>
        </div>
    );
};

export default DynamicDropdown;
