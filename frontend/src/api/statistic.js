import instance from "./instance";

const StatisticApi = {
  getTicketsStatistic() {
    const url = `/tickets/statistic`;
    return instance.get(url);
  },

  getTransactionsStatistic() {
    const url = `/transactions/statistic`;
    return instance.get(url);
  },

  getCountTickets() {
    const url = `/tickets/count`;
    return instance.get(url);
  },

  getCountTransactions(fromDate,toDate) {
    const url = `/transactions/get-amounts?fromDate=${fromDate}&toDate=${toDate}`;
    return instance.get(url);
  },

  

  getCountCustomers() {
    const url = `/customers/count`;
    return instance.get(url);
  },

  getCountProducts() {
    const url = `/products/count`;
    return instance.get(url);
  },

  getCountServices() {
    const url = `/services/count`;
    return instance.get(url);
  },
};

export default StatisticApi;