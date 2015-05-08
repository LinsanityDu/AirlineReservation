import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.apache.log4j.Logger; 
import org.apache.log4j.PropertyConfigurator;

public class Test {
	 private static Logger logger = Logger.getLogger(Test.class);  
	 
    public static void main(String args[]) {
    	PropertyConfigurator.configure("log4j.properties");
    	System.out.println("Welcome to Bluefly Airline!");
        logger.info("System: " + "Welcome to Bluefly Airline");
        
        DOMParser p = new DOMParser();
        List<Airport> airports = p.getAirports();
        
        for (Airport airport : airports) {

            System.out.println(airport.toString());
        }

        List<Airplane> airplanes = p.getAirplanes();

        for (Airplane airplane : airplanes) {

            System.out.println(airplane.toString());
        }
        System.out.println("Please enter the departing airport:");
        logger.info("System: " + "Please enter the departing airport:"); 
        Scanner departScan = new Scanner(System.in);
        String departAirport = departScan.nextLine();
        logger.info("Client: "+departAirport);
        
        System.out.println("Please enter the arriving airport:");
        logger.info("System: " + "Please enter the arriving airport:");
        Scanner arriveScan = new Scanner(System.in);
        String arriveAirport = arriveScan.nextLine();
        logger.info("Client: " + arriveAirport);
        
        System.out
                .println("Please enter the departing date(Format: YYYY_MM_DD; Valid Date: 2015_05_07 - 2015_05_18):");
        logger.info("System: " + 
        		"Please enter the departing date(Format: YYYY_MM_DD; Valid Date: 2015_05_07 - 2015_05_18):");
        Scanner dateScan = new Scanner(System.in);
        String departDate = dateScan.nextLine();
        logger.info("Client: "+ departDate);
        
        List<Flight> flights = p.search("Team03", departAirport, arriveAirport,
                departDate);
        int index = 0;
        
        System.out.println("These are the searching results:");
        logger.info("System: " + "These are the searching results:"); 
        for (Flight flight : flights) {
            System.out.println("\nSelection " + ++index);
            System.out.println(flight.toString());
        }
        for (Flight flight : flights) {
        	logger.info("\nSystem: Selection " + ++index);
        	logger.info("System: " + flight.toString());
        }
        
        if (index == 0) {
            System.out.println("Flight not found!");
            logger.info("System: " + "Flight not found!");
        } else {
            List<Order> orders = new ArrayList<Order>();
            System.out
                    .println("Which one do you want to buy?(Please input 1,2,3...etc; 0 exit)");
            logger.info("System: " + 
            		"Which one do you want to buy?(Please input 1,2,3...etc; 0 exit)");
            Scanner stdin = new Scanner(System.in);
            int re = stdin.nextInt();
            logger.info("Client: " + re);
            if (re != 0) {
                Flight f = flights.get(re - 1);
//                System.out
//                        .println("What kind of ticket do you want to buy?(0: FirstClass, 1: Coach)");
//                logger.info("System: " + "What kind of ticket do you want to buy?(0: FirstClass, 1: Coach)");
                int type = stdin.nextInt();
                logger.info("Client: " + type);
                System.out.println("How many tickets do you want to buy?");
                logger.info("System: " + "How many tickets do you want to buy?");
                int num = stdin.nextInt();
                logger.info("Client: " + num);
                Order order = new Order(f.getNumber(), f,
                        (type == 0) ? "FirstClass" : "Coach", num);
                orders.add(order);
                System.out.println("Purchase Successful!\n");
                logger.info("System: " + "Purchase Successful!\n");
                p.buyTicket(orders);
            }

            System.out.println("The updated flight information:\n");
            logger.info("System: " + "The updated flight information:");
            List<Flight> updateFlights = p.search("Team03", departAirport,
                    arriveAirport, departDate);
            Flight updateFlight = updateFlights.get(re - 1);
            System.out.println(updateFlight.toString());
            logger.info("System: " + updateFlight + "\n");

        }

    }

}
