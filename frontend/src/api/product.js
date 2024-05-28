import instance from "./instance";

export const ProductApi = {
  getAll: (page, size) => {
    return instance.get(`/products?page=${page}&size=${size}`);
  },
};
