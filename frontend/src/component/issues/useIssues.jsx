import { useEffect, useState } from "react";
import { issueApi } from "../../api/issue";
import translateRole from "../../assets/js/translateRole";

export const useIssues = (page = 0, size = 1000) => {
  const [issues, setIssues] = useState([]);

  useEffect(() => {
    const { id: employeeId } = JSON.parse(localStorage.getItem("user"));
    const fetchData = async () => {
      const res = await issueApi.getAllByEmployeeId(employeeId);
      const transformedData = res.data.map(issue => ({
        ...issue,
        status: translateRole[issue.status] || issue.status,
      }));
      setIssues(transformedData);
    };
    fetchData();
  }, [page, size]);

  return { issues };
};