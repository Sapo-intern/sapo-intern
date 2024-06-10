import { Button, Card, Col, Divider, Flex, Progress, Row, Slider } from "antd";
import React from "react";
import { useNavigate, useParams } from "react-router-dom";
import { useIssueDetail } from "./useIssueDetail";

const DetailIssueScreen = () => {
  const { id } = useParams();
  const { issue, updateProgress, saveProgress } = useIssueDetail(id);

  const navigate = useNavigate();

  if (issue === null) return <div className="">Not found!</div>;

  return (
    <>
      <Flex gap={"small"} vertical>
        <Card title={`Mã vấn đề: ${issue.id}`}>
          <p>Miêu tả: {issue.description}</p>
          <p>Trạng thái: {issue.status}</p>
          <Flex gap="middle" align="center">
            <p>Tình trạng:</p>
            <Slider
              onChange={updateProgress}
              style={{ width: "75%" }}
              value={issue.progress}
            />
          </Flex>
          <Button onClick={saveProgress}>Cập nhật</Button>
        </Card>
        <Row gutter={4}>
          <Col span={12}>
            <Card title={`Dịch vụ: ${issue.repairService.name}`}>
              <p>Mã dịch vụ: {issue.repairService.serviceCode}</p>
              <p>Miêu tả: {issue.repairService.description}</p>
            </Card>
          </Col>
          <Col span={12}>
            <Card title={`Linh kiện liên quan:`}>
              {issue.issueProducts.map(({ product, quantity }) => (
                <>
                  <Flex justify="space-between">
                    <p>Tên linh kiện: {product.name}</p>
                    <p>Số lượng: {quantity}</p>
                  </Flex>
                  <Divider />
                </>
              ))}
            </Card>
          </Col>
        </Row>
      </Flex>
      <Button
        style={{ marginTop: 10 }}
        onClick={() => {
          navigate("/issue");
        }}
      >
        Quay lại
      </Button>
    </>
  );
};

export default DetailIssueScreen;
