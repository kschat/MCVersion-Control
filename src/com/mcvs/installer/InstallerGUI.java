package com.mcvs.installer;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import com.mcvs.core.*;

public class InstallerGUI extends JFrame implements ActionListener, DocumentListener {
	private final int HEIGHT=450;
	private final int WIDTH=700;
	private Installer installer;
	private InstallerState state;
	private JPanel mainPanel, contentPanel, mainButtonPanel, innerButtonPanel, innerButtonPanel2, introductionPanel, 
	statusPanel, setDirectoryPanel, innerSetDirPanel, installTypePanel, innerInstallPanel, addJarPanel, summaryPanel;
	private JButton nextButton, backButton, cancelButton, browseButton;
	private JTextArea installTextArea;
	private JTextField dirTextField;
	private JLabel title;
	private JTextPane message1, message2;
	private JFileChooser fileChooser;
	private JRadioButton normalInstall, addJarInstall;
	private ButtonGroup installGroup;
	private JLabel[] steps;
	
	public InstallerGUI(String t) throws IOException {
		super(t);
		mainPanel = new JPanel();
		contentPanel = new JPanel();
		mainButtonPanel = new JPanel();
		innerButtonPanel = new JPanel();
		innerButtonPanel2 = new JPanel();
		introductionPanel = new JPanel();
		statusPanel = new JPanel();
		setDirectoryPanel = new JPanel();
		innerSetDirPanel = new JPanel();
		installTypePanel = new JPanel();
		innerInstallPanel = new JPanel();
		addJarPanel = new JPanel();
		summaryPanel = new JPanel();
		cancelButton = new JButton("Cancel");
		nextButton = new JButton("Next");
		backButton = new JButton("Back");
		browseButton = new JButton("Browse");
		backButton.setEnabled(false);
		installTextArea = new JTextArea("You will be guided through the installation process for Minecraft Version Swap");
		title = new JLabel("<html><b><h2>Installer for Minecraft Version Swap</h2></b></html>");
		message1 = new JTextPane();
		message2 = new JTextPane();
		dirTextField = new JTextField();
		normalInstall = new JRadioButton("Normal Install");
		addJarInstall = new JRadioButton("Add a jar file");
		installGroup = new ButtonGroup();
		steps = new JLabel[6];
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(new Dimension(WIDTH, HEIGHT));
		this.setLocation((dim.width/2)-(this.getWidth()/2), (dim.height/2)-(this.getWidth()/2));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.buildMainPanel();
		this.buildStatusPanel(mainPanel);
		this.buildFileChooser();
		this.buildIntroductionPanel(contentPanel);
	}
	
	private void buildMainPanel() {
		mainPanel.setLayout(new BorderLayout());
		contentPanel.setLayout(new BorderLayout());
		mainButtonPanel.setLayout(new BorderLayout());
		title.setHorizontalAlignment(SwingConstants.CENTER);
		
		innerButtonPanel.add(backButton);
		innerButtonPanel.add(nextButton);
		innerButtonPanel2.add(cancelButton);
		mainButtonPanel.add(innerButtonPanel2, BorderLayout.WEST);
		mainButtonPanel.add(innerButtonPanel, BorderLayout.EAST);
		mainPanel.add(mainButtonPanel, BorderLayout.SOUTH);
		mainPanel.add(new JLabel("    "), BorderLayout.EAST);
		mainPanel.add(contentPanel, BorderLayout.CENTER);
		mainPanel.add(title, BorderLayout.NORTH);
		
		this.add(mainPanel);
	}
	
	private void emptyPanel(JPanel panel) {
		contentPanel.removeAll();
	}
	
	private void redrawPanel(JPanel panel) {
		panel.revalidate();
		panel.repaint();
	}
	private void buildStatusPanel(JPanel panel) {
		statusPanel.setLayout(new GridLayout(6,1));
		//statusPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		statusPanel.setPreferredSize(new Dimension(120, mainPanel.getHeight()));
		
		steps[0] = new JLabel("<html><ul><li>Introduction</li></ul></html>");
		steps[1]= new JLabel("<html><ul><li>Choose Directory</li></ul></html>");
		steps[2]= new JLabel("<html><ul><li>Choose Installation Type</li></ul></html>");
		steps[3]= new JLabel("<html><ul><li>Installation Overview</li></ul></html>");
		steps[4] = new JLabel("<html><ul><li>Installation</li></ul></html>");
		steps[5] = new JLabel("<html><ul><li>Summary</li></ul></html>");
		
		for(int i=0; i<steps.length; i++) {
			statusPanel.add(steps[i]);
		}
		
		panel.add(statusPanel, BorderLayout.WEST);
	}
	
	class IntroductionPanel extends JPanel {
		private JTextArea installTextArea;
		
		public IntroductionPanel(String text) {
			this.setLayout(new BorderLayout());
			
			installTextArea.setText(text);
			installTextArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			installTextArea.setEditable(false);
			this.add(installTextArea, BorderLayout.CENTER);
		}
		
		public void setText(String text) {
			installTextArea.setText(text);
		}
		
		public String getText() {
			return installTextArea.getText();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	/*private void setStatus(int i, boolean on) {
		if(on) {
			steps[i].setText("<html><b>"+steps[i].getText()+"</b></html>");
		}
		else {
			steps[i].setText("<html>"+steps[i].getText()+"</html>");
		}
	}*/
	
	private void buildIntroductionPanel(JPanel panel) {
		emptyPanel(panel);
		//state = InstallerState.introduction;
		introductionPanel.setLayout(new BorderLayout());
		installTextArea.setEditable(false);
		installTextArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		
		introductionPanel.add(installTextArea, BorderLayout.CENTER);
		panel.add(introductionPanel, BorderLayout.CENTER);
		redrawPanel(panel);
		//this.setVisible(true);
	}
	
	/*private void buildSetDirectoryPanel(JPanel panel) {
		emptyPanel(panel);
		state = InstallerState.setDir;
		setDirectoryPanel.setLayout(new BorderLayout());
		setDirectoryPanel.setBackground(Color.WHITE);
		setDirectoryPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		message1.setText("The installer is going to install the program in the directory listed below. If you would like to " +
				"change the directory click the browse button and select a new directory.");
		message2.setText("Destionation Directory:");
		dirTextField.setPreferredSize(new Dimension(300, 20));
		dirTextField.setText(installer.getDefaultInstallDir());
		innerSetDirPanel.setBackground(Color.WHITE);
		
		innerSetDirPanel.add(message2, BorderLayout.NORTH);
		innerSetDirPanel.add(dirTextField, BorderLayout.CENTER);
		innerSetDirPanel.add(browseButton, BorderLayout.EAST);
		
		setDirectoryPanel.add(message1, BorderLayout.NORTH);
		setDirectoryPanel.add(innerSetDirPanel, BorderLayout.CENTER);
		panel.add(setDirectoryPanel, BorderLayout.CENTER);
		
		
		dirTextField.getDocument().removeDocumentListener(this);
		dirTextField.getDocument().addDocumentListener(this);
		browseButton.removeActionListener(this);
		browseButton.addActionListener(this);
		
		redrawPanel(panel);
	}*/
	
	/*
	private void buildInstallType(JPanel panel) {
		emptyPanel(panel);
		state = InstallerState.installType;
		
		innerInstallPanel.setLayout(new BorderLayout());
		installTypePanel.setLayout(new BorderLayout());
		addJarPanel.setLayout(new BorderLayout());
		
		installTypePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		installTypePanel.setBackground(Color.WHITE);
		innerInstallPanel.setBackground(Color.WHITE);
		normalInstall.setBackground(Color.WHITE);
		addJarInstall.setBackground(Color.WHITE);
		
		message1.setText("Please choose weather you would like to go with the normal install process or the advanced install " +
				"process. The advanced install process allows you to add minecraft jar files right now.");
		
		normalInstall.setSelected(true);
		installGroup.add(normalInstall);
		installGroup.add(addJarInstall);
		
		dirTextField.setSize(new Dimension(100, 20));
		browseButton.setSize(new Dimension(20, 20));
		dirTextField.setEnabled(false);
		browseButton.setEnabled(false);
		message2.setEnabled(false);
		
		JLabel spacer = new JLabel(" ");
		spacer.setOpaque(true);
		spacer.setBackground(Color.WHITE);
		spacer.setPreferredSize(new Dimension(panel.getWidth(), 175));
		
		addJarPanel.add(message2, BorderLayout.NORTH);
		addJarPanel.add(dirTextField, BorderLayout.CENTER);
		addJarPanel.add(browseButton, BorderLayout.EAST);
		addJarPanel.add(spacer, BorderLayout.SOUTH);
		
		innerInstallPanel.add(normalInstall, BorderLayout.NORTH);
		innerInstallPanel.add(addJarInstall, BorderLayout.CENTER);
		innerInstallPanel.add(addJarPanel, BorderLayout.SOUTH);
		installTypePanel.add(message1, BorderLayout.NORTH);
		installTypePanel.add(innerInstallPanel, BorderLayout.CENTER);
		panel.add(installTypePanel, BorderLayout.CENTER);
		
		dirTextField.getDocument().removeDocumentListener(this);
		dirTextField.getDocument().addDocumentListener(this);
		browseButton.removeActionListener(this);
		browseButton.addActionListener(this);
		
		normalInstall.addActionListener(this);
		addJarInstall.addActionListener(this);
		
		redrawPanel(panel);
	}*/
	
	/*
	private void buildSummaryPanel(JPanel panel) {
		emptyPanel(panel);
		state = InstallerState.summary;
		summaryPanel.setLayout(new BorderLayout());
		summaryPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		message1.setContentType("text/html");
		message1.setText("Summary for how Minecraft Version Swap will be installed:<br />");
		
		summaryPanel.add(message1);
		panel.add(summaryPanel);
		redrawPanel(panel);
	}*/
	
	private void buildFileChooser() {
		fileChooser = new JFileChooser("Choose a directory...");
		//fileChooser.setCurrentDirectory(new File(installer.getDefaultInstallDir()));
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setMultiSelectionEnabled(false);
	}
	
	private String displayFileChooser() {
		int opt = fileChooser.showOpenDialog(this);
		if(opt==JFileChooser.APPROVE_OPTION) {
			return fileChooser.getSelectedFile().getPath();
		}
		return null;
	}
	
	public void enableNextButton(boolean enabled) {
		this.nextButton.setEnabled(enabled);
	}
	
	public void enableBackButton(boolean enabled) {
		this.backButton.setEnabled(enabled);
	}
	
	public void addBackButtonActionListener(ActionListener aListener) {
		backButton.addActionListener(aListener);
	}
	
	public void addNextButtonActionListener(ActionListener aListener) {
		nextButton.addActionListener(aListener);
	}
	
	public void addCancelButtonActionListener(ActionListener aListener) {
		cancelButton.addActionListener(aListener);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		/*switch(state) {
		case introduction:
			this.buildSetDirectoryPanel(contentPanel);
			backButton.setEnabled(true);
			break;
		case setDir:
			if(e.getSource().equals(backButton)) {
				this.buildIntroductionPanel(contentPanel);
				backButton.setEnabled(false);
				nextButton.setEnabled(true);
			}
			if(e.getSource().equals(nextButton)) {
				this.buildInstallType(contentPanel);
			}
			break;
		case installType:
			if(e.getSource().equals(backButton)) {
				this.buildSetDirectoryPanel(contentPanel);
				dirTextField.setEnabled(true);
				browseButton.setEnabled(true);
				message2.setEnabled(true);
			}
			if(e.getSource().equals(nextButton)) {
				this.buildSummaryPanel(contentPanel);
				nextButton.setText("Confirm");
			}
			if(e.getSource().equals(normalInstall)) {
				dirTextField.setEnabled(false);
				browseButton.setEnabled(false);
				message2.setEnabled(false);
			}
			if(e.getSource().equals(addJarInstall)) {
				dirTextField.setEnabled(true);
				browseButton.setEnabled(true);
				message2.setEnabled(true);
			}
			break;
		case summary:
			if(e.getSource().equals(backButton)) {
				this.buildInstallType(contentPanel);
				nextButton.setText("Next");
			}
			if(e.getSource().equals(nextButton)) {
				
			}
			break;
		}
		
		if(e.getSource().equals(cancelButton)) {
			int opt = JOptionPane.showConfirmDialog(this, "Are you sure you want to cancel the installation?",
					"Cancel Installation", JOptionPane.WARNING_MESSAGE);
			
			if(opt==JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		}
		
		if(e.getSource().equals(browseButton)) {
			String value = displayFileChooser();
			if(value!=null) {
				dirTextField.setText(value);
			}
		}*/
	}
	
	/*
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Throwable e) {
			
		}
		
		try {
			new InstallerGUI();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

	@Override
	public void changedUpdate(DocumentEvent e) {
		if(dirTextField.getText().length()==0) {
			nextButton.setEnabled(false);
		}
		else {
			nextButton.setEnabled(true);
		}
	}
	
	@Override
	public void insertUpdate(DocumentEvent e) {
		if(dirTextField.getText().length()==0) {
			nextButton.setEnabled(false);
		}
		else {
			nextButton.setEnabled(true);
		}
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		if(dirTextField.getText().length()==0) {
			nextButton.setEnabled(false);
		}
		else {
			nextButton.setEnabled(true);
		}
	}
}
