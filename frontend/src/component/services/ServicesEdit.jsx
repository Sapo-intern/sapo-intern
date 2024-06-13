import { Button, Form, Input } from "antd";
import Swal from "sweetalert2";
import { useEffect } from "react";
import ServiceApi from "../../api/services";

const ServiceEdit = ({ closeModal, fetchServices, serviceId, currentPage, pageSize  }) => {
  const [form] = Form.useForm();

  useEffect(() => {
    const fetchServices = async () => {
      try {
        const response = await ServiceApi.getOneService(serviceId);
        const servicesData = response.result;

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
  }, [serviceId, form]);

  const handleSubmit = async (values) => {
    try {
      await ServiceApi.updateService(serviceId, values);

      Swal.fire({
        title: "Success!",
        text: "Cập nhật dịch vụ thành công",
        icon: "success",
        confirmButtonText: "OK",
      }).then(() => {
        closeModal();
        fetchServices(currentPage, pageSize); 
      });
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

        <Form.Item style={{ display: "flex", marginRight: 12 }}>
          <Button type="primary" htmlType="submit">
            Sửa
          </Button>
        </Form.Item>
      </Form>
    </>
  );
};

export default ServiceEdit;
