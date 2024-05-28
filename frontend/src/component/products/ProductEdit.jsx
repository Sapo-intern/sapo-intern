import { Button, Col, Form, Input, Row } from "antd";
import Swal from "sweetalert2";
import { Link, useNavigate, useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import ProductApi from "../../api/products";
import { useAuth } from "../../Context/ContextAuth";

const ProductEdit = () => {
  const navigate = useNavigate();
  const [product, setProduct] = useState(null);
  const { token } = useAuth();
  const { id } = useParams();
  const [form] = Form.useForm();

  useEffect(() => {
    const fetchProduct = async () => {
      try {
        const response = await ProductApi.getOneProduct(id, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        const productData = response.result;
        setProduct(productData);

        if (productData) {
          form.setFieldsValue({
            productCode: productData.productCode,
            name: productData.name,
            price: productData.price,
            quantity: productData.quantity,
            description: productData.description,
          });
        }
      } catch (error) {
        console.error("Error fetching category:", error);
      }
    };

    fetchProduct();
  }, [id, token, form]);

  const handleSubmit = async (values) => {
    try {
      await ProductApi.updateProduct(
        id,
        values,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      Swal.fire({
        title: "Success!",
        text: "Cập nhật sản phẩm thành công",
        icon: "success",
        confirmButtonText: "OK",
      }).then(() => navigate("/product"));
    } catch (error) {
      Swal.fire({
        title: "Error!",
        text: "Cập nhật sản phẩm thất bại!",
        icon: "error",
        confirmButtonText: "OK",
      });
    }
  };

  const onFinishFailed = (errorInfo) => {
    console.log("Failed:", errorInfo);
  };

  return (
    <>
      <h1 style={{ marginBottom: 16, textAlign: "center" }}>
        Cập nhật sản phẩm
      </h1>

      <Form
        name="basic"
        form={form}
        labelCol={{
          span: 8,
        }}
        wrapperCol={{
          span: 16,
        }}
        style={{
          maxWidth: 600,
        }}
        onFinish={handleSubmit}
        onFinishFailed={onFinishFailed}
        autoComplete="off"
      >
        <Form.Item
          label="Mã sản phẩm"
          name="productCode"
          rules={[
            {
              required: true,
              message: "Vui lòng nhập mã sản phẩm ",
            },
          ]}
        >
          <Input />
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
          <Input />
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
          <Input type="number" />
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
          <Input type="number" />
        </Form.Item>

        <Form.Item label="Mô tả" name="description">
          <Input />
        </Form.Item>

        <Row>
          <Col>
            <Form.Item style={{ display: "flex", marginRight: 12 }}>
              <Button type="primary" htmlType="submit">
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

export default ProductEdit;
