package myproject.Model;

public enum LevelInfo {

	ORCHESTRA(100, 25, 50), MAIN(75, 20, 100), BALCONY1(50, 15, 100), BALCONY2(40, 15, 100);

	private int price;
	private int rows;
	private int seats;

	LevelInfo(int price, int rows, int seats) {
		this.price = price;
		this.rows = rows;
		this.seats = seats;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}
}
