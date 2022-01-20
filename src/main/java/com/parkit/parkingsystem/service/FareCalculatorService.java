package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService package com.parkit.parkingsystem.service;

        import com.parkit.parkingsystem.constants.Fare;
        import com.parkit.parkingsystem.constants.ParkingType;
        import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

    public void calculateFare(Ticket ticket) {
        if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
            throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
        }

        long inMillis = ticket.getInTime().getTime();
        long outMillis = ticket.getOutTime().getTime();


        //TODO: Some tests are failing here. Need to check if this logic is correct
        double duration = (outMillis - inMillis) / (60 * 1000);
        duration = duration / 60; // durée en heure

        if (duration <= 0.50) {
            ticket.setPrice(Fare.PRICE_FREE);
            System.out.println("free spot, have a nice day");
        } else {
            switch (ticket.getParkingSpot().getParkingType()) {
                case CAR: {
                    ticket.setPrice(duration * Fare.CAR_RATE_PER_HOUR);
                    break;
                }
                case BIKE: {
                    ticket.setPrice(duration * Fare.BIKE_RATE_PER_HOUR);
                    break;
                }
                default:
                    throw new IllegalArgumentException("Unkown Parking Type");
            }
        }
    }
}
