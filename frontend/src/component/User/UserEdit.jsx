import { Button, Form, Input } from "antd";
import Swal from "sweetalert2";
import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import UserApi from "../../api/user";
import { useAuth } from "../../Context/ContextAuth";

const UserEdit = () => {
  const [user, setUser] = useState(null);
  const { token } = useAuth();
  const { id } = useParams();
  const [form] = Form.useForm();

  useEffect(() => {
    const fetchUser = async () => {
      try {
        const response = await UserApi.getOneUser(id, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
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
  }, [id, token, form]);

  const handleSubmit = async (values) => {
    try {
      await UserApi.editUser(id, values, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

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
        }}
        onFinish={handleSubmit}
        onFinishFailed={onFinishFailed}
        autoComplete="off"
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
        <Form.Item style={{ display: "flex", marginRight: 12 }}>
          <Button type="primary" htmlType="submit">
            Lưu
          </Button>
        </Form.Item>

        {/* <Row>
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
                <Link to="/service">Quay lại</Link>
              </Button>
            </Form.Item>
          </Col>
        </Row> */}
      </Form>
    </>
  );
};

export default UserEdit;
