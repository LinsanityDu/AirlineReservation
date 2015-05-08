
public class Order {
	
	private String Number;
	private Flight flight;
	private String Seating;
	private int amount;
	
	public Order(String Number, Flight flight, String Seating, int amount) {
		this.Number = Number;
		this.flight = flight;
		this.Seating = Seating;
		this.amount = amount;		
	}

	public String getNumber() {
		return this.Number;
	}
	
	public Flight getFlight() {
		
		return this.flight;
	}
	
	public String getSeating() {
		return Seating;
	}
	
	public int getAmount() {
		return amount;
	}
}
