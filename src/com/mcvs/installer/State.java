package com.mcvs.installer;

public enum State {
	summary(6), installation(5), installType(4), setDir(3), license(2), readMe(1), introduction(0);
	
	int index;
	State(int i) {
		index = i;
	}
	
	public int getIndex() {
		return index;
	}
	
	public static State getByIndex(int i) {
		return values()[i];
	}
}
