import instance from "./instance";

export const issueApi = {
  updateIssue: async (
    issueId,
    serviceId,
    employeeId,
    productId,
    quantity,
    issueProductId
  ) => {
    console.log(issueProductId);
    return await instance.put(`/issues/${issueId}/`, {
      serviceId,
      employeeId,
      productId,
      quantity,
      issueProductId,
    });
  },
  getAllByEmployeeId: async (uid) => {
    return await instance.get(`/issues/?employeeId=${uid}`);
  },
  getIssueById: async (issueId) => {
    return await instance.get(`/issues/${issueId}`);
  },
  updateProgress: async (issueId, progress) => {
    return instance.post(`/issues/${issueId}?progress=${progress}`);
  },
};
