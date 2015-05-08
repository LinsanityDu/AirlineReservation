package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.Airport;
import controller.ReservationSys;

public class Test {
	public static void main(String args[]){
	    System.out.println("Welcome to Bluefly Airline!");
	    
		ReservationSys p = new ReservationSys();
		//List<Airport> airports = p.getAirports();
		/*
		for(Airport airport:airports){
			
			System.out.println(airport.toString());
		}
		*/
		//List<Airplane> airplanes = p.getAirplanes();
		
		/*
		for(Airplane airplane:airplanes){
			
			System.out.println(airplane.toString());
		}
		*/
//		System.out.println("Please enter the departing airport:");
//		Scanner departScan = new Scanner(System.in);
//		String departAirport = departScan.nextLine();
//		
//		System.out.println("Please enter the arriving airport:");
//		Scanner arriveScan = new Scanner(System.in);
//		String arriveAirport = arriveScan.nextLine();
//		
//		System.out.println("Please enter the departing date(Format: YYYY_MM_DD; Valid Date: 2015_05_07 - 2015_05_18):");
//		Scanner dateScan = new Scanner(System.in);
//		String departDate = dateScan.nextLine();
//		
//		List<Flight> flights = p.search("Team03",departAirport,departDate);
//		int index = 0;
//		
//		System.out.println("These are the searching results:");
//		for(Flight flight:flights) {
//			System.out.println("\nSelection "+ ++index);
//			System.out.println(flight.toString());
//		}
//		
//		if (index == 0) {
//			System.out.println("Flight not found!");
//		} else {
//			List<Order> orders = new ArrayList<Order>();
//			System.out.println("Which one do you want to buy?(Please input 1,2,3...etc; 0 exit)");
//			Scanner stdin = new Scanner(System.in); 
//			int re = stdin.nextInt();
//			if (re != 0){
//				Flight f = flights.get(re-1);
//				System.out.println("What kind of ticket do you want to buy?(0: FirstClass, 1: Coach)");
//				int type = stdin.nextInt();
//				System.out.println("How many tickets do you want to buy?");
//				int num = stdin.nextInt();
//				Order order = new Order(f.getNumber(), f, (type == 0)?"FirstClass":"Coach", num);
//				orders.add(order);
//			    p.buyTicket(orders);
//			}
//			
//			System.out.println("The updated flight information:\n");
//			List<Flight> updateFlights = p.search("Team03",departAirport,departDate);
//			Flight updateFlight = updateFlights.get(re-1);
//			System.out.println(updateFlight.toString());
//			
//		}
		
		for(Airport airport:p.getAirports()){
			System.out.println(airport.toString());
		}
			
		
		
	}

}
