package controller;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import model.Airplane;
import model.Flight;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * 
 * Organize parsed information from XML in readable structure
 * 
 *
 */
public class SaxParseFlight extends DefaultHandler{
	private List<Flight> flights = null;
	private Flight flight = null;
	private String preTag = null;
	private boolean isDep = true;
	private List<Airplane> airplanes = null;
	
	
	/**
	 * 
	 * Constructor of flight information list.
	 * 
	 * @param airplanes List of the Airplane objects.
	 */
	public SaxParseFlight(List<Airplane> airplanes){
		this.airplanes = airplanes;
	}
	
	/**
	 * 
	 * Get flight information from XML
	 * 
	 * Return a list of flight.
	 * 
	 * @param  xmlStream XML stream fetched from Database
	 * @param  airplanes List of the airplanes built after parsing the XML stream
	 * @return List of Flight object after parse the list of airplanes
	 * @throws Exception Exception
	 */
	public List<Flight> getFlights(InputStream xmlStream, List<Airplane> airplanes) throws Exception{
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		SaxParseFlight handler = new SaxParseFlight(airplanes);
		parser.parse(xmlStream, handler);
		return handler.getFlights();
	}
	
	/**
	 * Return flight list.
	 * 
	 * @return flight list.
	 */
	public List<Flight> getFlights(){
		return flights;
	}
	
	
	/**
	 * 
	 * Create flight list.
	 * 
	 */
	@Override
	public void startDocument() throws SAXException {
		flights = new ArrayList<Flight>();
	}

	
	/**
	 * 
	 * Process an input flight organize the output structure.
	 * 
	 */
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if("Flight".equals(qName)){
			flight = new Flight();
			flight.setAirplane(attributes.getValue(0));
			flight.setFlightTime(Integer.parseInt(attributes.getValue(1)));
			flight.setNumber(attributes.getValue(2));
		}else if("FirstClass".equals(qName)){
			flight.setFirstClass_Price(Float.parseFloat(attributes.getValue(0).substring(1).replaceAll(",", "")));
		}else if("Coach".equals(qName)){
			flight.setCoach_Price(Float.parseFloat(attributes.getValue(0).substring(1).replaceAll(",", "")));		
			}
		preTag = qName;
	}

	
	/**
	 * 
	 * End of processing flight element.
	 * 
	 */
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if("Flight".equals(qName)){
			flights.add(flight);
			flight = null;
		}
		preTag = null;
	}
	
	/**
	 * 
	 * Process flight text information.
	 * 
	 */
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if(preTag!=null){
			String content = new String(ch,start,length);
			if("Code".equals(preTag)){
				if(isDep)
				    flight.setDep_Code(content);
				else
					flight.setArv_Code(content);
			}else if("Time".equals(preTag)){
				if(isDep)
				    flight.setDep_Time(content);
				else
					flight.setArv_Time(content);				
				isDep = !isDep;
			}else if("FirstClass".equals(preTag)){
				flight.setFirstClass_SoldNum(Integer.parseInt(content));
				flight.setFirstClass_Left(checkLeftseats(flight.getAirplane(),"FirstClass",flight.getFirstClass_SoldNum()));
			}else if("Coach".equals(preTag)){
				flight.setCoach_SoldNum(Integer.parseInt(content));
				flight.setCoach_Left(checkLeftseats(flight.getAirplane(),"Coach",flight.getCoach_SoldNum()));
			}
			
		}
			
	}
	
	
	/**
	 * 
	 * Check search result.
	 * 
	 * Return number of seats left of a certain flight.
	 * 
	 * Return -1 if there is no seat available.
	 * 
	 * @param airplane_no Number of the airplane selected by the customer
	 * @param seat_type First class seat or Coach seat
	 * @param sold_num Number of seats that has been sold.
	 * @return Number of seats left in a flight and -1 if no seat available
	 */
	public int checkLeftseats(String airplane_no, String seat_type, int sold_num){
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

