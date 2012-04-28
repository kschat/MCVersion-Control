package com.mcvs.core;

public class MinecraftVersions {
	private static String[] versions = {"1.2.5", "1.2.4", "1.2.3", "1.2.2", "1.2.1", "1.1", "1.0.1", "1.0.0", "1.8.1 Beta",
		"1.8 Beta", "1.7.3 Beta", "1.7.2 Beta", "1.7 Beta", "1.6.6 Beta", "1.6.5 Beta", "1.6.4 Beta", "1.6.3 Beta", "1.6.2 Beta",
		"1.6.1 Beta", "1.6 Beta", "1.5 Beta", "1.4 Beta", "1.3 Beta", "1.2 Beta", "1.1 Beta", "1.0 Beta", "1.2.6 Alpha", "1.2.5 Alpha",
		"1.2.4 Alpha", "1.2.3 Alpha", "1.2.2 Alpha", "1.2.1 Alpha", "1.2.0 Alpha", "1.1.2 Alpha", "1.1.1 Alpha", "1.1.0 Alpha",
		"1.0.17 Alpha", "1.0.16 Alpha", "1.0.15 Alpha", "1.0.14 Alpha", "1.0.13 Alpha", "1.0.12 Alpha", "1.0.11 Alpha", "1.0.10 Alpha",
		"1.0.9 Alpha", "1.0.8 Alpha", "1.0.7 Alpha", "1.0.6 Alpha", "1.0.5 Alpha", "1.0.3 Alpha", "1.0.2 Alpha", "1.0.1 Alpha", "1.0.0 Alpha"};
	
	private MinecraftVersions() { }
	
	public static String[] getComboBoxVersions() {
		return versions;
	}
}
