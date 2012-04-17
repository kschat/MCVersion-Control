import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ProgressDialog extends JDialog {
	private final int WIDTH=300;
	private final int HEIGHT=150;
	private JPanel mainPanel;
	private JProgressBar progress;
	
	
	public ProgressDialog(GUI root) {
		super(root, "Current Progress");
		
		mainPanel = new JPanel();
		progress = new JProgressBar(0);
		
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(new Dimension(WIDTH, HEIGHT));
		setLocation((int)(screen.width/2 - (this.getWidth()/2)), (int)(screen.height/2 - (this.getHeight()/2)));
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setModalityType(Dialog.ModalityType.DOCUMENT_MODAL);
		this.setResizable(false);
		this.buildPanel();
	}
	
	public void buildPanel() {
		//mainPanel.setLayout(new BorderLayout());
		progress.setStringPainted(true);
		mainPanel.add(progress);
		this.add(mainPanel);
		this.setVisible(true);
	}
}
