import { useCallback, useEffect, useState } from "react";
import UserApi from "../../../api/user";

export const useEmployees = (page = 0, size = 1000) => {
  const [employees, setEmployees] = useState([]);
  const [selectedEmployee, setSelectedEmployee] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      const res = await UserApi.getAll(page, size);
      setEmployees(res.content);
    };
    fetchData();
  }, [page,size]);

  const selectEmployeeById = useCallback(
    (employeeId) => {
      setSelectedEmployee(
        employees.find((employee) => employee.id === employeeId) || null
      );
    },
    [employees]
  );

  return { employees, selectEmployeeById, selectedEmployee };
};
