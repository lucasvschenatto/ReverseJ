package StoryPackage;

public class Enemy {
	String name;
	Princess captured;
	public Enemy(String name){
		this.name = name;
	}
	
	public void capture(Princess p){
		captured = p;
		p.setGuard(this);
	}
	public boolean release(Princess p){
		if(p == captured){
			captured = null;
			p.removeGuard();
			return true;
		}
		else
			return false;
	}
	String getName(){
		return name;
	}
}
