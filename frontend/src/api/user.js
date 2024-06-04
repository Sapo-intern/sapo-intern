import instance from "./instance";

const UserApi = {
  getAll(page, size) {
    const url = `/users?page=${page}&size=${size}`;
    return instance.get(url);
  },

  getOneUser(id) {
    const url = `/users/${id}`;
    return instance.get(url);
  },

  delete(id) {
    const url = `/users/${id}`;
    return instance.delete(url);
  },

  editUser(id, data) {
    const url = `/users/${id}`;  
    return instance.patch(url, data);
  },

  searchUser(keyword) {
    const url = `/users/search?keyword=${keyword}`;
    return instance.get(url);
  },
};

export default UserApi;