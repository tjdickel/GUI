
import javax.swing.*;

import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.WindowConstants;


public class Ballot extends JFrame implements ActionListener{
	
	String ballotFormat;
	int candidateNum;
	String nameList;
	String vote;
	
	ArrayList<String> array;
	ArrayList<String> array2;
	Scanner file;
	JRadioButton list;
	JCheckBox list2;
	ButtonGroup btnRadio;
	Box box;
	JLabel note;
	
	Ballot(){
		
		nameList = "";
		vote = "";
		array = new ArrayList<String>();
		array2 = new ArrayList<String>();
		box = Box.createVerticalBox();
		
		try {
			
			file = new Scanner(new File("BallotSettings.txt"));
			candidateNum =  file.nextInt();	
			
		}catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		
		file.nextLine();
		if(file.hasNextLine()){ballotFormat = file.nextLine();}
		
		int n = candidateNum;
		String line = "";
		
		while(n != 0){
			
			if(file.hasNextLine()) {line = file.nextLine();}
			
			array.add(line);
			
			nameList = nameList + line + "\n";
			
			n--;
			System.out.println(candidateNum  + "\n" + ballotFormat + "\n" + nameList);
		}
		
		JPanel panelMain = new JPanel();
		GroupLayout layout = new GroupLayout(panelMain);
		panelMain.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		JButton btnConfirm = new JButton("Confirm");
        btnConfirm.setActionCommand("Confirmation");
        btnConfirm.addActionListener(this);
		
		if(ballotFormat.charAt(0) == '1'){
			
		
		note = new JLabel("Please select the candidate you wish to vote for.");
		
		btnRadio = new ButtonGroup();
		
			for(int i = 0; i < array.size(); i++){
				
				list = new JRadioButton(array.get(i));
				list.setActionCommand(array.get(i));
				list.addActionListener(this);
				btnRadio.add(list);
				box.add(list);
	
			}
		}
		
		if(ballotFormat.charAt(0) == '2'){
			
			note = new JLabel("Please select the candidates you wish to vote for.");
			
			for(int i = 0; i < array.size(); i++){
				
				list2 = new JCheckBox(array.get(i));
				list2.setText(array.get(i));
				list2.setSelected(false);
				list2.addActionListener(this);
				
				box.add(list2);
			}
		}
		
		layout.setHorizontalGroup(
		        layout.createSequentialGroup()
		            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		            		.addComponent(note)
		            		.addComponent(box)
		            		.addComponent(btnConfirm)
		            		));
		
		layout.setVerticalGroup(
		         layout.createParallelGroup()
		            .addGroup(layout.createSequentialGroup()
		            		.addComponent(note)
		            		.addComponent(box)
		            		.addComponent(btnConfirm)
		            		));
		
		setSize(750,900);
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Voting Ballot");
		
		panelMain.setBackground(Color.white);
		
		getContentPane().add(panelMain);
	    
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
		setLocation(x, y);
		
		setVisible(true);
	}
	
	public static void main(String[] args){
		new Ballot();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(!e.getActionCommand().equals("Confirmation")){
		
			if(ballotFormat.charAt(0) == '1'){
					
				vote = btnRadio.getSelection().getActionCommand().toString();	
			}
			
			else if(ballotFormat.charAt(0) == '2'){
				
				JCheckBox cb = (JCheckBox) e.getSource();
				
				if(cb.isSelected()){
					
					array2.add(cb.getText().toString());
						
				}
				else if(!cb.isSelected()){
					
					e.setSource(cb);
					
					ArrayList<String> arrayCopy = new ArrayList<String>();
					
					for(int i = 0; i < array2.size(); i++){
						
						if(!cb.getText().equals(array2.get(i))){
							
							arrayCopy.add(array2.get(i).toString());
						}
					}
					
					array2.clear();
					array2.addAll(arrayCopy); 
				}
				
				vote = "";
					
				for(int j = 0; j < array2.size(); j++){
					
					vote = vote + array2.get(j).toString() + "\n";
				}
				
				System.out.println(vote);
			}
		}
		else{
			
			System.out.println(vote);
		}
	}
}
