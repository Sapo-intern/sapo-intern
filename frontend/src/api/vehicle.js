import instance from "./instance";

export const VehicleApi = {
  getByPlateNumber: async (plateNumber = "") => {
    return await instance.get(`/vehicles/?plateNumber=${plateNumber}`);
  },
  updateVehicle: async (vehicleId, vehicle) => {
    return await instance.post(`/vehicles/${vehicleId}/`, { vehicle });
  },
};
