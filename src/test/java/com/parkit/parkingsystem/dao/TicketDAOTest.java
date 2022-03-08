package com.parkit.parkingsystem.dao;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
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
        ticket.setParkingSpot(new ParkingSpot(1, ParkingType.CAR, true));
    }

    @BeforeEach
    private void clearDB() {
        dataBasePrepareService.clearDataBaseEntries();
    }

    @Test
    void testSaveTicketTrue() {
        boolean ticketSave = ticketDAO.saveTicket(ticket);
        assertTrue(ticketSave);
    }

    @Nested
    public class InternalTicketDaoTest {

        @BeforeEach
        public void init() {
            ticketDAO.saveTicket(ticket);
        }

        @Test
        void testGetTicket() {
            Ticket getTicket = ticketDAO.getTicket("ABCDE");
            assertEquals(getTicket.getVehicleRegNumber(), "ABCDE");
        }

        @Test
        void testUpdateTicket() {
            Ticket getTicket = ticketDAO.getTicket("ABCDE");
            getTicket.setOutTime(new Date());
            getTicket.setPrice(666);
            boolean updateTicket = ticketDAO.updateTicket(getTicket);
            assertTrue(updateTicket);
        }

        @Test
        void testCountTicker() {
            int countTicket = ticketDAO.countTicket("ABCDE");
            assertEquals(1, countTicket);
        }

        @Test
        void testGetWrongTicket() {
            Ticket getTicket = ticketDAO.getTicket("FGHIJ");
            assertNull(getTicket);
        }
    }
}