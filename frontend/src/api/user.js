import instance from "./instance";

const UserApi = {
  getAll(page, size, headers) {
    const url = `/users?page=${page}&size=${size}`;
    return instance.get(url, headers);
  },

  delete(id, headers) {
    const url = `/users/${id}`;
    return instance.delete(url, headers);
  },

  editCategory(id, data, headers) {
    const url = `/category/${id}`;  
    return instance.put(url, data, headers);
  },
};

export default UserApi;