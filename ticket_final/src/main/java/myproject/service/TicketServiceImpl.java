package myproject.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import myproject.DAO.TicketsDAO;
import myproject.Model.LevelInfo;
import myproject.Model.SeatHold;

public class TicketServiceImpl implements TicketService {

	public static LinkedList<SeatHold> orchestra_seatsHeld = new LinkedList<SeatHold>();
	public static LinkedList<SeatHold> main_seatsHeld = new LinkedList<SeatHold>();
	public static LinkedList<SeatHold> balcony1_seatsHeld = new LinkedList<SeatHold>();
	public static LinkedList<SeatHold> balcony2_seatsHeld = new LinkedList<SeatHold>();

	public static LinkedList<Integer> orchestra_seatsReleased = new LinkedList<Integer>();
	public static LinkedList<Integer> main_seatsReleased = new LinkedList<Integer>();
	public static LinkedList<Integer> balcony1_seatsReleased = new LinkedList<Integer>();
	public static LinkedList<Integer> balcony2_seatsReleased = new LinkedList<Integer>();
	TicketsDAO ticketsDAO = new TicketsDAO();

	public static Map<String, Integer> level1 = new HashMap<String, Integer>();
	static {
		level1.put("available", 1250);
		level1.put("hold", 0);
		level1.put("reserved", 0);
	}
	public static Map<String, Integer> level2 = new HashMap<String, Integer>();
	static {
		level2.put("available", 2000);
		level2.put("hold", 0);
		level2.put("reserved", 0);
	}
	public static Map<String, Integer> level3 = new HashMap<String, Integer>();
	static {
		level3.put("available", 1500);
		level3.put("hold", 0);
		level3.put("reserved", 0);
	}
	public static Map<String, Integer> level4 = new HashMap<String, Integer>();
	static {
		level4.put("available", 1500);
		level4.put("hold", 0);
		level4.put("reserved", 0);
	}

	public int numSeatsAvailable(Integer venueLevel) {

		if (venueLevel == 1) {
			return level1.get("available").intValue();
		} else if (venueLevel == 2) {
			return level2.get("available").intValue();
		} else if (venueLevel == 3) {
			return level4.get("available").intValue();
		} else if (venueLevel == 4) {
			return level4.get("available").intValue();
		}
		return 0;
	}

	public int numSeatsAvailable() {

		return level1.get("available").intValue() + level2.get("available").intValue()
				+ level4.get("available").intValue() + level4.get("available").intValue();

	}

	public SeatHold findAndHoldSeats(int numSeats, int minLevel, int maxLevel, String customerEmail) {

		SeatHold seatHold = holdSeats(numSeats, minLevel, maxLevel, customerEmail);

		if (seatHold != null) {
			synchronized (this) {
				if (seatHold.getLevelID() == 1) {
					TicketServiceImpl.orchestra_seatsHeld.add(seatHold);
					TicketServiceImpl.level1.put("available",
							TicketServiceImpl.level1.get("available") - seatHold.getSeats().size());
					TicketServiceImpl.level1.put("hold",
							TicketServiceImpl.level1.get("hold") + seatHold.getSeats().size());
				} else if (seatHold.getLevelID() == 2) {
					TicketServiceImpl.main_seatsHeld.add(seatHold);
					TicketServiceImpl.level2.put("available",
							TicketServiceImpl.level2.get("available") - seatHold.getSeats().size());
					TicketServiceImpl.level2.put("hold",
							TicketServiceImpl.level2.get("hold") + seatHold.getSeats().size());
				} else if (seatHold.getLevelID() == 3) {
					TicketServiceImpl.balcony1_seatsHeld.add(seatHold);
					TicketServiceImpl.level3.put("available",
							TicketServiceImpl.level3.get("available") - seatHold.getSeats().size());
					TicketServiceImpl.level3.put("hold",
							TicketServiceImpl.level3.get("hold") + seatHold.getSeats().size());
				} else {
					TicketServiceImpl.balcony2_seatsHeld.add(seatHold);
					TicketServiceImpl.level4.put("available",
							TicketServiceImpl.level4.get("available") - seatHold.getSeats().size());
					TicketServiceImpl.level4.put("hold",
							TicketServiceImpl.level4.get("hold") + seatHold.getSeats().size());
				}
			}
		}

		return seatHold;
	}

	public SeatHold holdSeats(int numSeats, int minLevel, int maxLevel, String customerEmail) {

		int seatsAvailable;
		if (maxLevel - minLevel >= 0) {
			for (int level = minLevel; level <= maxLevel; level++) {
				seatsAvailable = numSeatsAvailable(level);
				if (numSeats <= seatsAvailable) {
					SeatHold seatHold = getSeatsToHold(numSeats, level, customerEmail);
					if (seatHold != null)
						return seatHold;
				}

			}
		}

		return null;
	}

	SeatHold getSeatsToHold(int numSeats, int level, String customerEmail) {

		if (level == 1) {
			SeatHold seatHold = ticketsDAO.getSeatsToHold(numSeats, customerEmail, orchestra_seatsReleased,
					LevelInfo.ORCHESTRA.getPrice(), LevelInfo.ORCHESTRA.getRows(), LevelInfo.ORCHESTRA.getSeats(),
					TicketsDAO.orchestra_reserved, orchestra_seatsHeld, 1);
			return seatHold;
		}

		if (level == 2) {
			SeatHold seatHold = ticketsDAO.getSeatsToHold(numSeats, customerEmail, main_seatsReleased,
					LevelInfo.MAIN.getPrice(), LevelInfo.MAIN.getRows(), LevelInfo.MAIN.getSeats(),
					TicketsDAO.main_reserved, main_seatsHeld, 2);
			return seatHold;
		}
		if (level == 3) {
			SeatHold seatHold = ticketsDAO.getSeatsToHold(numSeats, customerEmail, balcony1_seatsReleased,
					LevelInfo.BALCONY1.getPrice(), LevelInfo.BALCONY1.getRows(), LevelInfo.BALCONY1.getSeats(),
					TicketsDAO.balcony1_reserved, balcony1_seatsHeld, 3);
			return seatHold;
		}
		if (level == 4) {
			SeatHold seatHold = ticketsDAO.getSeatsToHold(numSeats, customerEmail, balcony2_seatsReleased,
					LevelInfo.BALCONY2.getPrice(), LevelInfo.BALCONY2.getRows(), LevelInfo.BALCONY2.getSeats(),
					TicketsDAO.balcony2_reserved, balcony2_seatsHeld, 4);
			return seatHold;
		}

		return null;
	}

	public String reserveSeats(String seatHoldId, String customerEmail) {

		for (SeatHold s : orchestra_seatsHeld) {
			if (seatHoldId.equalsIgnoreCase(s.getSeatHoldId()) && customerEmail.equalsIgnoreCase(s.getEmail())) {
				String reservationCode = ticketsDAO.reserveSeats(s, TicketsDAO.orchestra_reserved, level1,
						orchestra_seatsHeld);
				return reservationCode;
			}
		} // end for loop

		for (SeatHold s : main_seatsHeld) {
			if (seatHoldId.equalsIgnoreCase(s.getSeatHoldId()) && customerEmail.equalsIgnoreCase(s.getEmail())) {
				String reservationCode = ticketsDAO.reserveSeats(s, TicketsDAO.main_reserved, level2, main_seatsHeld);
				return reservationCode;
			}
		} // end for loop

		for (SeatHold s : balcony1_seatsHeld) {
			if (seatHoldId.equalsIgnoreCase(s.getSeatHoldId()) && customerEmail.equalsIgnoreCase(s.getEmail())) {

				String reservationCode = ticketsDAO.reserveSeats(s, TicketsDAO.balcony1_reserved, level3,
						balcony1_seatsHeld);
				return reservationCode;
			}
		} // end for loop

		for (SeatHold s : balcony2_seatsHeld) {
			if (seatHoldId.equalsIgnoreCase(s.getSeatHoldId()) && customerEmail.equalsIgnoreCase(s.getEmail())) {

				String reservationCode = ticketsDAO.reserveSeats(s, TicketsDAO.balcony2_reserved, level4,
						balcony2_seatsHeld);
				return reservationCode;
			}
		} // end for loop
		return null;
	}
}
