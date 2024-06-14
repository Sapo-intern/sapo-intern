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
  LockOutlined,
  DownOutlined,
} from "@ant-design/icons";
import { Breadcrumb, Layout, Menu, Avatar, Dropdown, theme, Space } from "antd";
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

  const items =
    user && user.role === "MANAGER"
      ? [
          getItem("Trang chủ", "1", <HomeOutlined />, "/"),
          getItem("Phiếu sửa chữa", "2", <ToolOutlined />, "/ticket"),
          getItem("Vấn đề xe", "3", <ToolOutlined />, "/issue"),
          getItem("Nhân viên", "4", <UserOutlined />, "/user"),
          getItem("Khách hàng", "5", <UsergroupAddOutlined />, "/customers"),
          getItem("Kho sản phẩm", "6", <ToolOutlined />, "/storage"),
          getItem("Sản phẩm", "7", <ProductOutlined />, "/product"),
          getItem("Dịch vụ", "8", <SettingOutlined />, "/services"),
        ]
      : user && user.role === "COORDINATOR"
      ? [
          getItem("Trang chủ", "1", <HomeOutlined />, "/"),
          getItem("Phiếu sửa chữa", "2", <ToolOutlined />, "/ticket"),
          getItem("Vấn đề xe", "3", <ToolOutlined />, "/issue"),
        ]
      : [
          getItem("Trang chủ", "1", <HomeOutlined />, "/"),
          getItem("Phiếu sửa chữa", "2", <ToolOutlined />, "/ticket"),
          getItem("Vấn đề xe", "3", <ToolOutlined />, "/issue"),
        ];

  const dropdownMenu = (
    <Menu>
      <Menu.Item key="1" icon={<ProfileOutlined />}>
        <Link to={user ? `/user/${user.id}` : "#"}>Thông tin cá nhân</Link>
      </Menu.Item>
      <Menu.Item key="2" icon={<LockOutlined />}>
        <Link to="/user/changepassword">Thay đổi mật khẩu</Link>
      </Menu.Item>
      <Menu.Item key="3" icon={<LogoutOutlined />} onClick={handleLogout}>
        Đăng xuất
      </Menu.Item>
    </Menu>
  );

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
              onClick={item.key === "10" ? handleLogout : undefined}
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
            display: "flex",
            justifyContent: "flex-end",
            alignItems: "center",
            paddingRight: 16,
          }}
        >
          <Dropdown overlay={dropdownMenu} trigger={["click"]}>
            <Space style={{ cursor: "pointer" }}>
              <Avatar size={50} src={user?.urlImage} />
              <DownOutlined />
            </Space>
          </Dropdown>
        </Header>
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
        <Footer></Footer>
      </Layout>
    </Layout>
  );
};

export default Layouts;
