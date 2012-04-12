import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class AddJarDialog extends JDialog implements ActionListener {
	private final int WIDTH=300;
	private final int HEIGHT=300;
	private JPanel mainPanel, controlPanel, buttonPanel;
	private JList listView;
	private JLabel nameLabel, versionLabel;
	private JTextField nameText;
	private JComboBox versionComboBox;
	private JButton submit, cancel;
	
	public AddJarDialog(GUI root, ArrayList<Directory> list) {
		super(root, "Add a jar");
		
		mainPanel = new JPanel();
		controlPanel = new JPanel();
		buttonPanel = new JPanel();
		nameLabel = new JLabel("Label for jar:");
		versionLabel = new JLabel("jar version:");
		nameText = new JTextField();
		submit = new JButton("Submit");
		cancel = new JButton("Cancel");
		versionComboBox = new JComboBox(MCVersionSwap.getMCVersions().toArray());
		
		nameText.setPreferredSize(new Dimension(100, 20));
		versionComboBox.setPreferredSize(new Dimension(100, 25));
		
		listView = new JList(list.toArray());
		listView.setPreferredSize(new Dimension(125, HEIGHT));
		listView.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
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
		buttonPanel.add(submit, BorderLayout.WEST);
		buttonPanel.add(cancel, BorderLayout.EAST);
		
		mainPanel.add(listView, BorderLayout.WEST);
		mainPanel.add(controlPanel, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		submit.addActionListener(this);
		cancel.addActionListener(this);
		
		this.add(mainPanel);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(submit)) {
			
		}
		
		if(e.getSource().equals(cancel)) {
			
		}
	}
}
