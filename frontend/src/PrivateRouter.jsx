import { Navigate, Outlet } from "react-router-dom";
import { useAuth } from "./Context/ContextAuth";

const PrivateRouter = ({ requiredRole }) => {
  const { isLoggedIn, user } = useAuth();

  if (!isLoggedIn) {
    return <Navigate to="/login" />;
  }

  if (requiredRole && user?.role !== requiredRole) {
    return <Navigate to="/error" />;
  }

  return <Outlet />;
};

export default PrivateRouter;