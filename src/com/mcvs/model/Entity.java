package com.mcvs.model;

public class Entity {
	private String name;
	private String version;
	private String directory;
	
	public Entity(String n, String v, String d) {
		this.name = n;
		this.version = v;
		directory = d;
	}
	
	public Entity(String n, String d) {
		this(n, "", d);
	}
	
	public Entity(Entity e) {
		this(e.getName(), e.getVersion(), e.getDirectory());
	}
	
	public void setName(String n) {
		this.name = n;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setVersion(String v) {
		this.version = v;
	}
	
	public String getVersion() {
		return this.version;
	}
	
	public void setDirectory(String d) {
		this.directory = d;
	}
	
	public String getDirectory() {
		return this.directory;
	}
	
	@Override
	public String toString() {
		return this.getName() + " " + this.getVersion() + " " + this.getDirectory();
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Entity) {
			Entity e = (Entity)o;
			if(this.getVersion().equals(e.getVersion())) {
				return true;
			}
		}
		return false;
	}
}
