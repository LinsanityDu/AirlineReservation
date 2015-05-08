package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.ReservationSys;
import controller.TimeSys;
import model.Flight;
import model.Voyage;

/**
 * 
 * Selection Panel class.
 * 
 * Create the selected flight window when a customer makes a reservation.
 * 
 *
 */
public class SelectionPanel extends JPanel {

	/**
	 * 
	 * Build up the selection window.
	 * 
	 * Needs the input information of the selected flight
	 * 
	 * @param voyage
	 *            A Voyage object used to parse the flight tree node
	 * @param frame
	 *            The frame object of the UI interface
	 * @param isBrief
	 *            Identify the action occurred on a button
	 */
	public SelectionPanel(final Voyage voyage, boolean isBrief) {
		super();

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		final GUItest frame = GUItest.getInstance();
		final TimeSys timeSys = TimeSys.getInstance();

		JPanel showPrice = new JPanel();
		showPrice.setLayout(new FlowLayout());
		showPrice.setPreferredSize(new Dimension(760, 50));
		showPrice.setMaximumSize(new Dimension(760, 50));
		// showPrice.setBorder(BorderFactory.createEtchedBorder());

		JLabel Jprice = new JLabel("Price: $" + voyage.getTotalPrice());
		JButton selectButton = new JButton("SELECT");
		selectButton.setPreferredSize(new Dimension(90, 25));
		selectButton.setMaximumSize(new Dimension(90, 25));
		selectButton.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				/*
				 * PurchaseDialog dia = new PurchaseDialog(voyage, p);
				 * dia.setVisible(true); dia.dispose(); if(dia.isBuy())
				 * frame.search(); dia = null;
				 */

				frame.enableClear();
				if (frame.getDepVoyage() == null) {
					frame.setDepVoyage(voyage);
					frame.addToReservation();
					if (!frame.isRoundtrip()) {
						frame.removeAllResults();
						frame.enableOK();
					} else {
						frame.setSearching(true);
						frame.searchRet();
						final WaitDialog wd = new WaitDialog();
						wd.setLocationRelativeTo(frame);

						new Thread(new Runnable() {

							@Override
							public void run() {

								while (frame.isSearching()) {
									// System.out.println("ssssss");
									try {
										Thread.sleep(100);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
								wd.setVisible(false);

							}
						}).start();
						wd.setVisible(true);
					}
				} else {
					frame.setRetVoyage(voyage);
					frame.addToReservation();
					frame.removeAllResults();
					frame.enableOK();
				}

			}
		});
		final JButton detailButton = new JButton("DETAIL");
		detailButton.setPreferredSize(new Dimension(90, 25));
		detailButton.setMaximumSize(new Dimension(90, 25));
		JPanel briefInfo = new JPanel();
		briefInfo.setLayout(new FlowLayout());
		briefInfo.setPreferredSize(new Dimension(760, 30));
		briefInfo.setMaximumSize(new Dimension(760, 30));
		Flight depFlight = voyage.getLeaveFlights().get(0);
		Flight arvFlight = voyage.getLeaveFlights().get(
				voyage.getLeaveFlights().size() - 1);
		String[] stopover = { "nonstop", "1 stop", "2 stops" };

		briefInfo.add(new JLabel(depFlight.getDep_Time_Local().split(" ")[3]
				+ " " + depFlight.getDep_Code() + " -> "
				+ arvFlight.getArv_Time_Local().split(" ")[3] + " "
				+ arvFlight.getArv_Code() + "         " + voyage.getTotalTime()
				/ 60 + "h " + voyage.getTotalTime() % 60 + "m" + "          "
				+ stopover[voyage.getStopover()] + "          "));
		briefInfo.add(new JLabel(voyage.getTicketLeft()
				+ " seats left at this price!"));
		// briefInfo.setBorder(BorderFactory.createEtchedBorder());

		final JPanel flightInfo = new JPanel();

		flightInfo.setLayout(new BoxLayout(flightInfo, BoxLayout.Y_AXIS));

		showPrice.add(Jprice);
		if (!isBrief) {
			showPrice.add(selectButton);
		}
		showPrice.add(detailButton);

		add(showPrice);
		add(briefInfo);
		add(flightInfo);

		detailButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (detailButton.getText().equals("DETAIL")) {
					flightInfo.removeAll();
					flightInfo.updateUI();
					List<Flight> flights = voyage.getLeaveFlights();
					Flight preflight = null;

					for (Flight flight : flights) {
						if (preflight != null) {
							int diff = timeSys.calTimeDiff(
									preflight.getArv_Time(),
									flight.getDep_Time());
							JLabel layover = new JLabel("Layover: "
									+ String.valueOf(diff / 60) + "h "
									+ String.valueOf(diff % 60) + "m");
							JPanel mid = new JPanel();
							mid.add(layover);
							mid.setPreferredSize(new Dimension(760, 30));
							mid.setMaximumSize(new Dimension(760, 30));
							flightInfo.add(mid);
						}
						FlightPanel flightDetail = new FlightPanel(flight);
						flightInfo.add(flightDetail);
						preflight = flight;

					}
					detailButton.setText("HIDE");
				} else {
					flightInfo.removeAll();
					detailButton.setText("DETAIL");
				}
				flightInfo.updateUI();

			}
		});

	}

}
