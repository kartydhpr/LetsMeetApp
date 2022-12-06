/*
This is the Client class.
*/

import java.io.*;
import java.net.*;

public class Client {
    public void Client(Person p, String message){
        System.out.println("Client Starting...");
        
        try{
            Socket s = new Socket(p.getIp(), p.getPort());   
            
            try{
                OutputStream outStream = s.getOutputStream();
                PrintWriter out = new PrintWriter(outStream, true);
                out.println(message);
            }
            
            finally{   
                s.close(); 
            }
        }
        
        catch(IOException ioexc){   
            ioexc.printStackTrace();   
        }
    }
}