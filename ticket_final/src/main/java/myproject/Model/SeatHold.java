package myproject.Model;

import java.util.ArrayList;

public class SeatHold {

	private String seatHoldId;
	private int levelID;
	private int purchasePrice;
	private ArrayList<Integer> seats = new ArrayList<Integer>();
	private String email;

	public int getLevelID() {
		return levelID;
	}

	public void setLevelID(int levelID) {
		this.levelID = levelID;
	}

	public int getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(int purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setSeatHoldId(String seatHoldId) {
		this.seatHoldId = seatHoldId;
	}

	public String getSeatHoldId() {
		return seatHoldId;
	}

	public void setSeats(ArrayList<Integer> seats) {
		this.seats = seats;
	}

	public ArrayList<Integer> getSeats() {
		return seats;
	}
}
