import { useEffect, useState } from "react";
import { Col, Row, Table, Input, Pagination } from "antd";
import { CustomerApi } from "../../api/customer";
const { Search } = Input;

const getColumns = [
  {
    title: "Mã khách hàng",
    dataIndex: "id",
  },
  {
    title: "Tên",
    dataIndex: "name",
  },
  {
    title: "Số điện thoại",
    dataIndex: "phoneNumber",
  },
  {
    title: "Email",
    dataIndex: "email",
  },
  {
    title: "Address",
    dataIndex: "address",
  },
];

const CustomersList = () => {
  const [customers, setCustomers] = useState([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [pageSize, setPageSize] = useState(5);
  const [totalItems, setTotalItems] = useState(0);

  const fetchCustomers = async (page, size) => {
    try {
      const response = await CustomerApi.getAll(page, size);
      setCustomers(response.content);
      setTotalItems(response.totalElements);
    } catch (error) {
      console.error("Không có dữ liệu:", error);
    }
  };

  useEffect(() => {
    fetchCustomers(currentPage, pageSize);
  }, [currentPage, pageSize]);

  const handlePageChange = (page, pageSize) => {
    setCurrentPage(page - 1);
    setPageSize(pageSize);
  };

  const searchCustomer = async (keyword) => {
    try {
      const response = await CustomerApi.searchCustomer(keyword);
      setCustomers(response);
    } catch (error) {
      console.error("Không tìm thấy khách hàng:", error);
    }
  };

  const handleSearch = (value) => {
    if (value) {
      searchCustomer(value);
    } else {
      fetchCustomers(currentPage, pageSize);
    }
  };

  return (
    <>
      <h1>Khách hàng</h1>
      <Row style={{ marginBottom: 16, marginTop: 16 }}>
        <Col span={8}>
          <Search
            placeholder="Vui lòng nhập khách hàng"
            allowClear
            enterButton="Tìm kiếm"
            size="large"
            onSearch={handleSearch}
            onChange={(e) => handleSearch(e.target.value)}
          />
        </Col>
      </Row>
      <Table
        columns={getColumns}
        dataSource={customers.map((item) => ({
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

export default CustomersList;
