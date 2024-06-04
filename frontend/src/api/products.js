import instance from "./instance";

const ProductApi = {
  getAllProducts(page, size) {
    const url = `/products?page=${page}&size=${size}`;
    return instance.get(url);
  },

  getOneProduct(id, ) {
    const url = `/products/${id}`;
    return instance.get(url);
  },

  addProduct(data, ) {
    const url = "/products";  
    return instance.post(url, data);
  },

  deleteProduct(id, ) {
    const url = `/products/${id}`;
    return instance.delete(url);
  },

  updateProduct(id, data, ) {
    const url = `/products/${id}`;  
    return instance.patch(url, data);
  },

  searchProduct(name, ) {
    const url = `/products/search?name=${name}`;
    return instance.get(url);
  },
};

export default ProductApi;