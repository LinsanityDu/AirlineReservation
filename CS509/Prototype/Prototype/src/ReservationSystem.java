/**
 * 
 */


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * @author blake
 *
 */
class QueryFactory {
	
	public static String getAirports(String ticketAgency) {
		return "?team=" + ticketAgency + "&action=list&list_type=airports";
	}
	
	public static String getAirplanes(String ticketAgency) {
		return "?team=" + ticketAgency + "&action=list&list_type=airplanes";
	}
	
	public static String searchDep(String ticketAgency, String dep, String date) {
		return "?team=" + ticketAgency + "&action=list&list_type=departing&airport=" + dep + "&day=" + date;
	}
		
	public static String lock (String ticketAgency) {
		return "team=" + ticketAgency + "&action=lockDB";
	}
	
	public static String unlock (String ticketAgency) {
		return "team=" + ticketAgency + "&action=unlockDB";
	}
	
	public static String buytickets (String ticketAgency, String xmlFile) {
		return "team=" + ticketAgency + "&action=buyTickets&flightData=" + xmlFile;
	}
}

public class ReservationSystem {
	private final String mUrlBase = "http://cs509.cs.wpi.edu:8181/CS509.server/ReservationSystem";
	
	/**
	 * Return an XML list of all the airports
	 * 
	 * Retrieve the list of airports available to the specified ticketAgency via HTTPGet of the server
	 * 
	 * @param ticketAgency identifies the ticket agency requesting the information
	 * @return xml string listing all airports
	 */
	public String getAirports (String ticketAgency) {
		
		URL url;
		HttpURLConnection connection;
		BufferedReader reader;
		String line;
		StringBuffer result = new StringBuffer();
		
	      try {
	          url = new URL(mUrlBase + QueryFactory.getAirports(ticketAgency));
	          connection = (HttpURLConnection) url.openConnection();
	          connection.setRequestMethod("GET");
	          connection.setRequestProperty("User-Agent", ticketAgency);
	          
	          int responseCode = connection.getResponseCode();
	          if ((responseCode >= 200) && (responseCode <= 299)) {
		          InputStream inputStream = connection.getInputStream();
		          String encoding = connection.getContentEncoding();
		          encoding = (encoding == null ? "URF-8" : encoding);
		          
		          reader = new BufferedReader(new InputStreamReader(inputStream));
		          while ((line = reader.readLine()) != null) {
		             result.append(line);
		          }
		          reader.close();
	          }
	       } catch (IOException e) {
	          e.printStackTrace();
	       } catch (Exception e) {
	          e.printStackTrace();
	       }
	       return result.toString();
	}
	
	/**
	 * Return an XML list of all the airplanes
	 * 
	 * Retrieve the list of airplanes available to the specified ticketAgency via HTTPGet of the server
	 * 
	 * @param ticketAgency identifies the ticket agency requesting the information
	 * @return xml string listing all airplanes
	 */
	public String getAirplanes (String ticketAgency) {
		
		URL url;
		HttpURLConnection connection;
		BufferedReader reader;
		String line;
		StringBuffer result = new StringBuffer();
		
	      try {
	          url = new URL(mUrlBase + QueryFactory.getAirplanes(ticketAgency));
	          connection = (HttpURLConnection) url.openConnection();
	          connection.setRequestMethod("GET");
	          connection.setRequestProperty("User-Agent", ticketAgency);
	          
	          int responseCode = connection.getResponseCode();
	          if ((responseCode >= 200) && (responseCode <= 299)) {
		          InputStream inputStream = connection.getInputStream();
		          String encoding = connection.getContentEncoding();
		          encoding = (encoding == null ? "URF-8" : encoding);
		          
		          reader = new BufferedReader(new InputStreamReader(inputStream));
		          while ((line = reader.readLine()) != null) {
		             result.append(line);
		          }
		          reader.close();
	          }
	       } catch (IOException e) {
	          e.printStackTrace();
	       } catch (Exception e) {
	          e.printStackTrace();
	       }
	       return result.toString();
	}
	
	/**
	 * Return an XML list of all the Flights
	 * 
	 * Retrieve the list of flights available to the specified ticketAgency, departure, arrival, date via HTTPGet of the server
	 * 
	 * @param ticketAgency identifies the ticket agency requesting the information
	 * @param dep identifies the departure airport
	 * @param date identifies the date of departure
	 * @return xml string listing all Flights
	 */
	public String searchDep (String ticketAgency, String dep, String date) {
		
		URL url;
		HttpURLConnection connection;
		BufferedReader reader;
		String line;
		StringBuffer result = new StringBuffer();
		
	      try {
	          url = new URL(mUrlBase + QueryFactory.searchDep(ticketAgency, dep, date));
	          connection = (HttpURLConnection) url.openConnection();
	          connection.setRequestMethod("GET");
	          connection.setRequestProperty("User-Agent", ticketAgency);
	          
	          int responseCode = connection.getResponseCode();
	          if ((responseCode >= 200) && (responseCode <= 299)) {
		          InputStream inputStream = connection.getInputStream();
		          String encoding = connection.getContentEncoding();
		          encoding = (encoding == null ? "URF-8" : encoding);
		          
		          reader = new BufferedReader(new InputStreamReader(inputStream));
		          while ((line = reader.readLine()) != null) {
		             result.append(line);
		          }
		          reader.close();
	          }
	       } catch (IOException e) {
	          e.printStackTrace();
	       } catch (Exception e) {
	          e.printStackTrace();
	       }
	       return result.toString();
	}
	
	
	public boolean lock (String ticketAgency) {
		URL url;
		HttpURLConnection connection;
		

		try {
			url = new URL(mUrlBase);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("User-Agent", ticketAgency);
			connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			
			String params = QueryFactory.lock(ticketAgency);
			
			connection.setDoOutput(true);
			DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
			writer.writeBytes(params);
			writer.flush();
			writer.close();
			
			int responseCode = connection.getResponseCode();
			System.out.println("\nSending 'POST' to lock database");
			System.out.println(("\nResponse Code : " + responseCode));
			
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			StringBuffer response = new StringBuffer();
			
			while ((line = in.readLine()) != null) {
				response.append(line);
			}
			in.close();
			
			System.out.println(response.toString());
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean unlock (String ticketAgency) {
		URL url;
		HttpURLConnection connection;
		

		try {
			url = new URL(mUrlBase);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("User-Agent", ticketAgency);
			connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			
			String params = QueryFactory.unlock(ticketAgency);
			
			connection.setDoOutput(true);
			DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
			writer.writeBytes(params);
			writer.flush();
			writer.close();
			
			int responseCode = connection.getResponseCode();
			System.out.println("\nSending 'POST' to unlock database");
			System.out.println(("\nResponse Code : " + responseCode));
			
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			StringBuffer response = new StringBuffer();
			
			while ((line = in.readLine()) != null) {
				response.append(line);
			}
			in.close();
			
			System.out.println(response.toString());
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean buyTickets (String ticketAgency, String xmlFile) {
		URL url;
		HttpURLConnection connection;
		

		try {
			url = new URL(mUrlBase);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("User-Agent", ticketAgency);
			connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			
			String params = QueryFactory.buytickets(ticketAgency, xmlFile);
			
			connection.setDoOutput(true);
			DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
			writer.writeBytes(params);
			writer.flush();
			writer.close();
			
			int responseCode = connection.getResponseCode();
			System.out.println("\nSending 'POST' to update database");
			System.out.println(("\nResponse Code : " + responseCode));
			
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			StringBuffer response = new StringBuffer();
			
			while ((line = in.readLine()) != null) {
				response.append(line);
			}
			in.close();
			
			System.out.println(response.toString());
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}
}
