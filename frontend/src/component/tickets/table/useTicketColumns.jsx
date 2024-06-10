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
    // {
    //   title: "Số lượng vấn đề",
    //   dataIndex: "status",
    // },
    {
      title: "Action",
      key: "action",
      render: (option) => (
        <Space size="middle">
          <Button
            onClick={() => {
              console.log(option);
              navigate(`/ticket/${option.id}`);
            }}
            type="primary"
          >
            Xem chi tiết
          </Button>
          <Button disabled={option.status === 'CANCEL'}  onClick={() => cancleTicket(option.id)} danger>
            Hủy
          </Button>
          <Button disabled={option.status === 'PAID'} onClick={() => completeTicket(option.id)}>Thanh toán</Button>
        </Space>
      ),
    },
  ];
};
