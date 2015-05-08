package model;

/**
 * Airport class
 * 1. Code: string format of airport information
 * 2. Name: name of the airpoart
 * 3. Latitude
 * 4. Longitude
 */
public class Airport {  
 
	private String Code;  
    private String Name;  
    private float Latitude;  
    private float Longitude;
    
    /**
     * 
     * Get the string format of the airport
     * 
     * @return Get the string format of the airport
     */
    public String getCode() {  
        return Code;  
    }  
    
    /**
     * Set the string format of the airport
     * 
     * @param Code String format of the airport
     */
    public void setCode(String Code) {  
        this.Code = Code;  
    } 
    
    /**
     * Get the name of the airport
     * 
     * @return Name of the airport
     */
    public String getName() {  
        return Name;  
    }  
    
    /**
     * 
     * Set the name of the airport
     * 
     * @param Name Name of the airport
     */
    public void setName(String Name) {  
        this.Name = Name;  
    }  
    /**
     * 
     * Get the latitude of the airport
     * 
     * @return latitude of the airport
     */
    public float getLatitude() {  
        return Latitude;  
    }  
    
    
    /**
     * 
     * Set the latitude of the airport
     * 
     * @param Latitude latitude of the airport
     */
    public void setLatitude(float Latitude) {  
        this.Latitude = Latitude;  
    }  
 
    /**
     * Get the longitude of the airport
     * 
     * @return longitude of the airport
     */
    public float getLongitude() {  
        return Longitude;  
    }  
    
    /**
     * 
     * set the longitude of the airport
     * 
     * @param Longitude longitude of the airport
     */
    public void setLongitude(float Longitude) {  
        this.Longitude = Longitude;  
    } 
    
    
    /**
     * Return String structure of the airport
     * 
     */
    @Override  
    public String toString(){  
        return this.Code+" | "+this.Name+" | "+this.Latitude+" | "+this.Longitude;  
    }  
}  