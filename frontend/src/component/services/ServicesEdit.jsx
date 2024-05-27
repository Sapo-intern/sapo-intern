import { Button, Col, Form, Input, Row } from "antd";
import Swal from "sweetalert2";
import { Link, useNavigate, useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import ProductApi from "../../api/products";
import { useAuth } from "../../Context/ContextAuth";
import ServiceApi from "../../api/services";

const ServiceEdit = () => {
  const navigate = useNavigate();
  const [services, setServices] = useState(null);
  const { token } = useAuth();
  const { id } = useParams();
  const [form] = Form.useForm();

  useEffect(() => {
    const fetchServices = async () => {
      try {
        const response = await ServiceApi.getOneService(id, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        const servicesData = response.result;
        setServices(servicesData);

        if (servicesData) {
          form.setFieldsValue({
            servicesCode: servicesData.servicesCode,
            name: servicesData.name,
            price: servicesData.price,
            description: servicesData.description,
          });
        }
      } catch (error) {
        console.error("Error fetching category:", error);
      }
    };

    fetchServices();
  }, [id, token, form]);

  const handleSubmit = async (values) => {
    try {
      await ServiceApi.updateService(id, values, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      Swal.fire({
        title: "Success!",
        text: "Cập nhật dịch vụ thành công",
        icon: "success",
        confirmButtonText: "OK",
      }).then(() => navigate("/service"));
    } catch (error) {
      Swal.fire({
        title: "Error!",
        text: "Cập nhật dịch vụ thất bại!",
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
        Cập nhật dịch vụ
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
          label="Mã dịch vụ"
          name="servicesCode"
          rules={[
            {
              required: true,
              message: "Vui lòng nhập mã dịch vụ ",
            },
          ]}
        >
          <Input />
        </Form.Item>

        <Form.Item
          label="Tên dịch vụ"
          name="name"
          rules={[
            {
              required: true,
              message: "Vui lòng nhập tên dịch vụ ",
            },
          ]}
        >
          <Input />
        </Form.Item>

        <Form.Item
          label="Giá dịch vụ"
          name="price"
          rules={[
            {
              required: true,
              message: "Vui lòng nhập giá dịch vụ ",
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
                <Link to="/service">Quay lại</Link>
              </Button>
            </Form.Item>
          </Col>
        </Row>
      </Form>
    </>
  );
};

export default ServiceEdit;
