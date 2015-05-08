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
 * Contains functions used for parsing the XML file fetched from data base
 *
 */
public class DOMParser {
	
	
	private DocumentBuilderFactory builderFactory; 
	private List<Airport> airports;
	private List<Airplane> airplanes;

	/**
	 * Entrance of DOM parser.
	 */
	public DOMParser(){
		builderFactory = DocumentBuilderFactory.newInstance();	
	}
	
	
	/**
	 * Load and parse XML into DOM
	 * 
	 * @param Xml string listing all Flights
	 * @return return the Document object after parsing the XML string
	 */
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
	
	/**
	 * Parse XML and get airports information
	 * 
	 * @return list contains all of Airport object created after parsing the XML string.
	 */
	public List<Airport> getAirports() {
		List<Airport> list = new ArrayList<Airport>();
		AccessDB Rs = new AccessDB();
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

	/**
	 * 
	 * Parse XML and get the airplanes information
	 * 
	 * @return list that contains all of Airplane objects created after parsing the XML string.
	 */
	public List<Airplane> getAirplanes() {
		List<Airplane> list = new ArrayList<Airplane>();
		AccessDB Rs = new AccessDB();
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



}