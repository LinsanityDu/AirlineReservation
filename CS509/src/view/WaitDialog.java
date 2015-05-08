package view;
import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * 
 * Paint the waiting icon when the system is processing serching.
 * 
 *
 */
public class WaitDialog extends JDialog{
	
	
	/**
	 * 
	 * Create the waiting icon used for painting while the system is processing flight searching.
	 * 
	 */
	public WaitDialog(){
	    super();	
		setSize(42, 42);
		setDefaultLookAndFeelDecorated(false);
		setUndecorated(true);
		setAlwaysOnTop(true);
		setModal(true);
		JLabel l = new JLabel();
		l.setIcon(new ImageIcon(getClass().getResource("../loading.gif")));
		add(l,BorderLayout.CENTER);
		
	}

}
