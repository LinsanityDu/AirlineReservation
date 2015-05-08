package model;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 
 * Voyage class used for parse the flight tree list.
 *
 */

public class Voyage{
	private List<Flight> leaveFlights;
	private int totalTime;
	private float totalPrice;
	private String depCode;
	private String arvCode;
	private String priceDetail;
	private int stopover;
	private int TicketLeft;
	

	/**
	 * 
	 * Constructor of a Voyage
	 * 
	 * 
	 */
	public Voyage(){
		leaveFlights = new ArrayList<Flight>();
		totalTime = 0;
		totalPrice = 0;
		priceDetail = "";
		depCode = "";
		arvCode = "";
		stopover = 0;
		TicketLeft = 0;
	}

	
	/**
	 * 
	 * Add leaving flight to this voyage
	 * 
	 * 
	 * @param flight flight object
	 */
	public void addLeaveFlight(Flight flight){
		leaveFlights.add(flight);
	}
	
	/**
	 * 
	 * Set total time 
	 * 
	 * @param time time span
	 */
	public void setTotalTime(int time){
		totalTime = time;
	}
	
	
	/**
	 * 
	 * Set the departure
	 * 
	 * @param dep departure airport
	 */
	public void setDepCode(String dep){
	        depCode = dep;
	}
	
	
	/**
	 * 
	 * Set the arrival airport 
	 * 
	 * 
	 * @param arv  arrival airport 
	 */
	public void setArvCode(String arv){
	        arvCode = arv;
	}
	
	
	/**
	 * 
	 * Reverse the flight node list to find the backway
	 * 
	 */
	public void reverseFlights(){
		Collections.reverse(leaveFlights);	
	}
	
	/**
	 * 
	 * get the list of the leaving flight
	 * 
	 * @return list of the leaving flight
	 */
	public List<Flight> getLeaveFlights(){		
		return leaveFlights;
	}
	
	/**
	 * 
	 * Get the total time of the flights
	 * 
	 * @return total time of the flights
	 */
	public int getTotalTime(){
		return totalTime;
	}
	
	
	/**
	 * 
	 * Set the total price of the flights
	 * 
	 * 
	 * @param price total price of the flights
	 */
	public void setTotalPrice(float price){
		totalPrice = price;
	}
	
	
	/**
	 * 
	 * get the total price of the flight
	 * 
	 * @return total price of the flight
	 */
	public float getTotalPrice(){
		return totalPrice;
	}
	
	/**
	 * 
	 * Set the price 
	 * 
	 * @param priceDetail price string
	 */
	public void setPriceDetail(String priceDetail){
		this.priceDetail = priceDetail;
	}
	
	
	/**
	 * 
	 * Get the price string
	 * 
	 * @return price string
	 */
	public String getPriceDetail(){
		return this.priceDetail;
	}
	
	/**
	 * 
	 * get the string format of the arrival airport
	 * 
	 * @return string format of the arrival airport
	 */
	public String getArvCode(){   
	    return arvCode;
	}
	
	
	/**
	 * 
	 * get the string format of the departure airport
	 * 
	 * @return  string format of the departure airport
	 */
	public String getDepCode(){   
	    return depCode;
	}

	
	/**
	 * 
	 * get the number of stopover
	 * 
	 * @return number of stopover
	 */
	public int getStopover() {
		return stopover;
	}

	
	/**
	 * 
	 * set the number of stop overs
	 * 
	 * @param stopover  number of stop overs
	 */
	public void setStopover(int stopover) {
		this.stopover = stopover;
	}
	
	/**
	 * get number of tickets left in the flights
	 * 
	 * @return number of tickets left in the flights
	 */
	public int getTicketLeft() {
		return TicketLeft;
	}

	/**
	 * 
	 * set the number of tickets left in the flights
	 * 
	 * @param TicketLeft number of tickets left in the flights
	 */
	public void setTicketLeft(int TicketLeft) {
		this.TicketLeft = TicketLeft;
	}


}

