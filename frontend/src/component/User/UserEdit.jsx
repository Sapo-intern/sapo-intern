import { Button, Col, Form, Input, Row } from "antd";
import Swal from "sweetalert2";
import { Link, useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import UserApi from "../../api/user";

const UserEdit = () => {
  const [user, setUser] = useState(null);
  const { id } = useParams();
  const [form] = Form.useForm();

  useEffect(() => {
    const fetchUser = async () => {
      try {
        const response = await UserApi.getOneUser(id);
        const userData = response.result;
        setUser(userData);

        if (userData) {
          form.setFieldsValue({
            name: userData.name,
            phone: userData.phone,
            age: userData.age,
            address: userData.address,
            role: userData.role,
          });
        }
      } catch (error) {
        console.error("Error fetching user:", error);
      }
    };

    fetchUser();
  }, [id, form]);

  const handleSubmit = async (values) => {
    try {
      await UserApi.editUser(id, values);

      Swal.fire({
        title: "Success!",
        text: "Cập nhật thông tin thành công",
        icon: "success",
        confirmButtonText: "OK",
      });
    } catch (error) {
      Swal.fire({
        title: "Error!",
        text: "Cập nhật thông tin thất bại!",
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
        Thông tin cá nhân
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
          margin: "0 auto",
        }}
        onFinish={handleSubmit}
        onFinishFailed={onFinishFailed}
        autoComplete="off"
        layout="vertical"
      >
        <Form.Item label="Tên " name="name">
          <Input />
        </Form.Item>

        <Form.Item label="Số điện thoại" name="phone">
          <Input type="number" />
        </Form.Item>

        <Form.Item label="Tuối" name="age">
          <Input type="number" />
        </Form.Item>

        <Form.Item label="Địa chỉ" name="address">
          <Input />
        </Form.Item>

        <Form.Item label="Nhân viên" name="role">
          <Input disabled />
        </Form.Item>

        <Row>
          <Col>
            <Form.Item style={{ display: "flex", marginRight: 12 }}>
              <Button type="primary" htmlType="submit">
                Lưu
              </Button>
            </Form.Item>
          </Col>
          <Col>
            <Form.Item style={{ display: "flex" }}>
              <Button type="primary">
                <Link to="/user">Quay lại</Link>
              </Button>
            </Form.Item>
          </Col>
        </Row>
      </Form>
    </>
  );
};

export default UserEdit;
