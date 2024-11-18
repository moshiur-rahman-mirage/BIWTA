import { Link } from "react-router-dom";


const ErrorPage = () => {
    return (
        <div className="text-center mt-60">
            <h2 className="text-6xl font-extrabold  text-red-400">SORRY</h2>
            <h2 className="text-3xl font-bold">An Error Occured</h2>
            <br/>
            <Link to="/">
                <button className="btn btn-primary">Go Back</button>
            </Link>
        </div>
    );
};

export default ErrorPage;