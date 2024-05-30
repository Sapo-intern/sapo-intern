import instance from "./instance";

const UserApi = {
  getAll(page, size, headers) {
    const url = `/users?page=${page}&size=${size}`;
    return instance.get(url, headers);
  },

  getOneUser(id, headers) {
    const url = `/users/${id}`;
    return instance.get(url, headers);
  },

  delete(id, headers) {
    const url = `/users/${id}`;
    return instance.delete(url, headers);
  },

  editUser(id, data, headers) {
    const url = `/users/${id}`;  
    return instance.patch(url, data, headers);
  },

  searchUser(keyword, headers) {
    const url = `/users/search?keyword=${keyword}`;
    return instance.get(url, headers);
  },
};

export default UserApi;