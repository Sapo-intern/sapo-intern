import { useEffect, useState } from "react";
import { StorageApi } from "../../api/storage";

export const useStorages = () => {
  const [storages, setStorages] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      const { data } = await StorageApi.getAll();
      const storages = data.map((st) => ({ ...st, importQuantity: 0 }));
      setStorages(storages);
    };
    fetchData();
  }, []);

  const saveStorage = async (productId, quantity) => {
    const { data } = await StorageApi.saveStorage(productId, quantity);
    console.log(data);
    setStorages((prevStorage) =>
      prevStorage.map((s) =>
        s.productId === productId
          ? { ...s, totalQuantity: s.totalQuantity + Number(quantity), importQuantity: 0 }
          : s
      )
    );
    alert("Nhập số lượng sản phẩm thành công!");
  };

  const setStorageQuantity = (productId, quantity) => {
    console.log(productId, quantity);
    setStorages((st) =>
      st.map((p) => {
        return p.productId === productId
          ? {
              ...p,
              importQuantity: Number(quantity) > 0 ? Number(quantity) : 0,
            }
          : p;
      })
    );
  };

  return { storages, saveStorage, setStorageQuantity };
};
