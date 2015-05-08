package controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import model.Airplane;
import model.Airport;
import model.Flight;
import model.Order;
import model.Voyage;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * 
 * The reservation System class -- singleton
 * 
 *
 */
public class ReservationSys {

	private List<Airplane> airplanes;
	private List<Airport> airports;
	private DOMParser domparser;
	private TimeSys timeSys;
	private AccessDB Rs;

	/**
	 * initial self-instance prepared for Singleton
	 */
	private static ReservationSys instance = null;

	/**
	 * get ReservationSys instance based on Singleton Pattern
	 * 
	 * @return ReservationSys instance
	 */
	public static ReservationSys getInstance() {
		if (instance == null) {
			synchronized (ReservationSys.class) {
				if (instance == null) {
					instance = new ReservationSys();
				}
			}
		}
		return instance;
	}

	/**
	 * 
	 * Create a reservation System. 1st step: Parse DOM 2nd step: get airports
	 * information 3rd step: get airplanes information
	 */
	public ReservationSys() {
		domparser = new DOMParser();
		timeSys = TimeSys.getInstance();
		Rs = new AccessDB();
		airplanes = getAirplanes();
		airports = getAirports();
		timeSys.initTimeZone(airports);
	}

	/**
	 * 
	 * Return the list of airport parsed from the XML.
	 * 
	 * @return List that contains all of the Airport objects parsed from the xml
	 *         string
	 */

	public List<Airport> getAirports() {
		List<Airport> list = new ArrayList<Airport>();
		list = domparser.getAirports();
		return list;
	}

	/**
	 * 
	 * Return the list of airplane parsed from the XML.
	 * 
	 * @return List that contains all of the Airplane objects parsed from the
	 *         xml string
	 */
	public List<Airplane> getAirplanes() {
		List<Airplane> list = new ArrayList<Airplane>();
		list = domparser.getAirplanes();
		return list;
	}

	/**
	 * 
	 * Basic search operation.
	 * 
	 * Needs input of departure location and date.
	 * 
	 * @param ticketAgency
	 *            ticketAgency identifies the ticket agency requesting the
	 *            information
	 * @param dep
	 *            Departure airport that selected by a customer
	 * @param date
	 *            Departure date selected by a customer
	 * @return List that contains all of the Flight object builded after parsing
	 *         the XML string and null if fail
	 */
	public List<Flight> search(String ticketAgency, String dep, String date) {
		String Xml = Rs.searchDep(ticketAgency, dep, date);
		InputStream input;
		try {
			input = new ByteArrayInputStream(Xml.getBytes("UTF-8"));
			SaxParseFlight sax = new SaxParseFlight(airplanes);
			return sax.getFlights(input, airplanes);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * Process the choice made by costumer.
	 * 
	 * Generate a reservation request.
	 * 
	 * Needs input of costumer order.
	 * 
	 * @param orders
	 *            List of orders.
	 */
	public boolean buyTicket(List<Order> orders) {
		boolean status = false;
		String xmlFile = "";
		for (Order order : orders) {
			Flight flight = order.getFlight();
			if (checkLeftseats(flight.getAirplane(), order.getNumber(), order
					.getNumber().equals("Coach") ? flight.getCoach_SoldNum()
					: flight.getFirstClass_SoldNum()) <= 0) {
				System.out.println("Sorry, all the tickets are sold out!\n");
				return false;
			}
			for (int i = 0; i < order.getAmount(); i++)
				xmlFile += "<Flight number=\"" + order.getNumber()
						+ "\" seating=\"" + order.getSeating() + "\" />";
		}
		xmlFile = "<Flights>" + xmlFile + "</Flights>";
		// System.out.println(xmlFile);
		// Rs.lock("Team03");
		if (Rs.buyTickets("Team03", xmlFile)) {
			System.out.println("Purchase Successful!\n");
			status = true;
		} else {
			System.out.println("Time Out!\n");
		}
		Rs.unlock("Team03");

		return status;
	}

	/**
	 * 
	 * Advanced search with number of stop overs.
	 * 
	 * Needs input of basic information of departure date and location.
	 * 
	 * Needs input of advanced information of number of stop overs.
	 * 
	 * 
	 * @param dep
	 *            Departure airport
	 * @param arv
	 *            Arrival airport
	 * @param date
	 *            Departure date
	 * @param stopover
	 *            Numbers of stop over
	 * @return List of Voyage objects after parsing the flight node tree
	 */
	public List<Voyage> searchDep(String dep, String arv, String dateS, String dateE,
			int stopover) {

			
		dateS = timeSys.formatTime(dateS);
		dateE = timeSys.formatTime(dateE);
		System.out.println(dateS);
		System.out.println(dateE);
		FlightTree sys = new FlightTree(true);
		long s = System.currentTimeMillis();
		
		
		sys.createTree(stopover + 1, dep, arv, dateS, true, "");
		
		long e = System.currentTimeMillis();
		List<Voyage> voyages = sys.getVoyages();
		System.out.println(e - s + "ms");
		if(!dateS.equals(dateE)){
			FlightTree sys1 = new FlightTree(true);
			long s1 = System.currentTimeMillis();

			sys1.createTree(stopover + 1, dep, arv, dateE, true, "");
			
			long e1 = System.currentTimeMillis();

			System.out.println(e1 - s1 + "ms");
			 
			voyages.addAll(sys1.getVoyages());
			

		}
		
		
        
		return voyages;

	}

	/**
	 * 
	 * Process result of search operation.
	 * 
	 * Build a tree and parse it.
	 * 
	 * @param dep
	 *            Departure airport
	 * @param arv
	 *            Arrival airport
	 * @param date
	 *            Departure date
	 * @param arvdate
	 *            Arrival date
	 * @param stopover
	 *            Number of stop overs
	 * @return List of Voyage objects after parsing the flight node tree
	 */
	public List<Voyage> searchRet(String dep, String arv, String dateS, String dateE,
			String arvdate, int stopover) {

		dateS = timeSys.formatTime(dateS);
		dateE = timeSys.formatTime(dateE);
		System.out.println(dateS);
		System.out.println(dateE);
		
		FlightTree sys = new FlightTree(true);
		long s = System.currentTimeMillis();

		System.out.println(arvdate);
		sys.createTree(stopover + 1, dep, arv, dateS, false, arvdate);

		long e = System.currentTimeMillis();

		System.out.println(e - s + "ms");
		List<Voyage> voyages = sys.getVoyages();
		
		if(!dateS.equals(dateE)){
			FlightTree sys1 = new FlightTree(true);
			long s1 = System.currentTimeMillis();

			sys1.createTree(stopover + 1, dep, arv, dateE, true, arvdate);
			
			long e1 = System.currentTimeMillis();

			System.out.println(e1 - s1 + "ms");
			 
			voyages.addAll(sys1.getVoyages());
			

		}
		return voyages;

	}

	
	/**
	 * double check the old voyage information such the amount of tickets 
	 * @param old previous voyage
	 * @return fresh voyage
	 */
	public Voyage doubleCheck(Voyage old) {
		if (old == null)
			return old;

		Voyage renew = new Voyage();
		for (Flight f : old.getLeaveFlights()) {
			String date = timeSys.formatTime(f.getDep_Time());
			String depCode = f.getDep_Code();
			String arvCode = f.getArv_Code();
			String number = f.getNumber();
			// System.out.println(date+" "+depCode+" "+arvCode+" "+number);
			List<Flight> flights;
			flights = search("Team03", depCode, date);
			for (Flight flight : flights) {
				if (flight.getArv_Code().equals(arvCode)
						&& flight.getNumber().equals(number)) {
					renew.addLeaveFlight(flight);
					break;
				}
			}
		}

		renew = completeVoyage(renew, renew.getLeaveFlights().size());

		return renew;
	}
	

	/**
	 * complete all the information of a Voyage
	 * @param v a Voyage is not completed which contains a list of Flights
	 * @param size the amount of flights
	 * @return a completed voyage
	 */
	public Voyage completeVoyage(Voyage v, int size) {

		
		//airports = getAirports();
		int time = timeSys.calTimeDiff(
				v.getLeaveFlights().get(0).getDep_Time(), v.getLeaveFlights()
						.get(size - 1).getArv_Time());

		int depTicketLeft = Integer.MAX_VALUE;
		float totalPrice = 0;

		for (int i = 0; i < size; i++) {
			// System.out.println(i);
			Flight f = v.getLeaveFlights().get(i);
			int coachLeft = f.getCoach_Left();
			int firstLeft = f.getFirstClass_Left();
			if (coachLeft != 0) {
				if (coachLeft < depTicketLeft) {
					depTicketLeft = coachLeft;
					// System.out.println(f.getCoach_Price());
				}
				totalPrice += f.getCoach_Price();
			} else if (firstLeft != 0) {
				if (firstLeft < depTicketLeft) {
					depTicketLeft = firstLeft;
				}
				totalPrice += f.getFirstClass_Price();
			}
			// convertTime
			// System.out.println(f.getDep_Time()+" "+
			// timeSys.timeConverter(f.getDep_Time(), f.getDep_Code()));
			f.setDep_Time_Local(timeSys.timeConvertoLocal(f.getDep_Time(),
					f.getDep_Code()));
			f.setArv_Time_Local(timeSys.timeConvertoLocal(f.getArv_Time(),
					f.getArv_Code()));

		}
		// System.out.println("-----"+totalPrice);
		v.setTotalPrice((float) Math.round(totalPrice * 1000) / 1000);
		v.setTicketLeft(depTicketLeft);

		// System.out.println(time);
		// v.reverseFlights();
		v.setTotalTime(time);
		v.setStopover(size - 1);

		return v;

	}

	/**
	 * 
	 * Check search result.
	 * 
	 * Return number of seats left of a certain flight.
	 * 
	 * 
	 * @param airplane_no
	 *            Number of a airplane
	 * @param seat_type
	 *            First class seat of Coach seat
	 * @param sold_num
	 *            Number of seats that has been sold out
	 * @return Return number of seats left in a flight and -1 if there is no
	 *         seat available.
	 */
	private int checkLeftseats(String airplane_no, String seat_type,
			int sold_num) {

		for (Airplane airplane : airplanes) {
			if (airplane.getModel().equals(airplane_no))
				if (seat_type.equals("Coach"))
					return airplane.getCoachSeats() - sold_num;
				else
					return airplane.getFirstClassSeats() - sold_num;
		}

		return -1;

	}

	/**
	 * lock Database
	 */
	public void lockDB() {
		Rs.lock("Team03");
	}

	/**
	 * unlock Database
	 */
	public void unlockDB() {
		Rs.unlock("Team03");
	}

}