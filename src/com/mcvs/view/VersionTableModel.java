package com.mcvs.view;

import java.util.*;
import javax.swing.table.*;
import com.mcvs.core.*;

public class VersionTableModel extends AbstractTableModel {
	private static final int COLUMN_FILE_NAME = 0;
	private static final int COLUMN_VERSION = 1;
	private Vector<Entity> tableContent;
	private String[] columns;
	
	public VersionTableModel() {
		this.tableContent = new Vector<Entity>();
		this.columns = new String[]{"Name:", "Version:"};
	}
	
	public VersionTableModel(Vector<Entity> list) {
		this.tableContent = new Vector<Entity>(list);
		this.columns = new String[]{"Name:", "Version:"};
	}
	
	public void addEntity(Entity e) {
		this.tableContent.add(e);
		fireTableDataChanged();
	}
	
	public void addEntityList(Vector<Entity> l) {
		this.tableContent.addAll(l);
		fireTableDataChanged();
	}
	
	public boolean replaceEntity(Entity e) {
		if(this.tableContent.removeElement(e)) {
			this.tableContent.add(e);
			fireTableDataChanged();
			return true;
		}
		return false;
	}
	
	public boolean removeEntity(Entity e) {
		if(this.tableContent.removeElement(e)) {
			fireTableDataChanged();
			return true;
		}
		return false;
	}
	
	public Object getObjectAtRow(int rowIndex) {
		if(rowIndex>getRowCount()) {
			return null;
		}
		Entity entity = tableContent.get(rowIndex);
		return entity;
	}
	
	@Override
	public int getColumnCount() {
		return this.columns.length;
	}
	
	@Override
	public String getColumnName(int col) {
		return this.columns[col];
	}

	@Override
	public int getRowCount() {
		return this.tableContent.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Entity entity = tableContent.get(rowIndex);
		
		switch(columnIndex) {
			case COLUMN_FILE_NAME:
				return entity.getName();
			case COLUMN_VERSION:
				return entity.getVersion();
			default:
				return null;
		}
	}
	
	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		Entity entity = tableContent.get(rowIndex);
		
		switch(columnIndex) {
			case COLUMN_FILE_NAME:
				entity.setName(value.toString());
				break;
			case COLUMN_VERSION:
				entity.setVersion(value.toString());
				break;
		}
	}
	
	@Override
	public boolean isCellEditable(int rowIndex,int mColIndex) {
		
		return false;
	}
}
