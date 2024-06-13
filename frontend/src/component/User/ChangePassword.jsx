import { useState } from "react";
import { useNavigate } from "react-router-dom";
import auth from "../../api/auth";
import Swal from "sweetalert2";
import { useAuth } from "../../Context/ContextAuth";
import { Button, Form, Input } from "antd";

const ChangePasswordUser = () => {
  const [oldPassword, setOldPassword] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [confirmNewPassword, setConfirmNewPassword] = useState("");
  const { user } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = async () => {
    try {
      await auth.changePassword({
        email: user.email,
        oldPassword,
        newPassword,
        confirmNewPassword,
      });

      Swal.fire({
        title: "Success!",
        text: "Đổi mật khẩu thành công",
        icon: "success",
        confirmButtonText: "OK",
      }).then(() => navigate(`/user/${user.id}`));
    } catch (error) {
      Swal.fire({
        title: "Error!",
        text: error.response.data.message,
        icon: "error",
        confirmButtonText: "OK",
      });
    }
  };

  const validatePassword = (_, value) => {
    if (!value) {
      return Promise.reject(new Error("Vui lòng nhập mật khẩu mới"));
    }
    if (value.length < 8) {
      return Promise.reject(new Error("Mật khẩu phải dài ít nhất 8 kí tự"));
    }
    if (!/[A-Z]/.test(value)) {
      return Promise.reject(new Error("Mật khẩu phải có ít nhất 1 chữ hoa"));
    }
    if (!/[a-z]/.test(value)) {
      return Promise.reject(new Error("Mật khẩu phải có ít nhất 1 chữ thường"));
    }
    if (!/[0-9]/.test(value)) {
      return Promise.reject(new Error("Mật khẩu phải có ít nhất 1 chữ số"));
    }
    return Promise.resolve();
  };

  const validateConfirmPassword = ({ getFieldValue }) => ({
    validator(_, value) {
      if (!value || getFieldValue('newPassword') === value) {
        return Promise.resolve();
      }
      return Promise.reject(new Error('Xác nhận mật khẩu không khớp với mật khẩu mới'));
    },
  });

  return (
    <>
      <h1 style={{ marginBottom: 16, textAlign: "center" }}>Thay đổi mật khẩu</h1>

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
        autoComplete="off"
      >
        <Form.Item
          label="Mật khẩu cũ"
          name="oldPassword"
          rules={[
            {
              required: true,
              message: "Vui lòng nhập mật khẩu cũ ",
            },
          ]}
        >
          <Input
            value={oldPassword}
            onChange={(e) => setOldPassword(e.target.value)}
          />
        </Form.Item>

        <Form.Item
          label="Mật khẩu mới"
          name="newPassword"
          rules={[
            { required: true, validator: validatePassword }
          ]}
        >
          <Input
            value={newPassword}
            onChange={(e) => setNewPassword(e.target.value)}
          />
        </Form.Item>

        <Form.Item
          label="Xác nhận lại mật khẩu cũ"
          name="confirmNewPassword"
          rules={[
            { required: true, message: "Vui lòng xác nhận lại mật khẩu mới" },
            validateConfirmPassword
          ]}
        >
          <Input
            value={confirmNewPassword}
            onChange={(e) => setConfirmNewPassword(e.target.value)}
          />
        </Form.Item>

        <Form.Item style={{ display: "flex", marginRight: 12 }}>
          <Button type="primary" htmlType="submit" onClick={handleSubmit}>
            Lưu
          </Button>
        </Form.Item>
      </Form>
    </>
  );
};

export default ChangePasswordUser;
