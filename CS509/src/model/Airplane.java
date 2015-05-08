package model;

/**
 * 
 * Airplane class
 * 1. Manufacturer
 * 2. Model
 * 3. FirstClassSeats
 * 4. CoachSeats
 *
 */
public class Airplane {
	
	private String Manufacturer;  
    private String Model;  
    private int FirstClassSeats;  
    private int CoachSeats;

    /**
     * 
     * Get the manufacture of the airplane
     * 
     * @return  manufacture of the airplane
     */
    public String getManufacturer() {  
        return Manufacturer;  
    }  
    
    /**
     * Set the manufacture of the airplane
     * 
     * 
     * @param Manufacturer the manufacture of the airplane
     */
    public void setManufacturer(String Manufacturer) {  
        this.Manufacturer = Manufacturer;  
    }  
    
    /**
     * 
     * Get the airplane model
     * 
     * @return airplane model
     */
    public String getModel() {  
        return Model;  
    }  
    
    /**
     * 
     * Set the airplane model
     * 
     * @param Model airplane model
     */
    public void setModel(String Model) {  
        this.Model = Model;  
    }  
    
    /**
     * 
     * Get the number of first class seats
     * 
     * @return number of first class seats
     */
    public int getFirstClassSeats() {  
        return FirstClassSeats;  
    }  
    
    /**
     * 
     * Set the number of first class seats
     * 
     * 
     * @param FirstClassSeats number of first class seats
     */
    public void setFirstClassSeats(int FirstClassSeats) {  
        this.FirstClassSeats = FirstClassSeats;  
    }  
    
    /**
     * 
     * get the number of coach seats
     * 
     * @return the number of coach seats
     */
    public int getCoachSeats() {  
        return CoachSeats;  
    } 
    
    /**
     * 
     * Set the number of coach seats
     * 
     * 
     * @param CoachSeats number of coach seats
     */
    public void setCoachSeats(int CoachSeats) {  
        this.CoachSeats = CoachSeats;  
    } 
    
    
    /**
     * 
     * Return the string format of the airplane
     * 
     */
    @Override  
    public String toString(){  
        return this.Manufacturer+" | "+this.Model+" | "+this.FirstClassSeats+" | "+this.CoachSeats;  
    } 
}
