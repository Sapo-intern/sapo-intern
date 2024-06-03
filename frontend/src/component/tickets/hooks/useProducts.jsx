import { useCallback, useEffect, useState } from "react";
import { ProductApi } from "../../../api/product";

export const useProducts = (page = 0, size = 1000) => {
  const [products, setProducts] = useState([]);
  const [selectedProduct, setSelectedProduct] = useState(null);
  useEffect(() => {
    const fetchData = async () => {
      const res = await ProductApi.getAll(page, size);
      setProducts(res.content);
    };
    fetchData();
  }, [page, size]);

  const selectProductById = useCallback(
    (productId) => {
      setSelectedProduct(
        products.find((product) => product.id === productId) || nul
      );
    },
    [products]
  );

  return { products, selectedProduct, selectProductById };
};
