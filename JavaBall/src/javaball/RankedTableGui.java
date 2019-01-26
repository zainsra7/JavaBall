package javaball;
import javax.swing.*;

public class RankedTableGui extends JFrame{

	Tournament tournament;
	
	RankedTableGui(Tournament t){
		tournament = t;
		setTitle("Ranked Table");
		setSize(450,300);
		setLocation(200,200);
		
		String data[][]=tournament.generate_RankedTable();
		String columns[] = {"Team","Rank","Won","Drawn","Lost","GFor","GAgst","MtchPts","GoalDiff","Medal"};
		
	    JTable jt=new JTable(data,columns);
	    jt.setBounds(0,0,500,500);          
	    JScrollPane sp=new JScrollPane(jt);    
		add(sp);
		setVisible(true);
	}
}