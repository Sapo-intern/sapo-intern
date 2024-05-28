import { Button, Col, Form, Input, Row } from "antd";
import Swal from "sweetalert2";
import { Link, useNavigate } from "react-router-dom";
import { useState } from "react";
import ProductApi from "../../api/products";
import { useAuth } from "../../Context/ContextAuth";

const ProductsAdd = () => {
  const navigate = useNavigate();
  const [productsCode, setProductsCode] = useState("");
  const [name, setName] = useState("");
  const [price, setPrice] = useState("");
  const [quantity, setQuantity] = useState("");
  const [description, setDescription] = useState("");
  const { token } = useAuth();

  const handleSubmit = async (e) => {
    try {
      e.preventDefault();

      await ProductApi.addProduct(
        {
          productsCode,
          name,
          price,
          quantity,
          description,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      Swal.fire({
        title: "Success!",
        text: "Thêm sản phẩm thành công",
        icon: "success",
        confirmButtonText: "OK",
      }).then(() => navigate("/product"));
    } catch (error) {
      Swal.fire({
        title: "Error!",
        text: "Thêm sản phẩm thất bại!",
        icon: "error",
        confirmButtonText: "OK",
      });
    }
  };

  const onFinish = (values) => {
    console.log("Success:", values);
  };
  const onFinishFailed = (errorInfo) => {
    console.log("Failed:", errorInfo);
  };

  return (
    <>
      <h1 style={{ marginBottom: 16, textAlign: "center" }}>Thêm sản phẩm</h1>

      <Form
        name="basic"
        labelCol={{
          span: 8,
        }}
        wrapperCol={{
          span: 16,
        }}
        style={{
          maxWidth: 600,
        }}
        initialValues={{
          remember: true,
        }}
        onFinish={onFinish}
        onFinishFailed={onFinishFailed}
        autoComplete="off"
      >
        <Form.Item
          label="Mã sản phẩm"
          name="productsCode"
          rules={[
            {
              required: true,
              message: "Vui lòng nhập mã dịch vụ ",
            },
          ]}
        >
          <Input
            value={productsCode}
            onChange={(e) => setProductsCode(e.target.value)}
          />
        </Form.Item>

        <Form.Item
          label="Tên sản phẩm"
          name="name"
          rules={[
            {
              required: true,
              message: "Vui lòng nhập tên sản phẩm ",
            },
          ]}
        >
          <Input value={name} onChange={(e) => setName(e.target.value)} />
        </Form.Item>

        <Form.Item
          label="Giá sản phẩm"
          name="price"
          rules={[
            {
              required: true,
              message: "Vui lòng nhập giá sản phẩm ",
            },
          ]}
        >
          <Input
            type="number"
            value={price}
            onChange={(e) => setPrice(e.target.value)}
          />
        </Form.Item>

        <Form.Item
          label="Số lượng sản phẩm"
          name="price"
          rules={[
            {
              required: true,
              message: "Vui lòng nhập số lượng sản phẩm ",
            },
          ]}
        >
          <Input
            type="number"
            value={quantity}
            onChange={(e) => setQuantity(e.target.value)}
          />
        </Form.Item>

        <Form.Item label="Mô tả" name="description">
          <Input
            value={description}
            onChange={(e) => setDescription(e.target.value)}
          />
        </Form.Item>

        <Row>
          <Col>
            <Form.Item style={{ display: "flex", marginRight: 12 }}>
              <Button type="primary" htmlType="submit" onClick={handleSubmit}>
                Thêm
              </Button>
            </Form.Item>
          </Col>
          <Col>
            <Form.Item style={{ display: "flex" }}>
              <Button type="primary">
                <Link to="/product">Quay lại</Link>
              </Button>
            </Form.Item>
          </Col>
        </Row>
      </Form>
    </>
  );
};
export default ProductsAdd;
