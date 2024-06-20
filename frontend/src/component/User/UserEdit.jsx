import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import UserApi from "../../api/user";
import Swal from "sweetalert2";
import { Form, Input, Button, Card, Avatar, Row, Col, Upload } from "antd";
import { UserOutlined, UploadOutlined } from "@ant-design/icons";
import translateRole from "../../assets/js/translateRole";

const UserEdit = () => {
  const [user, setUser] = useState(null);
  const { id } = useParams();
  const [form] = Form.useForm();
  const [fileList, setFileList] = useState([]);
  const [file, setFile] = useState(null);

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
          role: translateRole[userData.role] || userData.role,
        });

        if (userData.urlImage) {
          setFileList([
            {
              uid: "-1",
              name: "image.png",
              status: "done",
              url: userData.urlImage,
            },
          ]);
        }
      }
    } catch (error) {
      console.error("Error fetching user:", error);
    }
  };

  useEffect(() => {
    fetchUser();
  }, [id, form]);

  const handleSubmit = async (values) => {
    const formData = new FormData();
    formData.append("name", values.name);
    formData.append("phone", values.phone);
    formData.append("age", values.age);
    formData.append("address", values.address);
    formData.append(
      "role",
      Object.keys(translateRole).find(
        (key) => translateRole[key] === values.role
      ) || values.role
    );

    if (file) {
      formData.append("imageFile", file);
    } else if (user.urlImage) {
      try {
        const response = await fetch(user.urlImage);
        const blob = await response.blob();
        formData.append("imageFile", blob);
      } catch (error) {
        console.error("Error fetching image:", error);
      }
    }

    try {
      await UserApi.editUser(id, formData);

      Swal.fire({
        title: "Success!",
        text: "Cập nhật thông tin thành công",
        icon: "success",
        confirmButtonText: "OK",
      }).then(() => fetchUser());
    } catch (error) {
      Swal.fire({
        title: "Error!",
        text: error.response.data.message,
        icon: "error",
        confirmButtonText: "OK",
      });
    }
  };

  const onFinishFailed = (errorInfo) => {
    console.log("Failed:", errorInfo);
  };

  const handleUploadChange = ({ fileList }) => {
    setFileList(fileList);
    if (fileList.length > 0 && fileList[0].originFileObj) {
      setFile(fileList[0].originFileObj);
    }
  };

  const validatePhoneNumber = (_, value) => {
    const phoneRegex = /^[0-9]{10}$/;
    if (value && !phoneRegex.test(value)) {
      return Promise.reject(new Error("Số điện thoại không hợp lệ!"));
    }
    return Promise.resolve();
  };

  return (
    <div style={{ padding: "20px", maxWidth: "1200px", margin: "0 auto" }}>
      <Row gutter={16}>
        <Col span={8}>
          <Card style={{ textAlign: "center" }}>
            <div style={{ marginBottom: "16px" }}>
              <Avatar size={90} icon={<UserOutlined />} src={user?.urlImage} />
            </div>
            <div style={{ marginBottom: "16px" }}>
              <Upload beforeUpload={() => false} onChange={handleUploadChange}>
                <Button icon={<UploadOutlined />}>Thay đổi ảnh</Button>
              </Upload>
            </div>
            <h2>{user?.name}</h2>
            <p>{translateRole[user?.role] || user?.role}</p>
          </Card>
        </Col>
        <Col span={16}>
          <Card title="Thông tin cá nhân">
            <Form
              layout="vertical"
              form={form}
              onFinish={handleSubmit}
              onFinishFailed={onFinishFailed}
              autoComplete="off"
            >
              <Form.Item
                label="Họ và tên"
                name="name"
                rules={[
                  {
                    required: true,
                    message: "Vui lòng nhập tên của bạn",
                  },
                ]}
              >
                <Input />
              </Form.Item>

              <Form.Item
                label="Số điện thoại"
                name="phone"
                rules={[
                  { required: true, message: "Vui lòng nhập số điện thoại!" },
                  { validator: validatePhoneNumber },
                ]}
              >
                <Input type="number" />
              </Form.Item>

              <Form.Item label="Tuổi" name="age">
                <Input type="number" />
              </Form.Item>

              <Form.Item label="Địa chỉ" name="address">
                <Input />
              </Form.Item>

              <Form.Item label="Vai trò" name="role">
                <Input disabled />
              </Form.Item>

              <Form.Item style={{ display: "flex", marginRight: 12 }}>
                <Button type="primary" htmlType="submit">
                  Lưu
                </Button>
              </Form.Item>
            </Form>
          </Card>
        </Col>
      </Row>
    </div>
  );
};

export default UserEdit;
