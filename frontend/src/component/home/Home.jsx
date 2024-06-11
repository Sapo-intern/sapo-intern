import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  Tooltip,
  Legend,
  ResponsiveContainer,
  AreaChart,
  Area,
  } from "recharts";
  import "./home.css";
  import { useEffect, useState } from "react";
  import StatisticApi from "../../api/statistic";
  import { BsPeopleFill, BsTicketPerforated } from "react-icons/bs";
import { BiMoneyWithdraw } from "react-icons/bi";
import { AiOutlineProduct } from "react-icons/ai";
import { MdMiscellaneousServices } from "react-icons/md";
import { format, subDays } from 'date-fns';


const Home = () => {
  const [tickets, setTickets] = useState([]);
  const [transactions, setTransactions] = useState([]);
  const [countTransactions, setCountTransactions] = useState([]);
  const [countTickets, setCountTickets] = useState(0);
  const [customers, setCustomers] = useState(0);
  const [products, setProducts] = useState(0);
  const [services, setServices] = useState(0);
  const endDate = format(new Date(), 'yyyyMMdd');
  const startDate = format(subDays(new Date(), 7), 'yyyyMMdd');

  const fetchAllData = async () => {
    try {
      const [
        ticketsResponse,
        transactionsResponse,
        countTicketsResponse,
        countCustomersResponse,
        countProductsResponse,
        countServicesResponse,
        countTransactionsResponse
      ] = await Promise.all([
        StatisticApi.getTicketsStatistic(),
        StatisticApi.getTransactionsStatistic(),
        StatisticApi.getCountTickets(),
        StatisticApi.getCountCustomers(),
        StatisticApi.getCountProducts(),
        StatisticApi.getCountServices(),
        StatisticApi.getCountTransactions(startDate,endDate)
      ]);

      const formattedTickets = ticketsResponse.data.map((item) => ({
        name: item.date,
        count: item.quantity,
      }));

      const formattedTransactions = transactionsResponse.data.map((item) => ({
        name: formatDate(item.date),
        uv: item.amount,
      }));

      setTickets(formattedTickets);
      setTransactions(formattedTransactions);
      setCountTickets(countTicketsResponse.data);
      setCustomers(countCustomersResponse.data);
      setProducts(countProductsResponse.result);
      setServices(countServicesResponse.result);
      setCountTransactions(countTransactionsResponse.data);
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  };

  useEffect(() => {
    fetchAllData();
  }, []);

  const formatDate = (dateStr) => {
    const month = dateStr.slice(4, 6);
    const day = dateStr.slice(6, 8);
    return `${day}-${month}`;
  };

  return (
    <main className="main-container">
      {/* <div className="main-title">
        <h3>DASHBOARD</h3>
      </div> */}

      <div className="main-cards">
        <div className="card">
          <div className="card-inner">
            <h3>Hóa Đơn</h3>
            <BsTicketPerforated className="card_icon" />
          </div>
          <h1>{countTickets}</h1>
        </div>
        <div className="card">
          <div className="card-inner">
            <h3>Doanh Thu</h3>
            <BiMoneyWithdraw className="card_icon" />
          </div>
          <h1>{countTransactions}</h1>
        </div>
        <div className="card">
          <div className="card-inner">
            <h3>Khách hàng</h3>
            <BsPeopleFill className="card_icon" />
          </div>
          <h1>{customers}</h1>
        </div>
        <div className="card">
          <div className="card-inner">
            <h3>Sản phẩm</h3>
            <AiOutlineProduct unt className="card_icon" />
          </div>
          <h1>{products}</h1>
        </div>
        <div className="card">
          <div className="card-inner">
            <h3>Dịch Vụ</h3>
            <MdMiscellaneousServices className="card_icon" />
          </div>
          <h1>{services}</h1>
        </div>
      </div>

      <div className="charts">
        <div className="charts-item">
          <ResponsiveContainer width="100%" height="100%">
            <AreaChart
              width={500}
              height={400}
              data={transactions}
              margin={{
                top: 10,
                right: 30,
                left: 0,
                bottom: 0,
              }}
            >
              <XAxis dataKey="name" />
              <YAxis />
              <Tooltip />
              <Area
                type="monotone"
                dataKey="uv"
                stroke="#8884d8"
                fill="#8884d8"
              />
            </AreaChart>
          </ResponsiveContainer>
        </div>

        <div className="charts-item">
          <ResponsiveContainer width="100%" height="100%">
            <BarChart
              width={500}
              height={300}
              data={tickets}
              margin={{
                top: 5,
                right: 30,
                left: 20,
                bottom: 5,
              }}
              barSize={20}
            >
              <XAxis
                dataKey="name"
                scale="point"
                padding={{ left: 10, right: 10 }}
              />
              <YAxis />
              <Tooltip />
              <Legend />
              <Bar
                dataKey="count"
                fill="#8884d8"
                background={{ fill: "#eee" }}
              />
            </BarChart>
          </ResponsiveContainer>
        </div>
      </div>
    </main>
  );
};

export default Home;
