package javaball;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class WithdrawGui extends JFrame implements ActionListener {

	Tournament tournament;
	JComboBox comboBox;
	JButton submit;
	JTextArea mainDisplay;
	
	WithdrawGui(Tournament t,JTextArea display){
		tournament = t;
		mainDisplay = display;
		setTitle("Withdraw-Team");
		setSize(200,200);
		setLocation(200,200);
		
		String [] teams = tournament.getTeamAsArray();
		comboBox = new JComboBox(teams);
		submit = new JButton("Withdraw");
		submit.addActionListener(this);
		add(comboBox,"North");
		add(submit,"South");
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	if(e.getSource() == submit) {
			
		//Ask for Confirmation to Withdraw the team
		int result =JOptionPane.showConfirmDialog(null,"Do you want to remove "+comboBox.getItemAt(comboBox.getSelectedIndex())+" from the tournament?");
			
		if(result == JOptionPane.YES_OPTION) {
			String message = tournament.withdraw(""+ comboBox.getItemAt(comboBox.getSelectedIndex()));
			JOptionPane.showMessageDialog(null, message, "Message", JOptionPane.INFORMATION_MESSAGE);
			
			//Update Teams-List and Matches List for mainDisplay
			String display = tournament.getTeamList()+"\n\n" + tournament.getMatchesList(); //Getting List of all Teams and Matches List
			mainDisplay.setText(display);
			
			//If Fewer than 3 teams remain then cancel the tournament		
			if(tournament.getTeams().size() < 3) {
				//Display Message
				JOptionPane.showMessageDialog(null, "Less than 3 teams are left! Tournament Cancelled!", "Less Teams", JOptionPane.ERROR_MESSAGE);
				
				//Exit Program
				System.exit(0);
			}
		}
			//Close and dispose the withdraw window
			setVisible(false);
			dispose();
		}
	}
}
