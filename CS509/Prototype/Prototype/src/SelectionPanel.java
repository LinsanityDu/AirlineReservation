import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class SelectionPanel extends JPanel{
    
	public SelectionPanel(final Flight flight, final DOMParser p){
		super();
		
		GridBagLayout layout = new GridBagLayout();
   	    setLayout(layout);
        setPreferredSize(new Dimension(760,160));
        setMaximumSize(new Dimension(760,160));
		
		JPanel showPrice = new JPanel();
        showPrice.setLayout(new FlowLayout());
        showPrice.setBorder(BorderFactory.createEtchedBorder());

   	 
   	    
        float left = (flight.getCoach_Left()!=0)?flight.getCoach_Price():flight.getFirstClass_Price();
        
        JLabel Jprice = new JLabel("Lowest price: $"+ left);
        JButton selectButton = new JButton("SELECT");
        selectButton.addMouseListener(new MouseAdapter(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				PurchaseDialog dia = new PurchaseDialog(flight, p);
				dia.show();
			}	
			});
        
        JLabel Jnumber = new JLabel("Flight "+flight.getNumber());
        JLabel Jdepart = new JLabel(flight.getDep_Code());
        JLabel Jdedate = new JLabel(flight.getDep_Time());
        JLabel Jfirstp = new JLabel("FirstClass: $"+flight.getFirstClass_Price()+"("+flight.getFirstClass_Left()+" tickets left)");
        JLabel Jtime = new JLabel("Total Trip: "+flight.getFlightTime()/60+"h"+flight.getFlightTime()%60+"m");
        JLabel Jarrival = new JLabel(flight.getArv_Code());
        JLabel Javdate = new JLabel(flight.getArv_Time());
        JLabel Jcoachp = new JLabel("Coach: $"+flight.getCoach_Price()+"("+flight.getCoach_Left()+" tickets left)");
              
        showPrice.add(Jprice);
        showPrice.add(selectButton);
        
        add(showPrice);
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
        s.gridwidth = 0;
        s.weightx = 1;
        s.weighty = 1;
        s.gridy = 0;
        s.gridx =0;
        layout.setConstraints(showPrice, s);
        s.weighty = 2;
        s.gridwidth = 1;
        s.gridy = 1;
        s.gridx =0;
        layout.setConstraints(Jnumber,s);
        s.gridx =1;
        layout.setConstraints(Jdepart,s);
        s.gridx =2;
        layout.setConstraints(Jdedate,s);
        s.gridwidth = 0;
        s.gridx =3;
        layout.setConstraints(Jfirstp,s);
        s.weighty = 2;
        s.gridwidth = 1;
        s.gridy = 2;
        s.gridx =0;
        layout.setConstraints(Jtime,s);
        s.gridx =1;
        layout.setConstraints(Jarrival,s);
        s.gridx =2;
        layout.setConstraints(Javdate,s);
        s.gridwidth = 0;
        s.gridx =3;
        layout.setConstraints(Jcoachp,s);

        
		
		
	}
	
	
}
