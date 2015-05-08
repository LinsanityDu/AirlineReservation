package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import controller.ReservationSys;
import model.Flight;
import model.Order;
import model.Voyage;

/**
 * 
 * Purchase Dialog structure.
 * 
 *
 */
public class PurchaseDialog extends JDialog {

	private final JComboBox field;
	private boolean isBuy, isClose;
	// private JPanel deprecom, retrecom;
	private JPanel depcust, retcust;
	private JLabel cprice, cprice1;
	private Voyage depvoyage, retvoyage;
	private List<JRadioButton> depcoachstatus, retcoachstatus, depfcstatus,
			retfcstatus;
	private JLabel totalPrice;
	private ReservationSys reservSys;

	/**
	 * 
	 * Paint a dialog for the reservation.
	 * 
	 * @param depvoyage
	 *            Voyage object used for parse the departure flight tree node
	 * @param retvoyage
	 *            Voyage object used for parse the arrival flight tree node
	 */
	public PurchaseDialog(final Voyage depvoyage, final Voyage retvoyage) {
		super();
		setTitle("Reservation");
		setModal(true);
		this.depvoyage = depvoyage;
		this.retvoyage = retvoyage;
		isBuy = false;
		isClose = true;
		reservSys = ReservationSys.getInstance();

		JPanel info = new JPanel();
		GridBagLayout layout = new GridBagLayout();
		info.setLayout(layout);
		GridBagConstraints s = new GridBagConstraints();
		s.fill = GridBagConstraints.BOTH;

		final ButtonGroup group = new ButtonGroup();
		final JRadioButton Recommend = new JRadioButton("Recommendation");
		Recommend.setSelected(true);
		Recommend.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				field.removeAllItems();

				int ticketleft = depvoyage.getTicketLeft();
				if (retvoyage != null && retvoyage.getTicketLeft() < ticketleft)
					ticketleft = retvoyage.getTicketLeft();

				for (int i = 0; i < ticketleft; i++)
					field.addItem(i + 1);
				field.updateUI();
				// deprecom.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),"Current Selection"));
				depcust.setBorder(BorderFactory.createEtchedBorder());

				for (int i = 0; i < depcoachstatus.size(); i++) {
					Flight flight = depvoyage.getLeaveFlights().get(i);
					if (flight.getCoach_Left() == 0)
						depfcstatus.get(i).setSelected(true);
					else
						depcoachstatus.get(i).setSelected(true);
				}

				int num = Integer.parseInt(field.getSelectedItem().toString());
				if (retvoyage != null) {
					totalPrice.setText("Total Price: ($"
							+ depvoyage.getTotalPrice()
							+ " + $"
							+ retvoyage.getTotalPrice()
							+ ") * "
							+ num
							+ " = $"
							+ (float) Math.round((depvoyage.getTotalPrice() + retvoyage
									.getTotalPrice()) * num * 1000) / 1000);
					retcust.setBorder(BorderFactory.createEtchedBorder());
					for (int i = 0; i < retcoachstatus.size(); i++) {
						Flight flight = retvoyage.getLeaveFlights().get(i);
						if (flight.getCoach_Left() == 0)
							retfcstatus.get(i).setSelected(true);
						else
							retcoachstatus.get(i).setSelected(true);
					}

					// retrecom.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),"Current Selection"));
				} else
					totalPrice.setText("Total Price: $"
							+ depvoyage.getTotalPrice()
							+ " * "
							+ num
							+ " = $"
							+ (float) Math.round(depvoyage.getTotalPrice()
									* num * 1000) / 1000);

			}
		});

		final JRadioButton Custom = new JRadioButton("Custom");
		Custom.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				field.removeAllItems();

				int ticketleft = getCustDepTicket();
				if (retvoyage != null && getCustRetTicket() < ticketleft)
					ticketleft = getCustRetTicket();

				for (int i = 0; i < ticketleft; i++)
					field.addItem(i + 1);
				field.updateUI();
				// deprecom.setBorder(BorderFactory.createEtchedBorder());
				// depcust.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),"Current Selection"));
				int num = Integer.parseInt(field.getSelectedItem().toString());
				if (retvoyage != null) {
					// retrecom.setBorder(BorderFactory.createEtchedBorder());
					// retcust.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),"Current Selection"));
					totalPrice.setText("Total Price: ($"
							+ getCustDepPrice()
							+ " + $"
							+ getCustRetPrice()
							+ ") * "
							+ num
							+ " = $"
							+ (float) Math
									.round((getCustDepPrice() + getCustRetPrice())
											* num * 1000) / 1000);
				} else
					totalPrice.setText("Total Price: $"
							+ getCustDepPrice()
							+ " * "
							+ num
							+ " = $"
							+ (float) Math
									.round(getCustDepPrice() * num * 1000)
							/ 1000);

			}
		});

		group.add(Recommend);
		group.add(Custom);

		final JLabel num = new JLabel("Adult:");
		field = new JComboBox();

		/*
		 * if (flight.getCoach_Left() == 0){ coach.setEnabled(false);
		 * field.setEnabled(false); if (flight.getFirstClass_Left() == 0)
		 * Custom.setEnabled(false); else { for (int i=0;
		 * i<flight.getFirstClass_Left(); i++) field.addItem(i+1);
		 * Custom.setSelected(true); field.setEnabled(true);
		 * 
		 * } }else
		 */
		int ticketleft = depvoyage.getTicketLeft();
		if (retvoyage != null && retvoyage.getTicketLeft() < ticketleft)
			ticketleft = retvoyage.getTicketLeft();
		for (int i = 0; i < ticketleft; i++)
			field.addItem(i + 1);

		field.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				// TODO Auto-generated method stub
				if (arg0.getStateChange() == ItemEvent.SELECTED) {
					int num = Integer.parseInt(field.getSelectedItem()
							.toString());
					if (Custom.isSelected()) {
						if (retvoyage != null)
							totalPrice.setText("Total Price: ($"
									+ getCustDepPrice()
									+ " + "
									+ getCustRetPrice()
									+ ") * "
									+ num
									+ " = $"
									+ (float) Math
											.round((getCustDepPrice() + getCustRetPrice())
													* num * 1000) / 1000);
						else
							totalPrice.setText("Total Price: $"
									+ getCustDepPrice()
									+ " * "
									+ num
									+ " = $"
									+ (float) Math.round(getCustDepPrice()
											* num * 1000) / 1000);

					} else {
						if (retvoyage != null)
							totalPrice.setText("Total Price: ($"
									+ depvoyage.getTotalPrice()
									+ " + $"
									+ retvoyage.getTotalPrice()
									+ ") * "
									+ num
									+ " = $"
									+ (float) Math.round((depvoyage
											.getTotalPrice() + retvoyage
											.getTotalPrice())
											* num * 1000) / 1000);
						else
							totalPrice.setText("Total Price: $"
									+ depvoyage.getTotalPrice()
									+ " * "
									+ num
									+ " = $"
									+ (float) Math.round(depvoyage
											.getTotalPrice() * num * 1000)
									/ 1000);
					}
				}
			}
		});

		JPanel detail = new JPanel();

		// deprecom = new JPanel();
		// deprecom.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),"Current Selection"));
		// deprecom.setMinimumSize(new Dimension(220,
		// 65+60*(depvoyage.getStopover()+1)));
		// deprecom.setPreferredSize(new Dimension(220,
		// 65+60*(depvoyage.getStopover()+1)));

		depcust = new JPanel();
		depcust.setBorder(BorderFactory.createEtchedBorder());
		depcust.setMinimumSize(new Dimension(300, 65 + 75 * (depvoyage
				.getStopover() + 1)));
		depcust.setPreferredSize(new Dimension(300, 65 + 75 * (depvoyage
				.getStopover() + 1)));

		if (retvoyage != null) {
			// retrecom = new JPanel();
			// retrecom.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),"Current Selection"));
			// retrecom.setMinimumSize(new Dimension(220,
			// 65+60*(retvoyage.getStopover()+1)));
			// retrecom.setPreferredSize(new Dimension(220,
			// 65+60*(retvoyage.getStopover()+1)));

			retcust = new JPanel();
			retcust.setBorder(BorderFactory.createEtchedBorder());
			retcust.setMinimumSize(new Dimension(300, 65 + 75 * (retvoyage
					.getStopover() + 1)));
			retcust.setPreferredSize(new Dimension(300, 65 + 75 * (retvoyage
					.getStopover() + 1)));
		}
		totalPrice = new JLabel();
		if (retvoyage != null)
			totalPrice.setText("Total Price: ($"
					+ depvoyage.getTotalPrice()
					+ " + $"
					+ retvoyage.getTotalPrice()
					+ ") * 1 = $"
					+ (float) Math.round((depvoyage.getTotalPrice() + retvoyage
							.getTotalPrice()) * 1000) / 1000);
		else
			totalPrice.setText("Total Price: $" + depvoyage.getTotalPrice()
					+ " * 1 = $" + depvoyage.getTotalPrice());

		detail.setLayout(new FlowLayout());
		// detail.add(deprecom);
		detail.add(depcust);
		if (retvoyage != null) {
			// detail.add(retrecom);
			detail.add(retcust);
		}
		detail.add(totalPrice);

		s.gridwidth = 2;
		s.gridx = 0;
		s.gridy = 0;
		info.add(Recommend, s);
		s.gridx = 2;
		s.gridwidth = 0;
		info.add(Custom, s);
		s.gridx = 0;
		s.gridy = 1;
		s.gridwidth = 1;
		info.add(num, s);
		s.gridx = 1;
		s.gridwidth = 0;
		info.add(field, s);

		/*
		 * //For recommendation---dep JLabel cheapest = new JLabel();
		 * //deprecom.add(cheapest); String cheapSchedule = "<html>";
		 * 
		 * for(Flight flight:depvoyage.getLeaveFlights()){
		 * cheapSchedule+="<p></p>";
		 * cheapSchedule+="<p>"+flight.getDep_Code()+"->"
		 * +flight.getArv_Code()+": $"
		 * +(flight.getCoach_Left()==0?flight.getFirstClass_Price
		 * ():flight.getCoach_Price())+"</p>";
		 * cheapSchedule+="<p>Seat: "+(flight
		 * .getCoach_Left()==0?"First Class":"Coach")+"</p>"; }
		 * 
		 * cheapSchedule+="<p></p><p>Price: $"+depvoyage.getTotalPrice()+"</p>";
		 * cheapSchedule += "</html>"; cheapest.setText(cheapSchedule);
		 * 
		 * //For recommendation---ret if(retvoyage != null){ JLabel cheapest1 =
		 * new JLabel(); //retrecom.add(cheapest1); String cheapSchedule1 =
		 * "<html>";
		 * 
		 * for(Flight flight:retvoyage.getLeaveFlights()){
		 * cheapSchedule1+="<p></p>";
		 * cheapSchedule1+="<p>"+flight.getDep_Code()+
		 * "->"+flight.getArv_Code()+": $"
		 * +(flight.getCoach_Left()==0?flight.getFirstClass_Price
		 * ():flight.getCoach_Price())+"</p>";
		 * cheapSchedule1+="<p>Seat: "+(flight
		 * .getCoach_Left()==0?"First Class":"Coach")+"</p>"; }
		 * 
		 * cheapSchedule1+="<p></p><p>Price: $"+retvoyage.getTotalPrice()+"</p>";
		 * cheapSchedule1 += "</html>"; cheapest1.setText(cheapSchedule1); }
		 */

		// For custom---dep
		GridBagLayout customLayout = new GridBagLayout();
		GridBagConstraints cs = new GridBagConstraints();
		depcust.setLayout(customLayout);
		int level = 0;
		depcoachstatus = new ArrayList<JRadioButton>();
		depfcstatus = new ArrayList<JRadioButton>();
		for (final Flight flight : depvoyage.getLeaveFlights()) {
			final JLabel best = new JLabel();
			final JLabel ticket = new JLabel();
			String bSchedule = "<html><p></p>";
			bSchedule += "<p>"
					+ flight.getDep_Code()
					+ "->"
					+ flight.getArv_Code()
					+ ": $"
					+ (flight.getCoach_Left() == 0 ? flight
							.getFirstClass_Price() : flight.getCoach_Price())
					+ "</p>";
			bSchedule += "</html>";
			best.setText(bSchedule);
			cs.anchor = GridBagConstraints.NORTH;
			cs.weighty = 1;
			cs.gridwidth = 0;
			cs.gridx = 0;
			cs.gridy = level;
			depcust.add(best, cs);
			final JRadioButton firstclass = new JRadioButton("FirstClass");
			firstclass.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub

					if (flight.getCoach_Left() != 0)
						Custom.setSelected(true);

					field.removeAllItems();
					int ticketleft = getCustDepTicket();
					if (retvoyage != null && getCustRetTicket() < ticketleft)
						ticketleft = getCustRetTicket();

					for (int i = 0; i < ticketleft; i++)
						field.addItem(i + 1);
					field.updateUI();
					int num = Integer.parseInt(field.getSelectedItem()
							.toString());
					if (retvoyage != null)
						totalPrice.setText("Total Price: ($"
								+ getCustDepPrice()
								+ " + $"
								+ getCustRetPrice()
								+ ") * "
								+ num
								+ " = $"
								+ (float) Math
										.round((getCustDepPrice() + getCustRetPrice())
												* num * 1000) / 1000);
					else
						totalPrice.setText("Total Price: $"
								+ getCustDepPrice()
								+ " * "
								+ num
								+ " = $"
								+ (float) Math.round(getCustDepPrice() * num
										* 1000) / 1000);

					String bSchedule = "<html><p></p>";
					bSchedule += "<p>" + flight.getDep_Code() + "->"
							+ flight.getArv_Code() + ": $"
							+ flight.getFirstClass_Price() + "</p>";
					bSchedule += "</html>";
					best.setText(bSchedule);
					ticket.setText("Tickets: "+flight.getFirstClass_Left()+" left");
					cprice.setText("<html><p></p><p>Price: $"
							+ getCustDepPrice() + "</p></html>");

				}
			});
			depfcstatus.add(firstclass);
			cs.weighty = 1;
			cs.gridwidth = 1;
			cs.gridx = 0;
			cs.gridy = level + 1;
			depcust.add(firstclass, cs);
			final JRadioButton coach = new JRadioButton("Coach");
			coach.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub

					field.removeAllItems();

					int ticketleft = getCustDepTicket();
					if (retvoyage != null && getCustRetTicket() < ticketleft)
						ticketleft = getCustRetTicket();

					for (int i = 0; i < ticketleft; i++)
						field.addItem(i + 1);
					field.updateUI();
					int num = Integer.parseInt(field.getSelectedItem()
							.toString());
					if (retvoyage != null)
						totalPrice.setText("Total Price: ($"
								+ getCustDepPrice()
								+ " + $"
								+ getCustRetPrice()
								+ ") * "
								+ num
								+ " = $"
								+ (float) Math
										.round((getCustDepPrice() + getCustRetPrice())
												* num * 1000) / 1000);
					else
						totalPrice.setText("Total Price: $"
								+ getCustDepPrice()
								+ " * "
								+ num
								+ " = $"
								+ (float) Math.round(getCustDepPrice() * num
										* 1000) / 1000);

					String bSchedule = "<html><p></p>";
					bSchedule += "<p>" + flight.getDep_Code() + "->"
							+ flight.getArv_Code() + ": $"
							+ flight.getCoach_Price() + "</p>";
					bSchedule += "</html>";
					best.setText(bSchedule);
					ticket.setText("Tickets: "+flight.getCoach_Left()+" left");
					cprice.setText("<html><p></p><p>Price: $"
							+ getCustDepPrice() + "</p></html>");
				}
			});
			depcoachstatus.add(coach);
			cs.gridwidth = 0;
			cs.gridx = 1;
			cs.gridy = level + 1;
			depcust.add(coach, cs);
			cs.gridwidth = 0;
			cs.gridx = 0;
			cs.gridy = level + 2;
		    ticket.setText("Tickets: "+flight.getCoach_Left()+" left");
			depcust.add(ticket, cs);
			level += 3;
			final ButtonGroup pergroup = new ButtonGroup();
			pergroup.add(firstclass);
			pergroup.add(coach);
			coach.setSelected(true);
			if (flight.getCoach_Left() == 0) {
				coach.setEnabled(false);
				firstclass.setSelected(true);
			}
			if (flight.getFirstClass_Left() == 0) {
				firstclass.setEnabled(false);
				coach.setSelected(true);
			}
		}
		cprice = new JLabel();
		cprice.setText("<html><p></p><p>Price: $" + depvoyage.getTotalPrice()
				+ "</p></html>");
		cs.weighty = 2 * (depvoyage.getStopover() + 1);
		cs.gridwidth = 0;
		cs.gridx = 0;
		cs.gridy = level;
		depcust.add(cprice, cs);

		
		// For custom---ret
		if (retvoyage != null) {
			GridBagLayout customLayout1 = new GridBagLayout();
			GridBagConstraints cs1 = new GridBagConstraints();
			retcust.setLayout(customLayout1);
			int level1 = 0;
			retcoachstatus = new ArrayList<JRadioButton>();
			retfcstatus = new ArrayList<JRadioButton>();
			for (final Flight flight : retvoyage.getLeaveFlights()) {
				final JLabel best = new JLabel();
				final JLabel ticket = new JLabel();
				String bSchedule = "<html><p></p>";
				bSchedule += "<p>"
						+ flight.getDep_Code()
						+ "->"
						+ flight.getArv_Code()
						+ ": $"
						+ (flight.getCoach_Left() == 0 ? flight
								.getFirstClass_Price() : flight
								.getCoach_Price()) + "</p>";
				bSchedule += "</html>";
				best.setText(bSchedule);
				cs1.anchor = GridBagConstraints.NORTH;
				cs1.weighty = 1;
				cs1.gridwidth = 0;
				cs1.gridx = 0;
				cs1.gridy = level1;
				retcust.add(best, cs1);
				final JRadioButton firstclass = new JRadioButton("FirstClass");
				firstclass.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub

						if (flight.getCoach_Left() != 0)
							Custom.setSelected(true);

						field.removeAllItems();
						int ticketleft = getCustDepTicket();
						if (retvoyage != null
								&& getCustRetTicket() < ticketleft)
							ticketleft = getCustRetTicket();

						for (int i = 0; i < ticketleft; i++)
							field.addItem(i + 1);
						field.updateUI();
						int num = Integer.parseInt(field.getSelectedItem()
								.toString());
						if (retvoyage != null)
							totalPrice.setText("Total Price: ($"
									+ getCustDepPrice()
									+ " + "
									+ getCustRetPrice()
									+ ") * "
									+ num
									+ " = $"
									+ (float) Math
											.round((getCustDepPrice() + getCustRetPrice())
													* num * 1000) / 1000);
						else
							totalPrice.setText("Total Price: $"
									+ getCustDepPrice()
									+ " * "
									+ num
									+ " = $"
									+ (float) Math.round(getCustDepPrice()
											* num * 1000) / 1000);

						String bSchedule = "<html><p></p>";
						bSchedule += "<p>" + flight.getDep_Code() + "->"
								+ flight.getArv_Code() + ": $"
								+ flight.getFirstClass_Price() + "</p>";
						bSchedule += "</html>";
						best.setText(bSchedule);
						ticket.setText("Tickets: "+flight.getFirstClass_Left()+" left");
						cprice1.setText("<html><p></p><p>Price: $"
								+ getCustRetPrice() + "</p></html>");

					}
				});
				retfcstatus.add(firstclass);
				cs1.weighty = 1;
				cs1.gridwidth = 1;
				cs1.gridx = 0;
				cs1.gridy = level1 + 1;
				retcust.add(firstclass, cs1);
				final JRadioButton coach = new JRadioButton("Coach");
				coach.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub

						field.removeAllItems();
						int ticketleft = getCustDepTicket();
						if (retvoyage != null
								&& getCustRetTicket() < ticketleft)
							ticketleft = getCustRetTicket();

						for (int i = 0; i < ticketleft; i++)
							field.addItem(i + 1);
						field.updateUI();
						int num = Integer.parseInt(field.getSelectedItem()
								.toString());
						if (retvoyage != null)
							totalPrice.setText("Total Price: ($"
									+ getCustDepPrice()
									+ " + "
									+ getCustRetPrice()
									+ ") * "
									+ num
									+ " = $"
									+ (float) Math
											.round((getCustDepPrice() + getCustRetPrice())
													* num * 1000) / 1000);
						else
							totalPrice.setText("Total Price: $"
									+ getCustDepPrice()
									+ " * "
									+ num
									+ " = $"
									+ (float) Math.round(getCustDepPrice()
											* num * 1000) / 1000);

						String bSchedule = "<html><p></p>";
						bSchedule += "<p>" + flight.getDep_Code() + "->"
								+ flight.getArv_Code() + ": $"
								+ flight.getCoach_Price() + "</p>";
						bSchedule += "</html>";
						best.setText(bSchedule);
						ticket.setText("Tickets: "+flight.getCoach_Left()+" left");
						cprice1.setText("<html><p></p><p>Price: $"
								+ getCustRetPrice() + "</p></html>");
					}
				});
				retcoachstatus.add(coach);
				cs1.gridwidth = 0;
				cs1.gridx = 1;
				cs1.gridy = level1 + 1;
				retcust.add(coach, cs1);
				cs1.gridwidth = 0;
				cs1.gridx = 0;
				cs1.gridy = level1 + 2;
			    ticket.setText("Tickets: "+flight.getCoach_Left()+" left");
				retcust.add(ticket, cs1);
				level1 += 3;
				final ButtonGroup pergroup = new ButtonGroup();
				pergroup.add(firstclass);
				pergroup.add(coach);
				coach.setSelected(true);
				if (flight.getCoach_Left() == 0) {
					coach.setEnabled(false);
					firstclass.setSelected(true);
				}
				if (flight.getFirstClass_Left() == 0) {
					firstclass.setEnabled(false);
					coach.setSelected(true);
				}
			}
			cprice1 = new JLabel();
			cprice1.setText("<html><p></p><p>Price: $"
					+ retvoyage.getTotalPrice() + "</p></html>");
			cs1.weighty = 2 * (retvoyage.getStopover() + 1);
			cs1.gridwidth = 0;
			cs1.gridx = 0;
			cs1.gridy = level1;
			retcust.add(cprice1, cs1);

		}

		final JButton ok = new JButton(" Buy! ");
		final JButton cancel = new JButton("Cancel");
		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				boolean pass = true;

				int amount = Integer.parseInt(field.getSelectedItem()
						.toString());

				// if (Recommend.isSelected()){
				// List<Order> orders = new ArrayList<Order>();
				// for(Flight flight:depvoyage.getLeaveFlights()){
				// Order order = new Order(flight.getNumber(), flight,
				// (flight.getCoach_Left()==0)?"FirstClass":"Coach", amount);
				// orders.add(order);
				// }
				// if(retvoyage!=null){
				// for(Flight flight:retvoyage.getLeaveFlights()){
				// Order order = new Order(flight.getNumber(), flight,
				// (flight.getCoach_Left()==0)?"FirstClass":"Coach", amount);
				// orders.add(order);
				// }
				// }
				//
				// p.buyTicket(orders);
				// }
				// else{
				List<Order> orders = new ArrayList<Order>();
				for (int i = 0; i < depcoachstatus.size(); i++) {
					Flight flight = depvoyage.getLeaveFlights().get(i);
					Order order = new Order(flight.getNumber(), flight,
							(depcoachstatus.get(i).isSelected()) ? "Coach"
									: "FirstClass", amount);
					orders.add(order);
				}
				if (retvoyage != null) {
					for (int i = 0; i < retcoachstatus.size(); i++) {
						Flight flight = retvoyage.getLeaveFlights().get(i);
						Order order = new Order(flight.getNumber(), flight,
								(retcoachstatus.get(i).isSelected()) ? "Coach"
										: "FirstClass", amount);
						orders.add(order);
					}
				}
				isBuy = reservSys.buyTicket(orders);
				isClose = false;
				// }
				setVisible(false);
			}
		});

		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				setVisible(false);
			}
		});

		JPanel action = new JPanel();
		action.add(ok);
		action.add(cancel);
		add(info, BorderLayout.NORTH);
		add(detail, BorderLayout.CENTER);
		add(action, BorderLayout.SOUTH);
		setSize(300,
				240
						+ 70
						* (depvoyage.getStopover() + 1)
						+ ((retvoyage != null) ? 70 * (retvoyage.getStopover() + 1) + 60
								: 0));

		int width = getToolkit().getScreenSize().width;
		int height = getToolkit().getScreenSize().height;

		int left = (width - this.getSize().width) / 2;
		int top = (height - this.getSize().height) / 2;

		this.setLocation(left, top);

	}

	/**
	 * 
	 * return decision made by the customer.
	 * 
	 * @return Identify if a customer is buying a ticket
	 */
	public boolean isBuy() {
		return this.isBuy;
	}

	/**
	 * 
	 * return decision made by the customer.
	 * 
	 * @return Identify if a customer closed a ticket
	 */
	public boolean isClose() {
		return this.isClose;
	}

	/**
	 * 
	 * Return the price of the departure flight of a round trip.
	 * 
	 * @return Price of the selected departure flight in a round trip option
	 */

	private float getCustDepPrice() {
		float custprice = 0;
		for (int i = 0; i < depcoachstatus.size(); i++) {
			custprice += depcoachstatus.get(i).isSelected() ? depvoyage
					.getLeaveFlights().get(i).getCoach_Price() : depvoyage
					.getLeaveFlights().get(i).getFirstClass_Price();
		}
		return (float) Math.round(custprice * 1000) / 1000;
	}

	/**
	 * 
	 * Return the price of the return flight of a round trip
	 * 
	 * @return Price of the selected arrival flight in a round trip option
	 */
	private float getCustRetPrice() {
		float custprice = 0;
		for (int i = 0; i < retcoachstatus.size(); i++) {
			custprice += retcoachstatus.get(i).isSelected() ? retvoyage
					.getLeaveFlights().get(i).getCoach_Price() : retvoyage
					.getLeaveFlights().get(i).getFirstClass_Price();
		}
		return (float) Math.round(custprice * 1000) / 1000;
	}

	/**
	 * 
	 * Return the number of seats left of a departure flight round trip search
	 * result.
	 * 
	 * @return the number of seats left of a departure flight round trip search
	 *         result.
	 */
	private int getCustDepTicket() {
		int depTicketLeft = Integer.MAX_VALUE;
		for (int i = 0; i < depcoachstatus.size(); i++) {
			if (depcoachstatus.get(i).isSelected()) {
				if (depvoyage.getLeaveFlights().get(i).getCoach_Left() < depTicketLeft)
					depTicketLeft = depvoyage.getLeaveFlights().get(i)
							.getCoach_Left();
			} else {
				if (depvoyage.getLeaveFlights().get(i).getFirstClass_Left() < depTicketLeft)
					depTicketLeft = depvoyage.getLeaveFlights().get(i)
							.getFirstClass_Left();
			}
		}

		return depTicketLeft;

	}

	/**
	 * 
	 * Return the number of seats left of the return flight of a round trip
	 * search result.
	 * 
	 * @return the number of seats left of the return flight of a round trip
	 *         search result.
	 */
	private int getCustRetTicket() {
		int retTicketLeft = Integer.MAX_VALUE;
		for (int i = 0; i < retcoachstatus.size(); i++) {
			if (retcoachstatus.get(i).isSelected()) {
				if (retvoyage.getLeaveFlights().get(i).getCoach_Left() < retTicketLeft)
					retTicketLeft = retvoyage.getLeaveFlights().get(i)
							.getCoach_Left();
			} else {
				if (retvoyage.getLeaveFlights().get(i).getFirstClass_Left() < retTicketLeft)
					retTicketLeft = retvoyage.getLeaveFlights().get(i)
							.getFirstClass_Left();
			}
		}

		return retTicketLeft;

	}
}
