import { useEffect, useState } from "react";
import { TransactionApi } from "../../api/transactions";

export const useTransaction = (page = 0, size = 1000) => {
  const [transactions, setTransactions] = useState([]);
  const [totalAmount, setTotalAmount] = useState({
    today: 0,
    week: 0,
    month: 0,
  });

  function getDateNow() {
    const date = new Date();
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, "0");
    const day = String(date.getDate()).padStart(2, "0");
    return `${year}${month}${day}`;
  }

  function getDateOneWeekBefore() {
    const date = new Date();
    date.setDate(date.getDate() - 7); // Subtract 7 days from the current date
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, "0");
    const day = String(date.getDate()).padStart(2, "0");
    return `${year}${month}${day}`;
  }

  function getDateOneMonthAgo() {
    const date = new Date();
    date.setMonth(date.getMonth() - 1); // Subtract 1 month from the current date
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, "0");
    const day = String(date.getDate()).padStart(2, "0");
    return `${year}${month}${day}`;
  }

  useEffect(() => {
    const fetchData = async () => {
      const { data } = await TransactionApi.getTransactions(page, size);
      setTransactions(data);
    };
    fetchData();
  }, [page, size]);

  useEffect(() => {
    const fetchAmounts = async () => {
      const resNow = await TransactionApi.getAmountByDate(
        getDateNow(),
        getDateNow()
      );
      const resWeek = await TransactionApi.getAmountByDate(
        getDateOneWeekBefore(),
        getDateNow()
      );
      const resMonth = await TransactionApi.getAmountByDate(
        getDateOneMonthAgo(),
        getDateNow()
      );
      setTotalAmount({
        today: resNow.data,
        week: resWeek.data,
        month: resMonth.data,
      });
    };
    fetchAmounts();
  }, []);

  return { transactions, totalAmount };
};
