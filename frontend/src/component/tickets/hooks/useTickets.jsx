import { useEffect, useState } from "react";
import { TicketApi } from "../../../api/ticket";

export const useTickets = (page = 0, size = 1000) => {
  const [tickets, setTickets] = useState([]);

  useEffect(() => {
    const fetchTickets = async () => {
      try {
        const { data } = await TicketApi.getAll(page, size);
        for (const ticket of data) {
          delete ticket.issues;
          delete ticket.customer;
        }
        setTickets(data);
      } catch (err) {
        console.log(err);
      }
    };
    fetchTickets();
  }, [page, size]);

  const cancleTicket = async (ticketId) => {
    try {
      const { data: canceledTicket } = await TicketApi.cancle(ticketId);
      delete canceledTicket.issues;
      delete canceledTicket.customer;
      setTickets((prevTickets) =>
        prevTickets.map((prevTicket) =>
          prevTicket.id === ticketId ? canceledTicket : prevTicket
        )
      );
      alert("Update ticket successfully!");
    } catch (err) {
      console.log(err);
      alert("Update ticket failed!");
    }
  };

  const completeTicket = async (ticketId) => {
    try {
      const { data: completedTicket } = await TicketApi.complete(ticketId);
      delete completedTicket.issues;
      delete completedTicket.customer;
      setTickets((prevTickets) =>
        prevTickets.map((prevTicket) =>
          prevTicket.id === ticketId ? completedTicket : prevTicket
        )
      );
      alert("Update ticket successfully!");
    } catch (err) {
      console.log(err);
      alert("Update ticket failed!");
    }
  };

  return { tickets, cancleTicket, completeTicket };
};
