import { useEffect, useState } from "react";
import { Button, Col, Row, Space, Table, Input, Pagination } from "antd";
import { Link, useNavigate } from "react-router-dom";
import Swal from "sweetalert2";
const { Search } = Input;
import { useAuth } from "../../Context/ContextAuth";
import ServiceApi from "../../api/services";

const getColumns = (handleDelete) => [
  {
    title: "Mã dịch vụ",
    dataIndex: "servicesCode",
  },
  {
    title: "Tên",
    dataIndex: "name",
  },
  {
    title: "Giá",
    dataIndex: "price",
  },
  {
    title: "Mô tả",
    dataIndex: "description",
  },
  {
    title: "Action",
    key: "action",
    render: (text, record) => (
      <Space size="middle">
        <Button danger onClick={() => handleDelete(record.id)}>Xóa</Button>
        <Button><Link to={`/product/${record.id}`}>Sửa</Link></Button>
      </Space>
    ),
  },
];

const ServicesList = () => {
  const [services, setServices] = useState([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [pageSize, setPageSize] = useState(5);
  const [totalItems, setTotalItems] = useState(0);
  const { token } = useAuth();
  const navigate = useNavigate();

  const fetchServices = async (page, size) => {
    try {
      const response = await ServiceApi.getAllService(page, size, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setServices(response.content);
      setTotalItems(response.totalElements);
    } catch (error) {
      console.error("Không có dữ liệu:", error);
    }
  };

  const searchSearch = async (name) => {
    try {
      const response = await ServiceApi.searchService(name, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setServices(response);
    } catch (error) {
      console.error("Không tìm thấy sản phẩm:", error);
    }
  };

  useEffect(() => {
    fetchServices(currentPage, pageSize);
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
        await ServiceApi.deleteService(id, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        Swal.fire("Đã xóa!", "Dịch vụ đã được xóa.", "success").then(() =>
          navigate("/product")
        );
        fetchServices(currentPage, pageSize);
      }
    } catch (error) {
      Swal.fire({
        title: "Error!",
        text: "Xóa dịch vụ thất bại!",
        icon: "error",
        confirmButtonText: "OK",
      });
    }
  };

  const handleSearch = (value) => {
    if (value) {
      searchSearch(value);
    } else {
      fetchServices(currentPage, pageSize);
    }
  };

  return (
    <>
      <h1>DỊCH VỤ</h1>
      <Row style={{ marginBottom: 16, marginTop: 16 }}>
        <Col span={8}>
          <Search
            placeholder="Vui lòng nhập dịch vụ"
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
          <Link to="/service/add">
            <Button size="large" type="primary">
              Thêm dịch vụ
            </Button>
          </Link>
        </Col>
      </Row>
      <Table
        columns={getColumns(handleDelete)}
        dataSource={services.map((item) => ({
          ...item,
          key: item.id,
        }))}
        pagination={false}
      />
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

export default ServicesList;
