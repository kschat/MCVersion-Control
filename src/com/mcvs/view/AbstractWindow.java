package com.mcvs.view;

import java.awt.*;
import javax.swing.*;

public abstract class AbstractWindow extends JFrame {
	protected int WIDTH, HEIGHT;
	protected JPanel mainPanel;
	protected JMenuBar mainMenu;
	
	public AbstractWindow() {
		this("");
	}
	
	public AbstractWindow(String title) {
		this(title, 100, 100);
	}
	
	public AbstractWindow(String title, int w, int h) {
		super(title);
		this.setDimensions(w, h);
		this.mainPanel = new JPanel();
		this.mainMenu = new JMenuBar();
	}
	
	protected void setupFrame(Dimension dim, int closeOperation) {
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(new Dimension(WIDTH, HEIGHT));
		this.setLocation((dim.width/2)-(this.getWidth()/2), (dim.height/2)-(this.getWidth()/2));
		this.setDefaultCloseOperation(closeOperation);
	}
	
	public void setWidth(int w) {
		this.WIDTH = w;
	}
	
	public void setHeight(int h) {
		this.HEIGHT = h;
	}
	
	public void setDimensions(int w, int h) {
		this.setWidth(w);
		this.setHeight(h);
	}
	
	abstract protected void initComponents();
	abstract protected void buildPanel(JPanel panel);
	abstract protected void buildMenu(JMenuBar menu);
}
