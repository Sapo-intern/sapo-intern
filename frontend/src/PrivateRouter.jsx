import { Navigate, Outlet, } from "react-router-dom";
import { useAuth } from "./Context/ContextAuth";


const PrivateRouter = () => {
  const { isLoggedIn } = useAuth();

  return isLoggedIn ? <Outlet /> : <Navigate to="/login" />;
};

export default PrivateRouter;