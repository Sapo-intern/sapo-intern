import { Input, Modal, Row, Select } from "antd";
import React, { useState } from "react";
import { useServices } from "../hooks/useServices";
import { useEmployees } from "../hooks/useEmployees";
import { useProducts } from "../hooks/useProducts";
import { TicketApi } from "../../../api/ticket";
import { useParams } from "react-router-dom";

const IssueAddDialog = ({ isModalOpen, setIsModalOpen, addIssue }) => {
  const { selectedService, selecteServiceById, services } = useServices();
  const { selectedEmployee, selectEmployeeById, employees } = useEmployees();
  const { selectedProduct, selectProductById, products } = useProducts();
  const [quantity, setQuantity] = useState(0);
  const { id: ticketId } = useParams();

  const handleOk = async () => {
    try {
      const res = await TicketApi.addIssue(
        ticketId,
        selectedService.id,
        selectedEmployee.id,
        selectedProduct.id,
        quantity
      );
      const newIssue = res.data;
      addIssue(newIssue);
      setIsModalOpen(false);
    } catch (err) {
      alert(err);
    }
  };

  const handleCancel = () => {
    setIsModalOpen(false);
  };

  return (
    <Modal
      title="Basic Modal"
      open={isModalOpen}
      onOk={handleOk}
      onCancel={handleCancel}
    >
      <span>Dịch vụ</span>
      <Row>
        <Select
          style={{ width: 200 }}
          options={services.map((service) => ({
            label: service.name,
            value: service.id,
          }))}
          placeholder="Chon dich vu"
          value={selectedService?.id}
          onSelect={(e) => selecteServiceById(e)}
        />
      </Row>
      <span>Người thực hiện</span>
      <Row>
        <Select
          style={{ width: 200 }}
          options={employees.map((employee) => ({
            label: employee.name,
            value: employee.id,
          }))}
          placeholder="Chon nhân viên"
          value={selectedEmployee?.id}
          onSelect={(e) => selectEmployeeById(e)}
        />
      </Row>
      <span>Linh kiện</span>
      <Row>
        <Select
          style={{ width: 200 }}
          options={products.map((product) => ({
            label: product.name,
            value: product.id,
          }))}
          placeholder="Chon linh kiện"
          value={selectedProduct?.id}
          onSelect={(e) => selectProductById(e)}
        />
      </Row>
      <span>Số lượng: </span>
      <Row>
        <Input
          style={{ width: 100 }}
          name="quantity"
          type="number"
          value={quantity}
          onChange={(e) =>
            setQuantity(e.target.value >= 0 ? Number(e.target.value) : 0)
          }
        />
      </Row>
    </Modal>
  );
};

export default IssueAddDialog;
