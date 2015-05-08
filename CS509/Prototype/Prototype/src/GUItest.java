import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;


public class GUItest extends JFrame{

    private JTextField aTextField;
    private JTextField bTextField;
    private JComboBox combo;
    private JPanel showboard;
    
    public GUItest()
    {
        super();

        
        final DOMParser p = new DOMParser();
     	try{
     	   UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
     	   SwingUtilities.updateComponentTreeUI(this);
     	   this.pack();
     	}catch(Exception e){
     	   e.printStackTrace();
     	}
        
        setTitle("Agency Team03");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  
        final JPanel panel = new JPanel();
        getContentPane().add(panel,BorderLayout.NORTH);
        panel.add(new JLabel("From: "));
        aTextField = new JTextField("BOS",3);
        panel.add(aTextField);
        panel.add(new JLabel("To: "));
        bTextField = new JTextField("PHL",3);
        panel.add(bTextField);
        panel.add(new JLabel("Depart Date: "));
        
        String[] cont = {"2015_05_07", "2015_05_08","2015_05_09","2015_05_10","2015_05_11","2015_05_12","2015_05_13","2015_05_14","2015_05_15","2015_05_16","2015_05_17"};
        combo =new JComboBox(cont);
        panel.add(combo);
        
        final JButton addButton = new JButton("Search"); 
       
        panel.add(addButton);  
        
        
        showboard = new JPanel();
        showboard.setLayout(new BoxLayout(showboard, BoxLayout.Y_AXIS));
        

        JScrollPane scrollPane = new JScrollPane(showboard);
        getContentPane().add(scrollPane,BorderLayout.CENTER);
                
        
        scrollPane.setViewportView(showboard);
        addButton.addActionListener(new ActionListener(){//Ìí¼ÓÊÂ¼þ
            public void actionPerformed(ActionEvent e){
            	
            	List<Flight> flights = p.search("Team03", aTextField.getText(), bTextField.getText(), combo.getSelectedItem().toString());
            	int index = 0;
        		
        		System.out.println("These are the searching results:");
        		for(final Flight flight: flights) {
        			System.out.println("\nSelection "+ ++index);
        			System.out.println(flight.toString());
        			new Thread(new Runnable(){

						@Override
						public void run() {
							// TODO Auto-generated method stub
							showboard.removeAll();
							showboard.add(Box.createVerticalStrut(10));

					        
					        SelectionPanel selectionP = new SelectionPanel(flight, p);
					        selectionP.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"SELECTION "));
					        showboard.add(selectionP);
					        showboard.add(Box.createVerticalStrut(10));
					        	
							showboard.updateUI();
							
						}}).start();
        		
        		}
        		if (index == 0) {
        			new Thread(new Runnable(){

						@Override
						public void run() {
							// TODO Auto-generated method stub
							showboard.removeAll();
							showboard.updateUI();
		        			System.out.println("Flight not found!");
							
						}}).start();

        		}
            }
        });
        
        this.setSize(800, 600);
        int width = getToolkit().getScreenSize().width;
        int height = getToolkit().getScreenSize().height;

        int left = (width - this.getSize().width) / 2;
        int top = (height - this.getSize().height) /2;
        
        this.setLocation(left,top);
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
    	
        // TODO Auto-generated method stub
        GUItest guitest= new GUItest();
        guitest.setVisible(true);
    }

}