import { Button, Col, Form, Input, Row, Select } from "antd";
import Swal from "sweetalert2";
import { useEffect, useState } from "react";
import auth from "../../api/auth";

const UserAdd = ({ closeModal, fetchUser, currentPage, pageSize }) => {
  const [email, setEmail] = useState("");
  const [selectedRole, setSelectedRole] = useState("");
  const [roles, setRoles] = useState([]);
  const fetchRole = async () => {
    try {
      const response = await auth.getRole();
      setRoles(response);
    } catch (error) {
      console.error("Không có dữ liệu:", error);
    }
  };

  useEffect(() => {
    fetchRole();
  }, []);

  const roleTranslations = {
    "COORDINATOR": "Nhân viên điều phối",
    "TECHNICIAN": "Nhân viên sửa chữa",
  };
  const filteredRoles = roles.filter((role) => role !== "MANAGER");

  const options = filteredRoles.map((role) => ({
    value: role,
    label: roleTranslations[role] || role, 
  }));

  const handleSubmit = async (e) => {
    try {
      e.preventDefault();

      await auth.register({
        email,
        role: selectedRole,
      });

      Swal.fire({
        title: "Success!",
        text: "Thêm nhân viên thành công",
        icon: "success",
        confirmButtonText: "OK",
      }).then(() => {
        closeModal();
        fetchUser(currentPage, pageSize);
      });
    } catch (error) {
      Swal.fire({
        title: "Error!",
        text: "Thêm nhân viên thất bại!",
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
      <h1 style={{ marginBottom: 16, textAlign: "center" }}>Thêm nhân viên</h1>

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
          label="Email"
          name="email"
          rules={[
            {
              required: true,
              message: "Vui lòng nhập email ",
            },
            {
              type: "email",
              message: "Email không đúng định dạng",
            },
          ]}
        >
          <Input value={email} onChange={(e) => setEmail(e.target.value)} />
        </Form.Item>

        <Form.Item
          label="Quyền"
          name="role"
          rules={[
            {
              required: true,
              message: "Vui lòng chọn quyền cho nhân viên",
            },
          ]}
        >
          <Select
            style={{
              width: 400,
            }}
            placeholder="Chọn quyền"
            options={options}
            onChange={(value) => setSelectedRole(value)}
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
export default UserAdd;
