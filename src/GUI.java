import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;
import javax.swing.table.*;
//import com.apple.eawt.Application;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class GUI extends JFrame implements ActionListener, TableModelListener, MouseListener, KeyListener, WindowListener {
	private final int HEIGHT=450;
	private final int WIDTH=650;
	private final String CURRVERTEXT="<html><b>Current Version:</b><br /><center>";
	private JPanel mainPanel, controlPanel, labelPanel, buttonPanel;
	private MCVersionSwap mcSwap;
	private JLabel currentVersionLabel;
	private JTable versionTable;
	JScrollPane versionTableScroll;
	private JButton switchButton, launchButton;
	private FileDialog jarFileChooser;
	private String versionTableValue="";
	private JPopupMenu versionMenu;
	
	private JMenuBar menuBar;
	private JMenu fileMenu, editMenu, helpMenu;
	private JMenuItem exitItem, aboutItem, updateItem, addJarItem, renameItem, deleteItem, reportBugItem;
	
	//MacApplicationAdapter macApplication;
	
	public GUI() {
		super("Minecraft Version Swapper");
		//macApplication = new MacApplicationAdapter();
		
		try {
			mcSwap = MCVersionSwap.getInstance();
		} 
		catch (IOException e) { 
			System.out.println(e.getMessage());
			
			/*
			 * Uncomment for final version
			 */
			//JOptionPane.showMessageDialog(null, "There was a problem reading your minecraft files.");
			//MCVersionSwap.closeProgram();
		}
		
		mainPanel = new JPanel();
		labelPanel = new JPanel();
		controlPanel = new JPanel();
		buttonPanel = new JPanel();
		versionTable = new JTable(new VersionTableModel(MCVersionSwap.getEntities()));
		//this.removeKeyBinding();
		this.addKeyBinding();
		currentVersionLabel = new JLabel(CURRVERTEXT + MCVersionSwap.getCurrentVersion()+"</center></html>");
		switchButton = new JButton("Switch");
		launchButton = new JButton("Launch minecraft");
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(new Dimension(WIDTH, HEIGHT));
		this.setLocation((dim.width/2)-(this.getWidth()/2), (dim.height/2)-(this.getWidth()/2));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.buildMenu();
		this.buildPanel();
		this.buildJarFileChooser();
		this.buildPopupMenu();
	}
	
	public void buildMenu() {
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		editMenu = new JMenu("Edit");
		helpMenu = new JMenu("Help");
		exitItem = new JMenuItem("Exit");
		updateItem = new JMenuItem("Check For Updates");
		aboutItem = new JMenuItem("About");
		addJarItem = new JMenuItem("Add Jar...");
		reportBugItem = new JMenuItem("Report a bug");
		
		if(MCVersionSwap.getOS().equals(OS.windows)) {
			fileMenu.add(exitItem);
			helpMenu.add(aboutItem);
		}
		
		fileMenu.add(addJarItem);
		helpMenu.add(updateItem);
		helpMenu.add(reportBugItem);
		
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(helpMenu);
		
		exitItem.addActionListener(this);
		aboutItem.addActionListener(this);
		addJarItem.addActionListener(this);
		reportBugItem.addActionListener(this);
		this.setJMenuBar(menuBar);
	}
	
	public JTable getVersionTable() {
		return this.versionTable;
	}
	
	public void addKeyBinding() {
		Action action = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GUI.this.updateVersion();
				//GUI.this.currentVersionLabel.setText(CURRVERTEXT + MCVersionSwap.getCurrentVersion() + "</center></html>");
			}
		};
		versionTable.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "JTable_enter");
		
		versionTable.getInputMap(JTable.WHEN_FOCUSED).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "JTable_enter");
		versionTable.getActionMap().put("JTable_enter", action);
	}
	
	private void buildPanel() {
		versionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		versionTable.setSize(new Dimension(100, 100));
		versionTable.setFillsViewportHeight(true);
		versionTableScroll = new JScrollPane(versionTable);
		
		mainPanel.setLayout(new BorderLayout());
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
		
		mainPanel.add(labelPanel, BorderLayout.NORTH);
		mainPanel.add(controlPanel, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		switchButton.addActionListener(this);
		launchButton.addActionListener(this);
		versionTable.addMouseListener(this);
		versionTable.addKeyListener(this);
		versionTable.getModel().addTableModelListener(this);
		this.addWindowListener(this);
		
		/*
		 * Disable the switchButton on launch, so the program doesn't
		 * change to the same version.
		 */
		if(versionTableValue.equals(MCVersionSwap.getCurrentVersion())) {
			switchButton.setEnabled(false);
		}
		
		this.add(mainPanel);
		this.setVisible(true);
	}
	
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
	
	private void buildPopupMenu() {
		versionMenu = new JPopupMenu();
		renameItem = new JMenuItem("Rename File");
		deleteItem = new JMenuItem("Delete File");
		
		versionMenu.add(renameItem);
		versionMenu.add(deleteItem);
		
		renameItem.addActionListener(this);
		deleteItem.addActionListener(this);
	}
	
	public void setCurrentVersionLabel(String text) {
		this.currentVersionLabel.setText(CURRVERTEXT+text+"</center></html>");
	}
	
	public void addEntityToTable(Entity e) {
		VersionTableModel vtm = (VersionTableModel)this.versionTable.getModel();
		vtm.addEntity(e);
	}
	
	public void replaceEntityToTable(Entity e) {
		VersionTableModel vtm = (VersionTableModel)this.versionTable.getModel();
		vtm.replaceEntity(e);
	}
	
	public void removeEntityFromTable(int row) {
		VersionTableModel vtm = (VersionTableModel)this.versionTable.getModel();
		String version = (String)vtm.getValueAt(row, 1);
		if(MCVersionSwap.getCurrentVersion().equals(version)) {
			JOptionPane.showMessageDialog(this, version + " is the current version, please change versions before deleting.");
		}
		else {
			MCVersionSwap.deleteEntityFile((String) version);
			vtm.removeEntity((Entity) vtm.getObjectAtRow(row));
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * ActionListener functions for buttons, and menuItems
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource().equals(launchButton)) {
			try {
				MCVersionSwap.runMinecraft();
			} catch (IOException ex) {
				System.out.println(ex.getMessage());
			}
		}
		
		if(e.getSource().equals(exitItem)) {
			MCVersionSwap.closeProgram();
		}
		
		if(e.getSource().equals(aboutItem)) {
			JOptionPane.showMessageDialog(this, "Program: Minecraft Jar Switcher\nCreated by: Kyle Schattler\nVersion: Pre-ALPHA", "About", 
					JOptionPane.PLAIN_MESSAGE);
		}
		
		if(e.getSource().equals(addJarItem)) {
			jarFileChooser.setVisible(true);
			if(jarFileChooser.getFile()!=null) {
				ArrayList<Entity> tempD = new ArrayList<Entity>();
				tempD.add(new Entity(jarFileChooser.getFile(), jarFileChooser.getDirectory()));
				new AddJarDialog(this, tempD);
			}
		}
		
		if(e.getSource().equals(renameItem)) {
			//TODO: Check for .jar, if it's present leave it alone. Else, append it.
			String value = JOptionPane.showInputDialog(this, "Enter new name:", "Rename File", JOptionPane.QUESTION_MESSAGE);
			if(value!=null || !(value.equals(""))) {
				VersionTableModel vtm = (VersionTableModel)versionTable.getModel();
				vtm.setValueAt(value, versionTable.getSelectedRow(), 0);
				try {
					MCVersionSwap.writeEntityFile(value, (String)vtm.getValueAt(versionTable.getSelectedRow(), 1));
				} catch (IOException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
			}
		}
		
		if(e.getSource().equals(deleteItem)) {
			this.removeEntityFromTable(versionTable.getSelectedRow());
		}
		
		if(e.getSource().equals(reportBugItem)) {
			new BugDialog(this);
		}
	}
	
	public void updateVersion() {
		if(!MCVersionSwap.getCurrentVersion().equals(versionTable.getValueAt(versionTable.getSelectedRow(), 1))) {
			int res = JOptionPane.showConfirmDialog(this, "Are you sure you want to change to this version?");
			
			if(res==JOptionPane.YES_OPTION) {
				versionTableValue = (String)versionTable.getValueAt(versionTable.getSelectedRow(), 1);
				String sourceDir = "/"+versionTableValue+"/"+versionTable.getValueAt(versionTable.getSelectedRow(), 0);
				
				try {
					MCVersionSwap.moveFile(new File(MCVersionSwap.getPath()+sourceDir), 
							new File(MCVersionSwap.getPath()+"/../minecraft.jar"));
					
					MCVersionSwap.updateCurrentVersion(versionTableValue);
					currentVersionLabel.setText(CURRVERTEXT + MCVersionSwap.getCurrentVersion() + "</center></html>");
				} 
				catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public void tableChanged(TableModelEvent e) {
		int row = e.getFirstRow();
		int column = e.getColumn();
		TableModel model = (TableModel)e.getSource();
		//String columnName = model.getColumnName(column);
		Object data = model.getValueAt(row, column);
		System.out.println(row + " " + column);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) { }

	@Override
	public void mouseEntered(MouseEvent e) { }

	@Override
	public void mouseExited(MouseEvent e) { }

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getSource().equals(versionTable) && e.getClickCount()==2 && e.getButton()==MouseEvent.BUTTON1) {
			
			if(!MCVersionSwap.getCurrentVersion().equals(versionTable.getValueAt(versionTable.getSelectedRow(), 1))) {
				int res = JOptionPane.showConfirmDialog(this, "Are you sure you want to change to this version?");
				
				if(res==JOptionPane.YES_OPTION) {
					versionTableValue = (String)versionTable.getValueAt(versionTable.getSelectedRow(), 1);
					String sourceDir = "/"+versionTableValue+"/"+versionTable.getValueAt(versionTable.getSelectedRow(), 0);
					
					try {
						MCVersionSwap.moveFile(new File(MCVersionSwap.getPath()+sourceDir), 
								new File(MCVersionSwap.getPath()+"/../minecraft.jar"));
						
						MCVersionSwap.updateCurrentVersion(versionTableValue);
						currentVersionLabel.setText(CURRVERTEXT + MCVersionSwap.getCurrentVersion() + "</center></html>");
					} 
					catch (IOException ex) {
						ex.printStackTrace();
					}
				}
				if(res==JOptionPane.NO_OPTION) { }
			}
		}
		
		if(e.getSource().equals(versionTable) && e.getButton()==MouseEvent.BUTTON3) {
			int r = versionTable.rowAtPoint(e.getPoint());
            if (r >= 0 && r < versionTable.getRowCount()) {
            	versionTable.setRowSelectionInterval(r, r);
            } else {
            	versionTable.clearSelection();
            }
            
            int rowindex = versionTable.getSelectedRow();
            if (rowindex < 0)
                return;
            if (e.isPopupTrigger() && e.getComponent() instanceof JTable ) {
                versionMenu.show(e.getComponent(), e.getX(), e.getY());
            }
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getSource().equals(versionTable) && e.getButton()==MouseEvent.BUTTON3) {
			int r = versionTable.rowAtPoint(e.getPoint());
            if (r >= 0 && r < versionTable.getRowCount()) {
            	versionTable.setRowSelectionInterval(r, r);
            } else {
            	versionTable.clearSelection();
            }

            int rowindex = versionTable.getSelectedRow();
            if (rowindex < 0)
                return;
            if (e.isPopupTrigger() && e.getComponent() instanceof JTable ) {
                versionMenu.show(e.getComponent(), e.getX(), e.getY());
            }
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.KEY_EVENT_MASK) {
			if(!MCVersionSwap.getCurrentVersion().equals(versionTable.getValueAt(versionTable.getSelectedRow(), 1))) {
				int res = JOptionPane.showConfirmDialog(this, "Are you sure you want to change to this version?");
				
				if(res==JOptionPane.YES_OPTION) {
					versionTableValue = (String)versionTable.getValueAt(versionTable.getSelectedRow(), 1);
					
					try {
						MCVersionSwap.moveFile(new File(MCVersionSwap.getPath()+"/"+versionTableValue+"/minecraft.jar"), 
								new File(MCVersionSwap.getPath()+"/../minecraft.jar"));
						
						MCVersionSwap.updateCurrentVersion(versionTableValue);
						currentVersionLabel.setText(CURRVERTEXT + MCVersionSwap.getCurrentVersion() + "</center></html>");
					} 
					catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}
		}
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) { }

	@Override
	public void windowClosing(WindowEvent e) {
		MCVersionSwap.closeProgram();
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		new GUI();
	}
}
