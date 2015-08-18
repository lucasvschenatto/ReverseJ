package acceptance.story;

public class Palace implements Location {
	String name;
	public Palace(String name){
		this.name = name;
	}
	@Override
	public String getName() {
		return name;
	}

}
