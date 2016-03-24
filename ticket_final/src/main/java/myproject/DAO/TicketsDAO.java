package myproject.DAO;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import myproject.Model.LevelInfo;
import myproject.Model.SeatHold;
import myproject.service.TicketServiceImpl;

public class TicketsDAO {

	public static ArrayList<Integer> orchestra_reserved = new ArrayList<Integer>();
	public static ArrayList<Integer> main_reserved = new ArrayList<Integer>();
	public static ArrayList<Integer> balcony1_reserved = new ArrayList<Integer>();
	public static ArrayList<Integer> balcony2_reserved = new ArrayList<Integer>();

	public String reserveSeats(SeatHold s, ArrayList<Integer> reserved, Map<String, Integer> level,
			LinkedList<SeatHold> seatsHeld) {

		String reservationCode = s.getSeatHoldId();
		synchronized (this) {
			for (Integer i : s.getSeats()) {
				reserved.add(i);
			}
			System.out.println("-------------------------------------------------");
			System.out.println("Seats Reserved::");
			System.out.println("ID:" + s.getSeatHoldId());
			System.out.println("LEVEL ID:" + s.getLevelID());
			System.out.println("Price:" + s.getPurchasePrice());
			System.out.print("Seats:");
			for (Integer j : s.getSeats()) {
				System.out.println(j);
			}
			System.out.println("-------------------------------------------------");
			level.put("hold", level.get("hold") - s.getSeats().size());
			level.put("reserved", level.get("reserved") + s.getSeats().size());
			// System.out.println("current thread name
			// "+Thread.currentThread().getName());
			// Thread.currentThread().notifyAll();
			seatsHeld.remove(s);

		}
		return reservationCode;
	}

	public SeatHold getSeatsToHold(int numSeats, String customerEmail, LinkedList<Integer> seatsReleased, int price,
			int rows, int seats, ArrayList<Integer> seatsReserved, LinkedList<SeatHold> seatsHeld, int levelID) {
		SeatHold obj = new SeatHold();
		boolean available = false;
		ArrayList<Integer> seatstoHold = new ArrayList<Integer>();
		UUID id = UUID.randomUUID();

		if (!seatsReleased.isEmpty() && numSeats <= seatsReleased.size()) {
			for (int i = 0; i < numSeats; i++) {
				seatstoHold.add(seatsReleased.get(0));
				seatsReleased.remove(0);
			}
			obj.setSeatHoldId(id.toString());
			obj.setEmail(customerEmail);
			obj.setLevelID(levelID);
			obj.setPurchasePrice(price * numSeats);
			obj.setSeats(seatstoHold);

		} else {
			for (int seat = 1; seat <= (rows * seats); seat++) {
				available = false;
				for (Integer i : seatsReserved) {
					if (seat == seatsReserved.get(i - 1)) {
						available = true;
						break;
					}
				} // end looping reserved
				if (!available) {
					ArrayList<Integer> totalSeatsHeld = new ArrayList<Integer>();
					for (SeatHold s : seatsHeld) {
						totalSeatsHeld.addAll(s.getSeats());
					}
					for (Integer j : totalSeatsHeld) {
						if (seat == j) {
							available = true;
							break;
						}
					}

				}
				if (!available) {
					for (Integer k : seatsReleased) {
						if (seat == k) {
							available = true;
							break;
						}
					}
				}
				if (!available) {
					seatstoHold.add(seat);
					if (seatstoHold.size() == numSeats) {
						break;
					}
				}
			}
			if (seatstoHold.size() != numSeats) {

			} else {
				obj.setSeatHoldId(id.toString());
				obj.setEmail(customerEmail);
				obj.setLevelID(levelID);
				obj.setPurchasePrice(price * numSeats);
				obj.setSeats(seatstoHold);
			}
		}
		return obj;
	}

	public List<Integer> getSeatsReservedinOrchestra() {

		// Fetch from DB, sort based on rownum and seatnum and return the list
		return orchestra_reserved;
	}

	public List<Integer> getSeatsReservedinMain() {
		// Fetch from DB, sort based on rownum and seatnum and return the list
		return main_reserved;

	}

	public List<Integer> getSeatsReservedinBalcony1() {
		// Fetch from DB, sort based on rownum and seatnum and return the list
		return balcony1_reserved;

	}

	public List<Integer> getSeatsReservedinBalcony2() {
		// Fetch from DB, sort based on rownum and seatnum and return the list
		return balcony2_reserved;

	}
}
