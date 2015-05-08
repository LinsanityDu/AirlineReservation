package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

import model.Airport;

/**
 * 
 * The time System class -- singleton focus on time calculation
 *
 */
public class TimeSys {

	private Hashtable<String, Integer> time_zone;
	/**
	 * initial self-instance prepared for Singleton
	 */
	private static TimeSys instance = null;

	/**
	 * get TimeSys instance based on Singleton Pattern
	 * 
	 * @return TimeSys instance
	 */
	public static TimeSys getInstance() {
		if (instance == null) {
			synchronized (TimeSys.class) {
				if (instance == null) {
					instance = new TimeSys();
				}
			}
		}
		return instance;
	}

	public TimeSys() {
	}

	/**
	 * 
	 * Initiate time zone prepared for time converter.
	 * 
	 * @param
	 */
	public void initTimeZone(List<Airport> airports) {

		//System.out.println("sadasdasdadssadad");
		time_zone = new Hashtable<String, Integer>();
		float longtitude = 0;
		// System.out.println(longtitude);
		float west_time_zone[][] = { { 7.5F, 22.5F }, { 22.5F, 37.5F },
				{ 37.5F, 52.5F }, { 52.5F, 67.5F }, { 67.5F, 82.5F },
				{ 82.5F, 97.5F }, { 97.5F, 112.5F }, { 112.5F, 127.5F },
				{ 127.5F, 142.5F }, { 142.5F, 157.5F }, { 157.5F, 172.5F } };
		for (Airport airport : airports) {
			longtitude = -airport.getLongitude();
			for (int i = 0; i < west_time_zone.length; i++) {
				if (longtitude > west_time_zone[i][0]
						&& (longtitude <= west_time_zone[i][1])) {
					time_zone.put(airport.getCode(), i + 1);
					break;
				}
			}
		}

	}

	/**
	 * covert GMT to local time
	 * 
	 * @param GMT_time
	 *            orignial time
	 * @param airport
	 *            airport code
	 * @return local time
	 */
	public String timeConvertoLocal(String GMT_time, String airport) {

		//System.out.println(airport+"   "+time_zone.size());
		String local_time = "";
		try {
			String Summer = Integer.parseInt(GMT_time.split(" ")[0])
					+ " Mar 1 02:00";
			SimpleDateFormat df = new SimpleDateFormat("yyyy MMM dd HH:mm",
					Locale.US);
			Date GMT = df.parse(GMT_time.replace(" GMT", ""));
			Calendar c = Calendar.getInstance();
			// c.set(Calendar.YEAR, );
			// c.set(Calendar.MONTH, Calendar.MARCH-1);
			c.setTime(df.parse(Summer));
			int time = 0;
			while (time < 2) {
				c.add(Calendar.DATE, 1);
				if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
					time++;
			}
			Date summer = c.getTime();
			Date local = new Date(GMT.getTime() - (time_zone.get(airport) * 60 * 60 * 1000));
			if (summer.before(local))
				local = new Date(GMT.getTime() - ((time_zone.get(airport)-1) * 60 * 60 * 1000));
			local_time = df.format(local) + " Local";
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return local_time;
	}

	/**
	 * covert local to time
	 * 
	 * @param local_time
	 *            orignial time
	 * @param airport
	 *            airport code
	 * @return GMT time
	 */
	public String timeConvertoGMT(String local_time, String airport) {

		//System.out.println("lll "+local_time);
		String GMT_time = "";
		try {
			String Summer = Integer.parseInt(local_time.split("_")[0])
					+ " Mar 1 02:00";
			SimpleDateFormat dft = new SimpleDateFormat("yyyy_MM_dd HH:mm");
			SimpleDateFormat df = new SimpleDateFormat("yyyy MMM dd HH:mm",
					Locale.US);
			Date local = dft.parse(local_time);
			Calendar c = Calendar.getInstance();
			// c.set(Calendar.YEAR, );
			// c.set(Calendar.MONTH, Calendar.MARCH-1);
			c.setTime(df.parse(Summer));
			int time = 0;
			while (time < 2) {
				c.add(Calendar.DATE, 1);
				if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
					time++;
			}
			Date summer = c.getTime();
			
			Date GMT = new Date(local.getTime() + (time_zone.get(airport) * 60 * 60 * 1000));
			if (summer.before(local))
				GMT = new Date(local.getTime() + ((time_zone.get(airport)-1) * 60 * 60 * 1000));
			
			
		    GMT_time = df.format(GMT);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return GMT_time;
	}
	
	/**
	 * 
	 * Process the data format increasing one day
	 * 
	 * @param current
	 *            date
	 * @return date after increasing the input date by one day
	 */
	public String increaseOneday(String cur) {

		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
			Date d = df.parse(cur);
			Date t = new Date(d.getTime() + 24 * 60 * 60 * 1000);
			return df.format(t);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * 
	 * Construct data format.
	 * 
	 * 
	 * @param date
	 * @return date after increasing the input date by one day
	 */
	public String formatTime(String date) {

		try {
			SimpleDateFormat db = new SimpleDateFormat("yyyy MMM dd HH:mm",
					Locale.US);
			SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
			Date d = db.parse(date);
			return df.format(d);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * 
	 * Calculate the time span between two dates.
	 * 
	 * @param t1
	 *            Departure date
	 * @param t2
	 *            Arrival date
	 * @return Time span between the tow date or -1 if error occurred during
	 *         parsing
	 */
	public int calTimeDiff(String t1, String t2) {

		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy MMM dd HH:mm",
					Locale.US);
			Date d1 = df.parse(t1.replace(" GMT", ""));
			Date d2 = df.parse(t2.replace(" GMT", ""));

			return (int) (d2.getTime() - d1.getTime()) / (60 * 1000);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return -1;
	}

	/**
	 * 
	 * Check if two date suitable for real world
	 * 
	 * @param t1
	 *            Departure date
	 * @param t2
	 *            Arrival date
	 * @return True if the two date is reasonable for two flight and false if
	 *         not
	 */
	public boolean isSuitable(String t1, String t2) {
		boolean diff = false;
		// System.out.println(t1+" "+t2);

		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy MMM dd HH:mm",
					Locale.US);
			Date d1 = df.parse(t1.replace(" GMT", ""));
			Date d2 = df.parse(t2.replace(" GMT", ""));
			Date min = new Date(d1.getTime() + 30 * 60 * 1000);
			Date max = new Date(d1.getTime() + 12 * 60 * 60 * 1000);

			// System.out.println(d2+"   "+max);
			if (d2.after(min) && max.after(d2))
				diff = true;

		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return diff;
	}

	/**
	 * 
	 * Check if two dates of the flights are reasonable
	 * 
	 * @param t1
	 *            Departure time that in GMT format
	 * @param t2
	 *            Arrival time that in GMT format
	 * @return True if the two date is reasonable for two flight and false if
	 *         not
	 */
	public boolean returnisSuitable(String t1, String t2) {
		boolean diff = false;
		// System.out.println(t1+" "+t2);

		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy MMM dd HH:mm",
					Locale.US);
			Date d1 = df.parse(t1.replace(" GMT", ""));
			Date d2 = df.parse(t2.replace(" GMT", ""));
			Date min = new Date(d1.getTime() + 30 * 60 * 1000);

			// System.out.println(d2+"   "+max);
			if (d2.after(min))
				diff = true;

		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return diff;
	}
	
	public boolean satisfyDep(String t, String start, String end){
		boolean isSatisfied = false;
		
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy MMM dd HH:mm",
					Locale.US);
			Date dt = df.parse(t);
			Date ds = df.parse(start);
			Date de = df.parse(end);
			if((dt.after(ds) && dt.before(de))||dt.equals(ds)||dt.equals(de))
				isSatisfied = true;
			
			}catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		
		return isSatisfied;
	}

	public boolean satisfyArv(String t, String end){
		boolean isSatisfied = false;
		
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy MMM dd HH:mm",
					Locale.US);
			Date dt = df.parse(t);
			Date de = df.parse(end);
			if(dt.before(de)||dt.equals(de))
				isSatisfied = true;
			
			}catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		
		return isSatisfied;
	}
}
