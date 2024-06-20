import { Button, Space } from "antd";
import { useNavigate } from "react-router-dom";

export const useTicketColumns = (cancleTicket, completeTicket) => {
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
      title: "Ngày tạo",
      dataIndex: "createdDate",
    },
    {
      title: "Ngày hoàn thành",
      dataIndex: "completeDate",
    },
    {
      title: "Tổng tiền",
      dataIndex: "totalAmount",
    },
    {
      title: "Trạng thái",
      dataIndex: "status",
    },
    {
      title: "",
      key: "action",
      render: (option) => (
        <Space size="middle">
          <Button
            onClick={() => {
              navigate(`/ticket/${option.id}`);
            }}
            type="primary"
          >
            Xem chi tiết
          </Button>
          <Button disabled={option.status === 'Đã hủy' || option.status === 'Đã thanh toán'}  onClick={() => cancleTicket(option.id)} danger>
            Hủy
          </Button>
          <Button disabled={option.status !== 'Đã hoàn thành'} onClick={() => completeTicket(option.id)}>Thanh toán</Button>
        </Space>
      ),
    },
  ];
};
