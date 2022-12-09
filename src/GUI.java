/*
This is the Gui class
*/
import java.net.*;
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.Color;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import com.mindfusion.common.DateTime;
import com.mindfusion.common.Duration;
import com.mindfusion.scheduling.*;
import com.mindfusion.scheduling.awt.AwtCalendar;
import com.mindfusion.scheduling.model.*;
import com.mindfusion.scheduling.model.ItemEvent;
import java.awt.event.ItemListener;

public class GUI extends JFrame implements ActionListener, MouseListener, Internationalizer {
    API api = new API();
    Chatroom chat = new Chatroom();
    Message m = new Message();
    Time timeClass = new Time();
    Contacts contactClass = new Contacts();

    JFrame frame;
    JPanel calendarPanel, chatroomPanel, mapPanel, zoomPanel, pnlContain;
    JTabbedPane tabbedPane;
    JLabel clockLbl, lblHistory, lblMessage, lblSelectPerson;
    JCheckBox undergraduateBox, graduateBox, phdBox;
    JTextArea txtHistory, txtMessage;
    JComboBox comboReceiver, comboSender, comboLanguage;
    JButton btnSendMessage;
    Choice people;
    AwtCalendar calendar;
    ArrayList<Contact> contactsList;;

    String[] pList1 = {"Select Sender", "Bob", "Karto", "Fred", "John", "Greg", "Eric"};
    String[] pList2 = {"Select Receiver", "Bob", "Karto", "Fred", "John", "Greg", "Eric"};
    String[] pList3 = {"Select Language", "English", "Spanish", "Chinese"};
    String lang;
    String cntry;

    Color bgColor = new Color(0, 40, 69);
    Color secondaryColor = new Color(255, 153, 115);
    Color panelColor = new Color(32, 61, 84);
    Color accentColor = new Color(0, 207, 204);
    LineBorder lineBorder = new LineBorder(secondaryColor);
    LineBorder lineBorderMessaActive = new LineBorder(Color.green , 3, true);



    public GUI() {
        //General page specs
        frame = new JFrame();
        tabbedPane = new JTabbedPane();
        tabbedPane.setForeground(secondaryColor);
        tabbedPane.setBackground(Color.white);

        //Calendar panel specs
        SpringLayout layout = new SpringLayout();
        calendarPanel = new JPanel();
        clockLbl = new JLabel("");
        clockLbl.setFont(clockLbl.getFont().deriveFont(16f));
        clockLbl.setForeground(secondaryColor);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                String string = new SimpleDateFormat("h:mm aa").format(new Date());
                clockLbl.setText(string);
            }
        }, 0, 1000);

        layout.putConstraint(SpringLayout.NORTH, clockLbl,50, SpringLayout.SOUTH, calendarPanel);
        contactsList = new ArrayList<Contact>();
        SpringLayout springLayout = new SpringLayout();
        calendarPanel.setLayout(springLayout);
        people = new Choice();
        calendarPanel.add(people);

        undergraduateBox = new JCheckBox("Undergraduate");
        undergraduateBox.setForeground(Color.white);
        undergraduateBox.setSelected(true);
        undergraduateBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                checkBoxChanged(e);
            }
        });
        calendarPanel.add(undergraduateBox);

        phdBox = new JCheckBox("PHD");
        phdBox.setForeground(Color.white);
        phdBox.setSelected(true);
        phdBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                checkBoxChanged(e);
            }
        });
        calendarPanel.add(phdBox);

        graduateBox = new JCheckBox("Graduate");
        graduateBox.setForeground(Color.white);
        graduateBox.setSelected(true);
        graduateBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                checkBoxChanged(e);
            }
        });
        calendarPanel.add(graduateBox);

        springLayout.putConstraint(SpringLayout.SOUTH, graduateBox, -5, SpringLayout.SOUTH, calendarPanel);
        springLayout.putConstraint(SpringLayout.WEST, graduateBox, 5, SpringLayout.EAST, undergraduateBox);
        springLayout.putConstraint(SpringLayout.SOUTH, phdBox, -5, SpringLayout.SOUTH, calendarPanel);
        springLayout.putConstraint(SpringLayout.WEST, phdBox, 5, SpringLayout.EAST, graduateBox);

        lblSelectPerson = new JLabel("Select a person:");
        lblSelectPerson.setForeground(secondaryColor);
        calendarPanel.add(lblSelectPerson);

        calendar = new AwtCalendar();
        calendar.beginInit();
        calendar.setCurrentView(CalendarView.Timetable);
        calendar.setTheme(ThemeType.Light);
        calendar.setCustomDraw(CustomDrawElements.TimetableItem);
        calendar.setGroupType(GroupType.FilterByContacts);

        calendar.getTimetableSettings().getCellStyle().setBorderBottomWidth(1);
        calendar.getTimetableSettings().getCellStyle().setBorderLeftWidth(1);
        calendar.getTimetableSettings().getCellStyle().setBorderRightWidth(1);
        calendar.getTimetableSettings().getCellStyle().setBorderTopWidth(1);
        calendar.getTimetableSettings().getCellStyle().setHeaderTextShadowOffset(0);
        calendar.getTimetableSettings().getCellStyle().setHeaderTextShadowStyle(ShadowStyle.None);
        calendar.getTimetableSettings().getDates().clear();

        for (int i = 0; i < 7; i++)
            calendar.getTimetableSettings().getDates().add(DateTime.today().addDays(i - 1));

        calendar.getTimetableSettings().setItemOffset(30);
        calendar.getTimetableSettings().setShowItemSpans(false);
        calendar.getTimetableSettings().setSnapInterval(Duration.fromMinutes(1));
        calendar.getTimetableSettings().setVisibleColumns(7);
        calendar.endInit();

        springLayout.putConstraint(SpringLayout.EAST, calendar, 0, SpringLayout.EAST, calendarPanel);
        springLayout.putConstraint(SpringLayout.NORTH, calendar, 0, SpringLayout.NORTH, calendarPanel);
        springLayout.putConstraint(SpringLayout.WEST, calendar, 0, SpringLayout.WEST, calendarPanel);
        springLayout.putConstraint(SpringLayout.SOUTH, calendar, -35, SpringLayout.NORTH, undergraduateBox);

        springLayout.putConstraint(SpringLayout.WEST, people, 5, SpringLayout.EAST, lblSelectPerson);
        springLayout.putConstraint(SpringLayout.SOUTH, people, -5, SpringLayout.NORTH, undergraduateBox);

        springLayout.putConstraint(SpringLayout.WEST, lblSelectPerson, 5, SpringLayout.WEST, calendarPanel);
        springLayout.putConstraint(SpringLayout.SOUTH, lblSelectPerson, -5, SpringLayout.NORTH, undergraduateBox);

        springLayout.putConstraint(SpringLayout.SOUTH, undergraduateBox, -5, SpringLayout.SOUTH, calendarPanel);
        springLayout.putConstraint(SpringLayout.WEST, undergraduateBox, 5, SpringLayout.WEST, calendarPanel);

        calendar.setEnableDragCreate(true);
        calendar.addCalendarListener(new CalendarAdapter() {

            public void itemCreated (ItemEvent e){
                onItemCreated(e);
            }

            public void itemCreating (ItemConfirmEvent e){
                onCalendarItemCreating(e);
            }
        });

        initializeContacts();
        calendarPanel.add(clockLbl);
        calendarPanel.add(calendar);
        calendarPanel.setBackground(panelColor);


        // Chatroom panel specs
        chatroomPanel = new JPanel();
        chatroomPanel.setLayout(new BoxLayout(chatroomPanel, BoxLayout.Y_AXIS));
        chatroomPanel.setBackground(panelColor);

        LineBorder lineBorder = new LineBorder(secondaryColor, 3, true);
        LineBorder lineBorderMessaActive = new LineBorder(Color.green, 3, true);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");

        // Chat frame
        comboSender = new JComboBox(pList1);
        comboSender.addActionListener(this);
        comboReceiver = new JComboBox(pList2);
        comboReceiver.addActionListener(this);
        comboLanguage = new JComboBox(pList3);
        comboLanguage.addActionListener(this);

        lblHistory = new JLabel(" _______________ Chat History _______________ ");
        lblHistory.setForeground(Color.white);
        txtHistory = new JTextArea(30, 30);
        txtHistory.setEditable(false);
        txtHistory.setLineWrap(true);
        txtHistory.setBackground(Color.black);
        txtHistory.setForeground(Color.green);
        txtHistory.setBorder(lineBorder);
        txtHistory.addMouseListener(this);


        lblMessage = new JLabel("Message:");
        lblMessage.setForeground(Color.white);
        txtMessage = new JTextArea(3, 30);
        txtMessage.setLineWrap(true);
        txtMessage.setBackground(Color.black);
        txtMessage.setForeground(Color.white);
        txtMessage.setBorder(lineBorder);
        txtMessage.addMouseListener(this);


        btnSendMessage = new JButton("Send");
        btnSendMessage.addActionListener(this);

        Container cp = getContentPane();
        pnlContain = new JPanel();
        pnlContain.setLayout(new BoxLayout(pnlContain, BoxLayout.Y_AXIS));
        comboSender.setAlignmentX(pnlContain.CENTER_ALIGNMENT);
        comboReceiver.setAlignmentX(pnlContain.CENTER_ALIGNMENT);
        comboLanguage.setAlignmentX(pnlContain.CENTER_ALIGNMENT);
        lblHistory.setAlignmentX(pnlContain.CENTER_ALIGNMENT);
        txtHistory.setAlignmentX(pnlContain.CENTER_ALIGNMENT);
        lblMessage.setAlignmentX(pnlContain.CENTER_ALIGNMENT);
        txtMessage.setAlignmentX(pnlContain.CENTER_ALIGNMENT);
        btnSendMessage.setAlignmentX(pnlContain.CENTER_ALIGNMENT);
        pnlContain.add(comboSender);
        pnlContain.add(comboReceiver);
        pnlContain.add(comboLanguage);
        pnlContain.add(lblHistory);
        pnlContain.add(txtHistory);
        pnlContain.add(lblMessage);
        pnlContain.add(txtMessage);
        pnlContain.add(btnSendMessage);
        pnlContain.setBackground(bgColor);
        cp.add(pnlContain);
        chatroomPanel.add(cp);
        chatroomPanel.setSize(123, 123);

        // Map panel specs
        mapPanel = new JPanel();
//        mapPanel.add(api.startApi("maps.google.com"), BorderLayout.CENTER);
        mapPanel.add(api.startApi(System.getProperty("user.dir") + "/src/map.html"), BorderLayout.CENTER);

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

    // Message Scheduler methods
    private void checkBoxChanged (java.awt.event.ItemEvent e){
        boolean addItems = true;
        if (e.getStateChange() == java.awt.event.ItemEvent.DESELECTED) {
            addItems = false;
        }

        Object source = e.getItemSelectable();
        if (source == undergraduateBox) {

            for (Contact c : contactsList) {
                if (c.getId().startsWith("u")) {

                    if (addItems) {
                        calendar.getContacts().add(c);
                        people.add(c.getName());
                    } else {
                        calendar.getContacts().remove(c);
                        people.remove(c.getName());
                    }
                }
            }
        } else if (source == phdBox) {
            for (Contact c : contactsList) {
                if (c.getId().startsWith("g")) {

                    if (addItems) {
                        calendar.getContacts().add(c);
                        people.add(c.getName());
                    } else {
                        calendar.getContacts().remove(c);
                        people.remove(c.getName());
                    }
                }
            }
        } else if (source == graduateBox) {
            for (Contact c : contactsList) {
                if (c.getId().startsWith("p")) {

                    if (addItems) {
                        calendar.getContacts().add(c);
                        people.add(c.getName());
                    } else {
                        calendar.getContacts().remove(c);
                        people.remove(c.getName());
                    }
                }
            }

        }
    }

    private void initializeContacts () {
        Contact contact = new Contact();
        contact.setId("u_1");
        contact.setName("Bob Murphy");
        people.add(contact.getName());
        calendar.getContacts().add(contact);
        contactsList.add(contact);

        contact = new Contact();
        contact.setId("u_2");
        contact.setName("Karto Smith");
        calendar.getContacts().add(contact);
        people.add(contact.getName());
        contactsList.add(contact);

        contact = new Contact();
        contact.setId("g_1");
        contact.setName("Fred Melenchuck");
        calendar.getContacts().add(contact);
        people.add(contact.getName());
        contactsList.add(contact);

        contact = new Contact();
        contact.setId("g_2");
        contact.setName("John Mahedy");
        calendar.getContacts().add(contact);
        people.add(contact.getName());
        contactsList.add(contact);

        contact = new Contact();
        contact.setId("p_1");
        contact.setName("Greg O'Neil");
        calendar.getContacts().add(contact);
        people.add(contact.getName());
        contactsList.add(contact);

        contact = new Contact();
        contact.setId("p_2");
        contact.setName("Eric Matson");
        calendar.getContacts().add(contact);
        people.add(contact.getName());
        contactsList.add(contact);
    }

    protected void onItemCreated (ItemEvent e){
        Appointment item = (Appointment) e.getItem();
        String personName = people.getSelectedItem();
        for (Contact c : calendar.getSchedule().getContacts()) {
            if (c.getName().equals(personName)) {
                item.getContacts().add(calendar.getContacts().get(c.getId()));

            }
        }
        item.setHeaderText(personName);
    }

    protected void onCalendarItemCreating (ItemConfirmEvent e) {
        DateTime start = e.getItem().getStartTime();
        DateTime end = e.getItem().getEndTime();
    }

    public void setLanguage(String language, String country){
        Locale l = new Locale(language, country);
        ResourceBundle r = ResourceBundle.getBundle("bundle", l);

        comboSender.setSelectedIndex(0);
        comboSender.insertItemAt(r.getString("PICK SENDER"), 0);
        comboSender.setSelectedIndex(0);
        comboSender.removeItemAt(1);

        comboReceiver.setSelectedIndex(0);
        comboReceiver.insertItemAt(r.getString("PICK RECEIVER"), 0);
        comboReceiver.setSelectedIndex(0);
        comboReceiver.removeItemAt(1);

        comboLanguage.setSelectedIndex(0);
        comboLanguage.insertItemAt(r.getString("PICK LANGUAGE"), 0);
        comboLanguage.setSelectedIndex(0);
        comboLanguage.removeItemAt(1);

        tabbedPane.setTitleAt(0, r.getString("CALENDAR"));
        tabbedPane.setTitleAt(1, r.getString("CHAT"));
        tabbedPane.setTitleAt(2, r.getString("MAP"));
        lblSelectPerson.setText(r.getString("SELECT PERSON"));
        undergraduateBox.setText(r.getString("UNDERGRADUATE"));
        graduateBox.setText(r.getString("GRADUATE"));
        lblHistory.setText(r.getString("CHAT HISTORY"));
        lblMessage.setText(r.getString("MESSAGE LABEL"));
        btnSendMessage.setText(r.getString("SEND"));
    }

    public void actionPerformed(ActionEvent evt){
        //All send buttons
        if(evt.getSource() == btnSendMessage){
            m.setMessage(txtMessage.getText());
            chat.sendMessage(chat.getReceiver(), m.getMessage());
            txtMessage.setText("");
            txtMessage.requestFocus();
            txtHistory.append("   "+chat.getSender().getFirstname()+" "+chat.getSender().getLastname()+" @"
                    +timeClass.getTime()+": "+m.getMessage()+"\n");
        }

        //Sender Combobox
        if(evt.getSource() == comboSender){
            JComboBox cb = (JComboBox)evt.getSource();
            String contacts2 = (String)cb.getSelectedItem();

            if(contacts2.equals("Bob")){
                chat.setSender(contactClass.u1);
                comboSender.setSelectedIndex(1);
            }

            if(contacts2.equals("Karto")){
                chat.setSender(contactClass.u2);
                comboSender.setSelectedIndex(2);
            }

            if(contacts2.equals("Fred")){
                chat.setSender(contactClass.g1);
                comboSender.setSelectedIndex(3);
            }

            if(contacts2.equals("John")){
                chat.setSender(contactClass.g2);
                comboSender.setSelectedIndex(4);
            }

            if(contacts2.equals("Greg")){
                chat.setSender(contactClass.p1);
                comboSender.setSelectedIndex(5);
            }

            if(contacts2.equals("Eric")){
                chat.setSender(contactClass.p2);
                comboSender.setSelectedIndex(6);
            }
        }

        //Receiver ComboBox
        if(evt.getSource() == comboReceiver){
            JComboBox cb = (JComboBox)evt.getSource();
            String contacts2 = (String)cb.getSelectedItem();

            if(contacts2.equals("Bob")){
                chat.setReceiver(contactClass.u1);
                comboReceiver.setSelectedIndex(1);
            }

            if(contacts2.equals("Karto")){
                chat.setReceiver(contactClass.u2);
                comboReceiver.setSelectedIndex(2);
            }

            if(contacts2.equals("Fred")){
                chat.setReceiver(contactClass.g1);
                comboReceiver.setSelectedIndex(3);
            }

            if(contacts2.equals("John")){
                chat.setReceiver(contactClass.g2);
                comboReceiver.setSelectedIndex(4);
            }

            if(contacts2.equals("Greg")){
                chat.setReceiver(contactClass.p1);
                comboReceiver.setSelectedIndex(5);
            }

            if(contacts2.equals("Eric")){
                chat.setReceiver(contactClass.p2);
                comboReceiver.setSelectedIndex(6);
            }
        }

        //Set languages
        if(evt.getSource() == comboLanguage){
            JComboBox cb = (JComboBox)evt.getSource();
            String contact9 = (String)cb.getSelectedItem();

            if(contact9.equals("English")){
                lang = "en";
                cntry = "US";
                setLanguage(lang, cntry);
            }

            if(contact9.equals("Spanish")){
                lang = "es";
                cntry = "MX";
                setLanguage(lang, cntry);
            }

            if(contact9.equals("Chinese")){
                lang= "cn";
                cntry= "CN";
                setLanguage(lang, cntry);
            }
        }
    }

    //Mouse Listeners
    public void mouseClicked(MouseEvent e) {} public void mousePressed(MouseEvent e){}public void mouseReleased(MouseEvent e) {} //irellevant abstract methods

    public void mouseEntered(MouseEvent e) {
        if(e.getSource() == btnSendMessage){
            txtMessage.requestFocus();
        }
        if(e.getSource()==txtMessage){
            txtMessage.setBorder(lineBorderMessaActive);
        }
    }

    public void mouseExited(MouseEvent e) {
        if(e.getSource() == btnSendMessage){
            pnlContain.requestFocus();
        }
        if(e.getSource()==txtMessage){
            txtMessage.setBorder(lineBorder);
        }
    }

    public void receiveMessage(int port){                //server
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
                            txtHistory.append("   "+chat.getReceiver().getFirstname()+" "+chat.getReceiver().getLastname()+" @"
                                    +timeClass.getTime()+": "+m.getMessage()+"\n");
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
        g.receiveMessage(8189);
    }
}