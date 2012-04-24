package com.mcvs.view;

import com.mcvs.core.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

//TODO: Finish porting for new application pattern
public class AddJarDialog extends JDialog implements DocumentListener {
	private final int WIDTH=300;
	private final int HEIGHT=200;
	private JPanel mainPanel, controlPanel, buttonPanel;
	private JList listView;
	private JLabel nameLabel, versionLabel;
	private JTextField nameText;
	private JComboBox versionComboBox;
	private JButton submit, cancel;
	private JCheckBox setAsCurrentVersion;
	private MCVSView root;
	
	public AddJarDialog(MCVSView root, ArrayList<Entity> list) {
		super(root, "Add a jar");
		
		this.root = root;
		mainPanel = new JPanel();
		controlPanel = new JPanel();
		buttonPanel = new JPanel();
		nameLabel = new JLabel("Label for file:");
		versionLabel = new JLabel("jar version:");
		nameText = new JTextField();
		submit = new JButton("Submit");
		cancel = new JButton("Cancel");
		setAsCurrentVersion = new JCheckBox("Set as current version");
		submit.setEnabled(false);
		versionComboBox = new JComboBox(/*MCVersionSwap.getMCVersions().toArray()*/);
		
		nameText.setPreferredSize(new Dimension(100, 20));
		versionComboBox.setPreferredSize(new Dimension(100, 25));
		
		listView = new JList(/*list.toArray()*/);
		listView.setCellRenderer(new JarRenderer());
		listView.setPreferredSize(new Dimension(125, HEIGHT));
		listView.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		listView.setSelectedIndex(0);
		
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(new Dimension(WIDTH, HEIGHT));
		setLocation((int)(screen.width/2 - (this.getWidth()/2)), (int)(screen.height/2 - (this.getHeight()/2)));
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setModalityType(Dialog.ModalityType.DOCUMENT_MODAL);
		this.setResizable(false);
		this.buildPanel();
	}
	
	public void buildPanel() {
		mainPanel.setLayout(new BorderLayout());
		controlPanel.setLayout(new FlowLayout());
		buttonPanel.setLayout(new BorderLayout());
		
		controlPanel.add(nameLabel);
		controlPanel.add(nameText);
		controlPanel.add(versionLabel);
		controlPanel.add(versionComboBox);
		controlPanel.add(setAsCurrentVersion);
		buttonPanel.add(cancel, BorderLayout.WEST);
		buttonPanel.add(submit, BorderLayout.EAST);
		
		mainPanel.add(listView, BorderLayout.WEST);
		mainPanel.add(controlPanel, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		/*
		submit.addActionListener(this);
		cancel.addActionListener(this);
		nameText.addActionListener(this);
		nameText.getDocument().addDocumentListener(this);
		*/
		
		this.add(mainPanel);
		//this.setVisible(true);
	}
	
	private void submitEntity(Entity entity, File file, boolean replace) throws IOException {
		/*
		MCVersionSwap.moveFile(new File(entity.getDirectory()+entity.getName()), 
				new File(file.toString()+"/"+this.nameText.getText()+".jar"));
		
		entity.setName(this.nameText.getText()+".jar");
		MCVersionSwap.writeEntityFile(entity.getName(), file.getName());
		
		if(setAsCurrentVersion.isSelected()) {
			//TODO: should fix updateCurrentVersion to also update text field
			MCVersionSwap.updateCurrentVersion(file.getName());
			this.root.setCurrentVersionLabel(file.getName());
		}
		
		if(replace) {
			this.root.replaceEntityToTable(new Entity(entity));
		}
		else {
			this.root.addEntityToTable(new Entity(entity));
		}*/
	}
	
	public void setListViewData(ArrayList<Entity> list) {
		listView.setModel(new FileListModel(list));
	}
	
	public JComboBox getVersionComboBox() {
		return versionComboBox;
	}
	
	public void addSubmitButtonListener(ActionListener aListener) {
		submit.addActionListener(aListener);
	}
	
	public void addCancelButtonListener(ActionListener aListener) {
		cancel.addActionListener(aListener);
	}
	
	/*
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource().equals(submit)) {
			try {
				File f = new File(MCVersionSwap.getPath() + "/" + this.versionComboBox.getSelectedItem().toString());
				Entity entity = new Entity((Entity)this.listView.getSelectedValue());
				entity.setVersion(f.getName());
				
				if(!f.mkdir()) {
					int opt=JOptionPane.showConfirmDialog(this, "This version of minecraft already exists, do you want to overwrite it?");
					
					if(opt==JOptionPane.YES_OPTION) {
						this.submitEntity(entity, f, true);
						this.dispose();
					}
					
					if(opt==JOptionPane.NO_OPTION) {
						
					}
				}
				else {
					this.submitEntity(entity, f, false);
					this.dispose();
				}
			} 
			catch (IOException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
				
		}
		
		if(e.getSource().equals(cancel)) {
			this.dispose();
		}
	}
	*/
	@Override
	public void changedUpdate(DocumentEvent e) { 
		if(this.nameText.getText().length()==0) {
			this.submit.setEnabled(false);
		}
		else {
			this.submit.setEnabled(true);
		}
	}

	@Override
	public void insertUpdate(DocumentEvent e) { 
		if(this.nameText.getText().length()==0) {
			this.submit.setEnabled(false);
		}
		else {
			this.submit.setEnabled(true);
		}
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		if(this.nameText.getText().length()==0) {
			this.submit.setEnabled(false);
		}
		else {
			this.submit.setEnabled(true);
		}
	}
}
