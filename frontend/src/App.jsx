import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import ProductList from "./component/products/ProductsList";
import Layouts from "./Layout";
import Home from "./component/home/Home";
import ProductEdit from "./component/products/ProductEdit";
import ServicesList from "./component/services/ServicesList";
import ServicesEdit from "./component/services/ServicesEdit";
import Login from "./component/Auth/Login";
import NotFound from "./component/NotFound";
import UserList from "./component/User/UserList";
import PrivateRouter from "./PrivateRouter";
import ForgotPassword from "./component/Auth/ForgotPassword";
import ResetPassword from "./component/Auth/ResetPassword";
import ChangePassword from "./component/Auth/ChangePassword";
import UserEdit from "./component/User/UserEdit";
import TicketList from "./component/tickets/TicketList";
import TicketAdd from "./component/tickets/TicketAdd";
import TicketDetail from "./component/tickets/TicketDetail";
import ListIssueScreen from "./component/issues/ListIssueScreen";
import DetailIssueScreen from "./component/issues/DetailIssueScreen";
import ListTransactionScreen from "./component/Transaction/ListTransactionScreen";
import ProductStatisticScreen from "./component/storages/ProductStatisticScreen";
import CustomersList from "./component/customers/CustomersList";
import { AuthProvider } from "./Context/ContextAuth";
import ChangePasswordUser from "./component/User/ChangePassword";

const App = () => {
  return (
    <Router>
      <AuthProvider>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/forgotpassword" element={<ForgotPassword />} />
          <Route path="/resetpassword/:token" element={<ResetPassword />} />
          <Route path="/changepassword" element={<ChangePassword />} />

          <Route element={<PrivateRouter />}>
            <Route element={<Layouts />}>

              <Route element={<PrivateRouter requiredRole="MANAGER" />}>
                <Route path="/" element={<Home />} />
                <Route path="/user" element={<UserList />} />
                <Route path="/storage" element={<ProductStatisticScreen />} />
              </Route>

              <Route path="/product" element={<ProductList />} />
              <Route path="/product/:id" element={<ProductEdit />} />

              <Route path="/services" element={<ServicesList />} />
              <Route path="/services/:id" element={<ServicesEdit />} />

              <Route path="/user/:id" element={<UserEdit />} />
              <Route
                path="/user/changepassword"
                element={<ChangePasswordUser />}
              />

              <Route path="ticket" element={<TicketList />} />
              <Route path="ticket/add" element={<TicketAdd />} />
              <Route path="ticket/:id" element={<TicketDetail />} />
              <Route path="/ticket" element={<TicketList />} />
              <Route path="/ticket/add" element={<TicketAdd />} />
              <Route path="/ticket/:id" element={<TicketDetail />} />

              <Route path="customers" element={<CustomersList />} />
              <Route path="/transaction" element={<ListTransactionScreen />} />

              <Route path="/issue" element={<ListIssueScreen />} />
              <Route path="/issue/:id" element={<DetailIssueScreen />} />

            </Route>
          </Route>
          <Route path="/error" element={<NotFound />} />
        </Routes>
      </AuthProvider>
    </Router>
  );
};

export default App;
