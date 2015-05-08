package controller;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

import model.Flight;
import model.FlightNode;
import model.Voyage;

/**
 * 
 * Build a tree of flight used for finding path with stop overs.
 *
 */
public class FlightTree {

    private FlightNode root;
    private int num;
    private ReservationSys reservSys;
	private String depCode;
	private String arvCode;
	private String date;
	private List<Voyage> voyages;
	private Hashtable<String,List<Flight>> storage;
    private boolean isDepart; 
    private TimeSys timeSys;
	
    /**
     * Initiate flight tree
     * 
     * @param isDepart Identify if the node is departure flight
     */
	public FlightTree(boolean isDepart){
		root = new FlightNode();
		num = 0;
        voyages = new ArrayList<Voyage>();
		reservSys = ReservationSys.getInstance();
		timeSys = TimeSys.getInstance();
		storage = new Hashtable<String, List<Flight>>();
        this.isDepart = isDepart;
	}
	
	
	/**
	 * 
	 * Create a flight tree base on one search operation
	 * 
	 * @param num Number of flights
	 * @param dep Departure airport
	 * @param arv Arrival airport
	 * @param date Departure date
	 * @param isDepart Identify the departure status
	 * @param arvTime Arrival time of the selected flight
	 */
	public void createTree(int num, String dep, String arv, String date, boolean isDepart, String arvTime){
		//long s = System.currentTimeMillis();
		
		
		depCode = dep;
		arvCode = arv;
		this.num = num;
		this.date = date;
		this.isDepart = isDepart;
		System.out.println("start");
		Flight r = new Flight();
		r.setArv_Code(depCode);
		r.setArv_Time(arvTime);
		//System.out.println(initTime(date));
		root.setFlight(r);
		build(root, 0, date);
	
		//long e = System.currentTimeMillis();
	
		
		//System.out.println(e-s+"ms");
	}
	
	
	/**
	 * 
	 * Voyage entrance
	 * 
	 * @return return the list of Voyage object used for parse the flight node tree.
	 */
	public List<Voyage> getVoyages(){
		recursTree(root);
		return voyages;
//		for(int i=0;i<voyages.size();i++){
//			Voyage v = voyages.get(i);
//			List<Flight> flights = v.getLeaveFlights();
//			for(Flight flight:flights){
//				System.out.println((flight.getDep_Code()+" "+flight.getDep_Time()+" "+flight.getArv_Code()+" "+flight.getArv_Time()));
//			}
//			System.out.println(v.getTotalTime()/60+"h "+v.getTotalTime()%60+"m");
//			System.out.println();
//		}
//		
//		System.out.println(voyages.size());
		
	}
	
	/**
	 * 
	 * Recurs the tree.
	 * 
	 * @param parent Parent node of a certain flight node
	 */
	private void recursTree(FlightNode parent){
		List<FlightNode> flightNodes = parent.getChildren();
		
		if (flightNodes == null)
			return;
		
		for(FlightNode flightNode:flightNodes) { 		
			Flight flight = flightNode.getFlight();
			if(flight.getArv_Code().equals(arvCode)){
				Voyage v = new Voyage();
				v.setDepCode(depCode);
				v.setArvCode(arvCode);
				
				int size = 0;
				
				do{
				if(flight.getAirplane() != null){
				   v.addLeaveFlight(flight);
				   size++;
				   //depTime = flight.getDep_Time();
				}
				flightNode = flightNode.getParent();
				if(flightNode != null)
				   flight = flightNode.getFlight();
				else 
				   flight = null;
				}while(flight != null);
				
				v.reverseFlights();
				
			    voyages.add(reservSys.completeVoyage(v, size));
			}
			else
				recursTree(flightNode);
			
		}	
		
	}
	
	/**
	 * 
	 * Build the tree with flight nodes.
	 * 
	 * @param parent
	 * @param level
	 * @param date
	 */
	private void build(FlightNode parent,int level, String date){
		
		if(level < num){
		   List<Flight> flights;
		
		   flights = storage.get(parent.getFlight().getArv_Code()+date);
		   if (flights == null){
		      flights = reservSys.search("Team03", parent.getFlight().getArv_Code(), date);
		      storage.put(parent.getFlight().getArv_Code()+date, flights);   
		   }
		   
		   for(Flight flight:flights) {	
			   
			   //System.out.println(parent.getFlight().getArv_Time());
			  if((level == 0 && isDepart) || (!isDepart && timeSys.returnisSuitable(parent.getFlight().getArv_Time(), flight.getDep_Time())) || timeSys.isSuitable(parent.getFlight().getArv_Time(), flight.getDep_Time())){
				  if(flight.getCoach_Left() + flight.getFirstClass_Left() != 0){
				     FlightNode flightNode = new FlightNode();
				     flightNode.setFlight(flight);
				     parent.addChildren(flightNode);
				     flightNode.setParent(parent);
				     if(!flight.getArv_Code().equals(arvCode)){
					    date = timeSys.formatTime(flight.getArv_Time());
				        build(flightNode, level+1, date);
			            build(flightNode, level+1, timeSys.increaseOneday(date));
			         }
				  }
			  }
			  

    	   }
		  }
	}
	

	
}
