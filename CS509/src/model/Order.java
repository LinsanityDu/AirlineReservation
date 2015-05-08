package model;


/**
 * 
 * Order class created when a customer made a reservation.
 *
 */
public class Order {
	
	private String Number;
	private Flight flight;
	private String Seating;
	private int amount;
	
	/**
	 * Constructor of th order object
	 * 
	 * 
	 * @param Number Number of the order
	 * @param flight flight object in this order
	 * @param Seating Seat type of this order
	 * @param amount amount of ticket needs to be reserved in this order
	 */
	public Order(String Number, Flight flight, String Seating, int amount) {
		this.Number = Number;
		this.flight = flight;
		this.Seating = Seating;
		this.amount = amount;		
	}

	
	/**
	 * 
	 * Get this order's number 
	 * 
	 * @return order's number 
	 */
	public String getNumber() {
		return this.Number;
	}
	
	/**
	 * 
	 * Get the flight object in this order
	 * 
	 * @return the flight object in this order
	 */
	public Flight getFlight() {
		
		return this.flight;
	}
	
	/**
	 * 
	 * Get the seat type in this order
	 * 
	 * 
	 * @return seat type
	 */
	public String getSeating() {
		return Seating;
	}
	
	
	/**
	 * 
	 * Get the number of tickets in this order
	 * 
	 * @return number of tickets needs to be reserved in this order
	 */
	public int getAmount() {
		return amount;
	}
}
