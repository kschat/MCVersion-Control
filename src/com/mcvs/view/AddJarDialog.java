package com.mcvs.view;

import com.mcvs.core.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

//TODO: Finish porting for new application pattern
public class AddJarDialog extends JDialog {
	private final int WIDTH=290;
	private final int HEIGHT=200;
	private JPanel mainPanel, controlPanel, buttonPanel;
	private JList listView;
	private JLabel nameLabel, versionLabel;
	private JTextField nameText;
	private JComboBox versionComboBox;
	private JButton submit, cancel;
	private JCheckBox setAsCurrentVersion;
	
	public AddJarDialog(MCVSView root, ArrayList<Entity> list) {
		super(root, "Add a jar");
		
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
		versionComboBox = new JComboBox();
		
		nameText.setPreferredSize(new Dimension(100, 20));
		versionComboBox.setPreferredSize(new Dimension(100, 25));
		
		listView = new JList();
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
		
		this.add(mainPanel);
	}
	
	public String getNameText() {
		return nameText.getText();
	}
	
	public void setNameTextEnabled(boolean enabled) {
		nameText.setEnabled(enabled);
	}
	
	public void setSubmitButtonEnabled(boolean enabled) {
		submit.setEnabled(enabled);
	}
	
	public void setListViewData(ArrayList<Entity> list) {
		listView.setModel(new FileListModel(list));
		listView.setSelectedIndex(0);
	}
	
	public JComboBox getVersionComboBox() {
		return versionComboBox;
	}
	
	public boolean setAsCurrentVersion() {
		return setAsCurrentVersion.isSelected();
	}
	
	public void addSubmitButtonListener(ActionListener aListener) {
		submit.addActionListener(aListener);
	}
	
	public void addCancelButtonListener(ActionListener aListener) {
		cancel.addActionListener(aListener);
	}
	
	public void addNameTextDocumentListener(DocumentListener dListener) {
		nameText.getDocument().addDocumentListener(dListener);
	}
	
	public void addSetAsCurrentVersionListener(ActionListener aListener) {
		setAsCurrentVersion.addActionListener(aListener);
	}
	
	public Entity getSelectedEntity() {
		return (Entity)listView.getSelectedValue();
	}
	
	public Object getVersion() {
		return versionComboBox.getSelectedItem();
	}
}
