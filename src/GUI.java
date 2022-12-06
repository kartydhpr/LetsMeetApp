/*
This is the Gui class
*/

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class GUI extends JFrame implements ActionListener, KeyListener, MouseListener{
//    Message m = new Message();
    API api = new API();
    
    JFrame frame;
    JPanel calendarPanel, chatroomPanel, mapPanel, zoomPanel;
    JTabbedPane tabbedPane;
//    JButton ;
//    JTextField ;
//    JLabel ;

    public GUI(){
        //General page specsa
        frame = new JFrame();   
        tabbedPane = new JTabbedPane();

        //Calendar panel specs
        calendarPanel = new JPanel();

        // Chatroom panel specs
        chatroomPanel = new JPanel();

        // Map panel specs
        mapPanel = new JPanel();
        mapPanel.add(api.startApi("maps.google.com"), BorderLayout.CENTER);

        // Zoom panel specs
        zoomPanel = new JPanel();
        zoomPanel.add(api.startApi("zoom.com/join"), BorderLayout.CENTER);

        //tabbed pane at the top of the screen
        tabbedPane.add("Calendar", calendarPanel);
        tabbedPane.add("Chat", chatroomPanel);
        tabbedPane.add("Map", mapPanel);
        tabbedPane.add("Zoom", zoomPanel);

        frame.add(tabbedPane);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setSize(900, 700);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }
    
    public void actionPerformed(ActionEvent evt){

    }
    
    //Key listeners
    public void keyPressed(KeyEvent e){

    }
    
    public void keyTyped(KeyEvent e){
 
    }
    
    public void keyReleased(KeyEvent e){

    }
    
    //Mouse Listeners
    public void mousePressed(MouseEvent e) {
       
    }

    public void mouseReleased(MouseEvent e) {
       
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseClicked(MouseEvent e) {
       
    }
    
    //Server to receive the messages
    public void receiveMessage(int port){               
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
//                            m.setMessage(lineIn);
                            // conversationBox.append(t.getTime() + " | " + c.getReceiver().getUsername() + ": " + m.getMessage() + "\n");
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
     
    //Main method
    public static void main(String [] args){
        GUI g = new GUI();
        //g.receiveMessage(8189); 
    }
}