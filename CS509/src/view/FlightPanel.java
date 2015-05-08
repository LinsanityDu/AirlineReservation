package view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Flight;

/**
 * 
 * GUI interface.
 * 
 * Panel used to paint the flight information.
 *
 */
public class FlightPanel extends JPanel {

	/**
	 * 
	 * Create panel to paint the flight information.
	 * 
	 * @param flight
	 *            flight object that needs to be shown.
	 */
	public FlightPanel(Flight flight) {
		super();

		GridBagLayout layout = new GridBagLayout();

		setLayout(layout);
		JLabel Jnumber = new JLabel("           Flight " + flight.getNumber());
		JLabel Jdepart = new JLabel(flight.getDep_Code());
		JLabel Jdedate = new JLabel(flight.getDep_Time_Local());
		JLabel Jfirstp = new JLabel("FirstClass: $"
				+ flight.getFirstClass_Price() + " ("
				+ flight.getFirstClass_Left() + " tickets left)");
		JLabel Jtime = new JLabel("           Total Trip: "
				+ flight.getFlightTime() / 60 + "h " + flight.getFlightTime()
				% 60 + "m");
		JLabel Jarrival = new JLabel(flight.getArv_Code());
		JLabel Javdate = new JLabel(flight.getArv_Time_Local());
		JLabel Jcoachp = new JLabel("Coach: $" + flight.getCoach_Price() + " ("
				+ flight.getCoach_Left() + " tickets left)");

		setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), flight.getDep_Code() + "->"
						+ flight.getArv_Code()));

		add(Jnumber);
		add(Jdepart);
		add(Jdedate);
		add(Jfirstp);
		add(Jtime);
		add(Jarrival);
		add(Javdate);
		add(Jcoachp);

		GridBagConstraints s = new GridBagConstraints();
		s.fill = GridBagConstraints.BOTH;
		s.weightx = 5;
		s.weighty = 2;
		s.gridwidth = 1;
		s.gridy = 0;
		s.gridx = 0;
		layout.setConstraints(Jnumber, s);
		s.gridx = 1;
		layout.setConstraints(Jdepart, s);
		s.gridx = 2;
		layout.setConstraints(Jdedate, s);
		s.gridwidth = 0;
		s.gridx = 3;
		layout.setConstraints(Jfirstp, s);
		s.weighty = 2;
		s.gridwidth = 1;
		s.gridy = 1;
		s.gridx = 0;
		layout.setConstraints(Jtime, s);
		s.gridx = 1;
		layout.setConstraints(Jarrival, s);
		s.gridx = 2;
		layout.setConstraints(Javdate, s);
		s.gridwidth = 0;
		s.gridx = 3;
		layout.setConstraints(Jcoachp, s);

		setPreferredSize(new Dimension(760, 100));
		setMaximumSize(new Dimension(760, 100));
	}

}
