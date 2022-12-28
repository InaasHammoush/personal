import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.*;
import javax.swing.*;


/**
 * A custom component that acts as a simple stop-watch.  When the user clicks
 * on it, this component starts timing.  When the user clicks again,
 * it displays the time between the two clicks.  Clicking a third time
 * starts another timer, etc.  While it is timing, the label displays the
 * running time until the stop watch stops.
 */
public class StopWatchLabel extends JLabel implements MouseListener, ActionListener{

	private long startTime;   // Start time of timer.
	//   (Time is measured in milliseconds.)

	private boolean running;  // True when the timer is running.

	private Timer timer; // the timer that will display the running time

	/**
	 * Constructor sets initial text on the label to
	 * "Click to start timer." and sets up a mouse listener
	 * so the label can respond to clicks.
	 */
	public StopWatchLabel() {
		super("  Click to start timer.  ", JLabel.CENTER);
		addMouseListener(this);
	}


	/**
	 * Tells whether the timer is currently running.
	 */
	public boolean isRunning() {
		return running;
	}


	public void actionPerformed(ActionEvent e) {
		long currentTime = System.currentTimeMillis();
		setText(Double.toString((currentTime - startTime) / 1000.0));
	}

	/**
	 * React when the user presses the mouse by starting
	 * or stopping the timer and changing the text that
	 * is shown on the label.
	 */
	public void mousePressed(MouseEvent evt) {
		if (running == false) {
			// Record the time and start the timer.
			running = true;
			startTime = evt.getWhen();  // Time when mouse was clicked.
			if (timer == null) { // to make sure not to add another timer each time
				timer = new Timer(200,this);
				timer.start();
			}
			else
				timer.restart();
		}
		else {
			// Stop the timer.  Compute the elapsed time since the
			// timer was started and display it.
			timer.stop();
			running = false;
			long endTime = evt.getWhen();
			double seconds = (endTime - startTime) / 1000.0;
			setText("Time: " + seconds + " sec.");
		}
	}

	public void mouseReleased(MouseEvent evt) { }
	public void mouseClicked(MouseEvent evt) { }
	public void mouseEntered(MouseEvent evt) { }
	public void mouseExited(MouseEvent evt) { }


	/**
	 * This is to test the StopWatchLabel Component
	 * created in this class
	 * @param args Ignored
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation( (screenSize.width - frame.getWidth())/2, 
				(screenSize.height - frame.getHeight())/2 );
		StopWatchLabel swl = new StopWatchLabel();
		frame.setContentPane(panel);
		panel.add(swl);
		frame.pack();
		frame.setVisible(true);
	}
}

