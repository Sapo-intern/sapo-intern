export const useTicketColumns = () => {
  return [
    {
      title: "Id",
      dataIndex: "id",
    },
    {
      title: "Mô tả",
      dataIndex: "Description",
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
      title: "Số lượng vấn đề",
      dataIndex: "status",
    },

    {
      title: "Action",
      key: "action",
      render: () => (
        <Space size="middle">
          <Button type="primary">Xem chi tiết</Button>
          <Button danger>Xóa</Button>
          <Button>Sửa</Button>
        </Space>
      ),
    },
  ];
};
