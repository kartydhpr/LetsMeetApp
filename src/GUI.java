/*
This is the Gui class.
*/

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class Gui extends JFrame implements ActionListener, KeyListener, MouseListener{
    Message m = new Message();
    
    JFrame frame;
    JPanel calendarPanel, messagePanel, mapPanel;
    JTabbedPane tabbedPane;
    JMenuBar menuBar;
    JMenu fileMenu;
    JMenuItem clearMessageMenuItem;
    JButton a;

    public Gui(){
        frame = new JFrame();   
        calendarPanel = new JPanel();  
        messagePanel = new JPanel();  
        mapPanel = new JPanel();
        tabbedPane = new JTabbedPane();  
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        fileMenu = new JMenu("hi");
        clearMessageMenuItem = new JMenuItem("yo");
        clearMessageMenuItem.addActionListener(this);
        menuBar.add(fileMenu);
        fileMenu.add(clearMessageMenuItem);
        
        a = new JButton("Hi");
        a.addActionListener(this);

        calendarPanel = new JPanel();
        calendarPanel.add(a);


        messagePanel = new JPanel();


        mapPanel = new JPanel();



        tabbedPane.add("Calendar", calendarPanel);  
        tabbedPane.add("Message", messagePanel);  
        tabbedPane.add("Map", mapPanel);

        frame.add(menuBar);
        frame.add(tabbedPane);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setSize(700,700);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true); 
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
                            m.setMessage(lineIn);
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
        Gui g = new Gui();
        //g.receiveMessage(8189); 
    }
}