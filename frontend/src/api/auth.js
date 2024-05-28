import instance from "./instance";

const auth = {
  register(data) {
    const url = "/register";
    return instance.post(url, data);
  },

  login(data) {
    const url = "/login";
    return instance.post(url, data);
  },

  changePassword(data){
    const url = "/change-password";
    return instance.post(url, data);
  },

  forgotPassword(email){
    const url = "/forgot-password?email=" + email;
    return instance.post(url);
  },

  resetPassword(token, newPassword, confirmNewPassword) {
    const url = "/reset-password";
    const formData = new FormData();
    formData.append('token', token);
    formData.append('newPassword', newPassword);
    formData.append('confirmNewPassword', confirmNewPassword);
  
    return instance.post(url, formData);
  },

  logout(data) {
    const url = "/logout-user";
    return instance.post(url, data);
  },

  getRole() {
    const url = "/role";
    return instance.get(url);
  },
};

export default auth;
