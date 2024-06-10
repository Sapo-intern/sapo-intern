import instance from "./instance";

export const StorageApi = {
  getAll: async () => {
    return instance.get("/storages/");
  },
  saveStorage: async (productId, quantity) => {
    return instance.post(`/storages/`, { productId, quantity });
  },
};
