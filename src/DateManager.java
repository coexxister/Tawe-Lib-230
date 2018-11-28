import java.util.Date;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class DateManager { 

    /**
     * Constructor of DateManager that creates a 
     * file if it doesn't exist
     * @throws IOException
     */
	public DateManager() throws IOException{
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	    Date date = new Date();
	    
		String filePathString = null;
		File dateFile = new File(filePathString);
		if(!dateFile.exists() && !dateFile.isDirectory()) { 
		BufferedWriter writer = new BufferedWriter(new FileWriter("dateFile.txt"));
        writer.write(df.format(date));
        writer.close();
		}
	}
	
	/**
	 * Method that returns the current date
	 * @return
	 */
	public static String returnCurrentDate(){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    Date date = new Date();
	    
	    return (df.format(date));
	}
	
	/**
	 * Method to update the date by one and write it into a file
	 * @throws IOException
	 */
	public static void  updateForward() throws IOException{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    Date date = new Date();
	    
		Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    
	    cal.add(Calendar.DAY_OF_MONTH, 1);
	
	    try {
	        
	    	BufferedWriter writer = new BufferedWriter(new FileWriter("dateFile.text"));
	        writer.write(df.format(cal.getTime()));
	        writer.close();
	       
	      } catch (FileNotFoundException e) {
	    	  e.printStackTrace();
	      }
	        
	    
	}

}