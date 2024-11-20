

const SideButtons = ({ onAdd, onUpdate, onDelete, onClear, onShow }) => {
    return (
        <div className="flex flex-col items-center justify-center gap-2 space-y-1">
            {/* Show Button */}
            <button className="sidebtn bg-blue-500   hover:bg-blue-600" onClick={onShow} >
                Show
            </button>
            {/* Clear Button */}
            <button className="sidebtn bg-green-500  hover:bg-green-600" onClick={onClear} >
                Clear
            </button>
            {/* Add Button */}
            <button className="sidebtn bg-yellow-500  hover:bg-yellow-600" onClick={onAdd} >
                Add
            </button>
            {/* Update Button */}
            <button className="sidebtn bg-orange-500 hover:bg-orange-600"onClick={onUpdate} >
                Update
            </button>
            {/* Delete Button */}
            <button className="sidebtn bg-red-500 hover:bg-red-600" onClick={onDelete} >
                Delete
            </button>
            {/* First Button */}
            {/* <button className="sidebtn bg-indigo-500 hover:bg-indigo-600" >
                First
            </button> */}
            {/* Previous Button */}
            <button className="sidebtn bg-purple-500 hover:bg-purple-600">
                Previous
            </button>
            {/* Next Button */}
            <button className="sidebtn bg-teal-500  hover:bg-teal-600">
                Next
            </button>
            {/* Last Button */}
            {/* <button className="sidebtn bg-gray-500 hover:bg-gray-600">
                Last
            </button> */}
        </div>
    );
};

export default SideButtons;
