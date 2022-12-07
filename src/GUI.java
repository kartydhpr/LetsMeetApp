/*
This is the Gui class
*/

import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Timer;

public class GUI extends JFrame implements ActionListener, KeyListener, MouseListener{

    Person sender;
    Person receiver;
    Message m = new Message();
    String currentMessage;

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
        //General page specs
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

        Person karty = new Person("Karty", "1", "10.186.173.143", 8189);
        Person jack = new Person("Jack", "2", "10.186.173.143", 8180);

        // Frame Variables
        JPanel pnlContain;
        JTextField txtPort;
        JTextArea txtHistory, txtMessage;
        JLabel lblHistory, lblMessage;
        JComboBox comboReceiver;
        JButton btnSendMessage;

        LineBorder lineBorder = new LineBorder(secondaryColor , 3, true);
        LineBorder lineBorderMessaActive = new LineBorder(Color.green , 3, true);

        String[] receiverList = {"Pick Recipient", "Jack", "Karty"}; // combo box list population
        String[] senderList = {"Pick Sender", "Jack", "Karty"}; // combo box list population

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");

            // Main frame
            comboReceiver = new JComboBox(receiverList);
            comboReceiver.addActionListener(this);

            lblHistory = new JLabel(" _______________ Chat History: _______________ ");
            lblHistory.setForeground(Color.white);
            txtHistory = new JTextArea(30,30);
            txtHistory.setEditable(false);
            txtHistory.setLineWrap(true);
            txtHistory.setBackground(Color.black);
            txtHistory.setForeground(Color.green);
            txtHistory.setBorder(lineBorder);

            lblMessage = new JLabel("Message:");
            lblMessage.setForeground(Color.white);
            txtMessage = new JTextArea(3,30);
            txtMessage.setLineWrap(true);
            txtMessage.setBackground(Color.black);
            txtMessage.setForeground(Color.white);
            txtMessage.setBorder(lineBorder);

            btnSendMessage = new JButton("Send");
            btnSendMessage.addActionListener(this);

            Container cp = getContentPane();
            pnlContain = new JPanel();
            pnlContain.add(comboReceiver);
            pnlContain.add(lblHistory);
            pnlContain.add(txtHistory);
            pnlContain.add(lblMessage);
            pnlContain.add(txtMessage);
            pnlContain.add(btnSendMessage);
            pnlContain.setBackground(bgColor);
            cp.add(pnlContain);
            chatroomPanel.add(cp);
            chatroomPanel.setSize(123,123);


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
        frame.setResizable(true);
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