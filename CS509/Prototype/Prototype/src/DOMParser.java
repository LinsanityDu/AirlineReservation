import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DOMParser {
	DocumentBuilderFactory builderFactory = DocumentBuilderFactory
			.newInstance();

	// Load and parse XML into DOM
	private Document parse(String Xml) {
		Document document = null;
		try {
			InputStream input = new ByteArrayInputStream(Xml.getBytes("UTF-8"));
			// DOM parser instance
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			// parse an XML into a DOM tree
			document = builder.parse(input);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return document;
	}

	public List<Airport> getAirports() {
		List<Airport> list = new ArrayList<Airport>();
		ReservationSystem Rs = new ReservationSystem();
		String Xml = Rs.getAirports("Team03");
		Document document = parse(Xml);
		// get root element
		Element rootElement = document.getDocumentElement();
		NodeList nodeList = rootElement.getElementsByTagName("Airport");
		if (nodeList != null) {
			for (int i = 0; i < nodeList.getLength(); i++) {
				Airport airport = new Airport();
				Element element = (Element) nodeList.item(i);
				String Code = element.getAttribute("Code");
				airport.setCode(Code);
				String Name = element.getAttribute("Name");
				airport.setName(Name);
				NodeList childNodes = element.getChildNodes();
				// System.out.println("*****"+childNodes.getLength());
				for (int j = 0; j < childNodes.getLength(); j++) {
					if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
						if ("Latitude".equals(childNodes.item(j).getNodeName())) {
							airport.setLatitude(Float.parseFloat(childNodes
									.item(j).getFirstChild().getNodeValue()));
						} else if ("Longitude".equals(childNodes.item(j)
								.getNodeName())) {
							airport.setLongitude(Float.parseFloat(childNodes
									.item(j).getFirstChild().getNodeValue()));
						}
					}
				}
				list.add(airport);
				// System.out.println(airport.toString());
			}
		}
		return list;
	}

	public List<Airplane> getAirplanes() {
		List<Airplane> list = new ArrayList<Airplane>();
		ReservationSystem Rs = new ReservationSystem();
		String Xml = Rs.getAirplanes("Team03");
		Document document = parse(Xml);
		// get root element
		Element rootElement = document.getDocumentElement();
		NodeList nodeList = rootElement.getElementsByTagName("Airplane");
		if (nodeList != null) {
			for (int i = 0; i < nodeList.getLength(); i++) {
				Airplane airplane = new Airplane();
				Element element = (Element) nodeList.item(i);
				String Manufacturer = element.getAttribute("Manufacturer");
				airplane.setManufacturer(Manufacturer);
				String Model = element.getAttribute("Model");
				airplane.setModel(Model);
				NodeList childNodes = element.getChildNodes();
				// System.out.println("*****"+childNodes.getLength());
				for (int j = 0; j < childNodes.getLength(); j++) {
					if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
						if ("FirstClassSeats".equals(childNodes.item(j)
								.getNodeName())) {
							airplane.setFirstClassSeats(Integer
									.parseInt(childNodes.item(j)
											.getFirstChild().getNodeValue()));
						} else if ("CoachSeats".equals(childNodes.item(j)
								.getNodeName())) {
							airplane.setCoachSeats(Integer.parseInt(childNodes
									.item(j).getFirstChild().getNodeValue()));
						}
					}
				}
				list.add(airplane);
				// System.out.println(airport.toString());
			}
		}
		return list;
	}

	public List<Flight> search(String ticketAgency, String dep, String arv, String date) {
		List<Flight> list = new ArrayList<Flight>();
		ReservationSystem Rs = new ReservationSystem();
		String Xml = Rs.searchDep(ticketAgency, dep, date);
		Document document = parse(Xml);
		// get root element
		Element rootElement = document.getDocumentElement();
		NodeList nodeList = rootElement.getElementsByTagName("Flight");
		if (nodeList != null) {
			for (int i = 0; i < nodeList.getLength(); i++) {
				Flight flight = new Flight();
				boolean isDes = false;
				Element element = (Element) nodeList.item(i);
				flight.setAirplane(element.getAttribute("Airplane"));
				flight.setFlightTime(Integer.parseInt(element.getAttribute("FlightTime")));
				flight.setNumber(element.getAttribute("Number"));
				
				//get arrival element
				NodeList ArvNodes = element.getElementsByTagName("Arrival").item(0).getChildNodes();
				for (int j = 0; j < ArvNodes.getLength(); j++) {
					if (ArvNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
						if ("Code".equals(ArvNodes.item(j)
								.getNodeName())) {				

							if (arv.equals(ArvNodes.item(j).getFirstChild().getNodeValue())){
								isDes = true;
					            flight.setArv_Code(ArvNodes.item(j).getFirstChild().getNodeValue());
							}else
								break;
							
						} else if ("Time".equals(ArvNodes.item(j)
								.getNodeName())) {							
							flight.setArv_Time(ArvNodes
									.item(j).getFirstChild().getNodeValue());
						}
					}
				}
				
				//check Destination's correctness
				if (!isDes)
					continue;
				
				//get departure element
				NodeList DepNodes = element.getElementsByTagName("Departure").item(0).getChildNodes();
				for (int j = 0; j < DepNodes.getLength(); j++) {
					if (DepNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
						if ("Code".equals(DepNodes.item(j)
								.getNodeName())) {
							
							flight.setDep_Code(DepNodes
									.item(j).getFirstChild().getNodeValue());
							
						} else if ("Time".equals(DepNodes.item(j)
								.getNodeName())) {
							
							flight.setDep_Time(DepNodes
									.item(j).getFirstChild().getNodeValue());
						}
					}
				}
	            
				//get price of seating
				NodeList SeatNodes = element.getElementsByTagName("Seating").item(0).getChildNodes();
				for (int j = 0; j < SeatNodes.getLength(); j++) {
					if (SeatNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
						if ("FirstClass".equals(SeatNodes.item(j)
								.getNodeName())) {
							
							flight.setFirstClass_Price(Float.parseFloat(((Element)SeatNodes
									.item(j)).getAttribute("Price").substring(1).replaceAll(",", "")));
							
							flight.setFirstClass_SoldNum(Integer.parseInt(SeatNodes
									.item(j).getFirstChild().getNodeValue()));
							
							flight.setFirstClass_Left(checkLeftseats(flight.getAirplane(),"FirstClass",flight.getFirstClass_SoldNum()));
							
						} else if ("Coach".equals(SeatNodes.item(j)
								.getNodeName())) {
							
							flight.setCoach_Price(Float.parseFloat(((Element)SeatNodes
									.item(j)).getAttribute("Price").substring(1).replaceAll(",", "")));
							
							flight.setCoach_SoldNum(Integer.parseInt(SeatNodes
									.item(j).getFirstChild().getNodeValue()));
							
							flight.setCoach_Left(checkLeftseats(flight.getAirplane(),"Coach",flight.getCoach_SoldNum()));
						}
					}
				}
				
				list.add(flight);
			}
		}
		return list;

	}
	
	public void buyTicket(List<Order> orders){
		String xmlFile = "";
		for (Order order:orders) {
			Flight flight = order.getFlight();
			if (checkLeftseats(flight.getAirplane(), order.getNumber(),order.getNumber().equals("Coach")?flight.getCoach_SoldNum():flight.getFirstClass_SoldNum()) <= 0){
				System.out.println("Sorry, all the tickets are sold out!\n");
				return;
			}
			for (int i=0;i<order.getAmount();i++)
			    xmlFile += "<Flight number=\""+order.getNumber()+"\" seating=\""+order.getSeating()+"\" />";
		}
		xmlFile = "<Flights>"+xmlFile+"</Flights>";
		// System.out.println(xmlFile);
		ReservationSystem Rs = new ReservationSystem();
		Rs.lock("Team03");
		if (Rs.buyTickets("Team03", xmlFile)) {
			System.out.println("Purchase Successful!\n");
		}
		Rs.unlock("Team03");
		
	}
	
	private int checkLeftseats(String airplane_no, String seat_type, int sold_num){
		List<Airplane> airplanes = getAirplanes();
	    for(Airplane airplane:airplanes){
			if(airplane.getModel().equals(airplane_no))
				if(seat_type.equals("Coach"))
					return airplane.getCoachSeats()-sold_num;
                else 
                	return airplane.getFirstClassSeats()-sold_num;				
	    }
				
		return -1;
		
		
	}

}