import React, { useState, useEffect, useRef } from 'react';

const DynamicDropdown = ({
    isOpen,
    onClose,
    triggerRef,
    data = [],
    headers = [],
    onSelect,
    rect,
    dropdownWidth, // Optional: dynamically calculated if not provided
    dropdownHeight = 400,
}) => {
    const dropdownRef = useRef(null);
    const [position, setPosition] = useState({ top: 0, left: 0, width: 0 });
    const [hoveredIndex, setHoveredIndex] = useState(null);

    useEffect(() => {
        if (isOpen && triggerRef?.current) {
            const triggerRect = triggerRef.current.getBoundingClientRect();
            const modalContainer = document.querySelector('.MuiModal-root');
          
            const modalRect = modalContainer?.getBoundingClientRect();

            if (rect) {
                setPosition({
                    top:  rect.top-30,
                    left:  rect.left-35
                    
                });
               
            } else {
                setPosition({
                    top: modalRect ? triggerRect.bottom - modalRect.top : window.scrollY + triggerRect.bottom,
                    left: modalRect ? triggerRect.left - modalRect.left : window.scrollX + triggerRect.left,
                    width: dropdownWidth || triggerRect.width, // Use dynamic width if not provided
                });
            }

        }
    }, [isOpen, triggerRef, dropdownWidth]);

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
                width: position.width,
                maxHeight: `${dropdownHeight}px`,
                overflowY: 'auto',
                border: '1px solid black',
                borderRadius: '4px',
                backgroundColor: '#fff',
                zIndex: 1300,
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
                    <div key={index} style={{ flex: 1, textAlign: 'left', paddingRight: '10px' }}>
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
                            transition: 'background-color 0.3s ease',
                            ...(hoveredIndex === index
                                ? { backgroundColor: '#e0e0e0' }
                                : {}),
                        }}
                        onClick={() => onSelect(item)}
                        onMouseEnter={() => setHoveredIndex(index)}
                        onMouseLeave={() => setHoveredIndex(null)}
                    >
                        {Object.values(item).map((value, idx) => (
                            <div key={idx} style={{ fontSize:'12px', flex: 1, textAlign: 'left', paddingRight: '10px' }}>
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
