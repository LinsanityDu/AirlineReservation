import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


public class PurchaseDialog extends JDialog{
	
	 private final JComboBox field;
	 
     public PurchaseDialog(final Flight flight, final DOMParser p){
    	 super();
    	 setTitle("info");
    	 setModal(true);
    	 
    	 JPanel info  = new JPanel();
    	 GridBagLayout layout = new GridBagLayout(); 
    	 info.setLayout(layout);
    	 GridBagConstraints s = new GridBagConstraints();
    	 s.fill = GridBagConstraints.BOTH;
    	 
    	 final ButtonGroup group = new ButtonGroup();
    	 final JRadioButton coach = new JRadioButton("Coach");
    	 coach.setSelected(true);
    	 coach.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				field.removeAll();
				field.addItem(0);
		    	for (int i=0; i<flight.getCoach_Left(); i++)
		    		 field.addItem(i+1);
		    	field.updateUI();
			}});
    	 final JRadioButton firstclass = new JRadioButton("First-Class"); 
    	 firstclass.addActionListener(new ActionListener(){

 			@Override
 			public void actionPerformed(ActionEvent arg0) {
 				// TODO Auto-generated method stub
 				field.removeAllItems();;
 				field.addItem(0);
 		    	for (int i=0; i<flight.getFirstClass_Left(); i++)
 		    		 field.addItem(i+1);
 		    	field.updateUI();
 			}});
    	 
    	 group.add(coach);
    	 group.add(firstclass);
    	 
    	 final JLabel num = new JLabel("amount: ");
    	 field = new JComboBox();
    	 field.addItem(0);
    	 for (int i=0; i<flight.getCoach_Left(); i++)
    		 field.addItem(i+1);
    	 
    	 s.gridwidth = 1;
    	 s.gridx = 0;
    	 s.gridy = 0;
    	 info.add(coach, s);
    	 s.gridx = 1;
    	 s.gridwidth = 0;
    	 info.add(firstclass, s);
    	 s.gridx = 0;
    	 s.gridy = 1;
    	 s.gridwidth = 1;
    	 info.add(num, s);
    	 s.gridx = 1;
    	 s.gridwidth = 0;
    	 info.add(field, s);
    	 
    	
    	
    	 final JButton ok = new JButton("Ok");
         final JButton cancel = new JButton("Cancel");
         ok.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				boolean pass = true;
				
				int amount = Integer.parseInt(field.getSelectedItem().toString());
				
				if (pass){
		      		List<Order> orders = new ArrayList<Order>();
				    Order order = new Order(flight.getNumber(), flight, (firstclass.isSelected())?"FirstClass":"Coach", Integer.parseInt(field.getSelectedItem().toString()));
				    orders.add(order);
				    p.buyTicket(orders);
				    }
			
					
				setVisible(false);
			}});
         
         
         JPanel action = new JPanel();
         action.add(ok);
         action.add(cancel);
         add(info,BorderLayout.NORTH);
         add(action,BorderLayout.SOUTH);
         pack();
         
         int width = getToolkit().getScreenSize().width;
         int height = getToolkit().getScreenSize().height;

         int left = (width - this.getSize().width) / 2;
         int top = (height - this.getSize().height) /2;
         
         this.setLocation(left,top);
         
     }
}
