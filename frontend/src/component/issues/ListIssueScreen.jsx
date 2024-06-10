import React from "react";
import { useIssues } from "./useIssues";
import { Table } from "antd";
import { useIssueColumns } from "./useIssueColumn";

const ListIssueScreen = () => {
  const { issues } = useIssues();
  console.log(issues)
  return (
    <div>
      <h2>Danh sách các vấn đề</h2>
      <Table dataSource={issues} columns={useIssueColumns()}></Table>
    </div>
  );
};

export default ListIssueScreen;
