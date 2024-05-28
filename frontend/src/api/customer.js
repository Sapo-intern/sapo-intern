import instance from "./instance";

export const CustomerApi = {
  getAll: async (page, size) => {
    return instance.get(`/customers/?page=${page}&size=${size}`);
  },
  add: async (customer) => {
    return instance.post("/customers/", { ...customer });
  },
};
