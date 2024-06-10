import { Button, Space } from "antd";
import { useNavigate } from "react-router-dom";

export const useIssueColumns = () => {
  const navigate = useNavigate();

  return [
    {
      title: "Id",
      dataIndex: "id",
    },
    {
      title: "Mô tả",
      dataIndex: "description",
    },
    {
      title: "Tình trạng",
      dataIndex: "status",
    },
    {
      title: "Tiến độ",
      dataIndex: "progress",
    },
    {
      title: "Tổng tiền",
      dataIndex: "totalAmount",
    },
    {
      title: "Action",
      key: "action",
      render: (option) => (
        <Space size="middle">
          <Button
            onClick={() => {
              console.log(option);
              navigate(`/issue/${option.id}`);
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
