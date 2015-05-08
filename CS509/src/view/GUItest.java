package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import controller.ReservationSys;
import controller.TimeSys;
import model.Airport;
import model.Voyage;

/**
 * 
 * GUI main program. -- singleton
 * 
 * Start up the reservation system.
 * 
 *
 */
public class GUItest extends JFrame {

	private JComboBox departField;
	private JComboBox arrivalField;
	private JComboBox departdate;
	private JComboBox departArvdate;
	private JComboBox returndate;
	private JComboBox returnArvdate;
	private JComboBox stopover;
	private JComboBox sortItem;
	private JComboBox departafterH;
	private JComboBox departafterM;
	private JComboBox departbeforeH;
	private JComboBox departbeforeM;
	private JComboBox returnafterH;
	private JComboBox returnafterM; 
	private JComboBox returnbeforeH;
	private JComboBox returnbeforeM;
	private JPanel showboard, reserv, DepF, RetF;
	private JCheckBox rp;
	final private ReservationSys reservsys;
	final private TimeSys timesys;
	final private GUItest frame;
	private List<Voyage> voyages;
	private Voyage depVoyage, retVoyage;
	private boolean isSearching;
	private JButton enter, clear;

	/**
	 * initial self-instance prepared for Singleton
	 */
	private static GUItest instance = null;

	/**
	 * get GUItest instance based on Singleton Pattern
	 * 
	 * @return GUItest instance
	 */
	public static GUItest getInstance() {
		if (instance == null) {
			synchronized (GUItest.class) {
				if (instance == null) {
					instance = new GUItest();
				}
			}
		}
		return instance;
	}

	/**
	 * 
	 * Create the window of reservation system.
	 * 
	 */

	public GUItest() {
		super();
		
		reservsys = ReservationSys.getInstance();
		timesys = TimeSys.getInstance();
		voyages = null;
		depVoyage = null;
		retVoyage = null;
		isSearching = false;
		frame = this;
		

		try {

			UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this);

			this.pack();
		} catch (Exception e) {
			e.printStackTrace();
		}

		JFrame.setDefaultLookAndFeelDecorated(true);
		setTitle("Agency Team03");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel combine = new JPanel();
		combine.setLayout(new BoxLayout(combine, BoxLayout.Y_AXIS));
		getContentPane().add(combine, BorderLayout.NORTH);

		// For reservation
		reserv = new JPanel();
		reserv.setPreferredSize(new Dimension(
				getToolkit().getScreenSize().width, 160));
		reserv.setMinimumSize(new Dimension(getToolkit().getScreenSize().width,
				160));
		reserv.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		reserv.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

		JPanel DepV = new JPanel();
		DepV.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		DepV.setPreferredSize(new Dimension(getToolkit().getScreenSize().width,
				30));
		DepV.setMinimumSize(new Dimension(getToolkit().getScreenSize().width,
				30));
		reserv.add(DepV);
		JLabel tipDep = new JLabel("Depart Flights");
		DepV.add(tipDep);

		DepF = new JPanel();
		final JScrollPane DepscrollPane = new JScrollPane(DepF);
		DepscrollPane.setPreferredSize(new Dimension(getToolkit()
				.getScreenSize().width, 90));
		DepscrollPane.setMinimumSize(new Dimension(
				getToolkit().getScreenSize().width, 90));
		DepscrollPane.setViewportView(DepF);
		reserv.add(DepscrollPane);

		final JPanel RetV = new JPanel();
		RetV.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		RetV.setPreferredSize(new Dimension(getToolkit().getScreenSize().width,
				30));
		RetV.setMinimumSize(new Dimension(getToolkit().getScreenSize().width,
				30));
		reserv.add(RetV);
		JLabel tipRet = new JLabel("Return Flights");
		RetV.add(tipRet);

		RetF = new JPanel();
		final JScrollPane RetscrollPane = new JScrollPane(RetF);
		RetscrollPane.setPreferredSize(new Dimension(getToolkit()
				.getScreenSize().width, 90));
		RetscrollPane.setMinimumSize(new Dimension(
				getToolkit().getScreenSize().width, 90));
		RetscrollPane.setViewportView(RetF);
		reserv.add(RetscrollPane);

		RetV.setVisible(false);
		RetscrollPane.setVisible(false);

		final JPanel makeReserv = new JPanel();
		makeReserv.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		makeReserv.setPreferredSize(new Dimension(
				getToolkit().getScreenSize().width, 39));
		makeReserv.setMinimumSize(new Dimension(
				getToolkit().getScreenSize().width, 39));
		reserv.add(makeReserv);
		enter = new JButton(" OK ");
		makeReserv.add(enter);
		enter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ((!rp.isSelected() && depVoyage != null)
						|| (rp.isSelected() && depVoyage != null && retVoyage != null)) {
					reservsys.lockDB();
					depVoyage = reservsys.doubleCheck(depVoyage);
					retVoyage = reservsys.doubleCheck(retVoyage);
					PurchaseDialog dia = new PurchaseDialog(depVoyage,
							retVoyage);
					dia.setVisible(true);
					dia.dispose();
					if (dia.isBuy()) {
						showSuccess();
						clear();
						enter.setEnabled(false);
						clear.setEnabled(false);
					} else if (dia.isClose()) {

					} else {
						showFail();
					}
					// search();
					dia = null;
				}
			}
		});
		enter.setEnabled(false);
		clear = new JButton("Clear");
		makeReserv.add(clear);
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeAllResults();
				clear();
				clear.setEnabled(false);
				enter.setEnabled(false);
			}
		});
		clear.setEnabled(false);
		final JPanel up = new JPanel();
		final JPanel mid1 = new JPanel();
		final JPanel mid2 = new JPanel();
		final JPanel down = new JPanel();
		
		// GridBagLayout layout = new GridBagLayout();
		// panel.setLayout(layout);
		// GridBagConstraints s = new GridBagConstraints();
		// s.fill = GridBagConstraints.BOTH;

		combine.add(up);
		combine.add(mid1);
		combine.add(mid2);
		combine.add(down);
		combine.add(reserv);

		// for menu part
		up.add(new JLabel("From: "));
		departField = new JComboBox();
		up.add(departField);
		up.add(new JLabel("To: "));
		arrivalField = new JComboBox();
		up.add(arrivalField);

		List<Airport> airports = reservsys.getAirports();
		for (Airport airport : airports) {
			departField.addItem(airport.getCode());
			arrivalField.addItem(airport.getCode());
		}

		departField.setSelectedItem("BOS");
		arrivalField.setSelectedItem("PHL");

		mid1.add(new JLabel("Depart: Leave Date: "));

		String[] cont = { "2015_05_08", "2015_05_09", "2015_05_10",
				"2015_05_11", "2015_05_12", "2015_05_13", "2015_05_14",
				"2015_05_15", "2015_05_16", "2015_05_17" };
		departdate = new JComboBox(cont);
		mid1.add(departdate);

		mid2.add(new JLabel("Return: Leave Date: "));

		String[] rcont = { "2015_05_08", "2015_05_09", "2015_05_10",
				"2015_05_11", "2015_05_12", "2015_05_13", "2015_05_14",
				"2015_05_15", "2015_05_16", "2015_05_17" };
		returndate = new JComboBox(rcont);
		returndate.setEnabled(false);
		
		mid2.add(returndate);
		
		mid1.add(new JLabel("Depart after: "));
		departafterH = new JComboBox();
	
		mid1.add(departafterH);
		mid1.add(new JLabel(":"));
		departafterM = new JComboBox();
		mid1.add(departafterM);
		
		mid1.add(new JLabel("Arrival Date: "));
		departArvdate = new JComboBox(cont);
		mid1.add(departArvdate);
		
		mid1.add(new JLabel("Arrival before: "));
		departbeforeH = new JComboBox();
	
		mid1.add(departbeforeH);
		mid1.add(new JLabel(":"));
		departbeforeM = new JComboBox();
		mid1.add(departbeforeM);
		
		mid2.add(new JLabel("Return after: "));
		returnafterH = new JComboBox();
		mid2.add(returnafterH);
		mid2.add(new JLabel(":"));
		returnafterM = new JComboBox();
		mid2.add(returnafterM);
		
		mid2.add(new JLabel("Arrival Date: "));
		returnArvdate = new JComboBox(cont);
		mid2.add(returnArvdate);

		mid2.add(new JLabel("Arrival before: "));
		returnbeforeH = new JComboBox();
	
		mid2.add(returnbeforeH);
		mid2.add(new JLabel(":"));
		returnbeforeM = new JComboBox();
		mid2.add(returnbeforeM);
		
		returnArvdate.setEnabled(false);
		returnafterH.setEnabled(false);
	    returnbeforeH.setEnabled(false);
	    returnafterM.setEnabled(false);
	    returnbeforeM.setEnabled(false);
		
		rp = new JCheckBox("Round-trip", false);
		rp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (rp.isSelected()) {
					returndate.setEnabled(true);
					returnArvdate.setEnabled(true);
					returnafterH.setEnabled(true);
				    returnbeforeH.setEnabled(true);
				    returnafterM.setEnabled(true);
				    returnbeforeM.setEnabled(true);
					RetV.setVisible(true);
					RetscrollPane.setVisible(true);
					reserv.setPreferredSize(new Dimension(getToolkit()
							.getScreenSize().width, 160 + 120));
					reserv.setMinimumSize(new Dimension(getToolkit()
							.getScreenSize().width, 160 + 120));
					clear();
					removeAllResults();
				} else {
					returndate.setEnabled(false);
					returnArvdate.setEnabled(false);
					returnafterH.setEnabled(false);
				    returnbeforeH.setEnabled(false);
				    returnafterM.setEnabled(false);
				    returnbeforeM.setEnabled(false);
					RetV.setVisible(false);
					RetscrollPane.setVisible(false);
					reserv.setPreferredSize(new Dimension(getToolkit()
							.getScreenSize().width, 160));
					reserv.setMinimumSize(new Dimension(getToolkit()
							.getScreenSize().width, 160));
					clear();
					removeAllResults();
				}
				enter.setEnabled(false);
				clear.setEnabled(false);
			}
		});
		// s.gridwidth = 0;
		up.add(rp);

		// s.gridx = 4;
		// s.gridwidth = 1;
	
		
		for(int i=0; i<24; i++){
			if(i<10){
				departafterH.addItem("0"+i);
			    returnafterH.addItem("0"+i);
			    departbeforeH.addItem("0"+i);
			    returnbeforeH.addItem("0"+i);
			}else{
				departafterH.addItem(i);
			    returnafterH.addItem(i);
				departbeforeH.addItem(i);
			    returnbeforeH.addItem(i);
			}
		}
		for(int i=0; i<60; i++){
			if(i<10){
				departafterM.addItem("0"+i);
			    returnafterM.addItem("0"+i);
			    departbeforeM.addItem("0"+i);
			    returnbeforeM.addItem("0"+i);
			}else{
				departafterM.addItem(i);
				returnafterM.addItem(i);
				departbeforeM.addItem(i);
				returnbeforeM.addItem(i);
			}
		}
	
		departbeforeH.setSelectedIndex(23);
	    returnbeforeH.setSelectedIndex(23);
	    departbeforeM.setSelectedIndex(59);
	    returnbeforeM.setSelectedIndex(59);
		
		
		down.add(new JLabel("Maximum stops: "));
		stopover = new JComboBox();
		stopover.addItem("nonstop");
		stopover.addItem("1 stop");
		stopover.addItem("2 stops");
		down.add(stopover);

		down.add(new JLabel("Sort by: "));
		sortItem = new JComboBox();
		sortItem.addItem("Time");
		sortItem.addItem("Price");
		down.add(sortItem);

		sortItem.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				// TODO Auto-generated method stub
				if (arg0.getStateChange() == ItemEvent.SELECTED) {
					sort();
				}
			}
		});

		JButton addButton = new JButton("Search");

		down.add(addButton);

		showboard = new JPanel();
		showboard.setLayout(new BoxLayout(showboard, BoxLayout.Y_AXIS));

		// showboard.add(reserv);

		JScrollPane scrollPane = new JScrollPane(showboard);
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		scrollPane.setViewportView(showboard);
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clear();
				enter.setEnabled(false);
				clear.setEnabled(false);
				isSearching = true;
				searchDep();
				final WaitDialog wd = new WaitDialog();
				wd.setLocationRelativeTo(frame);

				new Thread(new Runnable() {

					@Override
					public void run() {

						while (isSearching) {
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
		});

		/*
		 * int width = getToolkit().getScreenSize().width; int height =
		 * getToolkit().getScreenSize().height;
		 * 
		 * int left = (width - this.getSize().width) / 2; int top = (height -
		 * this.getSize().height) / 2;
		 * 
		 * this.setLocation(left, top);
		 * 
		 * Dimension screenSize = getToolkit().getScreenSize(); Rectangle bounds
		 * = new Rectangle( screenSize );
		 * 
		 * this.setBounds( bounds );
		 */

		this.setSize(860, 660);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	/**
	 * 
	 * Start search -- Departure.
	 * 
	 */
	public void searchDep() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				showboard.removeAll();
				String GMTtimeS = timesys.timeConvertoGMT(departdate.getSelectedItem().toString()+" "+departafterH.getSelectedItem().toString()+":"+departafterM.getSelectedItem().toString(), departField.getSelectedItem()
						.toString());
				String GMTtimeE = timesys.timeConvertoGMT(departdate.getSelectedItem().toString()+" 23:59", departField.getSelectedItem()
						.toString());
				String GMTtimeSpecifiedE = timesys.timeConvertoGMT(departArvdate.getSelectedItem().toString()+" "+departbeforeH.getSelectedItem().toString()+":"+departbeforeM.getSelectedItem().toString(), arrivalField.getSelectedItem()
						.toString());

				voyages = reservsys.searchDep(departField.getSelectedItem()
						.toString(), arrivalField.getSelectedItem().toString(),
						GMTtimeS, GMTtimeE, stopover
								.getSelectedIndex());

				Collections.sort(voyages, new Comparator<Voyage>() {

					public int compare(Voyage o1, Voyage o2) {
						int result = o1.getTotalTime() - o2.getTotalTime();
						return result;
					}
				});

				voyages = voyages.subList(0,
						voyages.size() > 30 ? 30 : voyages.size());
				System.out.println(voyages.size());
				int size = voyages.size();
				for(int i=0; i<size; i++){
					String t = voyages.get(i).getLeaveFlights().get(0).getDep_Time();
					String et = voyages.get(i).getLeaveFlights().get(voyages.get(i).getLeaveFlights().size()-1).getArv_Time();
					//System.out.println(et);
					//String std = t.substring(0, t.indexOf(":")-2)+departafterH.getSelectedItem().toString()+":"+departafterM.getSelectedItem().toString();
					//System.out.println(t+" "+GMTtimeS+" "+GMTtimeE);
					if(!timesys.satisfyDep(t, GMTtimeS, GMTtimeE) || !timesys.satisfyArv(et, GMTtimeSpecifiedE)){
						
						voyages.remove(voyages.get(i));
						i--;
						size--;					
					}
				}

				if (!sortItem.getSelectedItem().equals("Time")) {
					Collections.sort(voyages, new Comparator<Voyage>() {
						public int compare(Voyage o1, Voyage o2) {
							int result = (int) (o1.getTotalPrice() * 1000)
									- (int) (o2.getTotalPrice() * 1000);
							return result;
						}
					});
				}

				int index = 0;

				System.out.println("These are the searching results:");
				for (final Voyage voyage : voyages) {
					// System.out.println(voyage.toString());

					// TODO Auto-generated method stub
					index++;
					showboard.add(Box.createVerticalStrut(10));

					SelectionPanel selectionP = new SelectionPanel(voyage,
							false);
					selectionP.setBorder(BorderFactory.createTitledBorder(
							BorderFactory.createEtchedBorder(), ""));
					showboard.add(selectionP);
					showboard.add(Box.createVerticalStrut(10));

					showboard.updateUI();

				}

				System.out.println(index);
				if (index == 0) {

					// TODO Auto-generated method stub
					showboard.removeAll();
					showboard.updateUI();
					final JOptionPane optionPane = new JOptionPane(
							"Sorry! There are not availbale flights. You can try another dates.",
							JOptionPane.INFORMATION_MESSAGE,
							JOptionPane.CLOSED_OPTION);
					final JDialog dialog = new JDialog(frame, "info", true);
					isSearching = false;
					dialog.setContentPane(optionPane);
					optionPane
							.addPropertyChangeListener(new PropertyChangeListener() {
								public void propertyChange(PropertyChangeEvent e) {
									String prop = e.getPropertyName();
									if (dialog.isVisible()
											&& e.getSource() == optionPane
											&& JOptionPane.VALUE_PROPERTY
													.equals(prop)) {
										// If you were going to check something
										// before closing the window, you'd do
										// it here.
										dialog.setVisible(false);
										dialog.dispose();
									}
								}
							});
					dialog.pack();
					dialog.setLocationRelativeTo(frame);
					dialog.setVisible(true);
					clear();
					clear.setEnabled(false);
					System.out
							.println("Sorry! There are not return flights. You can try another dates.");

				}
				isSearching = false;
			}
		}).start();
	}

	/**
	 * 
	 * Start search -- Return.
	 * 
	 */
	public void searchRet() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				showboard.removeAll();
				String GMTtimeS = timesys.timeConvertoGMT(returndate.getSelectedItem().toString()+" "+returnafterH.getSelectedItem().toString()+":"+returnafterM.getSelectedItem().toString(), arrivalField.getSelectedItem()
						.toString());
				String GMTtimeE = timesys.timeConvertoGMT(returndate.getSelectedItem().toString()+" 23:59", arrivalField.getSelectedItem()
						.toString());
				String GMTtimeSpecifiedE = timesys.timeConvertoGMT(returnArvdate.getSelectedItem().toString()+" "+returnbeforeH.getSelectedItem().toString()+":"+returnbeforeM.getSelectedItem().toString(), departField.getSelectedItem()
						.toString());

				voyages = reservsys.searchRet(
						arrivalField.getSelectedItem().toString(),
						departField.getSelectedItem().toString(),
						GMTtimeS,GMTtimeE,
						depVoyage.getLeaveFlights()
								.get(depVoyage.getLeaveFlights().size() - 1)
								.getArv_Time(), stopover.getSelectedIndex());

				Collections.sort(voyages, new Comparator<Voyage>() {

					public int compare(Voyage o1, Voyage o2) {
						int result = o1.getTotalTime() - o2.getTotalTime();
						return result;
					}
				});

				voyages = voyages.subList(0,
						voyages.size() > 30 ? 30 : voyages.size());

				int size = voyages.size();
				for(int i=0; i<size; i++){
					String t = voyages.get(i).getLeaveFlights().get(0).getDep_Time();
					String et = voyages.get(i).getLeaveFlights().get(voyages.get(i).getLeaveFlights().size()-1).getArv_Time();
					//String std = t.substring(0, t.indexOf(":")-2)+departafterH.getSelectedItem().toString()+":"+departafterM.getSelectedItem().toString();
					//System.out.println(t+" "+GMTtimeS+" "+GMTtimeE);
					if(!timesys.satisfyDep(t, GMTtimeS, GMTtimeE) || !timesys.satisfyArv(et, GMTtimeSpecifiedE)){
						voyages.remove(voyages.get(i));
						i--;
						size--;					
					}
				}
				
				if (!sortItem.getSelectedItem().equals("Time")) {
					Collections.sort(voyages, new Comparator<Voyage>() {

						public int compare(Voyage o1, Voyage o2) {
							int result = (int) (o1.getTotalPrice() * 1000)
									- (int) (o2.getTotalPrice() * 1000);
							return result;
						}
					});
					// System.out.println("sssss");
				}

				int index = 0;

				System.out.println("These are the searching results:");
				for (final Voyage voyage : voyages) {
					// System.out.println(voyage.toString());

					// TODO Auto-generated method stub
					index++;
					showboard.add(Box.createVerticalStrut(10));

					SelectionPanel selectionP = new SelectionPanel(voyage,
							false);
					selectionP.setBorder(BorderFactory.createTitledBorder(
							BorderFactory.createEtchedBorder(), ""));
					showboard.add(selectionP);
					showboard.add(Box.createVerticalStrut(10));

					showboard.updateUI();

				}

				System.out.println(index);
				if (index == 0) {

					// TODO Auto-generated method stub
					showboard.removeAll();
					showboard.updateUI();
					final JOptionPane optionPane = new JOptionPane(
							"Sorry! There are not availbale return flights. You can try another dates.",
							JOptionPane.INFORMATION_MESSAGE,
							JOptionPane.CLOSED_OPTION);
					final JDialog dialog = new JDialog(frame, "info", true);
					dialog.setContentPane(optionPane);
					isSearching = false;
					optionPane
							.addPropertyChangeListener(new PropertyChangeListener() {
								public void propertyChange(PropertyChangeEvent e) {
									String prop = e.getPropertyName();
									if (dialog.isVisible()
											&& e.getSource() == optionPane
											&& JOptionPane.VALUE_PROPERTY
													.equals(prop)) {
										// If you were going to check something
										// before closing the window, you'd do
										// it here.
										dialog.setVisible(false);
										dialog.dispose();
									}
								}
							});

					dialog.pack();
					dialog.setLocationRelativeTo(frame);
					dialog.setVisible(true);

					clear();
					clear.setEnabled(false);
					System.out
							.println("Sorry! There are not availbale return flights. You can try another dates.");

				}
				isSearching = false;
			}
		}).start();
	}

	/**
	 * 
	 * Sort the search result.
	 * 
	 */
	public void sort() {

		// TODO Auto-generated method stub
		if (voyages != null) {
			showboard.removeAll();

			if (sortItem.getSelectedItem().equals("Time"))
				Collections.sort(voyages, new Comparator<Voyage>() {

					public int compare(Voyage o1, Voyage o2) {
						int result = o1.getTotalTime() - o2.getTotalTime();
						return result;
					}
				});
			else
				Collections.sort(voyages, new Comparator<Voyage>() {

					public int compare(Voyage o1, Voyage o2) {
						int result = (int) (o1.getTotalPrice() * 1000)
								- (int) (o2.getTotalPrice() * 1000);
						return result;
					}
				});
			int index = 0;

			System.out.println("These are the searching results:");
			for (final Voyage voyage : voyages) {
				// System.out.println(voyage.toString());

				// TODO Auto-generated method stub
				index++;
				showboard.add(Box.createVerticalStrut(10));

				SelectionPanel selectionP = new SelectionPanel(voyage, false);
				selectionP.setBorder(BorderFactory.createTitledBorder(
						BorderFactory.createEtchedBorder(), ""));
				showboard.add(selectionP);
				showboard.add(Box.createVerticalStrut(10));

				showboard.updateUI();

			}

			System.out.println(index);
			if (index == 0) {

				// TODO Auto-generated method stub
				showboard.removeAll();
				showboard.updateUI();
				System.out.println("Flight not found!");

			}
		}

	}

	/**
	 * Print the select flight on the window.
	 * 
	 */
	public void addToReservation() {
		if (depVoyage != null) {
			DepF.removeAll();
			DepF.updateUI();
			DepF.add(new SelectionPanel(depVoyage, true));
			DepF.updateUI();

		}
		if (retVoyage != null) {
			RetF.removeAll();
			RetF.updateUI();
			RetF.add(new SelectionPanel(retVoyage, true));
			RetF.updateUI();
		}

	}

	/**
	 * 
	 * Set search option of round trip
	 * 
	 * @return Identify if round trip option is selected
	 */
	public boolean isRoundtrip() {
		return rp.isSelected();
	}

	/**
	 * 
	 * Clear the window
	 * 
	 */
	public void removeAllResults() {
		showboard.removeAll();
		showboard.updateUI();
		voyages = null;
	}

	/**
	 * 
	 * Set departure Voyage object
	 * 
	 * @param depVoyage
	 *            departure Voyage object
	 */
	public void setDepVoyage(Voyage depVoyage) {
		this.depVoyage = depVoyage;
	}

	/**
	 * 
	 * Get departure Voyage object
	 * 
	 * @return depVoyage departure Voyage object
	 */
	public Voyage getDepVoyage() {
		return this.depVoyage;
	}

	/**
	 * 
	 * Set the return voyage object
	 * 
	 * 
	 * @param retVoyage
	 *            return airport Voyage object
	 */
	public void setRetVoyage(Voyage retVoyage) {
		this.retVoyage = retVoyage;
	}

	/**
	 * 
	 * clear the search request information
	 * 
	 */
	public void clear() {
		depVoyage = null;
		retVoyage = null;
		DepF.removeAll();
		DepF.updateUI();
		RetF.removeAll();
		RetF.updateUI();
	}

	/**
	 * enable the OK button
	 * 
	 */
	public void enableOK() {
		enter.setEnabled(true);
	}

	/**
	 * enable the Clear button
	 * 
	 */
	public void enableClear() {
		clear.setEnabled(true);
	}

	/**
	 * 
	 * Show reservation result.
	 * 
	 */
	public void showSuccess() {
		final JOptionPane optionPane = new JOptionPane("Successful! Thank you!",
				JOptionPane.INFORMATION_MESSAGE, JOptionPane.CLOSED_OPTION);
		final JDialog dialog = new JDialog(frame, "Info", true);
		dialog.setContentPane(optionPane);

		optionPane.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				String prop = e.getPropertyName();
				if (dialog.isVisible() && e.getSource() == optionPane
						&& JOptionPane.VALUE_PROPERTY.equals(prop)) {
					// If you were going to check something
					// before closing the window, you'd do
					// it here.
					dialog.setVisible(false);
					dialog.dispose();
				}
			}
		});

		dialog.pack();
		dialog.setLocationRelativeTo(frame);
		dialog.setVisible(true);
	}

	/**
	 * 
	 * Show reservation result.
	 * 
	 */
	public void showFail() {
		final JOptionPane optionPane = new JOptionPane("Time Out!",
				JOptionPane.INFORMATION_MESSAGE, JOptionPane.CLOSED_OPTION);
		final JDialog dialog = new JDialog(frame, "Error", true);
		dialog.setContentPane(optionPane);

		optionPane.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				String prop = e.getPropertyName();
				if (dialog.isVisible() && e.getSource() == optionPane
						&& JOptionPane.VALUE_PROPERTY.equals(prop)) {
					// If you were going to check something
					// before closing the window, you'd do
					// it here.
					dialog.setVisible(false);
					dialog.dispose();
				}
			}
		});

		dialog.pack();
		dialog.setLocationRelativeTo(frame);
		dialog.setVisible(true);
	}

	/**
	 * 
	 * Paint the searching icon indicate the state of the system.
	 * 
	 * @return Identify searching status
	 */
	public boolean isSearching() {
		return isSearching;
	}

	/**
	 * 
	 * Set searching status
	 * 
	 * @param isSearching
	 *            Identify the searching status
	 */
	public void setSearching(boolean isSearching) {
		this.isSearching = isSearching;
	}

	/**
	 * 
	 * Main method of our reservation system. Start the UI interface.
	 * 
	 * 
	 * @param args
	 *            Main method args
	 */
	public static void main(String[] args) {

		// TODO Auto-generated method stub
		GUItest guitest = GUItest.getInstance();
		guitest.setVisible(true);
	}

}