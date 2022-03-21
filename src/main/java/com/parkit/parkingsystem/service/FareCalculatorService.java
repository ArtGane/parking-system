package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

    private final TicketDAO ticketDAO = new TicketDAO();

    public void calculateFare(Ticket ticket) {
        if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
            throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
        }

        int nbTicket = ticketDAO.countTicket(ticket.getVehicleRegNumber());
        long inMillis = ticket.getInTime().getTime();
        long outMillis = ticket.getOutTime().getTime();

        //Some tests are failing here. Need to check if this logic is correct
        double duration = (outMillis - inMillis) / (60 * 1000);
        duration = duration / 60; // durée en heure

        if (ticket.getParkingSpot().getParkingType() == null) {
            throw new IllegalArgumentException("Our parking is only for car or bike customers.");
        }

        if (duration <= 0.50) {
            ticket.setPrice(Fare.PRICE_FREE);
            System.out.println("free spot, have a nice day");
        } else {
            double rate = ticket.getParkingSpot().getParkingType() == ParkingType.CAR ? Fare.CAR_RATE_PER_HOUR: Fare.BIKE_RATE_PER_HOUR;
            if (nbTicket > 5) {
                ticket.setPrice((duration * rate) - ((duration * rate)  * 0.05));

            } else {
                ticket.setPrice(duration * rate);
            }
        }
    }
}
