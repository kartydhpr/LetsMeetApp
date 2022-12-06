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
    JPanel calendarMessagePanel, mapPanel, zoomPanel;
    JTabbedPane tabbedPane;
    JButton getDirectionButton, zoomLinkButton, findRoute;
    JTextField getDirectionTextBox, zoomLinkTextBox, startDestinationTextBox, endDestinationTextBox;
    JLabel destinationStartLabel, destinationEndLabel, tempZoolPanelLabel;
    

    public GUI(){
        frame = new JFrame();   
        calendarMessagePanel = new JPanel();    
        mapPanel = new JPanel();
        tabbedPane = new JTabbedPane();  

        calendarMessagePanel = new JPanel();
        getDirectionTextBox = new JTextField(15);
        getDirectionTextBox.addActionListener(this);
        getDirectionButton = new JButton("Get Directions");
        getDirectionButton.addActionListener(this);
        zoomLinkTextBox = new JTextField(15);
        zoomLinkTextBox.addActionListener(this);
        zoomLinkButton = new JButton("Get Zoom Link");
        zoomLinkButton.addActionListener(this);
        calendarMessagePanel.add(getDirectionTextBox);
        calendarMessagePanel.add(getDirectionButton);
        calendarMessagePanel.add(zoomLinkTextBox);
        calendarMessagePanel.add(zoomLinkButton);
        
        mapPanel = new JPanel();
        destinationStartLabel = new JLabel("Start Point");
        startDestinationTextBox = new JTextField(15);
        startDestinationTextBox.addActionListener(this);
        destinationEndLabel = new JLabel("End Point");
        endDestinationTextBox = new JTextField(15);
        endDestinationTextBox.addActionListener(this);
        findRoute = new JButton("Find Route");
        findRoute.addActionListener(this);
        mapPanel.add(destinationStartLabel);
        mapPanel.add(startDestinationTextBox);
        mapPanel.add(destinationEndLabel);
        mapPanel.add(endDestinationTextBox);
        mapPanel.add(findRoute);
        mapPanel.add(api.startApi(System.getProperty("user.dir") + "/src/map.html"), BorderLayout.CENTER);
        
        zoomPanel = new JPanel();
        tempZoolPanelLabel = new JLabel("Please enter the Zoom link in the calendar tab.");
        zoomPanel.add(tempZoolPanelLabel);
   
        tabbedPane.add("Calendar", calendarMessagePanel);  
        tabbedPane.add("Map", mapPanel);
        tabbedPane.add("Zoom", zoomPanel);

        frame.add(tabbedPane);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setSize(900, 775);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent evt){
        if(evt.getSource() == getDirectionButton){
            endDestinationTextBox.setText(getDirectionTextBox.getText());
            tabbedPane.setSelectedIndex(1);
        }
        
        if(evt.getSource() == zoomLinkButton){
            zoomPanel.add(api.startApi(zoomLinkTextBox.getText()), BorderLayout.CENTER);
            tempZoolPanelLabel.setText("");
            tabbedPane.setSelectedIndex(2);
        }
        
        if(evt.getSource() == findRoute){
            
        }
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