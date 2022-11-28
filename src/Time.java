/*
This is the Time class.
*/

import java.io.*;
import java.net.*;
import java.util.*;
import java.text.*;

public class Time {
    public String getTime(){
        String line = "";
        String outputText = "";
        System.out.println("Getting Time");
        
        try{
            Socket s = new Socket("time-A.timefreq.bldrdoc.gov", 13);   
            
            try{
                InputStream inStream = s.getInputStream();  
                Scanner in = new Scanner(inStream); 
                               
                while(in.hasNextLine()){    
                    line = in.nextLine();   
                }
            }
            
            finally{   
                s.close();   
            }
        }
        
        catch(IOException ioexc){   
            ioexc.printStackTrace();   
        }
        
        try{
            String updatedString = line.substring(6, 23);

            SimpleDateFormat inputFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss", Locale.US);
            inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = inputFormat.parse(updatedString);
            
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            SimpleDateFormat outputFormat = new SimpleDateFormat("hh:mm a", Locale.US);
            outputFormat.setTimeZone(TimeZone.getTimeZone("EST"));
            outputText = outputFormat.format(date);
        }
        
        catch (ParseException ex) {
            ex.printStackTrace(); 
        }

        return outputText;
    }
}