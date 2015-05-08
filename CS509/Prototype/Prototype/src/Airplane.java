
public class Airplane {
	
	private String Manufacturer;  
    private String Model;  
    private int FirstClassSeats;  
    private int CoachSeats;

    
    public String getManufacturer() {  
        return Manufacturer;  
    }  
    public void setManufacturer(String Manufacturer) {  
        this.Manufacturer = Manufacturer;  
    }  
    public String getModel() {  
        return Model;  
    }  
    public void setModel(String Model) {  
        this.Model = Model;  
    }  
    public int getFirstClassSeats() {  
        return FirstClassSeats;  
    }  
    public void setFirstClassSeats(int FirstClassSeats) {  
        this.FirstClassSeats = FirstClassSeats;  
    }  
    public int getCoachSeats() {  
        return CoachSeats;  
    }  
    public void setCoachSeats(int CoachSeats) {  
        this.CoachSeats = CoachSeats;  
    } 
    
    @Override  
    public String toString(){  
        return this.Manufacturer+" | "+this.Model+" | "+this.FirstClassSeats+" | "+this.CoachSeats;  
    } 
}
