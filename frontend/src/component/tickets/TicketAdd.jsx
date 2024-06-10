import {
  Button,
  Card,
  Checkbox,
  Col,
  Flex,
  Form,
  Input,
  Row,
  Select,
  Space,
} from "antd";
import React, { useEffect, useState } from "react";
import { CloseCircleFilled, PlusOutlined } from "@ant-design/icons";
import { useNavigate } from "react-router-dom";
import UserApi from "../../api/user";
import { CustomerApi } from "../../api/customer";
import { ServiceApi } from "../../api/service";
import { ProductApi } from "../../api/product";
import instance from "../../api/instance";
import { TicketApi } from "../../api/ticket";
import { VehicleApi } from "../../api/vehicle";

const TicketAdd = () => {
  const formItemLayout = {
    labelCol: {
      xs: {
        span: 24,
      },
      sm: {
        span: 8,
      },
    },
    wrerCol: {
      xs: {
        span: 24,
      },
      sm: {
        span: 16,
      },
    },
  };
  const tailFormItemLayout = {
    wrapperCol: {
      xs: {
        span: 24,
        offset: 0,
      },
      sm: {
        span: 16,
        offset: 8,
      },
    },
  };

  const [customers, setCustomers] = useState([]);
  const [employees, setEmployees] = useState([]);
  const [vehicles, setVehicles] = useState([]);
  const [services, setServices] = useState([]);
  const [products, setProducts] = useState([]);

  const [form] = Form.useForm();
  const navigate = useNavigate();

  const onSearch = (value, _e, info) => console.log(info?.source, value);
  const onFinish = (values) => {
    console.log("Received values of form: ", values);
  };

  const fetchData = async () => {
    const fetchUsersResponse = await UserApi.getAll(0, 1000);
    const fetchServiceResponse = await ServiceApi.getAll(0, 1000);
    const fetchProductResponse = await ProductApi.getAll(0, 1000);

    setEmployees(fetchUsersResponse.content);
    setProducts(fetchProductResponse.content);
    setServices(fetchServiceResponse.content);
  };

  useEffect(() => {
    fetchData();
  }, []);

  const handleFormChange = async (value, field) => {
    if (field === "phoneNumber") {
      const fetchCustomerResponse = await CustomerApi.getAll(0, 1000);
      setCustomers(fetchCustomerResponse.data);

      let customerExisted = false;
      for (const customer of customers) {
        if (customer.phoneNumber === value) {
          form.setFieldValue(["customer", "id"], customer.id);
          form.setFieldValue(["customer", "name"], customer.name);
          form.setFieldValue(["customer", "address"], customer.address);
          customerExisted = true;
        }
      }
      if (!customerExisted) {
        form.setFieldValue(["customer", "id"], null);
      }
      form.setFieldValue(["customer", "phoneNumber"], value);
    } else if (field === "plateNumber") {
      const fetchVehicleResponse = await VehicleApi.getByPlateNumber(value);
      setVehicles(fetchVehicleResponse.data);
      let customerExisted = false;
      for (const vehicle of vehicles) {
        if (vehicle.plateNumber === value) {
          form.setFieldValue(["vehicle", "color"], vehicle.color);
          form.setFieldValue(["vehicle", "brand"], vehicle.brand);
          form.setFieldValue(["vehicle", "type"], vehicle.type);
          customerExisted = true;
        }
      }
      if (!vehicleExisted) {
        form.setFieldValue(["vehicle", "id"], null);
      }
      form.setFieldValue(["vehicle", "plateNumber"], value);
    } else {
      form.setFieldValue(field, value);
    }
    console.log(form.getFieldsValue());
  };

  const handleSaveForm = async () => {
    try {
      // Get data from form
      const customer = form.getFieldValue("customer");
      const vehicle = form.getFieldValue("vehicle");
      const issues = form.getFieldValue("issues");
      // Create ticket base on issues and customer;
      await TicketApi.createTicket(customer, vehicle, issues);
      console.log(Object.keys(form.getFieldsValue()));
      form.resetFields(Object.keys(form.getFieldsValue()));
      alert("Create success!");
    } catch (err) {
      console.log(err);
      alert(err);
    }
  };

  return (
    <>
      <h1 style={{ marginBottom: 16 }}>Thêm phiếu sửa chữa</h1>
      <Form
        {...formItemLayout}
        form={form}
        name="register"
        onFinish={onFinish}
        style={{
          maxWidth: 1400,
        }}
        scrollToFirstError
      >
        <h2>Thông tin khách hàng</h2>
        <Row>
          <Col span={8}>
            <Form.Item name={["customer", "id"]} label="Mã khách hàng">
              <Input
                disabled
                name="id"
                showSearch
                onChange={(e) => handleFormChange(e.target.value, "id")}
                onSearch={null}
                virtual={false}
              />
            </Form.Item>
          </Col>
          <Col span={8}>
            <Form.Item
              name={["customer", "phoneNumber"]}
              label="Số điện thoại"
              rules={[
                {
                  required: true,
                  message: "Hãy nhập số điện thoại!",
                },
              ]}
            >
              <Input
                name="phoneNumber"
                showSearch
                onChange={(e) =>
                  handleFormChange(e.target.value, "phoneNumber")
                }
                onSearch={null}
                virtual={false}
              />
            </Form.Item>
          </Col>
          <Col span={8}>
            <Form.Item
              name={["customer", "name"]}
              label="Tên khách hàng"
              rules={[
                {
                  required: true,
                  message: "Hãy nhập tên khách hàng!",
                },
              ]}
            >
              <Input
                onChange={(e) => handleFormChange(e.target.value, "name")}
              />
            </Form.Item>
          </Col>
        </Row>
        <Row>
          <Col span={8}>
            <Form.Item
              name={["customer", "address"]}
              label="Địa chỉ"
              rules={[
                {
                  required: true,
                  message: "Hãy nhập địa chỉ!",
                },
              ]}
            >
              <Input.TextArea
                onChange={(e) => handleFormChange(e.target.value, "address")}
                style={{ width: "100%" }}
              />
            </Form.Item>
          </Col>
        </Row>
        <h2>Thông tin xe</h2>
        <Row>
          <Col span={8}>
            <Form.Item
              name={["vehicle", "type"]}
              label="Loại xe"
              rules={[
                {
                  required: true,
                  message: "Hãy nhập loại xe!",
                },
              ]}
            >
              <Input
                onChange={(e) => handleFormChange(e.target.value, "type")}
              />
            </Form.Item>
          </Col>
          <Col span={8}>
            <Form.Item
              name={["vehicle", "plateNumber"]}
              label="Biển số"
              rules={[
                {
                  required: true,
                  message: "Hãy nhập Biển số!",
                },
              ]}
            >
              <Input
                onChange={(e) =>
                  handleFormChange(e.target.value, "plateNumber")
                }
              />
            </Form.Item>
          </Col>
          <Col span={8}>
            <Form.Item name={["vehicle", "color"]} label="Màu xe">
              <Input onChange={(e) => handleFormChange(e, "color")} />
            </Form.Item>
          </Col>
        </Row>
        <Row>
          <Col span={8}>
            <Form.Item
              name={["vehicle", "brand"]}
              label="Nhãn hiệu"
              rules={[
                {
                  required: true,
                  message: "Please input your E-mail!",
                },
              ]}
            >
              <Input onChange={(e) => handleFormChange(e, "brand")} />
            </Form.Item>
          </Col>
        </Row>
        <hr style={{ marginBottom: 16, marginTop: 16 }} />

        <h2>Vấn đề xe</h2>
        <Form.List name="issues">
          {(fields, { add, remove }) => (
            <>
              {fields.map(({ key, name, ...restField }) => (
                <Card style={{ margin: "1rem 0 1rem 0" }}>
                  <CloseCircleFilled
                    style={{
                      position: "absolute",
                      top: "0.5rem",
                      right: "0.5rem",
                      color: "red",
                    }}
                    onClick={() => remove(name)}
                  />
                  <Col span={8}>
                    <Form.Item
                      {...restField}
                      name={[name, "employeeId"]}
                      on
                      rules={[
                        {
                          required: true,
                          message: "Missing first name",
                        },
                      ]}
                    >
                      <Select
                        placeholder="Chọn nhân viên sửa chữa"
                        onChange={(e) => handleFormChange(e, "issue")}
                        options={employees?.map((employee) => ({
                          label: employee.name || "Mr. A",
                          value: employee.id,
                        }))}
                      />
                    </Form.Item>
                  </Col>
                  <Row gutter={16}>
                    <Col span={8}>
                      <Form.Item
                        {...restField}
                        name={[name, "serviceId"]}
                        rules={[
                          {
                            required: true,
                            message: "Missing first name",
                          },
                        ]}
                      >
                        <Select
                          placeholder="Chọn dịch vụ"
                          onChange={(e) => handleFormChange(e, "issue")}
                          options={services?.map((service) => ({
                            label: service.name || "Mr. A",
                            value: service.id,
                          }))}
                        />
                      </Form.Item>
                    </Col>
                    <Col span={8}>
                      <Form.Item
                        {...restField}
                        name={[name, "productId"]}
                        rules={[
                          {
                            required: true,
                            message: "Missing first name",
                          },
                        ]}
                      >
                        <Select
                          placeholder="Chọn linh kiện"
                          onChange={(e) => handleFormChange(e, "issue")}
                          options={products?.map((product) => ({
                            label: product.name,
                            value: product.id,
                          }))}
                        />
                      </Form.Item>
                    </Col>
                    <Col span={8}>
                      <Form.Item
                        {...restField}
                        name={[name, "quantity"]}
                        rules={[
                          {
                            required: true,
                            message: "Missing first name",
                          },
                        ]}
                      >
                        <Input
                          type="number"
                          placeholder="Số lượng"
                          onChange={(e) => handleFormChange(e, "quantity")}
                        />
                      </Form.Item>
                    </Col>
                  </Row>
                  <Form.Item
                    {...restField}
                    name={[name, "description"]}
                    rules={[
                      {
                        required: true,
                        message: "Missing last name",
                      },
                    ]}
                  >
                    <Input.TextArea
                      onChange={(e) =>
                        handleFormChange(e.target.value, "issue")
                      }
                      placeholder="Mô tả"
                    />
                  </Form.Item>
                </Card>
              ))}
              <Form.Item>
                <Button
                  type="dashed"
                  onClick={() => add()}
                  block
                  icon={<PlusOutlined />}
                >
                  Thêm vấn đề
                </Button>
              </Form.Item>
            </>
          )}
        </Form.List>

        <Row>
          <Col>
            <Form.Item {...tailFormItemLayout} style={{ display: "flex" }}>
              <Button type="primary" htmlType="submit" onClick={handleSaveForm}>
                Thêm
              </Button>
            </Form.Item>
          </Col>
          <Col>
            <Form.Item {...tailFormItemLayout} style={{ display: "flex" }}>
              <Button
                type="primary"
                htmlType="button"
                onClick={(e) => navigate("/ticket")}
              >
                Quay lại
              </Button>
            </Form.Item>
          </Col>
        </Row>
      </Form>
    </>
  );
};

export default TicketAdd;
