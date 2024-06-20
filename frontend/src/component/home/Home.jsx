import Highcharts from 'highcharts';
import HighchartsReact from 'highcharts-react-official';
import { useEffect, useState } from "react";
import StatisticApi from "../../api/statistic";
import { BsPeopleFill, BsTicketPerforated } from "react-icons/bs";
import { BiMoneyWithdraw } from "react-icons/bi";
import { AiOutlineProduct } from "react-icons/ai";
import { MdMiscellaneousServices } from "react-icons/md";
import { format, subDays } from 'date-fns';
import "./home.css";

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

  const transactionsOptions = {
    chart: {
      type: 'area'
    },
    title: {
      text: 'Doanh thu trong một tuần'
    },
    xAxis: {
      categories: transactions.map(item => item.name)
    },
    yAxis: {
      title: {
        text: 'Tiền'
      }
    },
    series: [{
      name: 'Doanh thu',
      data: transactions.map(item => item.uv)
    }],
    credits: {
      enabled: false,
    }
  };

  const ticketsOptions = {
    chart: {
      type: 'column'
    },
    title: {
      text: "Số lượng phiếu sửa chữa trong ngày"
    },
    xAxis: {
      categories: tickets.map(item => item.name)
    },
    yAxis: {
      title: {
        text: 'Số lượng'
      }
    },
    series: [{
      name: 'Phiếu sửa chữa',
      data: tickets.map(item => item.count)
    }],
    credits: {
      enabled: false,
    }
  };

  return (
    <main className="main-container">
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
            <AiOutlineProduct className="card_icon" />
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
          <HighchartsReact
            highcharts={Highcharts}
            options={transactionsOptions}
          />
        </div>

        <div className="charts-item">
          <HighchartsReact
            highcharts={Highcharts}
            options={ticketsOptions}
          />
        </div>
      </div>
    </main>
  );
};

export default Home;
