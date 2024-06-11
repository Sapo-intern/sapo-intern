import { useEffect, useState } from "react";
import { Button, Col, Row, Space, Table, Input, Pagination, Modal } from "antd";
import { Link, useNavigate } from "react-router-dom";
import Swal from "sweetalert2";
const { Search } = Input;
import ServiceApi from "../../api/services";
import ServicesAdd from "./ServicesAdd";
import ServiceEdit from "./ServicesEdit";

const getColumns = (handleDelete,handleEdit) => [
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
        <Button danger onClick={() => handleDelete(record.id)}>
          Xóa
        </Button>
        <Button onClick={() => handleEdit(record.id)}>
          Sửa
        </Button>
      </Space>
    ),
  },
];

const ServicesList = () => {
  const [services, setServices] = useState([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [pageSize, setPageSize] = useState(5);
  const [totalItems, setTotalItems] = useState(0);
  const navigate = useNavigate();
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [isEditModalVisible, setIsEditModalVisible] = useState(false);
  const [currentServicesId, setCurrentServicesId] = useState(null);

  const fetchServices = async (page, size) => {
    try {
      const response = await ServiceApi.getAllService(page, size);
      setServices(response.content);
      setTotalItems(response.totalElements);
    } catch (error) {
      console.error("Không có dữ liệu:", error);
    }
  };

  const searchSearch = async (name) => {
    try {
      const response = await ServiceApi.searchService(name);
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
        await ServiceApi.deleteService(id);
        Swal.fire("Đã xóa!", "Dịch vụ đã được xóa.", "success").then(() =>
          navigate("/services")
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

  const showModal = () => {
    setIsModalVisible(true);
  };

  const handleCancel = () => {
    setIsModalVisible(false);
    setIsEditModalVisible(false);
  };

  const handleEdit = (id) => {
    setCurrentServicesId(id);
    setIsEditModalVisible(true);
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
          <Button size="large" type="primary" onClick={showModal}>
            Thêm dịch vụ
          </Button>
        </Col>
      </Row>
      <Table
        columns={getColumns(handleDelete,handleEdit)}
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

      <Modal visible={isModalVisible} onCancel={handleCancel} footer={null}>
        <ServicesAdd closeModal={handleCancel} fetchServices={fetchServices} />
      </Modal>
      <Modal
        visible={isEditModalVisible}
        onCancel={handleCancel}
        footer={null}
        destroyOnClose={true}
      >
        <ServiceEdit closeModal={handleCancel} fetchServices={fetchServices} serviceId={currentServicesId} />
      </Modal>
    </>
  );
};

export default ServicesList;
