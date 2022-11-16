/*

*/

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    Message m = new Message();
    
    public void server(int port){
        System.out.println("Server Starting...");
        String lineIn;
        
        try{
            ServerSocket s = new ServerSocket(port); 
            boolean over = false;   

            while(!over){ 
                Socket incoming = s.accept(); 

                try{
                    InputStream inStream = incoming.getInputStream();  
                    Scanner in = new Scanner(inStream); 

                    boolean done = false;
                    while (!done && in.hasNextLine()){ 
                        lineIn = in.nextLine();

                        if (lineIn.trim().equals("END")){
                            done = true;
                        }
                        
                        else{
                            m.setMessage(lineIn);
                            
                        }
                    } 
                }

                catch(Exception exc1){
                    exc1.printStackTrace();
                } 
            }
            
        }
        
        catch(Exception exc2){
            exc2.printStackTrace();
        }
    }
}