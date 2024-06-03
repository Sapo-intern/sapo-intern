import { useCallback, useEffect, useState } from "react";
import { ServiceApi } from "../../../api/service";

export const useServices = (page = 0, size = 1000) => {
  const [services, setServices] = useState([]);
  const [selectedService, setSelectedService] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      const res = await ServiceApi.getAll(page, size);
      setServices(res.content);
    };
    fetchData();
  }, [page, size]);

  const selecteServiceById = useCallback(
    (serviceId) => {
      console.log(services.find((service) => service.id === serviceId) || null);
      setSelectedService(
        services.find((service) => service.id === serviceId) || null
      );
    },
    [services]
  );

  return { services, selectedService, selecteServiceById };
};
