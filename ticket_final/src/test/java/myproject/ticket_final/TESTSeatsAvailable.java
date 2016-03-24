package myproject.ticket_final;

import static org.junit.Assert.*;

import org.junit.Test;

import myproject.service.TicketService;
import myproject.service.TicketServiceImpl;

public class TESTSeatsAvailable {

	@Test
	public void seatsAvailableforOrchestra() {

		TicketService service1 = new TicketServiceImpl();
		int seats = service1.numSeatsAvailable(1);
		assertNotNull("Orchestra seats are null", seats);
		assertEquals(1250, seats);

	}

	@Test
	public void seatsAvailableforMain() {

		TicketService service1 = new TicketServiceImpl();
		int seats = service1.numSeatsAvailable(2);
		assertNotNull("Main seats are null", seats);
		assertEquals(2000, seats);

	}

	@Test
	public void seatsAvailableforBalcony1() {

		TicketService service1 = new TicketServiceImpl();
		int seats = service1.numSeatsAvailable(3);
		assertNotNull("Balcony 1 seats are null", seats);
		assertEquals(1500, seats);

	}

	@Test
	public void seatsAvailableforBalcony2() {

		TicketService service1 = new TicketServiceImpl();
		int seats = service1.numSeatsAvailable(4);
		assertNotNull("Balcony 2 seats are null", seats);
		assertEquals(1500, seats);

	}
}

