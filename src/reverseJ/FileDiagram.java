package reverseJ;

public class FileDiagram {
	private Diagram diagram;
	private String path;
	private String name;
	public FileDiagram(Diagram diagram, String path, String name) {
		this.diagram = diagram;
		this.path = path;
		this.name = name;
	}
	public Diagram getDiagram() {
		return diagram;
	}
	public String getPath(){
		return path;
	}
	public String getName(){
		return name;
	}
	public void save() {
		// TODO Auto-generated method stub
		
	}
}
