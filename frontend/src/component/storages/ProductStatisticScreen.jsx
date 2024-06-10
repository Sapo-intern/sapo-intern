import React, { useState } from "react";
import { useStorages } from "./useStorages";
import { Button, Col, Flex, Input, Row, Select, Space, Table } from "antd";
import { useProducts } from "../tickets/hooks/useProducts";
import { useNavigate } from "react-router-dom";

const ProductStatisticScreen = () => {
  const { storages, saveStorage, setStorageQuantity } = useStorages();
  const navigate = useNavigate();
  const columns = [
    {
      title: "Id",
      dataIndex: "productId",
    },
    {
      title: "Tên sản phẩm",
      dataIndex: "name",
    },
    {
      title: "Số lượng",
      dataIndex: "totalQuantity",
    },
    {
      title: "Nhập hàng",
      key: "import",
      render: (option) => {
        return (
          <Space size="middle">
            <Input
              type="number"
              value={option.importQuantity}
              onChange={(e) => setStorageQuantity(option.productId, e.target.value)}
            />
            <Button
              onClick={(e) => saveStorage(option.productId, option?.importQuantity)}
            >
              Thêm
            </Button>
          </Space>
        );
      },
    },
    {
      title: "Action",
      key: "action",
      render: (option) => (
        <Space size="middle">
          <Button
            onClick={() => {
              navigate(`/product/${option.productId}`);
            }}
            type="primary"
          >
            Xem chi tiết
          </Button>
        </Space>
      ),
    },
  ];

  return (
    <div>
      <h3>Thống kê số lượng hàng hóa</h3>
      <Table
        style={{ marginTop: 12 }}
        dataSource={storages}
        columns={columns}
      />
    </div>
  );
};

export default ProductStatisticScreen;
