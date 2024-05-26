import React, { useState } from "react";
import { Link } from "react-router-dom";
import {
  HomeOutlined,
  ToolOutlined,
  UserOutlined,
  UsergroupAddOutlined,
  ProductOutlined,
  SettingOutlined,
} from "@ant-design/icons";
import { Breadcrumb, Layout, Menu, theme } from "antd";
import { Outlet } from "react-router-dom";
const { Header, Content, Footer, Sider } = Layout;

function getItem(label, key, icon, path) {
  return {
    key,
    icon,
    path,
    label,
  };
}

const items = [
  getItem("Trang chủ", "1", <HomeOutlined />, "/"),
  getItem("Phiếu sửa chữa", "2", <ToolOutlined />, "/repair"),
  getItem("Nhân viên", "3", <UserOutlined />, "/user"),
  getItem("Khách hàng", "4", <UsergroupAddOutlined />, "/customers"),
  getItem("Sản phẩm", "5", <ProductOutlined />, "/product"),
  getItem("Dịch vụ", "6", <SettingOutlined />, "/services"),
];

const Layouts = () => {
  const [collapsed, setCollapsed] = useState(false);
  const {
    token: { colorBgContainer, borderRadiusLG },
  } = theme.useToken();
  return (
    <Layout
      style={{
        minHeight: "100vh",
      }}
    >
      <Sider
        collapsible
        collapsed={collapsed}
        onCollapse={(value) => setCollapsed(value)}
      >
        <div className="demo-logo-vertical" />
        <div style={{ backgroundColor: "blue", padding: 50 }}>image</div>
        <Menu theme="dark" defaultSelectedKeys={["1"]} mode="inline">
          {items.map((item) => (
            <Menu.Item key={item.key} icon={item.icon}>
              <Link to={item.path}>{item.label}</Link>
            </Menu.Item>
          ))}
        </Menu>
      </Sider>
      <h1></h1>
      <Layout>
        <Header
          style={{
            padding: 0,
            background: colorBgContainer,
          }}
        />
        <Content
          style={{
            margin: "0 16px",
          }}
        >
          <Breadcrumb
            style={{
              margin: "16px 0",
            }}
          />
          <div
            style={{
              padding: 24,
              minHeight: 540,
              background: colorBgContainer,
              borderRadius: borderRadiusLG,
            }}
          >
            <Outlet />
          </div>
        </Content>
        <Footer
          style={{
            textAlign: "center",
          }}
        >
          Ant Design ©{new Date().getFullYear()} Created by Ant UED
        </Footer>
      </Layout>
    </Layout>
  );
};

export default Layouts;
