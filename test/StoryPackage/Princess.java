package StoryPackage;

public class Princess {
	String name;
	Enemy guard;
	Princess(String name){
		this.name = name;
	}
	boolean isGuarded(){
		if(guard == null)
			return false;
		else
			return true;
	}
	void removeGuard(){
		guard = null;
	}
	String getName(){
		return name;
	}
	public void setGuard(Enemy enemy) {
		guard = enemy;		
	}
}
