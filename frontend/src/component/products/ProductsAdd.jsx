import { Button, Col, Form, Input, Row } from "antd";
import Swal from "sweetalert2";
import { useState } from "react";
import ProductApi from "../../api/products";

const ProductsAdd = ({ closeModal, fetchProducts }) => {
  const [productCode, setProductCode] = useState("");
  const [name, setName] = useState("");
  const [price, setPrice] = useState("");
  const [quantity, setQuantity] = useState("");
  const [description, setDescription] = useState("");

  const handleSubmit = async (e) => {
    try {
      e.preventDefault();

      await ProductApi.addProduct({
        productCode,
        name,
        price,
        quantity,
        description,
      });

      Swal.fire({
        title: "Success!",
        text: "Thêm sản phẩm thành công",
        icon: "success",
        confirmButtonText: "OK",
      }).then(() => {
        closeModal();
        fetchProducts();
      });
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
        layout="vertical"
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
            value={productCode}
            onChange={(e) => setProductCode(e.target.value)}
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
          name="quantity"
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
              <Button type="primary" onClick={closeModal}>
                Quay lại
              </Button>
            </Form.Item>
          </Col>
        </Row>
      </Form>
    </>
  );
};
export default ProductsAdd;
