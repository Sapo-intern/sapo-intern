import { Button, Col, Form, Input, Row, Upload } from "antd";
import Swal from "sweetalert2";
import { Link, useNavigate, useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { UploadOutlined } from "@ant-design/icons";
import UserApi from "../../api/user";

const UserEdit = () => {
  const [user, setUser] = useState(null);
  const { id } = useParams();
  const [form] = Form.useForm();
  const [fileList, setFileList] = useState([]);
  const [file, setFile] = useState(null);
  const navigate = useNavigate();

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

          if (userData.urlImage) {
            setFileList([{
              uid: '-1',
              name: 'image.png',
              status: 'done',
              url: userData.urlImage,
            }]);
          }
        }
      } catch (error) {
        console.error("Error fetching user:", error);
      }
    };

    fetchUser();
  }, [id, form]);
  
  const handleSubmit = async (values) => {
    const formData = new FormData();
    formData.append("name", values.name);
    formData.append("phone", values.phone);
    formData.append("age", values.age);
    formData.append("address", values.address);
    formData.append("role", values.role);
  
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
      }).then(() => navigate("/user"));
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

  const handleUploadChange = ({ fileList }) => {
    setFileList(fileList);
    if (fileList.length > 0 && fileList[0].originFileObj) {
      setFile(fileList[0].originFileObj);
    }
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

        <Form.Item label="Tuổi" name="age">
          <Input type="number" />
        </Form.Item>

        <Form.Item label="Địa chỉ" name="address">
          <Input />
        </Form.Item>

        <Form.Item label="Nhân viên" name="role">
          <Input disabled />
        </Form.Item>

        <Form.Item label="Ảnh đại diện">
          <Upload
            fileList={fileList}
            beforeUpload={() => false}
            onChange={handleUploadChange}
            listType="picture"
          >
            <Button icon={<UploadOutlined />}>Tải lên ảnh đại diện</Button>
          </Upload>
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
