import { useEffect, useState } from "react";
import { StorageApi } from "../../api/storage";
import Swal from "sweetalert2";

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
    try {
      await StorageApi.saveStorage(productId, quantity);
      setStorages((prevStorage) =>
        prevStorage.map((s) =>
          s.productId === productId
            ? { ...s, totalQuantity: s.totalQuantity + Number(quantity), importQuantity: 0 }
            : s
        )
      )

      Swal.fire({
        title: "Success!",
        text: "Thêm số lượng sản phẩm thành công",
        icon: "success",
        confirmButtonText: "OK",
      })
    } catch (error) {
      Swal.fire({
        title: "Error!",
        text: "Thêm số lượng sản phẩm thất bại!",
        icon: "error",
        confirmButtonText: "OK",
      });
    }
  };

  const setStorageQuantity = (productId, quantity) => {
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
