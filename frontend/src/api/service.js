import instance from "./instance";

export const ServiceApi = {
  getAll: (page, size) => {
    return instance.get(`/services?page=${page}&size=${size}`);
  },
};
