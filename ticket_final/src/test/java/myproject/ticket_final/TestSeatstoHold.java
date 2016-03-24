package myproject.ticket_final;

import static org.junit.Assert.*;
import myproject.Model.SeatHold;
import myproject.service.TicketService;
import myproject.service.TicketServiceImpl;

import org.junit.Test;

public class TestSeatstoHold {

	@Test
	public void seatstoHoldInOrchestra (){
		TicketService service1 = new TicketServiceImpl();
		SeatHold s = service1.findAndHoldSeats(100, 1, 1, "xxx@yyy.com");
		assertNotNull("SeatHold object is null",s);
		assertNotNull("seatHoldID is null",s.getSeatHoldId());
		assertNotNull("seata are null",s.getSeats());
		assertNotNull("purchase price is null",s.getPurchasePrice());
		assertNotNull("LevelID is null",s.getLevelID());
		assertNotNull("email is null",s.getEmail());
		
		int remainingSeatsAvailable = service1.numSeatsAvailable(1);
		assertEquals(1150, remainingSeatsAvailable);
		
		try {
			Thread.sleep(90000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		remainingSeatsAvailable = service1.numSeatsAvailable(1);
		assertEquals(1250, remainingSeatsAvailable);
		
	}
}
