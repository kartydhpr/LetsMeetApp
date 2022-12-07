/*
This is the Gui class
*/

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Timer;

public class GUI extends JFrame implements ActionListener, KeyListener, MouseListener{
//    Message m = new Message();
    API api = new API();
    Color bgColor = new Color(0,40,69);
    Color secondaryColor = new Color(255,153,115);
    Color panelColor = new Color(32,61,84);
    Color accentColor = new Color(0,207,204);
    JFrame frame;
    JPanel calendarPanel, chatroomPanel, mapPanel, zoomPanel;
    JTabbedPane tabbedPane;
//    JButton ;
//    JTextField ;
    JLabel clockLbl;

    public GUI(){
        //General page specsa
        frame = new JFrame();   
        tabbedPane = new JTabbedPane();
        tabbedPane.setForeground(secondaryColor);
        tabbedPane.setBackground(Color.white);

        //Calendar panel specs
        calendarPanel = new JPanel();
        clockLbl = new JLabel("");
        clockLbl.setFont(clockLbl.getFont().deriveFont(50f));
        clockLbl.setForeground(secondaryColor);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                String string = new SimpleDateFormat("hh:mm aa").format(new Date());
                clockLbl.setText(string);
            }
        }, 0, 1000);
        calendarPanel.add(clockLbl);
        calendarPanel.setBackground(panelColor);

        // Chatroom panel specs
        chatroomPanel = new JPanel();
        chatroomPanel.setBackground(panelColor);

        // Map panel specs
        mapPanel = new JPanel();
        mapPanel.add(api.startApi("maps.google.com"), BorderLayout.CENTER);

        // Zoom panel specs
        zoomPanel = new JPanel();
        zoomPanel.add(api.startApi("zoom.com/join"), BorderLayout.CENTER);
        zoomPanel.setBackground(Color.white);

        //tabbed pane at the top of the screen
        tabbedPane.add("Calendar", calendarPanel);
        tabbedPane.add("Chat", chatroomPanel);
        tabbedPane.add("Map", mapPanel);
        tabbedPane.add("Zoom", zoomPanel);

        frame.setTitle("LetsMeetApp");
        frame.getContentPane().setBackground(bgColor);
        frame.setSize(900, 700);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.add(tabbedPane);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
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
     
    //Main method
    public static void main(String [] args){
        GUI g = new GUI();
        //g.receiveMessage(8189); 
    }
}