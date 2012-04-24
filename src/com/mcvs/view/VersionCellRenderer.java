package com.mcvs.view;

import java.awt.*;
import javax.swing.*;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class VersionCellRenderer extends JLabel implements TableCellRenderer {
	
	public VersionCellRenderer() {
        setOpaque(true);
    }
	
	public Component getTableCellRendererComponent(JTable table, 
			Object obj, boolean isSelected, boolean hasFocus, int row, int column) {
		
		if(isSelected) {
			this.setBackground(Color.LIGHT_GRAY);
		}
		if(hasFocus) {
			this.setBackground(Color.LIGHT_GRAY);
		}
		
		else {
			this.setBackground(UIManager.getDefaults().getColor(this));
		}
		
		this.setText(obj.toString());
		
		return this;
	}
	
	public void validate() {}
    public void revalidate() {}
    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {}
    public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {}
}
