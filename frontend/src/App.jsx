import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import ProductList from "./component/products/ProductsList";
import Layouts from "./Layout";
import Home from "./component/home/Home";
import ProductsAdd from "./component/products/ProductsAdd";
import ProductEdit  from "./component/products/ProductEdit";
import ServicesList  from "./component/services/ServicesList";
import ServicesAdd  from "./component/services/ServicesAdd";
import ServicesEdit  from "./component/services/ServicesEdit";
import Login from "./component/Auth/Login";
import NotFound from "./component/NotFound";
import UserList from "./component/User/UserList";
import UserAdd from "./component/User/UserAdd";
import PrivateRouter from "./PrivateRouter";
import ForgotPassword from "./component/Auth/ForgotPassword";
import ResetPassword from "./component/Auth/ResetPassword";
import ChangePassword from "./component/Auth/ChangePassword";
import TicketList from "./component/tickets/TicketList";
import TicketAdd from "./component/tickets/TicketAdd";
import TicketDetail from "./component/tickets/TicketDetail";
import { ToastContainer } from "react-toastify";

const App = () => {
  return (
    <Router>
      <ToastContainer/>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/forgotpassword" element={<ForgotPassword />} />
        <Route path="/resetpassword/:token" element={<ResetPassword />} />
        <Route path="/changepassword" element={<ChangePassword />} />

        <Route element={<PrivateRouter />}>
          <Route element={<Layouts />}>

            <Route path="/" element={<Home />} />

            <Route path="/product" element={<ProductList />} />
            <Route path="/product/add" element={<ProductsAdd />} />
            <Route path="/product/:id" element={<ProductEdit />} />

            <Route path="/services" element={<ServicesList/>} />
            <Route path="/services/add" element={<ServicesAdd/>} />
            <Route path="/services/:id" element={<ServicesEdit />} />

            <Route path="/user" element={<UserList />} />
            <Route path="/user/add" element={<UserAdd />} />


            <Route path="ticket" element= {<TicketList/>}/>
            <Route path="ticket/add" element= {<TicketAdd/>}/>
            <Route path="ticket/:id" element= {<TicketDetail/>}/>

          </Route>
        </Route>
        <Route path="/error" element={<NotFound />} />
      </Routes>
    </Router>
  );
};

export default App;
