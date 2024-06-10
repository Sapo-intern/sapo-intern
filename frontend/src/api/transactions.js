import instance from "./instance";

export const TransactionApi = {
  getAmountByDate: async (fromDate, toDate) => {
    return await instance.get(`/transactions/get-amounts?fromDate=${fromDate}&toDate=${toDate}`);
  },
  getTransactions: async (page, size) => {
    return await instance.get(`/transactions/?page=${page}&size=${size}`);
  }
};
