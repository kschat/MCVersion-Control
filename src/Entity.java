public class Entity {
	private String name;
	private String version;
	private Directory directory;
	
	public Entity(String n, String v, Directory d) {
		this(n, v);
		directory = new Directory(d);
	}
	
	public Entity(String n, String v) {
		this.name = n;
		this.version = v;
		directory = new Directory("", "");
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
	
	public void setDirectory(Directory d) {
		this.directory = new Directory(d);
	}
	
	public Directory getDirectory() {
		return new Directory(this.directory);
	}
	
	public String toString() {
		return this.getName() + this.getVersion();
	}
}
