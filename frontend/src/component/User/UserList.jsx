import { useEffect, useState } from "react";
import { Button, Col, Row, Space, Table, Tag, Input, Pagination } from "antd";
import { Link, useNavigate } from "react-router-dom";
import Swal from "sweetalert2";
import UserApi from "../../api/user";
import Modal from "antd/es/modal/Modal";
const { Search } = Input;
import UserAdd from "./UserAdd";
const getColumns = (handleDelete) => [
  {
    title: "Tên",
    dataIndex: "name",
  },
  {
    title: "Số điện thoại",
    dataIndex: "phone",
    sorter: (a, b) => a.age - b.age,
  },
  {
    title: "Age",
    dataIndex: "age",
    sorter: (a, b) => a.age - b.age,
  },
  {
    title: "Hình ảnh",
    dataIndex: "urlImage",
    render: (urlImage) => (
      <img src={urlImage} style={{ width: 160, height: 160 }} />
    ),
  },
  {
    title: "Địa chỉ",
    dataIndex: "address",
    filters: [
      {
        text: "London",
        value: "London",
      },
      {
        text: "New York",
        value: "New York",
      },
    ],
    onFilter: (value, record) => record.address.startsWith(value),
    filterSearch: true,
    // width: "40%",
  },
  {
    title: "Email",
    dataIndex: "email",
  },
  {
    title: "Nhân viên",
    dataIndex: "role",
    render: (role) => {
      let color = "";
      if (role === "TECHNICIAN") {
        color = "red";
      } else if (role === "COORDINATOR") {
        color = "green";
      } else {
        color = "blue";
      }
      return <Tag color={color}>{role.toUpperCase()}</Tag>;
    },
    sorter: (a, b) => a.role.localeCompare(b.role),
  },
  {
    title: "Action",
    key: "action",
    render: (text, record) => (
      <Space size="middle">
        <Button danger onClick={() => handleDelete(record.id)}>
          Xóa
        </Button>
        <Button>
          <Link to={`/user/${record.id}`}>Sửa</Link>
        </Button>
      </Space>
    ),
  },
];

const UserList = () => {
  const [user, setUser] = useState([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [pageSize, setPageSize] = useState(5);
  const [totalItems, setTotalItems] = useState(0);
  const navigate = useNavigate();
  const [isModalVisible, setIsModalVisible] = useState(false);

  const fetchUser = async (page, size) => {
    try {
      const response = await UserApi.getAll(page, size);
      setUser(response.content);
      setTotalItems(response.totalElements);
    } catch (error) {
      console.error("Không có dữ liệu:", error);
    }
  };

  const searchUser = async (keyword) => {
    try {
      const response = await UserApi.searchUser(keyword);
      setUser(response.result);
    } catch (error) {
      console.error("Không tìm thấy nhân viên:", error);
    }
  };

  useEffect(() => {
    fetchUser(currentPage, pageSize);
  }, [currentPage, pageSize]);

  const handlePageChange = (page, pageSize) => {
    setCurrentPage(page - 1);
    setPageSize(pageSize);
  };

  const handleDelete = async (id) => {
    try {
      const result = await Swal.fire({
        title: "Bạn chắc chắn muốn xóa?",
        text: "Hành động này sẽ không thể hoàn tác!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#d33",
        cancelButtonColor: "#3085d6",
        confirmButtonText: "Xóa",
      });

      if (result.isConfirmed) {
        await UserApi.delete(id);
        Swal.fire("Đã xóa!", "Nhân viên đã được xóa.", "success").then(() =>
          navigate("/user")
        );
        fetchUser(currentPage, pageSize);
      }
    } catch (error) {
      Swal.fire({
        title: "Error!",
        text: "Xóa nhân viên thất bại!",
        icon: "error",
        confirmButtonText: "OK",
      });
    }
  };

  const handleSearch = (value) => {
    if (value) {
      searchUser(value);
    } else {
      fetchUser(currentPage, pageSize);
    }
  };

  const showModal = () => {
    setIsModalVisible(true);
  };

  const handleCancel = () => {
    setIsModalVisible(false);
  };

  return (
    <>
      <h1>Danh sách nhân viên</h1>
      <Row style={{ marginBottom: 16, marginTop: 16 }}>
        <Col span={8}>
          <Search
            placeholder="Nhập thông tin nhân viên"
            allowClear
            enterButton="Tìm kiếm"
            size="large"
            onSearch={handleSearch}
            onChange={(e) => handleSearch(e.target.value)}
          />
        </Col>
        <Col
          span={8}
          offset={8}
          style={{ display: "flex", justifyContent: "end" }}
        >
          <Button size="large" type="primary" onClick={showModal}>
            Thêm nhân viên
          </Button>
        </Col>
      </Row>
      <Table
        columns={getColumns(handleDelete)}
        dataSource={user.map((item) => ({
          ...item,
          key: item.id,
          name: item.name,
        }))}
        pagination={false}
      />
      ;
      <Pagination
        style={{ marginTop: "20px", textAlign: "center" }}
        current={currentPage + 1}
        pageSize={pageSize}
        total={totalItems}
        onChange={handlePageChange}
      />
      <Modal visible={isModalVisible} onCancel={handleCancel} footer={null}>
        <UserAdd closeModal={handleCancel} fetchUser={fetchUser} />
      </Modal>
    </>
  );
};
export default UserList;
