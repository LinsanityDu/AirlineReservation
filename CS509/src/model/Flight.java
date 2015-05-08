package model;

/**
 * 
 * Flight class
 *  
 *
 */
public class Flight {
	
	private String Airplane;  
    private int FlightTime;
    private String Number;
    private String Dep_Code;
    private String Dep_Time;
    private String Dep_Time_Local;
    private String Arv_Code;
    private String Arv_Time;
    private String Arv_Time_Local;
    private float FirstClass_Price; 
    private int FirstClass_SoldNum;
    private int FirstClass_Left;
    private float Coach_Price;
    private int Coach_SoldNum;
    private int Coach_Left;

    /**
     * Constructor of flight object
     * 
     */
    public Flight(){
    	
    }
    
    /**
     * 
     * Get the airplane of the flight
     * 
     * @return Airplane name
     */
    public String getAirplane() {  
        return Airplane;  
    }  
    /**
     * 
     * Set name of the airplane of the flight
     * 
     * @param Airplane name of the airplane of the flight
     */
    public void setAirplane(String Airplane) {  
        this.Airplane = Airplane;  
    }  

    /**
     * 
     * Get the time of the flight
     * 
     * @return flight time
     */
    public int getFlightTime() {  
        return FlightTime;  
    }  
    public void setFlightTime(int FlightTime) {  
        this.FlightTime = FlightTime;  
    }
    
    /**
     * 
     * Get the number of the flight
     * 
     * @return Number of flight
     */
    public String getNumber() {  
        return Number;  
    }  
    /**
     * 
     * Set the number of the flight 
     * 
     * @param Number Flight number
     */
    public void setNumber(String Number) {  
        this.Number = Number;  
    }
    
    /**
     * Get the departure time of the flight
     * 
     * @return Departure time
     */
    public String getDep_Time() {  
        return Dep_Time;  
    }  
    
    /**
     * 
     * Set the departure time
     * 
     * @param Dep_Time Departure time
     */
    public void setDep_Time(String Dep_Time) {  
        this.Dep_Time = Dep_Time;  
    }
    
    /**
     * Get the departure local time of the flight
     * 
     * @return Departure local time
     */
    public String getDep_Time_Local() {  
        return Dep_Time_Local;  
    }  
    
    /**
     * 
     * Set the departure local time
     * 
     * @param Dep_Time Departure local time
     */
    public void setDep_Time_Local(String Dep_Time_Local) {  
        this.Dep_Time_Local = Dep_Time_Local;  
    }
    
    
    
    /**
     * 
     * Set the departure airport in string format
     * 
     * @return departure airport in string format
     */
    public String getDep_Code() {  
        return Dep_Code;  
    } 
    /**
     * 
     * Set the Departure airport
     * 
     * @param Dep_Code Departure airport
     */
    public void setDep_Code(String Dep_Code) {  
        this.Dep_Code = Dep_Code;  
    }
    /**
     * 
     * get arrival time
     * 
     * @return Arrival time
     */
    public String getArv_Time() {  
        return Arv_Time;  
    } 
    
    /**
     * 
     * set the arrival time
     * 
     * @param Arv_Time arrival time
     */
    public void setArv_Time(String Arv_Time) {  
        this.Arv_Time = Arv_Time;  
    }
    
    /**
     * 
     * get arrival local time
     * 
     * @return Arrival local time
     */
    public String getArv_Time_Local() {  
        return Arv_Time_Local;  
    } 
    
    /**
     * 
     * set the arrival local time
     * 
     * @param Arv_Time arrival local time
     */
    public void setArv_Time_Local(String Arv_Time_Local) {  
        this.Arv_Time_Local = Arv_Time_Local;  
    }
    
    /**
     * 
     * Get the arrival time
     * 
     * @return arrival time
     */
    public String getArv_Code() {  
        return Arv_Code;  
    }
    
    
    /**
     * 
     * Set the arrival airport
     * 
     * @param Arv_Code arrival airport 
     */
    public void setArv_Code(String Arv_Code) {  
        this.Arv_Code = Arv_Code;  
    }
    
    /**
     * 
     * get the price of the fist class seat
     * 
     * 
     * @return  price of the fist class seat
     */
    public float getFirstClass_Price() {  
        return FirstClass_Price;  
    }  
    /**
     * 
     * Set the price of the first class seat
     * 
     * @param FirstClass_Price price of the first class seat
     */
    public void setFirstClass_Price(float FirstClass_Price) {  
        this.FirstClass_Price = FirstClass_Price;  
    }  
    
    
    /**
     * 
     * get the number of seats that has been sold out.
     * 
     * @return the number of seats that has been sold out.
     */
    public int getFirstClass_SoldNum() {  
        return FirstClass_SoldNum;  
    }
    
    /**
     * Set number of seats sold in the first class.
     * 
     * @param FirstClass_left number of seats sold in the first class.
     */
    public void setFirstClass_SoldNum(int FirstClass_left) {  
        this.FirstClass_SoldNum = FirstClass_left;  
    }
    
    /**
     * 
     *  Get number of seats left in the first class.
     * 
     * @return number of seats left in the first class.
     */
    public int getFirstClass_Left() {  
        return FirstClass_Left;  
    } 
    
    /**
     * Set number of seats left in the first class.
     * 
     * @param FirstClass_Left number of seats left in the first class.
     */
    public void setFirstClass_Left(int FirstClass_Left) {  
        this.FirstClass_Left = FirstClass_Left;  
    }  
    
    /**
     * 
     * Get the price of the coach seat
     * 
     * @return price of the coach seat
     */
    public float getCoach_Price() {  
        return Coach_Price;  
    } 
    
    
    /**
     * 
     * Set the price of the coach seat
     * 
     * @param Coach_Price of the coach seat
     */
    public void setCoach_Price(float Coach_Price) {  
        this.Coach_Price = Coach_Price;  
    } 
    
    /**
     * 
     * Get the number of Coach seats that have been sold 
     * 
     * @return number of Coach seats that have been sold 
     */
    public int getCoach_SoldNum() {  
        return Coach_SoldNum;  
    } 
    
    
    /**
     * 
     * Set the number of Coach seats that have been sold 
     * 
     * @param Coach_Num of Coach seats that have been sold 
     */
    public void setCoach_SoldNum(int Coach_Num) {  
        this.Coach_SoldNum = Coach_Num;  
    } 
    
    /**
     * 
     * Get the Number of coach seats left 
     * 
     * 
     * @return Number of coach seats left 
     */
    public int getCoach_Left() {  
        return Coach_Left;  
    } 
    
    
    /**
     * 
     * Set the Number of coach seats left 
     * 
     * @param Coach_Left Number of coach seats left
     */
    public void setCoach_Left(int Coach_Left) {  
        this.Coach_Left = Coach_Left;  
    } 
   
    @Override  
    public String toString(){  
        return "Airplane "+this.Airplane+" | FlightTime "+this.FlightTime+" | Number "+this.Number+"\nDep_Code "+this.Dep_Code+" | Dep_Time "+this.Dep_Time+"\nArv_Code"+this.Arv_Code+" | Arv_Time "+this.Arv_Time+"\nFirstClass_Price "+this.FirstClass_Price+" | FirstClass_SoldNum "+this.FirstClass_SoldNum+" | FirstClass_Left "+this.FirstClass_Left+" | Coach_Price "+this.Coach_Price+" | Coach_SoldNum "+this.Coach_SoldNum+" | Coach_Left "+this.Coach_Left+"\n";  
    }  
}
