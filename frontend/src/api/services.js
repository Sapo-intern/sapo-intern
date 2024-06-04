import instance from "./instance";

const ServiceApi = {
  getAllService(page, size) {
    const url = `/services?page=${page}&size=${size}`;
    return instance.get(url);
  },

  getOneService(id) {
    const url = `/services/${id}`;
    return instance.get(url);
  },

  addService(data) {
    const url = "/services";  
    return instance.post(url, data);
  },

  deleteService(id) {
    const url = `/services/${id}`;
    return instance.delete(url);
  },

  updateService(id, data) {
    const url = `/services/${id}`;  
    return instance.patch(url, data);
  },

  searchService(name) {
    const url = `/services/search?name=${name}`;
    return instance.get(url);
  },
};

export default ServiceApi;