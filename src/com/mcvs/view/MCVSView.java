package com.mcvs.view;

import com.mcvs.core.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class MCVSView extends AbstractWindow {
	private final String CURRVERTEXT="<html><b>Current Version:</b><br /><center>";
	private JPanel controlPanel, labelPanel, buttonPanel;
	private JLabel currentVersionLabel;
	private JTable versionTable;
	JScrollPane versionTableScroll;
	private JButton launchButton;
	private FileDialog jarFileChooser;
	//private String versionTableValue="";
	private JPopupMenu versionMenu;
	private AddJarDialog jarDialog;
	private BugDialog bugDialog;
	
	private JMenu fileMenu, editMenu, helpMenu;
	private JMenuItem exitItem, aboutItem, updateItem, addJarItem, renameItem, deleteItem, reportBugItem;
	
	public MCVSView(String title) {
		this(title, 100, 100);
	}
	
	public MCVSView(String title, int w, int h) {
		super(title, w, h);
		this.setupFrame(new Dimension(w, h), JFrame.EXIT_ON_CLOSE);
		this.initComponents();
		this.buildMenu(mainMenu);
		this.buildPanel(mainPanel);
	}

	@Override
	public void buildPanel(JPanel panel) {
		versionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		versionTable.setSize(new Dimension(100, 100));
		versionTable.setFillsViewportHeight(true);
		versionTableScroll = new JScrollPane(versionTable);
		
		panel.setLayout(new BorderLayout());
		labelPanel.setLayout(new FlowLayout());
		controlPanel.setLayout(new BorderLayout());
		buttonPanel.setLayout(new BorderLayout());
		
		labelPanel.add(currentVersionLabel);
		controlPanel.add(versionTableScroll, BorderLayout.CENTER);
		controlPanel.add(new JLabel("   "), BorderLayout.WEST);
		controlPanel.add(new JLabel("   "), BorderLayout.EAST);
		buttonPanel.add(launchButton, BorderLayout.CENTER);
		buttonPanel.add(new JLabel(" "), BorderLayout.WEST);
		buttonPanel.add(new JLabel(" "), BorderLayout.EAST);
		
		panel.add(labelPanel, BorderLayout.NORTH);
		panel.add(controlPanel, BorderLayout.CENTER);
		panel.add(buttonPanel, BorderLayout.SOUTH);
		
		/*
		switchButton.addActionListener(this);
		launchButton.addActionListener(this);
		versionTable.addMouseListener(this);
		versionTable.addKeyListener(this);
		versionTable.getModel().addTableModelListener(this);
		this.addWindowListener(this);
		
		 * Disable the switchButton on launch, so the program doesn't
		 * change to the same version.
		if(versionTableValue.equals(MCVersionSwap.getCurrentVersion())) {
			switchButton.setEnabled(false);
		}
		*/
		
		this.add(panel);
		this.setVisible(true);
	}

	@Override
	protected void buildMenu(JMenuBar menu) {
		
		fileMenu.add(addJarItem);
		fileMenu.add(exitItem);
		helpMenu.add(updateItem);
		helpMenu.add(reportBugItem);
		helpMenu.add(aboutItem);
		
		menu.add(fileMenu);
		menu.add(editMenu);
		menu.add(helpMenu);
		/*
		exitItem.addActionListener(this);
		aboutItem.addActionListener(this);
		addJarItem.addActionListener(this);
		reportBugItem.addActionListener(this);
		*/
		this.setJMenuBar(menu);
	}

	@Override
	protected void initComponents() {
		labelPanel = new JPanel();
		controlPanel = new JPanel();
		buttonPanel = new JPanel();
		versionTable = new JTable();
		
		//TODO Pass real value to label
		currentVersionLabel = new JLabel();
		launchButton = new JButton("Launch minecraft");
		
		//TODO: Pass list to jarDialog
		jarDialog = new AddJarDialog(this, null);
		bugDialog = new BugDialog(this);
		
		fileMenu = new JMenu("File");
		editMenu = new JMenu("Edit");
		helpMenu = new JMenu("Help");
		exitItem = new JMenuItem("Exit");
		updateItem = new JMenuItem("Check For Updates");
		aboutItem = new JMenuItem("About");
		addJarItem = new JMenuItem("Add Jar...");
		reportBugItem = new JMenuItem("Report a bug");
	}
	
	//TODO Make visible to controller
	private void buildJarFileChooser() {
		jarFileChooser = new FileDialog(this, "Choose a jar file...");
		//macFileChooser.setDirectory("/Users/kyleschattler/Desktop/jarfiles");
		
		class jarFilter implements FilenameFilter {
            public boolean accept(File dir, String name) {
                return (name.endsWith(".jar"));
            }
        };
		
        jarFileChooser.setFilenameFilter(new jarFilter());
	}
	
	//TODO Make visible to controller
	private void buildPopupMenu() {
		versionMenu = new JPopupMenu();
		renameItem = new JMenuItem("Rename File");
		deleteItem = new JMenuItem("Delete File");
		
		versionMenu.add(renameItem);
		versionMenu.add(deleteItem);
		
		//renameItem.addActionListener(this);
		//deleteItem.addActionListener(this);
	}
	
	public AddJarDialog getAddJarDialog() {
		return jarDialog;
	}
	
	public BugDialog getBugDialog() {
		return bugDialog;
	}
	
	public JTable getVersionTable() {
		return versionTable;
	}
	
	public void setCurrentVersionLabel(String val) {
		currentVersionLabel.setText(CURRVERTEXT+"\n" + val + "</center></html>");
	}
	
	public void addLaunchButtonListener(ActionListener aListener) {
		launchButton.addActionListener(aListener);
	}
	
	public void addExitListener(ActionListener aListener) {
		exitItem.addActionListener(aListener);
	}
	
	public void addAboutItemListener(ActionListener aListener) {
		aboutItem.addActionListener(aListener);
	}
	
	public void addJarItemListener(ActionListener aListener) {
		addJarItem.addActionListener(aListener);
	}
	
	public void addRenameItemListener(ActionListener aListener) {
		renameItem.addActionListener(aListener);
	}
	
	public void addDeleteItemListener(ActionListener aListener) {
		deleteItem.addActionListener(aListener);
	}
	
	public void addReportBugItemListener(ActionListener aListener) {
		reportBugItem.addActionListener(aListener);
	}
	
	public void addVersionTableListener(MouseListener mListener) {
		versionTable.addMouseListener(mListener);
	}
	
	public void addViewWindowListener(WindowListener wListener) {
		this.addWindowListener(wListener);
	}
}
