import React from "react";
import { Button, Col, Row, Table } from "antd";
import Search from "antd/es/transfer/search";
import { Link } from "react-router-dom";
import { useTicketColumns } from "./table/useTicketColumns";

const TicketList = () => {
  return (
    <div>
      <h1>Phiếu sửa chữa</h1>
      <Row style={{ marginBottom: 16, marginTop: 16 }}>
        <Col span={8}>
          <Search
            placeholder="Vui lòng nhập sản phẩm"
            allowClear
            enterButton="Tìm kiếm"
            size="large"
            // onSearch={onSearch}
          />
        </Col>
        <Col
          span={8}
          offset={8}
          style={{ display: "flex", justifyContent: "end" }}
        >
          <Link to="/ticket/add">
            <Button size="large" type="primary">
              Thêm phiếu sửa chữa
            </Button>
          </Link>
        </Col>
      </Row>
      <Table columns={useTicketColumns()} dataSource={[]} onChange={null} />;
    </div>
  );
};

export default TicketList;