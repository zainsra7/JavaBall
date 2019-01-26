package javaball;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

public class Gui extends JFrame implements ActionListener{

	Tournament tournament;
	JPanel buttons;
	JButton withdrawButn;
	JButton resultsInButn;
	JButton rankedTableButn;
	JButton exitButn;
	JButton addResultsButn;
	JTextArea matchesListView;
	
	
	Gui(Tournament t){
		tournament = t;
		
		//Setting Main Window
		setTitle("Zain-JavaBallMatches");
		setSize(450,600);
		setLocation(550,200);
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //Disabling Exit Button of the main window
		
		//Setting Buttons
		buttons = new JPanel();
		withdrawButn = new JButton("Withdraw");
		resultsInButn = new JButton("ResultsIn");
		rankedTableButn = new JButton("RankTable");
		addResultsButn = new JButton("Add Results");
		exitButn = new JButton("Exit");
		
		addResultsButn.setEnabled(false);
		rankedTableButn.setEnabled(false);
		
		String matches = t.read_TeamsIn(); //Reading TeamsIn.txt and returning Matches list (Initialization)
		
		if(matches.equals("File not Found!")) {
			//Display Message
			JOptionPane.showMessageDialog(null, matches, "Error Message", JOptionPane.ERROR_MESSAGE);
			
			//Exit Program
			System.exit(0);
		}
		
		String teams = t.getTeamList(); //Getting List of all Teams
		
		matchesListView = new JTextArea(teams + "\n\n"+ matches,10,50);
		matchesListView.setEditable(false); //Read-Only TextArea
		matchesListView.setBackground(Color.DARK_GRAY);
		matchesListView.setForeground(Color.white);
		
		
		JScrollPane scroll = new JScrollPane (matchesListView, 
				   JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); //Making TextArea Scrollable
		
		//Linking ActionListeners for Button Presses
		withdrawButn.addActionListener(this);
		resultsInButn.addActionListener(this);
		addResultsButn.addActionListener(this);
		rankedTableButn.addActionListener(this);
		exitButn.addActionListener(this);
		
		//Adding buttons to the Panel
		buttons.add(withdrawButn);
		buttons.add(addResultsButn);
		buttons.add(resultsInButn);
		buttons.add(rankedTableButn);
		buttons.add(exitButn);
		
		buttons.setLayout(new GridLayout(5,1));
		
		//Adding Panel and TextArea in the Frame (Main Window)
		add(scroll);
		add(buttons);
		
		setLayout(new GridLayout(2,1));
		setVisible(true); //Displaying main Window
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		
		if(e.getSource() == withdrawButn) {
			
			WithdrawGui wtg = new WithdrawGui(tournament,matchesListView);
			
		}else if(e.getSource() == resultsInButn) {
			
			String teams = tournament.getTeamList();
			String message = tournament.read_ResultsIn(); //Reading ResultsIn
			
			if(message.equals("File not Found!")) {
				//Display Message
				JOptionPane.showMessageDialog(null, message, "Error Message", JOptionPane.ERROR_MESSAGE);
				
				//Exit Program
				System.exit(0);
			}else {
				matchesListView.setText(teams + "\n\n" + message);
				
				//Teams can't withdraw after ResultsIn have been Processed!
				withdrawButn.setEnabled(false);
				
				//Check if all the results have been processed, if so then enable Rank-Table Button
				if(!tournament.isAnyMatch_Left()) {
					rankedTableButn.setEnabled(true);
				}
				
				//The functionality to add results manually should only be enabled after ResutlsIn.txt has been read.
				addResultsButn.setEnabled(true);
				
				//ResultsIn.txt can be read only once, so after this Disable the button
				resultsInButn.setEnabled(false);
			}
			
			
		}else if(e.getSource() == addResultsButn) {
			
			//if All matches are already processed then display message "All matches have been processed, Can't add new Results"
			if(!tournament.isAnyMatch_Left()) {
				JOptionPane.showMessageDialog(null, "All matches have been processed! Can't add new Results", "Message", JOptionPane.ERROR_MESSAGE);
			}else {
				AddResultGui adg = new AddResultGui(tournament,matchesListView,rankedTableButn);
			}
			
		}else if(e.getSource() == rankedTableButn) {
			RankedTableGui rt = new RankedTableGui(tournament);
			
			//Disable AddResults Button because there won't be any unprocessed matches for which a user can add results
			addResultsButn.setEnabled(false);
			
		}else if(e.getSource() == exitButn) {
			
			//Ask for Confirmation to Exit the Program
			int result =JOptionPane.showConfirmDialog(null,"Do you want to Exit the Program?");
			
			if(result == JOptionPane.YES_OPTION) {
				String message = tournament.resultsOut();
				JOptionPane.showMessageDialog(null, message, "Message", JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);
			}
		}
	}//end of actionPerformed

}
