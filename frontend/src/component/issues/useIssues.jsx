import { useEffect, useState } from "react";
import { issueApi } from "../../api/issue";

export const useIssues = (page = 0, size = 1000) => {
  const [issues, setIssues] = useState([]);

  useEffect(() => {
    const { id: employeeId } = JSON.parse(localStorage.getItem("user"));
    const fetchData = async () => {
      const res = await issueApi.getAllByEmployeeId(employeeId);
      setIssues(res.data);
    };
    fetchData();
  }, [page, size]);

  return { issues };
};
