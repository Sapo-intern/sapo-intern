import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import {
  HomeOutlined,
  ToolOutlined,
  UserOutlined,
  UsergroupAddOutlined,
  ProductOutlined,
  SettingOutlined,
  LogoutOutlined,
  ProfileOutlined,
} from "@ant-design/icons";
import { Breadcrumb, Layout, Menu, theme } from "antd";
import { Outlet } from "react-router-dom";
const { Header, Content, Footer, Sider } = Layout;
import Swal from "sweetalert2";
import { useAuth } from "./Context/ContextAuth";

const Layouts = () => {
  const navigate = useNavigate();
  const { onLogout, user } = useAuth();
  const [collapsed, setCollapsed] = useState(false);
  const {
    token: { colorBgContainer, borderRadiusLG },
  } = theme.useToken();

  const handleLogout = async () => {
    try {
      const result = await Swal.fire({
        title: "Bạn chắc chắn muốn đăng xuất không?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#d33",
        cancelButtonColor: "#3085d6",
        confirmButtonText: "Đăng xuất",
      });

      if (result.isConfirmed) {
        await onLogout();
        Swal.fire("Đã đăng xuất!", "", "success").then(() =>
          navigate("/login")
        );
      }
    } catch (error) {
      Swal.fire({
        title: "Error!",
        text: "Đăng xuất thất bại!",
        icon: "error",
        confirmButtonText: "OK",
      });
    }
  };

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
    getItem("Phiếu sửa chữa", "2", <ToolOutlined />, "/ticket"),
    getItem("Nhân viên", "3", <UserOutlined />, "/user"),
    getItem("Khách hàng", "4", <UsergroupAddOutlined />, "/customers"),
    getItem("Sản phẩm", "5", <ProductOutlined />, "/product"),
    getItem("Dịch vụ", "6", <SettingOutlined />, "/services"),
    getItem(
      "Thông tin cá nhân",
      "7",
      <ProfileOutlined />,
      user ? `/user/${user.id}` : "/"
    ),
    getItem("Đăng xuất", "8", <LogoutOutlined />),
  ];

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
        <div className="demo-logo-vertical">
          <img width={200} src="/sapo.png" alt="" />
        </div>

        <Menu theme="dark" defaultSelectedKeys={["1"]} mode="inline">
          {items.map((item) => (
            <Menu.Item
              key={item.key}
              icon={item.icon}
              onClick={item.key === "8" ? handleLogout : undefined}
            >
              {item.path ? (
                <Link to={item.path}>{item.label}</Link>
              ) : (
                item.label
              )}
            </Menu.Item>
          ))}
        </Menu>
      </Sider>
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
