import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


import javax.swing.JOptionPane;
import javax.swing.JFrame;

import java.io.*;




public class BallotBuilder extends JFrame implements ActionListener{

	JTextField candidateIn;
	ButtonGroup btnRadio;
	JTextArea candidateNames;
	String candidateList;
	int candidateNum;
	int ballotType;
	String ballotSettings;
	String ballotFile;
	ArrayList<String> array;
	
	
	BallotBuilder(){
		
		candidateList = "";
		candidateNum = 0;
		array = new ArrayList<String>();
		
		JPanel panelMain = new JPanel();
		GroupLayout layout = new GroupLayout(panelMain);
		JPanel pnlRadios = new JPanel();
		
		
		candidateIn = new JTextField();
		candidateNames = new JTextArea(15,50);
		candidateNames.setEditable(false);
		
		JLabel Lcandidate = new JLabel("Add Candidate Name");
		
		JLabel ballotSelect = new JLabel("Ballot Type:  ");
		ballotSelect.setFont(new Font(ballotSelect.getFont().getName(), Font.BOLD, 15));
		 
		pnlRadios.add(ballotSelect);
		
		JRadioButton singleChoice = new JRadioButton("Single Choice");
		singleChoice.setActionCommand("Single Choice Ballot");
		
		JRadioButton multiChoice = new JRadioButton("Multi Choice");
		multiChoice.setActionCommand("Multiple Choice Ballot");
		
		
		btnRadio = new ButtonGroup();
        btnRadio.add(singleChoice);
        btnRadio.add(multiChoice);
		
		pnlRadios.add(singleChoice);
        pnlRadios.add(multiChoice);
        
        
        
        JButton btnConfirm = new JButton("Confirm");
        btnConfirm.setActionCommand("Confirmation");
        btnConfirm.addActionListener(this);
        
        
        
        JButton btnSubmit = new JButton("Submit Name");
        btnSubmit.setActionCommand("Submitted");
        btnSubmit.addActionListener(this);
        
        JButton btnRemove = new JButton("Remove Name");
        btnRemove.setActionCommand("Removed");
        btnRemove.addActionListener(this);
        
       
		panelMain.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		  
		layout.setHorizontalGroup(
		        layout.createSequentialGroup()
		            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		            		 .addComponent(pnlRadios)
		            		 .addComponent(Lcandidate)
		            		 .addComponent(candidateIn)
		            		 .addComponent(btnSubmit)
		            		 .addComponent(btnRemove)
		            		 .addComponent(btnConfirm))
		            .addComponent(candidateNames)
		);
		    
		layout.setVerticalGroup(
		         layout.createParallelGroup()
		            .addGroup(layout.createSequentialGroup()
		            		 .addComponent(pnlRadios)
		            		 .addComponent(Lcandidate)
		            		 .addComponent(candidateIn)
		            		 .addComponent(btnSubmit)
		            		 .addComponent(btnRemove)
		            		 .addComponent(btnConfirm))
		            .addComponent(candidateNames)
		);
		    
		setSize(850, 350);
		    
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Ballot Builder");
		    
		panelMain.setBackground(Color.white);
		    
		getContentPane().add(panelMain);
		    
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
		setLocation(x, y);
		    
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		boolean popupTriggered = false;
		ballotType = 0;
		
		
		if(e.getActionCommand().equals("Confirmation")){
			
			if(btnRadio.getSelection() == null){
			
			String message = "Please select the Type of ballot you wish to use.";
			JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.OK_OPTION);
			
			}else{
			
				if(btnRadio.getSelection().getActionCommand() == "Single Choice Ballot") ballotType = 1;
				else if(btnRadio.getSelection().getActionCommand() == "Multiple Choice Ballot") ballotType = 2;
				
				popupTriggered = true;
				
				if(ballotType != 0){
					
					StringBuffer strBallot = new StringBuffer();
					strBallot.append(ballotType + "\n");
					strBallot.append(candidateNum + "\n");
					strBallot.append(candidateList);
				
					ballotFile = "" + strBallot.toString();
				}
			}
		}
		
		else if(e.getActionCommand().equals("Submitted")){
			
			candidateNum++;
			
			StringBuffer strCandidates = new StringBuffer();
			strCandidates.append(candidateIn.getText() + "\n");
			
			candidateList = candidateList +  strCandidates.toString();
			
			array.add(candidateIn.getText());
			
			System.out.println(array.toString());
			
			
		}
		else if(e.getActionCommand().equals("Removed")){
			
			candidateNum--;
			
			ArrayList<String> array2 = new ArrayList<String>();
			
			while(!array.isEmpty()){
				
				if(!candidateIn.getText().equals(array.get(0))){
					
					array2.add(array.get(0));
				}
				array.remove(0);
				
				if(!array.isEmpty()) System.out.println("\n" + array.toString() + "\n" + array2.toString());
				
			}
			
			array.clear();
			array.addAll(array2);
			
			System.out.println("\n" + array.toString());
			
		}
		
		
		if(popupTriggered){
			
			String message = "";
			
			if(ballotType == 0){
				
				message = "Please select the Type of ballot you wish to use.";
				JOptionPane.showConfirmDialog(null, message, "Confirmation", JOptionPane.OK_OPTION);
			}
			else{
			
				ballotSettings = "Type Selected: " + btnRadio.getSelection().getActionCommand() + "\n" + "\n"
						+ "Candidates" + "\n" + candidateList;
				
				message = ballotSettings + "\n" + "\n" + "Is this ballot correct?";
				
				//JOptionPane.showConfirmDialog(null, message, "Confirmation", JOptionPane.YES_NO_OPTION);
				
				if(JOptionPane.showConfirmDialog(null, message, "Confirmation", JOptionPane.YES_NO_OPTION)
						== JOptionPane.YES_OPTION){
					
					
	            	try {
	        			File file = new File("BallotSettings.txt");
	        			FileWriter fileWriter = new FileWriter(file);
	        			fileWriter.write(candidateNum + "\n");
	        			fileWriter.write(ballotType + "\n");
	        			for(int i = 0; i < candidateNum; i++){fileWriter.write(array.get(i) + "\n");}
	        			fileWriter.flush();
	        			fileWriter.close();
	        		} catch (IOException ex) {
	        			
	        			ex.printStackTrace();
	        		}
	            	
	            	popupTriggered = false;
					
					// Save BallotSettings.txt file
					
				}
				
			}
		}
		
		
			
			candidateList = "";
			
			for(int i = 0; i < array.size(); i++){
				
				candidateList = candidateList + "\n" + array.get(i);	
			}
			
			
		
		
		candidateNames.setText("Current Candidates" + "\n" + candidateList);
		candidateIn.setText("");
	
	}
	
	public static void main(String args[]){
		
		new BallotBuilder();
	}

}
