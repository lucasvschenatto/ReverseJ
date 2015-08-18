package acceptance.story;

public class Hero {
	private String name;
	Hero(String name){
		this.name = name;
	}
	boolean save(Princess princess, Enemy enemy){
		if( princess.isGuarded()){
			enemy.release(princess);
			return true;
		}else
			return false;
	}
	String getName(){
		return name;
	}
}
