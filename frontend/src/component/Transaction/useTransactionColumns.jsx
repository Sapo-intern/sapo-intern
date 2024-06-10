import { Button, Space } from "antd";
import { useNavigate } from "react-router-dom";

export const useTransactionColumns = () => {
  const navigate = useNavigate();
  return [
    {
      title: "Id",
      dataIndex: "id",
    },
    {
      title: "Ngày tạo",
      dataIndex: "createdDate",
    },
    {
      title: "Tổng tiền",
      dataIndex: "amount",
    },
    {
      title: "Action",
      key: "action",
      render: (option) => (
        <Space size="middle">
          <Button
            onClick={() => {
              console.log(option);
              navigate(`/transaction/${option.id}`);
            }}
            type="primary"
          >
            Xem chi tiết
          </Button>
        </Space>
      ),
    },
  ];
};
