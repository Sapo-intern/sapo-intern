import { useEffect, useState } from "react";
import { TicketApi } from "../../../api/ticket";
import translateRole from "../../../assets/js/translateRole";
import Swal from "sweetalert2";

const transformDate = (dateString) => {
  if (!dateString) return ''; 
  const [year, month, day] = dateString.split("-");
  return `${day}-${month}-${year}`;
};

export const useTickets = (page = 0, size = 1000) => {
  const [tickets, setTickets] = useState([]);

  useEffect(() => {
    const fetchTickets = async () => {
      try {
        const { data } = await TicketApi.getAll(page, size);
        const transformedData = data.map((ticket) => {
          const { status, createdDate, completeDate, ...rest } = ticket;
          return {
            ...rest,
            status: translateRole[status] || status,
            createdDate: transformDate(createdDate),
            completeDate: transformDate(completeDate),
          };
        });
        setTickets(transformedData);
      } catch (err) {
        console.log(err);
      }
    };
    fetchTickets();
  }, [page, size]);

  const cancleTicket = async (ticketId) => {
    try {
      const { data: canceledTicket } = await TicketApi.cancle(ticketId);
      const { status, createdDate, completeDate, ...rest } = canceledTicket;
      const transformedTicket = {
        ...rest,
        status: translateRole[status] || status,
        createdDate: transformDate(createdDate),
        completeDate: transformDate(completeDate),
      };
      setTickets((prevTickets) =>
        prevTickets.map((prevTicket) =>
          prevTicket.id === ticketId ? transformedTicket : prevTicket
        )
      );
      Swal.fire({
        title: "Success!",
        text: "Cập nhật phiếu sữa xe thành công",
        icon: "success",
        confirmButtonText: "OK",
      })
    } catch (err) {
      Swal.fire({
        title: "Error!",
        text: "Cập nhật phiếu sữa xe thất bại!",
        icon: "error",
        confirmButtonText: "OK",
      });
    }
  };

  const completeTicket = async (ticketId) => {
    try {
      const { data: completedTicket } = await TicketApi.complete(ticketId);
      const { status, createdDate, completeDate, ...rest } = completedTicket;
      const transformedTicket = {
        ...rest,
        status: translateRole[status] || status,
        createdDate: transformDate(createdDate),
        completeDate: transformDate(completeDate),
      };
      setTickets((prevTickets) =>
        prevTickets.map((prevTicket) =>
          prevTicket.id === ticketId ? transformedTicket : prevTicket
        )
      );
      Swal.fire({
        title: "Success!",
        text: "Cập nhật phiếu sữa xe thành công",
        icon: "success",
        confirmButtonText: "OK",
      })
    } catch (err) {
      Swal.fire({
        title: "Error!",
        text: "Cập nhật phiếu sữa xe thất bại!",
        icon: "error",
        confirmButtonText: "OK",
      });
    }
  };

  return { tickets, cancleTicket, completeTicket };
};