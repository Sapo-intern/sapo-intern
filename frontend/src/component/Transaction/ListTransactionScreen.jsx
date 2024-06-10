import React from "react";
import { useTransaction } from "./useTransactions";
import { Card, Col, DatePicker, Flex, Row, Table } from "antd";
import { useTransactionColumns } from "./useTransactionColumns";

const ListTransactionScreen = () => {
  const { transactions, totalAmount } = useTransaction();
  console.log(transactions)
  console.log(totalAmount);
  return (
    <div className="">
      <Row>
        <Col span={8}>
          <Card title="Thống kê doanh thu">
            <p>Tổng doanh thu theo tháng : {totalAmount.month} </p>
            <p>Tổng doanh thu theo tuần : {totalAmount.week} </p>
            <p>Tổng doanh thu theo ngày : {totalAmount.day || 0} </p>
          </Card>
        </Col>
      </Row>
      <Table
        style={{ marginTop: 10 }}
        dataSource={transactions}
        columns={useTransactionColumns()}
      ></Table>
      ;
    </div>
  );
};

export default ListTransactionScreen;
