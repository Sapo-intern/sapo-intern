import React from "react";
import { Button, Col, Row, Space, Table, Tag, Input } from "antd";
import { Link } from "react-router-dom";
import Swal from "sweetalert2";
const { Search } = Input;
const columns = [
  {
    title: "Name",
    dataIndex: "name",
    filters: [
      {
        text: "Joe",
        value: "Joe",
      },
      {
        text: "Category 1",
        value: "Category 1",
        children: [
          {
            text: "Yellow",
            value: "Yellow",
          },
          {
            text: "Pink",
            value: "Pink",
          },
        ],
      },
      {
        text: "Category 2",
        value: "Category 2",
        children: [
          {
            text: "Green",
            value: "Green",
          },
          {
            text: "Black",
            value: "Black",
          },
        ],
      },
    ],
    filterMode: "tree",
    filterSearch: true,
    onFilter: (value, record) => record.name.includes(value),
    width: "30%",
  },
  {
    title: "Age",
    dataIndex: "age",
    sorter: (a, b) => a.age - b.age,
  },
  {
    title: "Address",
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
    width: "40%",
  },
  {
    title: "Tags",
    dataIndex: "tags",
    render: (tags) => (
      <>
        {tags.map((tag) => {
          let color = tag.length > 5 ? "geekblue" : "green";
          if (tag === "loser") {
            color = "volcano";
          }
          return (
            <Tag color={color} key={tag}>
              {tag.toUpperCase()}
            </Tag>
          );
        })}
      </>
    ),
    sorter: (a, b) => a.age - b.age,
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
const data = [
  {
    key: "1",
    name: "John Brown",
    age: 32,
    address: "New York No. 1 Lake Park",
    tags: ["nice", "developer"],
  },
  {
    key: "2",
    name: "Jim Green",
    age: 42,
    address: "London No. 1 Lake Park",
    tags: ["loser"],
  },
  {
    key: "3",
    name: "Joe Black",
    age: 32,
    address: "Sydney No. 1 Lake Park",
    tags: ["cool", "teacher"],
  },
  {
    key: "4",
    name: "Jim Red",
    age: 32,
    address: "London No. 2 Lake Park",
    tags: ["loser"],
  },
];

// const handleDelete = async () => {
//   try {
//     const result = await Swal.fire({
//       title: "Bạn chắc chắn muốn xóa?",
//       text: "Hành động này sẽ không thể hoàn tác!",
//       icon: "warning",
//       showCancelButton: true,
//       confirmButtonColor: "#d33",
//       cancelButtonColor: "#3085d6",
//       confirmButtonText: "Xóa",
//     });
//   } catch (error) {
//     Swal.fire({
//       title: "Error!",
//       text: "Xóa sản phẩm thất bại!",
//       icon: "error",
//       confirmButtonText: "OK",
//     });
//   }
// };


const onChange = (pagination, filters, sorter, extra) => {
  console.log("params", pagination, filters, sorter, extra);
};
const ProductList = () => (
  <>
    <h1>SẢN PHẨM</h1>
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
        <Link to="/product/add">
          <Button size="large" type="primary">
            Thêm sản phẩm
          </Button>
        </Link>
      </Col>
    </Row>
    <Table columns={columns} dataSource={data} onChange={onChange} />;
  </>
);
export default ProductList;
