import { Button, Col, Form, Input, Row } from "antd";
import Swal from "sweetalert2";
import { useState } from "react";
import ServiceApi from "../../api/services";

const ServicesAdd = ({ closeModal, fetchServices, currentPage, pageSize }) => {
  const [servicesCode, setServicesCode] = useState("");
  const [name, setName] = useState("");
  const [price, setPrice] = useState("");
  const [description, setDescription] = useState("");

  const handleSubmit = async (e) => {
    try {
      e.preventDefault();

      await ServiceApi.addService({
        servicesCode,
        name,
        price,
        description,
      });

      Swal.fire({
        title: "Success!",
        text: "Thêm dịch vụ thành công",
        icon: "success",
        confirmButtonText: "OK",
      }).then(() => {
        closeModal();
        fetchServices(currentPage, pageSize); 
      });
    } catch (error) {
      Swal.fire({
        title: "Error!",
        text: "Thêm dịch vụ thất bại!",
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
      <h1 style={{ marginBottom: 16, textAlign: "center" }}>Thêm dịch vụ</h1>

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
          label="Mã dịch vụ"
          name="servicesCode"
          rules={[
            {
              required: true,
              message: "Vui lòng nhập mã dịch vụ ",
            },
          ]}
        >
          <Input
            value={servicesCode}
            onChange={(e) => setServicesCode(e.target.value)}
          />
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
          <Input value={name} onChange={(e) => setName(e.target.value)} />
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
          <Input
            type="number"
            value={price}
            onChange={(e) => setPrice(e.target.value)}
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
export default ServicesAdd;