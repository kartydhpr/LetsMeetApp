/*
This is the Gui class
*/

import java.awt.*;
import java.awt.Color;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.event.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Timer;
import com.mindfusion.common.DateTime;
import com.mindfusion.common.Duration;
import com.mindfusion.drawing.awt.AwtImage;
import com.mindfusion.scheduling.*;
import com.mindfusion.scheduling.awt.AwtCalendar;
import com.mindfusion.scheduling.model.*;
import com.mindfusion.scheduling.model.ItemEvent;
import java.awt.event.ItemListener;

public class GUI extends JFrame implements ActionListener, KeyListener, MouseListener {

    Person sender;
    Person receiver;
    Message m = new Message();
    String currentMessage;

    API api = new API();
    Color bgColor = new Color(0, 40, 69);
    Color secondaryColor = new Color(255, 153, 115);
    Color panelColor = new Color(32, 61, 84);
    Color accentColor = new Color(0, 207, 204);
    JFrame frame;
    JPanel calendarPanel, chatroomPanel, mapPanel, zoomPanel;
    JTabbedPane tabbedPane;
    //    JButton ;
//    JTextField ;
    JLabel clockLbl;
    JCheckBox personalBox;
    JCheckBox frenchBox;
    JCheckBox germanBox;
    JCheckBox pianoBox;
    Choice teachers;
    AwtCalendar calendar;
    ArrayList<Contact> contactsList;

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
        clockLbl.setFont(clockLbl.getFont().deriveFont(18f));
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

        teachers = new Choice();
        calendarPanel.add(teachers);

        personalBox = new JCheckBox("Personal");
        personalBox.setSelected(true);

        personalBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                checkBoxChanged(e);
            }
        });
        calendarPanel.add(personalBox);

        pianoBox = new JCheckBox("Piano");

        pianoBox.setSelected(true);
        pianoBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                checkBoxChanged(e);
            }
        });

        calendarPanel.add(pianoBox);

        germanBox = new JCheckBox("German");
        germanBox.setSelected(true);
        germanBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                checkBoxChanged(e);
            }
        });

        calendarPanel.add(germanBox);

        frenchBox = new JCheckBox("French");

        frenchBox.setSelected(true);
        frenchBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                checkBoxChanged(e);
            }
        });

        calendarPanel.add(frenchBox);

        springLayout.putConstraint(SpringLayout.SOUTH, frenchBox, -5, SpringLayout.SOUTH, calendarPanel);
        springLayout.putConstraint(SpringLayout.WEST, frenchBox, 5, SpringLayout.EAST, personalBox);

        springLayout.putConstraint(SpringLayout.SOUTH, pianoBox, -5, SpringLayout.SOUTH, calendarPanel);
        springLayout.putConstraint(SpringLayout.WEST, pianoBox, 5, SpringLayout.EAST, frenchBox);

        springLayout.putConstraint(SpringLayout.SOUTH, germanBox, -5, SpringLayout.SOUTH, calendarPanel);
        springLayout.putConstraint(SpringLayout.WEST, germanBox, 5, SpringLayout.EAST, pianoBox);

        JLabel label = new JLabel("Select a teacher:");
        calendarPanel.add(label);

        calendar = new AwtCalendar();
        calendar.beginInit();
        calendar.setCurrentView(CalendarView.Timetable);
        calendar.setTheme(ThemeType.Light);
        calendar.setCustomDraw(CustomDrawElements.TimetableItem);
        calendar.setGroupType(GroupType.FilterByContacts);

//        calendar.getTimetableSettings().getCellStyle().setBorderBottomColor(new Color(169, 169, 169));
        calendar.getTimetableSettings().getCellStyle().setBorderBottomWidth(1);
//        calendar.getTimetableSettings().getCellStyle().setBorderLeftColor(new Color(169, 169, 169));
        calendar.getTimetableSettings().getCellStyle().setBorderLeftWidth(1);
//        calendar.getTimetableSettings().getCellStyle().setBorderRightColor(new Color(169, 169, 169));
        calendar.getTimetableSettings().getCellStyle().setBorderRightWidth(1);
//        calendar.getTimetableSettings().getCellStyle().setBorderTopColor(new Color(169, 169, 169));
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
        springLayout.putConstraint(SpringLayout.SOUTH, calendar, -35, SpringLayout.NORTH, personalBox);

        springLayout.putConstraint(SpringLayout.WEST, teachers, 5, SpringLayout.EAST, label);
        springLayout.putConstraint(SpringLayout.SOUTH, teachers, -5, SpringLayout.NORTH, personalBox);

        springLayout.putConstraint(SpringLayout.WEST, label, 5, SpringLayout.WEST, calendarPanel);
        springLayout.putConstraint(SpringLayout.SOUTH, label, -5, SpringLayout.NORTH, personalBox);

        springLayout.putConstraint(SpringLayout.SOUTH, personalBox, -5, SpringLayout.SOUTH, calendarPanel);
        springLayout.putConstraint(SpringLayout.WEST, personalBox, 5, SpringLayout.WEST, calendarPanel);

        calendar.setEnableDragCreate(true);
        calendar.addCalendarListener(new CalendarAdapter() {
            public void draw (DrawEvent e){
                onCalendarDraw(e);
            }

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

        LineBorder lineBorder = new LineBorder(secondaryColor, 3, true);
        LineBorder lineBorderMessaActive = new LineBorder(Color.green, 3, true);

        String[] receiverList = {"Pick Recipient", "Jack", "Karty"}; // combo box list population
        String[] senderList = {"Pick Sender", "Jack", "Karty"}; // combo box list population

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");

        // Main frame
        comboReceiver = new JComboBox(receiverList);
        comboReceiver.addActionListener(this);

        lblHistory = new JLabel(" _______________ Chat History: _______________ ");
        lblHistory.setForeground(Color.white);
        txtHistory = new JTextArea(30, 30);
        txtHistory.setEditable(false);
        txtHistory.setLineWrap(true);
        txtHistory.setBackground(Color.black);
        txtHistory.setForeground(Color.green);
        txtHistory.setBorder(lineBorder);

        lblMessage = new JLabel("Message:");
        lblMessage.setForeground(Color.white);
        txtMessage = new JTextArea(3, 30);
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
        chatroomPanel.setSize(123, 123);


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

    // Message Scheduler methods
    private void checkBoxChanged (java.awt.event.ItemEvent e){
        boolean addItems = true;
        //Now that we know which Box was pushed, find out
        //whether it was selected or deselected.
        if (e.getStateChange() == java.awt.event.ItemEvent.DESELECTED) {
            addItems = false;
        }

        Object source = e.getItemSelectable();

        if (source == personalBox) {

            for (Contact c : contactsList) {
                if (c.getId().startsWith("guitar")) {

                    if (addItems) {
                        calendar.getContacts().add(c);
                        teachers.add(c.getName());
                    } else {
                        calendar.getContacts().remove(c);
                        teachers.remove(c.getName());
                    }
                }
            }
        } else if (source == pianoBox) {
            for (Contact c : contactsList) {
                if (c.getId().startsWith("piano")) {

                    if (addItems) {
                        calendar.getContacts().add(c);
                        teachers.add(c.getName());
                    } else {
                        calendar.getContacts().remove(c);
                        teachers.remove(c.getName());
                    }
                }
            }
        } else if (source == germanBox) {
            for (Contact c : contactsList) {
                if (c.getId().startsWith("german")) {

                    if (addItems) {
                        calendar.getContacts().add(c);
                        teachers.add(c.getName());
                    } else {
                        calendar.getContacts().remove(c);
                        teachers.remove(c.getName());
                    }

                }
            }
        } else if (source == frenchBox) {
            for (Contact c : contactsList) {
                if (c.getId().startsWith("french")) {

                    if (addItems) {
                        calendar.getContacts().add(c);
                        teachers.add(c.getName());
                    } else {
                        calendar.getContacts().remove(c);
                        teachers.remove(c.getName());
                    }
                }
            }

        }
    }

    private void initializeContacts () {

        Contact contact = new Contact();
        contact.setId("german_MW");
        contact.setName("Michael Walmann");
        teachers.add(contact.getName());
        calendar.getContacts().add(contact);
        contactsList.add(contact);

        contact = new Contact();
        contact.setId("german_LB");
        contact.setName("Brigitte Koepf");
        calendar.getContacts().add(contact);
        teachers.add(contact.getName());
        contactsList.add(contact);

        contact = new Contact();
        contact.setId("piano_DR");
        contact.setName("David Rohnson");
        calendar.getContacts().add(contact);
        teachers.add(contact.getName());
        contactsList.add(contact);

        contact = new Contact();
        contact.setId("piano_EE");
        contact.setName("Elisabeth Evans");
        calendar.getContacts().add(contact);
        teachers.add(contact.getName());
        contactsList.add(contact);

        contact = new Contact();
        contact.setId("guitar_RS");
        contact.setName("Ricardo Smith");
        calendar.getContacts().add(contact);
        teachers.add(contact.getName());
        contactsList.add(contact);

        contact = new Contact();
        contact.setId("guitar_RW");
        contact.setName("Robert Wilson");
        calendar.getContacts().add(contact);
        teachers.add(contact.getName());
        contactsList.add(contact);

        contact = new Contact();
        contact.setId("french_FT");
        contact.setName("Francois Toreau");
        calendar.getContacts().add(contact);
        teachers.add(contact.getName());
        contactsList.add(contact);

        contact = new Contact();
        contact.setId("french_CR");
        contact.setName("Chantale Saron");
        calendar.getContacts().add(contact);
        teachers.add(contact.getName());
        contactsList.add(contact);

        contact = new Contact();
        contact.setId("piano_PD");
        contact.setName("Peter Drysdale");
        calendar.getContacts().add(contact);
        teachers.add(contact.getName());
        contactsList.add(contact);

        contact = new Contact();
        contact.setId("guitar_ER");
        contact.setName("Emma Rodriguez");
        calendar.getContacts().add(contact);
        teachers.add(contact.getName());
        contactsList.add(contact);
    }


    protected void onCalendarDraw (DrawEvent e){
        if (e.getElement() == CustomDrawElements.TimetableItem) {

            Appointment app = (Appointment) e.getItem();

            if (app.getContacts().size() == 0)
                return;
            if (app.getContacts().get(0).getId().startsWith("guitar")) {

                java.awt.Image img = null;

                try {
                    // Read the image file from an input stream
                    InputStream is = new BufferedInputStream(
                            new FileInputStream("../guitar.png"));
                    img = ImageIO.read(is);

                    com.mindfusion.common.Rectangle r = e.getBounds();
                    AwtImage awtImage = new AwtImage(img);
                    //draw the image
                    e.getGraphics().drawImage(awtImage, e.getBounds().getLeft(), e.getBounds().getTop() + 20);

                } catch (IOException ioe) {
                }
            } else if (app.getContacts().get(0).getId().startsWith("piano")) {

                java.awt.Image img = null;

                try {
                    // Read the image file from an input stream
                    InputStream is = new BufferedInputStream(
                            new FileInputStream("../piano.png"));
                    img = ImageIO.read(is);

                    com.mindfusion.common.Rectangle r = e.getBounds();
                    AwtImage awtImage = new AwtImage(img);
                    //draw the image
                    e.getGraphics().drawImage(awtImage, e.getBounds().getLeft(), e.getBounds().getTop() + 20);

                } catch (IOException ioe) {
                }
            } else if (app.getContacts().get(0).getId().startsWith("german")) {

                java.awt.Image img = null;

                try {
                    // Read the image file from an input stream
                    InputStream is = new BufferedInputStream(
                            new FileInputStream("../german.png"));
                    img = ImageIO.read(is);

                    com.mindfusion.common.Rectangle r = e.getBounds();
                    AwtImage awtImage = new AwtImage(img);
                    //draw the
                    e.getGraphics().drawImage(awtImage, e.getBounds().getLeft(), e.getBounds().getTop() + 20);

                } catch (IOException ioe) {
                }
            } else if (app.getContacts().get(0).getId().startsWith("french")) {

                java.awt.Image img = null;

                try {
                    // Read the image file from an input stream
                    InputStream is = new BufferedInputStream(
                            new FileInputStream("../french.png"));
                    img = ImageIO.read(is);

                    com.mindfusion.common.Rectangle r = e.getBounds();
                    AwtImage awtImage = new AwtImage(img);
                    //draw the image
                    e.getGraphics().drawImage(awtImage, e.getBounds().getLeft(), e.getBounds().getTop() + 20);

                } catch (IOException ioe) {
                }
            }
        }
    }

    protected void onItemCreated (ItemEvent e){
        Appointment item = (Appointment) e.getItem();

        String teacherName = teachers.getSelectedItem();
        for (Contact c : calendar.getSchedule().getContacts()) {
            if (c.getName().equals(teacherName)) {
                item.getContacts().add(calendar.getContacts().get(c.getId()));

            }
        }
        item.setHeaderText(teacherName);

    }

    protected void onCalendarItemCreating (ItemConfirmEvent e)
    {
        DateTime start = e.getItem().getStartTime();
        DateTime end = e.getItem().getEndTime();


        if (start.getDayOfWeek() == 0 || end.getDayOfWeek() == 0) {
            JOptionPane.showMessageDialog(this, "No Classes on Sunday!");
            e.setConfirm(false);
        }
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