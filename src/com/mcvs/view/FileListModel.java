package com.mcvs.view;

import java.util.*;
import javax.swing.*;
import com.mcvs.core.*;

public class FileListModel extends AbstractListModel {
	private ArrayList<Entity> listContent;
	
	public FileListModel(ArrayList<Entity> list) {
		this.listContent = new ArrayList<Entity>(list);
	}
	
	@Override
	public Object getElementAt(int i) {
		return listContent.get(i);
	}

	@Override
	public int getSize() {
		return listContent.size();
	}
	
}
