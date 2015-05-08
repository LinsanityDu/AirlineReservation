package model;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * FlightNode class used for building a flight tree.
 *
 */
public class FlightNode {
	
     private FlightNode parent;
     private List<FlightNode> children;
     private Flight flight;
     
     /**
      * 
      * Constructor of the flight node
      * 
      * 
      */
     public FlightNode(){
    	 children = new ArrayList<FlightNode>(); 	 
     }
     
     /**
      * 
      * Add a child to the flight node
      * 
      * @param flightNode A flight node needs to be added as a childed
      */
	 public void addChildren(FlightNode flightNode){
	     children.add(flightNode);
	 }
	 
	 
	 /**
	  * 
	  * set the parent of the flight node
	  * 
	  * @param flightNode a flight node that needs to be added a parent
	  */
	 public void setParent(FlightNode flightNode){
	     parent = flightNode;
	 }
	 
	 /**
	  * 
	  * get the parent of the flight node
	  * 
	  * 
	  * @return parent of the flight node
	  */
	 public FlightNode getParent(){
	     return parent;
	 }
	 
	 /**
	  * 
	  * get the Children of the flight node
	  * 
	  * 
	  * 
	  * @return a flight node
	  */
	 public List<FlightNode> getChildren(){
	     return children;
	 }
	 
	 /**
	  * 
	  * set the fight object for this flight node
	  * 
	  * @param flight flight object that needs to be set to this flight node 
	  */
	 public void setFlight(Flight flight){
		 this.flight = flight;
	 }
	 
	 /**
	  * 
	  * Get the flight object linked to this flight node
	  * 
	  * @return flight object
	  */
	 public Flight getFlight(){
		 return this.flight;
	 }
	 
	
}
