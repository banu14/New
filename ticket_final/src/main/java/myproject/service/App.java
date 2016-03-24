// change the string reservationCode to int
//exception handling
// implementation for optional parameters
// implement for multi users - multi threading
//jnuit test code coverage
//readme text
//code java doc comments
//getseats logic change (optional)
//DAO and daoimpl seperation

// other levels to implement for holdmethod - done
//code for reserve seats - done
//after reservation - thread wakes up and again increasing the available count. - done

package myproject.service;

import java.util.Scanner;
import java.util.UUID;

import myproject.Model.SeatHold;

public class App {
	private static Scanner scanner;

	public static void main(String[] args) {
		TicketService service1 = new TicketServiceImpl();
		int option, levelID, numOfSeats, seatsCount, minLevel, maxLevel;
		String email, seatHoldID;
		scanner = new Scanner(System.in);
		while (true) {
			System.out.println("-------------------------------------------------");
			System.out.println("1. Enter 1 to view number of seats available");
			System.out.println("2. Enter 2 to view the seats available and hold the seats");
			System.out.println("3. Enter 3 to reserve the seats");
			System.out.println("4. Enter 0 to exit");
			System.out.println("-------------------------------------------------");
			try {
				option = scanner.nextInt();
				if (option != 1 && option != 2 && option != 3 && option != 0) {
					continue;
				}
			} catch (Exception e) {
				scanner.next();
				continue;
			}

			// Terminate
			if (option == 0) {
				System.out.println("-------------------------------------------------");
				System.out.println("Thanks for showing interest in booking tickets");
				System.out.println("-------------------------------------------------");
				System.exit(0);
				// view available seats
			} else if (option == 1) {
				while (true) {
					System.out.println("-------------------------------------------------");
					System.out.println("Enter level ID to view number of available seats");
					System.out.println("1. Orchestra");
					System.out.println("2. Main");
					System.out.println("3. Balcony 1");
					System.out.println("4. Balcony 2");
					System.out.println("5. ALL");
					System.out.println("0. Main Menu");
					System.out.println("-------------------------------------------------");
					try {
						levelID = scanner.nextInt();
						if (levelID != 1 && levelID != 2 && levelID != 3 && levelID != 4 && levelID != 5
								&& levelID != 0) {
							continue;
						}
					} catch (Exception e) {
						scanner.next();
						continue;
					}
					if (levelID == 0)
						break;
					else if (levelID == 5) {
						TicketServiceImpl service2 = (TicketServiceImpl) service1;
						numOfSeats = service2.numSeatsAvailable();
						System.out.println("-------------------------------------------------");
						System.out.println("TOTAL Number of Seats Available :: " + numOfSeats);
						System.out.println("-------------------------------------------------");
					} else {
						numOfSeats = service1.numSeatsAvailable(levelID);
						System.out.println("-------------------------------------------------");
						System.out.println("Number of Seats Available for Level ID " + levelID + ": " + numOfSeats);
						System.out.println("-------------------------------------------------");
					}
				}
			} else if (option == 2) {

				System.out.println("-------------------------------------------------");
				System.out.println("Enter number of Seats to Hold");
				seatsCount = scanner.nextInt();

				System.out.println("Enter Minimum LevelID to hold the seats");
				minLevel = scanner.nextInt();
				System.out.println("Enter Maximum LevelID to hold the seats");
				maxLevel = scanner.nextInt();
				System.out.println("Enter your Email ID to send you notification");
				System.out.println("-------------------------------------------------");
				email = scanner.next();

				SeatHold seatsHeld = service1.findAndHoldSeats(seatsCount, minLevel, maxLevel, email);
				if (seatsHeld != null) {
					System.out.println("-------------------------------------------------");
					System.out.println("Seats Held::");
					System.out.println("ID:" + seatsHeld.getSeatHoldId());
					System.out.println("LEVEL ID:" + seatsHeld.getLevelID());
					System.out.println("Price:" + seatsHeld.getPurchasePrice());
					System.out.print("Seats:");
					for (Integer i : seatsHeld.getSeats()) {
						System.out.print(i + " , ");
					}
					System.out.println("-------------------------------------------------");
				} else {
					System.out.println("Sorry seats not available, Please rehold and book");
				}
				
				FindandHoldSeats findthread = new FindandHoldSeats(seatsHeld);
				Thread objThread = new Thread(findthread);		
				objThread.setName(seatsHeld.getSeatHoldId());
				objThread.start();
			} else if (option == 3) {

				System.out.println("-------------------------------------------------");
				System.out.println("Enter SeatHold ID to reserve your seats");
				seatHoldID = scanner.next();
				System.out.println("Enter your Email ID to send you notification");
				System.out.println("-------------------------------------------------");
				email = scanner.next();
				String reservationCode = service1.reserveSeats(seatHoldID, email);
				if (reservationCode != null) {
					System.out.println("Please show this ReservationCode to print the tickets :: " + reservationCode);
				} else {
					System.out.println("Sorry...Please hold the tickets again and book within the time limit");
				}
			}

		}
	}

}
