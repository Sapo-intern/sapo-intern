import { Button, Card, Col, Flex, Input, Row, Select } from "antd";
import React, { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import { TicketApi } from "../../api/ticket";
import { BiUser } from "react-icons/bi";
import { IoCarOutline, IoWarning } from "react-icons/io5";
import { ServiceApi } from "../../api/service";
import { ProductApi } from "../../api/product";
import UserApi from "../../api/user";
import { CustomerApi } from "../../api/customer";
import { VehicleApi } from "../../api/vehicle";
import IssueAddDialog from "./components/IssueAddDialog";
import { issueApi } from "../../api/issue";

const TicketDetail = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [ticket, setTicket] = useState(null);

  const fetchTicketDetail = async (id) => {
    const response = await TicketApi.getById(id);
    setTicket(response?.data);
  };

  const [employees, setEmployees] = useState([]);
  const [services, setServices] = useState([]);
  const [products, setProducts] = useState([]);
  const [isModalOpen, setIsModalOpen] = useState(false);

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
    fetchTicketDetail(id);
  }, [id]);

  const handleChangeCustomerInfo = (e) => {
    setTicket((prevTicket) => ({
      ...prevTicket,
      customer: {
        ...prevTicket.customer,
        [e.target.name]: e.target.value,
      },
    }));
  };

  const handleChangeVehicleInfo = (e) => {
    setTicket((prevTicket) => ({
      ...prevTicket,
      vehicle: {
        ...prevTicket.vehicle,
        [e.target.name]: e.target.value,
      },
    }));
  };

  const updateIssueTotalAmount = (issue) => {
    const servicePrice = issue.repairService.price || 0;
    const productsTotal = issue.issueProducts.reduce(
      (sum, { product, quantity }) => sum + product.unitPrice * quantity,
      0
    );
    return servicePrice + productsTotal;
  };

  const updateTicketTotalAmount = (updatedIssues) => {
    return updatedIssues.reduce((sum, issue) => sum + issue.totalAmount, 0);
  };

  const handleOnChangeService = (issueId, serviceId) => {
    const selectedService = services.find(
      (service) => service.id === serviceId
    );
    setTicket((prevTicket) => {
      const updatedIssues = prevTicket.issues.map((issue) =>
        issue.id === issueId
          ? {
              ...issue,
              repairService: {
                ...issue.repairService,
                id: serviceId,
                name: selectedService.name,
                serviceCode: selectedService.serviceCode,
                price: selectedService.price,
              },
              totalAmount: updateIssueTotalAmount({
                ...issue,
                repairService: {
                  ...issue.repairService,
                  id: serviceId,
                  name: selectedService.name,
                  serviceCode: selectedService.serviceCode,
                  price: selectedService.price,
                },
              }),
            }
          : issue
      );

      return {
        ...prevTicket,
        issues: updatedIssues,
        totalAmount: updateTicketTotalAmount(updatedIssues),
      };
    });
  };

  const handleOnChangeEmployee = (issueId, employeeId) => {
    const selectedEmployee = employees.find(
      (employee) => employee.id === employeeId
    );
    setTicket((prevTicket) => ({
      ...prevTicket,
      issues: prevTicket.issues.map((issue) =>
        issue.id === issueId
          ? {
              ...issue,
              user: {
                id: employeeId,
                name: selectedEmployee.name,
              },
            }
          : issue
      ),
    }));
  };

  const handleOnChangeProduct = (issueId, productId) => {
    console.log(productId);
    const selectedProduct = products.find(
      (product) => product.id === productId
    );

    setTicket((prevTicket) => {
      const updatedIssues = prevTicket.issues.map((issue) =>
        issue.id === issueId
          ? {
              ...issue,
              issueProducts: issue.issueProducts.map((issueProduct) => ({
                ...issueProduct,
                product: { ...selectedProduct },
              })),
              totalAmount: updateIssueTotalAmount({
                ...issue,
                issueProducts: issue.issueProducts.map((issueProduct) => ({
                  ...issueProduct,
                  product: { ...selectedProduct },
                })),
              }),
            }
          : issue
      );

      console.log(updatedIssues[1].issueProducts[0].product.id);

      return {
        ...prevTicket,
        issues: updatedIssues,
        totalAmount: updateTicketTotalAmount(updatedIssues),
      };
    });
  };

  const handleOnChangeProductQuantity = (issueId, productId, quantity) => {
    setTicket((prevTicket) => {
      const updatedIssues = prevTicket.issues.map((issue) =>
        issue.id === issueId
          ? {
              ...issue,
              issueProducts: issue.issueProducts.map((issueProduct) =>
                issueProduct.product.id === productId
                  ? {
                      ...issueProduct,
                      quantity: quantity,
                    }
                  : issueProduct
              ),
              totalAmount: updateIssueTotalAmount({
                ...issue,
                issueProducts: issue.issueProducts.map((issueProduct) =>
                  issueProduct.product.id === productId
                    ? {
                        ...issueProduct,
                        quantity: quantity,
                      }
                    : issueProduct
                ),
              }),
            }
          : issue
      );

      return {
        ...prevTicket,
        issues: updatedIssues,
        totalAmount: updateTicketTotalAmount(updatedIssues),
      };
    });
  };

  const handleSaveCustomer = async () => {
    try {
      const { data: newCustomer } = await CustomerApi.updateCustomer(
        ticket.customer.id,
        ticket.customer
      );
      setTicket((prevTicket) => ({
        ...prevTicket,
        customer: newCustomer,
      }));
      alert("Update customer successfuly");
    } catch (err) {
      alert("Error when update customer!");
    }
  };

  const handleSaveVehicle = async () => {
    try {
      const res = await VehicleApi.updateVehicle(
        ticket.vehicle.id,
        ticket.vehicle
      );
      const newVehicle = res.data;
      setTicket((prevTicket) => ({
        ...prevTicket,
        vehicle: newVehicle,
      }));
      alert("Update vehicle success!");
    } catch (err) {
      alert("Error when update vehicle!");
    }
  };

  const addIssue = (issue) => {
    setTicket((prevTicket) => ({
      ...prevTicket,
      issues: [...prevTicket.issues, issue],
    }));
  };

  const updateIssue = async (
    issueId,
    serviceId,
    employeeId,
    productId,
    quantity,
    issueProductId
  ) => {
    console.log(issueProductId)
    try {
      const { data: newIssue } = await issueApi.updateIssue(
        issueId,
        serviceId,
        employeeId,
        productId,
        quantity,
        issueProductId
      );
      setTicket((prevTicket) => ({
        ...prevTicket,
        issues: prevTicket.issues.map((issue) =>
          issue.id === issueId ? newIssue : issue
        ),
      }));
      alert("Update issue successfully");
    } catch (err) {
      alert("Update issue failed");
    }
  };

  if (!ticket) return <div className="">Not found!</div>;

  return (
    <Flex vertical gap={"middle"}>
      <h4>Thông tin phiếu sửa chữa</h4>
      <Row>
        <Col span={8}>Mã phiếu: {ticket.id}</Col>
        <Col span={8}>Trạng thái: {ticket.status}</Col>
        <Col span={8}>Tổng tiền: {ticket.totalAmount} VNĐ</Col>
      </Row>
      <Flex horizontal="true" align="center" gap={"small"}>
        <BiUser size={24} />
        <h5 style={{ marginTop: "1rem" }}>Thông tin khách hàng</h5>
      </Flex>
      <Row>
        <Col span={4}>Mã khách hàng: {ticket.customer.id}</Col>
      </Row>
      <Row>
        <Col span={4}>Tên khách hàng:</Col>
        <Input
          name="name"
          onChange={handleChangeCustomerInfo}
          style={{ width: 200 }}
          size="middle"
          value={ticket.customer.name}
        />
      </Row>
      <Row>
        <Col span={4}>Số điện thoại:</Col>
        <Input
          name="phoneNumber"
          onChange={handleChangeCustomerInfo}
          style={{ width: 200 }}
          value={ticket.customer.phoneNumber}
        />
      </Row>
      <Row>
        <Col span={4}>Địa chỉ:</Col>
        <Input
          name="address"
          onChange={handleChangeCustomerInfo}
          style={{ width: 200 }}
          value={ticket.customer.address}
        />
      </Row>
      <Row>
        <Button onClick={handleSaveCustomer}>Lưu</Button>
      </Row>

      <Flex horizontal align="center" gap={"small"}>
        <IoCarOutline size={24} />
        <h5 style={{ marginTop: "1rem" }}>Thông tin xe</h5>
      </Flex>
      <Row>
        <Col span={8}>Mã phương tiện: {ticket.customer.id}</Col>
      </Row>
      <Row>
        <Col span={8}>
          Loại xe:{" "}
          <Input
            name="type"
            onChange={handleChangeVehicleInfo}
            style={{ width: 200 }}
            value={ticket.vehicle.type}
          />
        </Col>
        <Col span={8}>
          Hãng xe:{" "}
          <Input
            name="brand"
            onChange={handleChangeVehicleInfo}
            style={{ width: 200 }}
            value={ticket.vehicle.brand}
          />{" "}
        </Col>
      </Row>
      <Row>
        <Col span={8}>
          Biển số:{" "}
          <Input
            name="plateNumber"
            onChange={handleChangeVehicleInfo}
            style={{ width: 200 }}
            value={ticket.vehicle.plateNumber}
          />{" "}
        </Col>
        <Col span={8}>
          Màu:{" "}
          <Input
            name="color"
            onChange={handleChangeVehicleInfo}
            style={{ width: 200 }}
            value={ticket.vehicle.color}
          />{" "}
        </Col>
      </Row>
      <Row>
        <Button
          onClick={() => {
            handleSaveVehicle();
          }}
        >
          Lưu
        </Button>
      </Row>
      <IssueAddDialog
        addIssue={addIssue}
        isModalOpen={isModalOpen}
        setIsModalOpen={setIsModalOpen}
      />
      <Flex horizontal align="center" gap={"small"}>
        <IoWarning size={24} />
        <h5 style={{ marginTop: "1rem" }}>Vấn đề sửa chữa</h5>
        <Button onClick={() => setIsModalOpen(true)}>
          Thêm vấn đề sửa chữa
        </Button>
      </Flex>
      {ticket.issues.map((issue) => (
        <Card key={issue.id}>
          <div className="">
            <Row>
              <Col span={8}>Mã vấn đề: {issue.id}</Col>
            </Row>
            <Row>
              <Col span={8}>
                Người thực hiện:{" "}
                <Select
                  onChange={(e) => handleOnChangeEmployee(issue.id, e)}
                  value={issue?.user?.name || issue?.user?.id}
                  options={employees?.map((employee) => ({
                    label: employee.name || `NV-${employee.id}`,
                    value: employee.id,
                  }))}
                />
              </Col>
            </Row>
            <Row>
              <Col span={8}>Trạng thái vấn đề: {issue.status}</Col>
            </Row>
            <Row>
              <Col span={8}>Tổng tiền: {issue.totalAmount}</Col>
            </Row>
            <Row>
              <Col span={8}>
                <strong>Dịch vụ</strong>
              </Col>
            </Row>
            <Row>
              <Col span={6}>Mã dịch vụ: {issue.repairService.serviceCode}</Col>
              <Col span={8}>
                Tên dịch vụ:{" "}
                <Select
                  options={services?.map((service) => ({
                    label: service.name,
                    value: service.id,
                  }))}
                  onChange={(e) => handleOnChangeService(issue.id, e)}
                  style={{ width: 200 }}
                  value={issue.repairService.name}
                />
              </Col>
            </Row>
            <Row>
              <Col span={8}>Giá thành: {issue.repairService.price}</Col>
            </Row>
            <Row>
              <Col span={8}>
                <strong> Linh kiện:</strong>
              </Col>
            </Row>
            {issue.issueProducts.map(
              ({ product, quantity, id: issueProductId }) => (
                <React.Fragment key={product.id}>
                  <Row>
                    <Col span={6}>Mã linh kiện: {product.productCode}</Col>
                    <Col span={8}>
                      Tên linh kiện:{" "}
                      <Select
                        style={{ width: 200 }}
                        options={products?.map((prod) => ({
                          label: prod.name,
                          value: prod.id,
                        }))}
                        onChange={(e) => handleOnChangeProduct(issue.id, e)}
                        value={product.name}
                      />
                    </Col>
                    <Col span={8}>
                      Số lượng:{" "}
                      <Input
                        name="quantity"
                        style={{ width: 200 }}
                        value={quantity}
                        onChange={(e) =>
                          handleOnChangeProductQuantity(
                            issue.id,
                            product.id,
                            e.target.value
                          )
                        }
                      />
                    </Col>
                  </Row>
                  <Row>
                    <Col span={6}>Đơn giá: {product.unitPrice}</Col>
                  </Row>
                  <Row style={{ marginTop: 15 }}>
                    <Button
                      onClick={(e) => {
                        console.log(issueProductId);
                        updateIssue(
                          issue.id,
                          issue.repairService.id,
                          issue.user.id,
                          product.id,
                          quantity,
                          issueProductId
                        );
                      }}
                    >
                      Lưu
                    </Button>
                  </Row>
                </React.Fragment>
              )
            )}
          </div>
        </Card>
      ))}
      <Row>
        <Flex gap={"middle"}>
          <Button
            onClick={() => {
              navigate("/ticket");
            }}
          >
            Quay lại
          </Button>
        </Flex>
      </Row>
    </Flex>
  );
};

export default TicketDetail;
