package mindfusion.swt.sample;

import java.awt.Frame;
import java.awt.Panel;

import javax.swing.SwingUtilities;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import com.mindfusion.scheduling.Calendar;
import com.mindfusion.scheduling.CalendarAdapter;
import com.mindfusion.scheduling.ThemeType;
import com.mindfusion.scheduling.model.Appointment;
import com.mindfusion.scheduling.model.ItemEvent;

public class MainDisplay {
	
	public static void main (String [] args) {	
		
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("MindFusion Java Swing Calendar with Eclipse SWT");
		shell.setLayout(new RowLayout());
		Composite composite = new Composite(shell, SWT.EMBEDDED | SWT.NO_BACKGROUND);
		composite.setLayoutData(new RowData(1000, 500));
		
		Frame frame = SWT_AWT.new_Frame(composite);
		Panel panel = new Panel(new java.awt.BorderLayout());
		frame.add(panel);
		
		Calendar calendar = new Calendar();
		calendar.setTheme(ThemeType.Light);
		panel.add(calendar);
		
		Button button = new Button(shell, SWT.NONE);
		button.setText("Click to create a new calendar event");
		button.setLayoutData(new RowData(300, 40));
		
		Button button1 = new Button(shell, SWT.NONE);
		button1.setText("Number of appointments: 0");
		button1.setLayoutData(new RowData(300, 40));
		
		Utility utility = new Utility(display, calendar, button1);
		
		calendar.addCalendarListener(new CalendarAdapter() {
			public void itemCreated(ItemEvent e)
			{
				utility.updateButton();
			}
			
			public void itemDeleted(ItemEvent e)
			{
				utility.updateButton();
			}
		});
		
		 //handle click on the button that
		 //creates items programmatically
		 button.addListener(SWT.Selection, new Listener() {
		      public void handleEvent(Event e) {
		        switch (e.type) {
		        case SWT.Selection:
		          utility.updateProgress();			          
		          break;
		        }
		      }
		    });
		
		
		 // run the SWT event loop
		 shell.open();
		 while (!shell.isDisposed())
		 {
			 	if (!display.readAndDispatch())
			 	
			 		display.sleep();
		 }
		 display.dispose ();			
		
	}	

}


class Utility
{
	private Button button;
	private Calendar calendar;
	private Display display;
	
	public Utility(Display display, Calendar calendar, Button button)
	{
		this.display = display;
		this.calendar = calendar;
		this.button = button;		
		
	}
	
	  public void updateProgress() {
		  SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		      // Here, we can safely update the GUI
		      // because we'll be called from the
		      // event dispatch thread
		    	com.mindfusion.common.DateTime firstDate = calendar.getFirstVisibleDate();
		    	com.mindfusion.common.DateTime oneWeek = firstDate.addDays(7);	
		    	
		    	Appointment app = new Appointment();
		    	app.setStartTime(firstDate);
		    	app.setEndTime(oneWeek);
		    	app.setHeaderText("AWT Calendar updated from SWT button click");
		    	calendar.getSchedule().getItems().add(app);	
		    	updateButton();
		    	 
		    }
		    });
		  }
	  
	  public void updateButton() {
		  display.asyncExec(new Runnable() {
			  public void run() {
				  if(display.isDisposed())
					  return;
				  button.setText("Number of appointments in the calendar: " + 
					  String.valueOf(calendar.getSchedule().getItems().size()));
			  }
		  });
	  }
	  
	 
}































