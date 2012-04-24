package com.mcvs.view;

import javax.swing.*;
import javax.swing.event.ListDataListener;

public class VersionComboBoxModel implements ComboBoxModel {
	private String[] values;
	private String selection = null;
	
	public VersionComboBoxModel(String[] v) {
		this.values = v;
		this.selection = values[0];
	}
	
	@Override
	public Object getElementAt(int i) {
		return values[i];
	}

	@Override
	public int getSize() {
		return values.length;
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getSelectedItem() {
		return selection;
	}

	@Override
	public void setSelectedItem(Object item) {
		selection = (String) item;
	}

}
