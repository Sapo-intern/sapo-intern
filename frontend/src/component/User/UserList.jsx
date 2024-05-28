import { useEffect, useState } from "react";
import { Button, Col, Row, Space, Table, Tag, Input, Pagination } from "antd";
import { Link, useNavigate } from "react-router-dom";
import Swal from "sweetalert2";
import UserApi from "../../api/user";
const { Search } = Input;
import { useAuth } from "../../Context/ContextAuth";
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
        <Button danger onClick={() => handleDelete(record.id)}>Xóa</Button>
        <Button>Sửa</Button>
      </Space>
    ),
  },
];

const UserList = () => {
  const [user, setUser] = useState([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [pageSize, setPageSize] = useState(5);
  const [totalItems, setTotalItems] = useState(0);
  const { token } = useAuth();
  const navigate = useNavigate();

  const fetchUser = async (page, size) => {
    try {
      const response = await UserApi.getAll(page, size, 
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      setUser(response.content);
      setTotalItems(response.totalElements);
    } catch (error) {
      console.error("Không có dữ liệu:", error);
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
        await UserApi.delete(id, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
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

  return (
    <>
      <h1>Danh sách nhân viên</h1>
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
          <Link to="/user/add">
            <Button size="large" type="primary">
              Thêm nhân viên
            </Button>
          </Link>
        </Col>
      </Row>
      <Table
        columns={getColumns(handleDelete)}
        dataSource={user.map((item) => ({
          ...item,
          key: item.id,
          name: item.name
        }))}
        pagination={false}
        // onChange={onChange}
      />
      ;
      <Pagination
        style={{ marginTop: "20px", textAlign: "center" }}
        current={currentPage + 1}
        pageSize={pageSize}
        total={totalItems}
        onChange={handlePageChange}
      />
    </>
  );
};
export default UserList;
