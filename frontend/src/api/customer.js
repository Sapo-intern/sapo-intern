import instance from "./instance";

export const CustomerApi = {
  getAll: async (page, size) => {
    return instance.get(`/customers?page=${page}&size=${size}`);
  },
  createCustomer: async (customer) => {
    return instance.post("/customers/", { customer });
  },
  searchCustomer: async(keyword) => {
    return instance.get(`/customers/search?keyword=${keyword}`);
  },
  createVehicle: async (customerId, vehicle) => {
    return await instance.post(`/customers/${customerId}/vehicles/`, {
      vehicle,
    });
  },
  assignVehicle: async (customerId, vehicleId) => {
    return await instance.post(`/customer/${customerId}/vehicles/${vehicleId}`);
  },
  getVehicles: async (customerId) => {
    if (!customerId) return;
    return await instance.get(`customers/${customerId}/vehicles/`);
  },
  updateCustomer: async (customerId, customer) => {
    return await instance.put(`customers/${customerId}/`, { customer });
  },
};
