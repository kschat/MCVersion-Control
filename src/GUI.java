import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.DefaultTableModel;

import com.apple.eawt.Application;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.jar.Attributes.Name;

public class GUI extends JFrame implements ActionListener, TableModelListener, MouseListener, KeyListener {
	private final int HEIGHT=450;
	private final int WIDTH=650;
	private final String CURRVERTEXT="<html><b>Current Version:</b><br /><center>";
	private String[] COLUMNNAMES = {"Name:", "Version:"};
	private JPanel mainPanel, controlPanel, labelPanel, buttonPanel;
	private MCVersionSwap mcSwap;
	private JLabel currentVersionLabel;
	private JComboBox versionComboBox;
	private JTable versionTable;
	JScrollPane versionTableScroll;
	private JButton switchButton, launchButton;
	private JFileChooser jarFileChooser;
	private FileDialog macFileChooser;
	private String versionTableValue="";
	
	private JMenuBar menuBar;
	private JMenu fileMenu, editMenu, helpMenu;
	private JMenuItem exitItem, aboutItem, updateItem, addJarItem;
	
	MacApplicationAdapter macApplication;
	
	public GUI() {
		super("Minecraft Version Swapper");
		macApplication = new MacApplicationAdapter();
		
		try {
			mcSwap = new MCVersionSwap();
		} 
		catch (IOException e) { }
		
		mainPanel = new JPanel();
		labelPanel = new JPanel();
		controlPanel = new JPanel();
		buttonPanel = new JPanel();
		versionTable = new JTable(mcSwap.get2DArray(), COLUMNNAMES)  {
			public boolean isCellEditable(int rowIndex,int mColIndex) {
				return false;
			}
		};
		
		currentVersionLabel = new JLabel(CURRVERTEXT + MCVersionSwap.getCurrentVersion()+"</center></html>");
		switchButton = new JButton("Switch");
		launchButton = new JButton("Launch minecraft");
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(new Dimension(WIDTH, HEIGHT));
		this.setLocation((dim.width/2)-(this.getWidth()/2), (dim.height/2)-(this.getWidth()/2));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.buildMenu();
		this.buildPanel();
		
		if(MCVersionSwap.getOS().equals(OS.windows)) {
			this.buildJarFileChooser();
		}
		
		if(MCVersionSwap.getOS().equals(OS.mac)) {
			this.buildMacFileChooser();
		}
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
		
		fileMenu.add(exitItem);
		fileMenu.add(addJarItem);
		helpMenu.add(updateItem);
		helpMenu.add(aboutItem);
		
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(helpMenu);
		
		exitItem.addActionListener(this);
		aboutItem.addActionListener(this);
		addJarItem.addActionListener(this);
		
		this.setJMenuBar(menuBar);
	}
	
	public void buildPanel() {
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
		buttonPanel.add(launchButton, BorderLayout.CENTER);
		
		mainPanel.add(labelPanel, BorderLayout.NORTH);
		mainPanel.add(controlPanel, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		switchButton.addActionListener(this);
		launchButton.addActionListener(this);
		versionTable.addMouseListener(this);
		versionTable.addKeyListener(this);
		versionTable.getModel().addTableModelListener(this);
		
		if(versionTableValue.equals(MCVersionSwap.getCurrentVersion())) {
			switchButton.setEnabled(false);
		}
		
		this.add(mainPanel);
		this.setVisible(true);
	}
	
	public void buildJarFileChooser() {
		jarFileChooser = new JFileChooser("Choose jar file...");
		jarFileChooser.setCurrentDirectory(new File("/Users/kyleschattler/Desktop/jarfiles"));
		jarFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jarFileChooser.setFileFilter(new FileNameExtensionFilter("jar files", "jar"));
		jarFileChooser.setMultiSelectionEnabled(true);
	}
	
	public void buildMacFileChooser() {
		macFileChooser = new FileDialog(this, "Chooser jar file...");
		macFileChooser.setDirectory("/Users/kyleschattler/Desktop/jarfiles");
		
		class jarFilter implements FilenameFilter {
            public boolean accept(File dir, String name) {
                return (name.endsWith(".jar"));
            }
        };
		
		macFileChooser.setFilenameFilter(new jarFilter());
	}
	
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
			System.exit(0);
		}
		
		if(e.getSource().equals(aboutItem)) {
			JOptionPane.showMessageDialog(this, "Program: Minecraft Jar Switcher\nCreated by: Kyle Schattler\nVersion: Pre-ALPHA", "About", 
					JOptionPane.PLAIN_MESSAGE);
		}
		
		if(e.getSource().equals(addJarItem)) {
			if(MCVersionSwap.getOS().equals(OS.windows)) {
				int opt = jarFileChooser.showOpenDialog(this);
				
				if(opt==JFileChooser.APPROVE_OPTION) {
					File[] temp = jarFileChooser.getSelectedFiles();
					ArrayList<Directory> tempD = new ArrayList<Directory>();
					for(int i=0; i<temp.length; i++) {
						tempD.add(new Directory(temp[i]));
					}
					
					new AddJarDialog(this, tempD);
				}
				
				if(opt==JFileChooser.ERROR_OPTION) {
					System.out.println("A");
				}
			}
			
			if(MCVersionSwap.getOS().equals(OS.mac)) {
				macFileChooser.setVisible(true);
				if(!macFileChooser.getFile().equals(null)) {
					new AddJarDialog(this, new ArrayList<Directory>());
				}
				
			}
		}
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		int row = e.getFirstRow();
		int colum = e.getColumn();
		TableModel model = (TableModel)e.getSource();
		String columnName = model.getColumnName(colum);
		Object data = model.getValueAt(row, colum);
		System.out.println(row);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource().equals(versionTable) && e.getButton()==MouseEvent.BUTTON3) {
			System.out.println("B");
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getSource().equals(versionTable) && e.getClickCount()==2) {
			
			if(!MCVersionSwap.getCurrentVersion().equals(versionTable.getValueAt(versionTable.getSelectedRow(), 1))) {
				int res = JOptionPane.showConfirmDialog(this, "Are you sure you want to change to this version?");
				
				if(res==JOptionPane.YES_OPTION) {
					versionTableValue = (String)versionTable.getValueAt(versionTable.getSelectedRow(), 1);
					
					try {
						MCVersionSwap.moveFile(new File(MCVersionSwap.getPath()+"/"+versionTableValue+"/minecraft.jar"), 
								new File(MCVersionSwap.getPath()+"/../minecraft.jar"));
						
						mcSwap.updateCurrentVersion(versionTableValue);
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
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
						
						mcSwap.updateCurrentVersion(versionTableValue);
						currentVersionLabel.setText(CURRVERTEXT + MCVersionSwap.getCurrentVersion() + "</center></html>");
					} 
					catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}
		}
	}
}
