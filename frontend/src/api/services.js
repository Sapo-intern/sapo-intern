import instance from "./instance";

const ServiceApi = {
  getAllService(page, size, headers) {
    const url = `/services?page=${page}&size=${size}`;
    return instance.get(url, headers);
  },

  getOneService(id, headers) {
    const url = `/services/${id}`;
    return instance.get(url, headers);
  },

  addService(data, headers) {
    const url = "/services";  
    return instance.post(url, data, headers);
  },

  deleteService(id, headers) {
    const url = `/services/${id}`;
    return instance.delete(url, headers);
  },

  updateService(id, data, headers) {
    const url = `/services/${id}`;  
    return instance.patch(url, data, headers);
  },

  searchService(name, headers) {
    const url = `/services/search?name=${name}`;
    return instance.get(url, headers);
  },
};

export default ServiceApi;