import instance from "./instance";

const ProductApi = {
  getAllProducts(page, size, headers) {
    const url = `/products?page=${page}&size=${size}`;
    return instance.get(url, headers);
  },

  getOneProduct(id, headers) {
    const url = `/products/${id}`;
    return instance.get(url, headers);
  },

  addProduct(data, headers) {
    const url = "/products";  
    return instance.post(url, data, headers);
  },

  deleteProduct(id, headers) {
    const url = `/products/${id}`;
    return instance.delete(url, headers);
  },

  updateProduct(id, data, headers) {
    const url = `/products/${id}`;  
    return instance.patch(url, data, headers);
  },

  searchProduct(name, headers) {
    const url = `/products/search?name=${name}`;
    return instance.get(url, headers);
  },
};

export default ProductApi;