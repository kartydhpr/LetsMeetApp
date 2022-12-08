/*
This is the Chatroom class.
*/

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Chatroom extends JFrame implements ActionListener {
    private Person sender;
    private Person receiver;
    Message m = new Message();
    String currentMessage;


//    Person p1 = new Person("Karty", "1", "127.0.0.1", 8189);
//    Person p2 = new Person("Ryan", "2", "127.0.0.1", 8189);
//    Person p3 = new Person("Jack1", "3", "127.0.0.1", 8189);
//    Person p4 = new Person("Jack2", "4", "127.0.0.1", 8189);

    Person karty = new Person("Karty", "1", "10.186.173.143", 8189);
    Person jack = new Person("Jack", "2", "10.186.173.143", 8180);

    // Frame Variables
    JPanel pnlContain;
    JTextField txtPort;
    JTextArea txtHistory, txtMessage;
    JLabel lblHistory, lblMessage;
    JComboBox comboReceiver;
    JButton btnSendMessage;

    LineBorder lineBorder = new LineBorder(Color.orange , 3, true);
    LineBorder lineBorderMessaActive = new LineBorder(Color.green , 3, true);
    Color bgColor = new Color(0,40,80);
    Color toolColor = new Color(0,25,51);

    String[] receiverList = {"Pick Recipient", "Jack", "Karty"}; // combo box list population
    String[] senderList = {"Pick Sender", "Jack", "Karty"}; // combo box list population

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");

    public Chatroom(){

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
    }

    public void actionPerformed(ActionEvent evt){

        // Frame items
        if(evt.getSource() == comboReceiver){
            JComboBox comboReceiver = (JComboBox)evt.getSource();
            String msgReceiver = (String)comboReceiver.getSelectedItem();
            if(msgReceiver.equals("Jack")){
                receiver = jack;
                sender = karty;
            }

            if(msgReceiver.equals("Karty")){
                receiver = karty;
                sender = jack;
            }
        }
        //All send buttons
        if(evt.getSource() == btnSendMessage){
            m.setMessage(txtMessage.getText());
            currentMessage = m.getMessage();
            sendMessage(receiver, currentMessage);

            txtMessage.setText("");
            txtMessage.requestFocus();
            txtHistory.append("   "+sender.getFirstname()+" "+sender.getLastname()+" @"
                    +dtf.format(LocalDateTime.now())+": "+currentMessage+"\n");
        }
    }

    public void setSender(Person sender){
        this.sender = sender;
    }
    
    public Person getSender(){
        return sender;
    }
    
    public void setReceiver(Person receiver){
        this.receiver = receiver;
    }
    
    public Person getReceiver(){
        return receiver;
    }
    
    public void sendMessage(Person p, String message){    //client   
        Client c = new Client();
        c.Client(getReceiver(), message);
    }
}