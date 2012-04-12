import java.io.File;

public class Directory {
	private String fileName;
	private String directory;
	
	public Directory(String d, String f) {
		this.directory = d;
		this.fileName = f;
	}
	
	public Directory(File f) {
		this.directory = f.getPath();
		this.fileName = f.getName();
	}
	
	public Directory(Directory d) {
		this(d.getFileDirectory(), d.getFileName());
	}
	
	public void setFileName(String f) {
		this.fileName = f;
	}
	
	public String getFileName() {
		return this.fileName;
	}
	
	public void setFileDirectory(String d) {
		this.directory = d;
	}
	
	public String getFileDirectory() {
		return this.directory;
	}
	
	public String toString() {
		return this.getFileName();
	}
}
