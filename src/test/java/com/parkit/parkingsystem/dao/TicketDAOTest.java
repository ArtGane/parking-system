package com.parkit.parkingsystem.dao;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TicketDAOTest {

    private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    private static TicketDAO ticketDAO;
    private static DataBasePrepareService dataBasePrepareService;
    private static Ticket ticket = new Ticket();


    @BeforeAll
    private static void setUp() {
        ticketDAO = new TicketDAO();
        ticketDAO.dataBaseConfig = dataBaseTestConfig;
        dataBasePrepareService = new DataBasePrepareService();
        ticket.setInTime(new Date());
        ticket.setVehicleRegNumber("ABCDE");
        ticket.setParkingSpot(new ParkingSpot(1, ParkingType.CAR,true));
    }

    @Test
    void testSaveTicketFalse() {
        boolean ticketSave = ticketDAO.saveTicket(ticket);
        assertFalse(ticketSave);
    }

    @Test
    void testGetTicket() {
        Ticket getTicket = ticketDAO.getTicket("ABCDE");
        assertNotNull(getTicket);
    }

    @Test
    void testUpdateTicket() {
        ticket.setOutTime(new Date());
        boolean updateTicket = ticketDAO.updateTicket(ticket);
        assertTrue(updateTicket);
    }
}