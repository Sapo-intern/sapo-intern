import instance from "./instance";

export const TicketApi = {
  getAll: async (page, size) => {
    return await instance.get(`/tickets/?page=${page}&size=${size}`);
  },
  createTicket: async (customer, vehicle, issues) => {
    return await instance.post("/tickets/", {
      customer,
      vehicle,
      issueDtos: issues,
    });
  },
  assignCustomer: async (ticketId, vehicle) => {
    return await instance.post(`/tickets/${ticketId}/vehicles/`, vehicle);
  },
  getById: async (ticketId) => {
    return await instance.get(`/tickets/${ticketId}`);
  },
  addIssue: async (ticketId, serviceId, employeeId, productId, quantity) => {
    return await instance.post(`/tickets/${ticketId}/issues/`, {
      serviceId,
      employeeId,
      productId,
      quantity,
    });
  },
  complete: async (ticketId) => {
    return await instance.post(`/tickets/${ticketId}/complete`);
  },
  
  cancle: async (ticketId) => {
    return await instance.post(`/tickets/${ticketId}/cancel`);
  }
};
