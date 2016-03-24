package myproject.service;

import java.util.concurrent.Callable;

import myproject.Model.SeatHold;

public class FindandHoldSeats implements Runnable {

	SeatHold seatHold;

	public FindandHoldSeats(SeatHold seatHold) {
		this.seatHold = seatHold;
	}

	public void run() {
		System.out.println("current thread" + Thread.currentThread().getName());
		Thread thread = Thread.currentThread();
		try {
			thread.sleep(60000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("thread" + Thread.currentThread().getName() + "is awake");

		synchronized (this) {
			if (seatHold.getLevelID() == 1) {
				if (TicketServiceImpl.orchestra_seatsHeld.contains(seatHold)) {
					TicketServiceImpl.orchestra_seatsReleased.addAll(seatHold.getSeats());
					TicketServiceImpl.orchestra_seatsHeld.remove(seatHold);
					TicketServiceImpl.level1.put("available",
							TicketServiceImpl.level1.get("available") + seatHold.getSeats().size());
					TicketServiceImpl.level1.put("hold",
							TicketServiceImpl.level1.get("hold") - seatHold.getSeats().size());
				}
			} else if (seatHold.getLevelID() == 2) {
				if (TicketServiceImpl.main_seatsHeld.contains(seatHold)) {
					TicketServiceImpl.main_seatsReleased.addAll(seatHold.getSeats());
					TicketServiceImpl.main_seatsHeld.remove(seatHold);
					TicketServiceImpl.level2.put("available",
							TicketServiceImpl.level2.get("available") + seatHold.getSeats().size());
					TicketServiceImpl.level2.put("hold",
							TicketServiceImpl.level2.get("hold") - seatHold.getSeats().size());
				}
			} else if (seatHold.getLevelID() == 3) {
				if (TicketServiceImpl.balcony1_seatsHeld.contains(seatHold)) {
					TicketServiceImpl.balcony1_seatsReleased.addAll(seatHold.getSeats());
					TicketServiceImpl.balcony1_seatsHeld.remove(seatHold);
					TicketServiceImpl.level3.put("available",
							TicketServiceImpl.level3.get("available") + seatHold.getSeats().size());
					TicketServiceImpl.level3.put("hold",
							TicketServiceImpl.level3.get("hold") - seatHold.getSeats().size());
				}
			} else {
				if (TicketServiceImpl.balcony2_seatsHeld.contains(seatHold)) {
					TicketServiceImpl.balcony2_seatsReleased.addAll(seatHold.getSeats());
					TicketServiceImpl.balcony2_seatsHeld.remove(seatHold);
					TicketServiceImpl.level4.put("available",
							TicketServiceImpl.level4.get("available") + seatHold.getSeats().size());
					TicketServiceImpl.level4.put("hold",
							TicketServiceImpl.level4.get("hold") - seatHold.getSeats().size());
				}
			}
		}

	}
}
