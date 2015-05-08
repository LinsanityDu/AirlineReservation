public class Airport {  
 
	private String Code;  
    private String Name;  
    private float Latitude;  
    private float Longitude;
    
    
    public String getCode() {  
        return Code;  
    }  
    public void setCode(String Code) {  
        this.Code = Code;  
    }  
    public String getName() {  
        return Name;  
    }  
    public void setName(String Name) {  
        this.Name = Name;  
    }  
    public float getLatitude() {  
        return Latitude;  
    }  
    public void setLatitude(float Latitude) {  
        this.Latitude = Latitude;  
    }  
    public float getLongitude() {  
        return Longitude;  
    }  
    public void setLongitude(float Longitude) {  
        this.Longitude = Longitude;  
    } 
    
    @Override  
    public String toString(){  
        return this.Code+" | "+this.Name+" | "+this.Latitude+" | "+this.Longitude;  
    }  
}  