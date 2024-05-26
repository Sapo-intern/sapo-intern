import { Button, Col, Form, Input, Row, Select } from "antd";
import { MinusCircleOutlined, PlusOutlined } from "@ant-design/icons";
import Swal from "sweetalert2";
import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import auth from "../../api/auth";

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
const ProductsAdd = () => {
  const [form] = Form.useForm();
  const navigate = useNavigate();

  // const handleSubmit = async () => {
  //   try {
  //     Swal.fire({
  //       title: "Success!",
  //       text: "Thêm sản phẩm thành công",
  //       icon: "success",
  //       confirmButtonText: "OK",
  //     }).then(()=>navigate("/category"));
  //   } catch (error) {
  //     Swal.fire({
  //       title: "Error!",
  //       text: "Thêm sản phẩm thất bại!",
  //       icon: "error",
  //       confirmButtonText: "OK",
  //     });
  //   }
  // };

  const onFinish = (values) => {
    console.log("Received values of form: ", values);
  };

  return (
    <>
      <h1 style={{ marginBottom: 16 }}>Thêm phiếu sửa chữa</h1>

      <Form
        {...formItemLayout}
        form={form}
        name="register"
        onFinish={onFinish}
        initialValues={{
          residence: ["zhejiang", "hangzhou", "xihu"],
          prefix: "86",
        }}
        style={{
          maxWidth: 1400,
        }}
        scrollToFirstError
      >
        <h2>Thông tin khách hàng</h2>
        <Row>
          <Col span={8}>
            <Form.Item
              name="name"
              label="Tên khách hàng"
              rules={[
                {
                  required: true,
                  message: "Please input your E-mail!",
                },
              ]}
            >
              <Input />
            </Form.Item>
          </Col>
          <Col span={8}>
            <Form.Item
              name="phone"
              label="Số điện thoại"
              rules={[
                {
                  required: true,
                  message: "Please input your E-mail!",
                },
              ]}
            >
              <Input />
            </Form.Item>
          </Col>
          <Col span={8}>
            <Form.Item
              name="address"
              label="Địa chỉ"
              rules={[
                {
                  required: true,
                  message: "Please input your E-mail!",
                },
              ]}
            >
              <Input />
            </Form.Item>
          </Col>
        </Row>
        <h2>Thông tin xe</h2>
        <Row>
          <Col span={8}>
            <Form.Item
              name="type"
              label="Loại xe"
              rules={[
                {
                  required: true,
                  message: "Please input your E-mail!",
                },
              ]}
            >
              <Input />
            </Form.Item>
          </Col>
          <Col span={8}>
            <Form.Item
              name="number"
              label="Biển số"
              rules={[
                {
                  required: true,
                  message: "Please input your E-mail!",
                },
              ]}
            >
              <Input />
            </Form.Item>
          </Col>
          <Col span={8}>
            <Form.Item
              name="color"
              label="Màu xe"
              rules={[
                {
                  type: "email",
                  message: "The input is not valid E-mail!",
                },
                {
                  required: true,
                  message: "Please input your E-mail!",
                },
              ]}
            >
              <Input />
            </Form.Item>
          </Col>
        </Row>
        <Row>
          <Col span={8}>
            <Form.Item
              name="type"
              label="Sô khung"
              rules={[
                {
                  required: true,
                  message: "Please input your E-mail!",
                },
              ]}
            >
              <Input />
            </Form.Item>
          </Col>
          <Col span={8}>
            <Form.Item
              name="number"
              label="Nhãn hiệu"
              rules={[
                {
                  required: true,
                  message: "Please input your E-mail!",
                },
              ]}
            >
              <Input />
            </Form.Item>
          </Col>
        </Row>
        {/* <Row>
          <Col span={8}>
            <Form.Item
              name="color"
              label="Nhân viên sửa"
              rules={[
                {
                  type: "email",
                  message: "The input is not valid E-mail!",
                },
                {
                  required: true,
                  message: "Please input your E-mail!",
                },
              ]}
            >
              <Select
                defaultValue="lucy"
                style={{
                  width: 276,
                }}
                options={[
                  {
                    value: "lucy",
                    label: "Lucy",
                  },
                ]}
              />
            </Form.Item>
          </Col>
        </Row> */}
        <hr style={{ marginBottom: 16, marginTop: 16 }} />

        <h2>Vấn đề xe</h2>
        <Form.List name="users">
          {(fields, { add, remove }) => (
            <>
              {fields.map(({ key, name, ...restField }) => (
                <div key={key} style={{ marginBottom: 8 }}>
                  <Row gutter={8}>
                  <Col span={12}>
                      <Form.Item
                        {...restField}
                        name={[name, "last"]}
                        rules={[
                          {
                            required: true,
                            message: "Missing last name",
                          },
                        ]}
                      >
                        <Input placeholder="Mô tả" />
                      </Form.Item>
                    </Col>
                    <Col span={11}>
                      <Form.Item
                        {...restField}
                        name={[name, "first"]}
                        rules={[
                          {
                            required: true,
                            message: "Missing first name",
                          },
                        ]}
                      >
                        <Select
                          placeholder="Chọn nhân viên sửa chữa"
                          // defaultValue="lucy"
                          style={{
                            width: 560,
                          }}
                          // onChange={handleChange}
                          options={[
                            {
                              value: "jack",
                              label: "Jack",
                            },
                            {
                              value: "lucy",
                              label: "Lucy",
                            },
                            {
                              value: "Yiminghe",
                              label: "yiminghe",
                            },
                          ]}
                        />
                      </Form.Item>
                    </Col>
                    
                    <Col span={1}>
                      <MinusCircleOutlined onClick={() => remove(name)} />
                    </Col>
                  </Row>
                </div>
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
              <Button type="primary" htmlType="submit">
                Thêm
              </Button>
            </Form.Item>
          </Col>
          <Col>
            <Form.Item {...tailFormItemLayout} style={{ display: "flex" }}>
              <Button type="primary" htmlType="submit">
                Quay lại
              </Button>
            </Form.Item>
          </Col>
        </Row>
      </Form>
    </>
  );
};
export default ProductsAdd;
