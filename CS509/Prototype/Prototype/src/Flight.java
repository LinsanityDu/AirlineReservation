
public class Flight {
	
	private String Airplane;  
    private int FlightTime;
    private String Number;
    private String Dep_Code;
    private String Dep_Time;
    private String Arv_Code;
    private String Arv_Time;
    private float FirstClass_Price; 
    private int FirstClass_SoldNum;
    private int FirstClass_Left;
    private float Coach_Price;
    private int Coach_SoldNum;
    private int Coach_Left;
    
    
    
    public String getAirplane() {  
        return Airplane;  
    }  
    public void setAirplane(String Airplane) {  
        this.Airplane = Airplane;  
    }  

    public int getFlightTime() {  
        return FlightTime;  
    }  
    public void setFlightTime(int FlightTime) {  
        this.FlightTime = FlightTime;  
    }
    public String getNumber() {  
        return Number;  
    }  
    public void setNumber(String Number) {  
        this.Number = Number;  
    }
    public String getDep_Time() {  
        return Dep_Time;  
    }  
    public void setDep_Time(String Dep_Time) {  
        this.Dep_Time = Dep_Time;  
    }
    public String getDep_Code() {  
        return Dep_Code;  
    }  
    public void setDep_Code(String Dep_Code) {  
        this.Dep_Code = Dep_Code;  
    }
    public String getArv_Time() {  
        return Arv_Time;  
    }  
    public void setArv_Time(String Arv_Time) {  
        this.Arv_Time = Arv_Time;  
    }
    public String getArv_Code() {  
        return Arv_Code;  
    }  
    public void setArv_Code(String Arv_Code) {  
        this.Arv_Code = Arv_Code;  
    }
    public float getFirstClass_Price() {  
        return FirstClass_Price;  
    }  
    public void setFirstClass_Price(float FirstClass_Price) {  
        this.FirstClass_Price = FirstClass_Price;  
    }  
    public int getFirstClass_SoldNum() {  
        return FirstClass_SoldNum;  
    }  
    public void setFirstClass_SoldNum(int FirstClass_left) {  
        this.FirstClass_SoldNum = FirstClass_left;  
    }
    public int getFirstClass_Left() {  
        return FirstClass_Left;  
    }  
    public void setFirstClass_Left(int FirstClass_Left) {  
        this.FirstClass_Left = FirstClass_Left;  
    }  
    public float getCoach_Price() {  
        return Coach_Price;  
    }  
    public void setCoach_Price(float Coach_Price) {  
        this.Coach_Price = Coach_Price;  
    } 
    public int getCoach_SoldNum() {  
        return Coach_SoldNum;  
    }  
    public void setCoach_SoldNum(int Coach_Num) {  
        this.Coach_SoldNum = Coach_Num;  
    } 
    public int getCoach_Left() {  
        return Coach_Left;  
    }  
    public void setCoach_Left(int Coach_Left) {  
        this.Coach_Left = Coach_Left;  
    } 
    @Override  
    public String toString(){  
        return "Airplane "+this.Airplane+" | FlightTime "+this.FlightTime+" | Number "+this.Number+"\nDep_Code "+this.Dep_Code+" | Dep_Time "+this.Dep_Time+"\nArv_Code"+this.Arv_Code+" | Arv_Time "+this.Arv_Time+"\nFirstClass_Price "+this.FirstClass_Price+" | FirstClass_SoldNum "+this.FirstClass_SoldNum+" | FirstClass_Left "+this.FirstClass_Left+" | Coach_Price "+this.Coach_Price+" | Coach_SoldNum "+this.Coach_SoldNum+" | Coach_Left "+this.Coach_Left+"\n";  
    }  
}
