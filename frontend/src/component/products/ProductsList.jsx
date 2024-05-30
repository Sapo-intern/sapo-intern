import { useEffect, useState } from "react";
import { Button, Col, Row, Space, Table, Input, Pagination } from "antd";
import { Link, useNavigate } from "react-router-dom";
import Swal from "sweetalert2";
const { Search } = Input;
import { useAuth } from "../../Context/ContextAuth";
import ProductApi from "../../api/products";

const getColumns = (handleDelete) => [
  {
    title: "Mã sản phẩm",
    dataIndex: "productCode",
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
    title: "Số lượng",
    dataIndex: "quantity",
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

const ProductList = () => {
  const [products, setProducts] = useState([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [pageSize, setPageSize] = useState(5);
  const [totalItems, setTotalItems] = useState(0);
  const { token } = useAuth();
  const navigate = useNavigate();

  const fetchProducts = async (page, size) => {
    try {
      const response = await ProductApi.getAllProducts(page, size, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setProducts(response.content);
      setTotalItems(response.totalElements);
    } catch (error) {
      console.error("Không có dữ liệu:", error);
    }
  };

  const searchSearch = async (name) => {
    try {
      const response = await ProductApi.searchProduct(name, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setProducts(response);
    } catch (error) {
      console.error("Không tìm thấy sản phẩm:", error);
    }
  };

  useEffect(() => {
    fetchProducts(currentPage, pageSize);
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
        await ProductApi.deleteProduct(id, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        Swal.fire("Đã xóa!", "Sản phẩm đã được xóa.", "success").then(() =>
          navigate("/product")
        );
        fetchProducts(currentPage, pageSize);
      }
    } catch (error) {
      Swal.fire({
        title: "Error!",
        text: "Xóa sản phẩm thất bại!",
        icon: "error",
        confirmButtonText: "OK",
      });
    }
  };

  const handleSearch = (value) => {
    if (value) {
      searchSearch(value);
    } else {
      fetchProducts(currentPage, pageSize);
    }
  };

  return (
    <>
      <h1>Sản phẩm</h1>
      <Row style={{ marginBottom: 16, marginTop: 16 }}>
        <Col span={8}>
          <Search
            placeholder="Vui lòng nhập sản phẩm"
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
          <Link to="/product/add">
            <Button size="large" type="primary">
              Thêm sản phẩm
            </Button>
          </Link>
        </Col>
      </Row>
      <Table
        columns={getColumns(handleDelete)}
        dataSource={products.map((item) => ({
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

export default ProductList;
