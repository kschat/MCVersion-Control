import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class BugDialog extends JDialog implements ActionListener, DocumentListener {
	private final int WIDTH=400;
	private final int HEIGHT=300;
	private final String[] bugTypes = new String[]{"Windows 7", "Windows XP", "OS X 10.5+", "Linux", "Other"};
	private GUI root;
	private JPanel mainPanel, buttonPanel, errorTypePanel, messagePanel;
	private JComboBox errorTypeCB;
	private JTextArea messageTA;
	private JButton submit, cancel;
	private JLabel errorTypeLabel, messageLabel;
	
	public BugDialog(GUI root) {
		super(root, "Report a bug");
		this.root = root;
		
		mainPanel = new JPanel();
		buttonPanel = new JPanel();
		errorTypePanel = new JPanel();
		messagePanel = new JPanel();
		errorTypeCB = new JComboBox(bugTypes);
		messageTA = new JTextArea();
		errorTypeLabel = new JLabel("  OS bug occurred on:");
		messageLabel = new JLabel("  Please describe the bug with as much detail as possible:");
		submit = new JButton("Submit");
		cancel = new JButton("Cancel");
		
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
		messagePanel.setLayout(new BorderLayout());
		errorTypePanel.setLayout(new BorderLayout());
		messageTA.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		submit.setEnabled(false);
		
		errorTypePanel.add(errorTypeLabel, BorderLayout.NORTH);
		errorTypePanel.add(errorTypeCB, BorderLayout.CENTER);
		errorTypePanel.add(new JLabel("  "), BorderLayout.EAST);
		errorTypePanel.add(new JLabel("  "), BorderLayout.WEST);
		
		messagePanel.add(messageLabel, BorderLayout.NORTH);
		messagePanel.add(messageTA, BorderLayout.CENTER);
		messagePanel.add(new JLabel("  "), BorderLayout.EAST);
		messagePanel.add(new JLabel("  "), BorderLayout.WEST);
		
		buttonPanel.add(submit);
		buttonPanel.add(cancel);
		
		mainPanel.add(errorTypePanel, BorderLayout.NORTH);
		mainPanel.add(messagePanel, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		submit.addActionListener(this);
		cancel.addActionListener(this);
		messageTA.getDocument().addDocumentListener(this);
		
		this.add(mainPanel);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(submit)) {
			//TODO: Send bug to server
			this.dispose();
		}
		
		if(e.getSource().equals(cancel)) {
			this.dispose();
		}
	}

	@Override
	public void changedUpdate(DocumentEvent arg0) {
		if(this.messageTA.getText().trim().equals("")) {
			this.submit.setEnabled(false);
		}
		else {
			this.submit.setEnabled(true);
		}
	}

	@Override
	public void insertUpdate(DocumentEvent arg0) {
		if(this.messageTA.getText().trim().equals("")) {
			this.submit.setEnabled(false);
		}
		else {
			this.submit.setEnabled(true);
		}
	}

	@Override
	public void removeUpdate(DocumentEvent arg0) {
		if(this.messageTA.getText().trim().equals("")) {
			this.submit.setEnabled(false);
		}
		else {
			this.submit.setEnabled(true);
		}
	}
}
