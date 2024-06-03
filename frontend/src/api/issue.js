import instance from "./instance";

export const issueApi = {
  updateIssue: async (issueId, serviceId, employeeId, productId, quantity, issueProductId) => {
    console.log(issueProductId)
    return await instance.put(`/issues/${issueId}/`, {
      serviceId,
      employeeId,
      productId,
      quantity,
      issueProductId
    });
  },
};
