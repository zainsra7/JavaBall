package javaball;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class AddResultGui extends JFrame implements ActionListener{

	Tournament tournament;
	JTextArea mainDisplay;
	JComboBox matchList;
	JTextField teamA;
	JTextField teamB;
	JTextField goalByA;
	JTextField goalByB;
	JLabel name_A;
	JLabel name_B;
	JLabel name_goal_A;
	JLabel name_goal_B;
	JButton submit;
	JButton rankedTableButton;
	
	AddResultGui(Tournament t, JTextArea display,JButton rankButn){
		tournament = t;
		mainDisplay = display;
		rankedTableButton = rankButn;
		
		setTitle("Add Results");
		setSize(300,300);
		setLocation(200,200);
		
		List<String> matchesList = tournament.getUnprocessedMatchList(); //List of all unprocessed matches
		
		String[] matches_list = matchesList.toArray(new String[0]);
		
		matchList = new JComboBox(matches_list);
		name_A = new JLabel("Team A: ");
		name_B = new JLabel("Team B: ");
		name_goal_A = new JLabel("Goal Scored By A: ");
		name_goal_B = new JLabel("Goal Scored By B: ");
		teamA = new JTextField();
		teamB = new JTextField();
		goalByA = new JTextField("Enter Goals[0-9]");
		goalByB = new JTextField("Enter Goals[0-9]");
		submit = new JButton("Submit Results");
		
		String [] teamA_B = matchesList.get(0).split("-");
		
		
		teamA.setText(teamA_B[0]);
		teamB.setText(teamA_B[1]);
		
		//Listener for Selection Change of the MatchList ComboBox
		matchList.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				//Updating TextFields for Team A and B Names
				String input = ""+matchList.getItemAt(matchList.getSelectedIndex());
				String[] temp = input.split("-");
				teamA.setText(temp[0]);
				teamB.setText(temp[1]);
				
			}
			
		});
		
		submit.addActionListener(this);
		
		teamA.setEditable(false);
		teamB.setEditable(false);
		teamA.setBackground(Color.DARK_GRAY);
		teamA.setForeground(Color.WHITE);
		teamB.setBackground(Color.DARK_GRAY);
		teamB.setForeground(Color.WHITE);
		
		goalByA.setToolTipText("Enter Goals Scored by A team [0-9]");
		goalByB.setToolTipText("Enter Goals Scored by B team [0-9]");
		
		JPanel pan1 = new JPanel();
		pan1.add(name_A);
		pan1.add(teamA);
		pan1.add(name_B);
		pan1.add(teamB);
		
		JPanel pan2 = new JPanel();
		pan2.add(name_goal_A);
		pan2.add(goalByA);
		pan2.add(name_goal_B);
		pan2.add(goalByB);
		
		setLayout(new GridLayout(4,1));
		add(matchList);
		add(pan1);
		add(pan2);
		add(submit);
		
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == submit) {
			String goals_input_A = goalByA.getText().trim();
			String goals_input_B = goalByB.getText().trim();
			
			try {
			int g_A = Integer.parseInt(goals_input_A);
			int g_B = Integer.parseInt(goals_input_B);
			
			if(g_A < 0 || g_A > 9 || g_B < 0 || g_B > 9) {
				JOptionPane.showMessageDialog(null, "Goals can't be less than 0 or greater than 9!", "Wrong Goal Format", JOptionPane.ERROR_MESSAGE);	
			}
			else {
				
				//Ask for Confirmation to Exit the Program
				int result =JOptionPane.showConfirmDialog(null,"Do you want to Submit these goals?");
				
				if(result == JOptionPane.YES_OPTION) {
					String message = tournament.addResults(teamA.getText().trim(), goals_input_A, teamB.getText().trim(), goals_input_B);
					JOptionPane.showMessageDialog(null,message, "Message", JOptionPane.INFORMATION_MESSAGE);	
				
					//Update Teams-List and Matches List for mainDisplay
					String display = tournament.getTeamList()+"\n\n" + tournament.getMatchesList(); //Getting List of all Teams and Matches List
					mainDisplay.setText(display);
				
					//If there is no unprocessed match left, then enable the "Ranked table Button"
					if(!tournament.isAnyMatch_Left()) {
						rankedTableButton.setEnabled(true);
					}
					
					//Close and dispose the addResult window
					setVisible(false);
					dispose();
				}
			}
			
			}catch(NumberFormatException ex) {
				JOptionPane.showMessageDialog(null, "Goals can't be less than 0 or greater than 9!", "Wrong Goal Format", JOptionPane.ERROR_MESSAGE);	
			}
		}//end of if	
	}
}
