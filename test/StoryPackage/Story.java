package StoryPackage;

public class Story {
	public void tellStory(){
		Location palace = new Palace("Kings Palace");
		Enemy salazar = new Enemy("Salazar");
		Hero aladin = new Hero("Aladin");
		Princess jasmine = new Princess("Jasmine");
		
		salazar.capture(jasmine);
		aladin.save(jasmine, salazar);
		
		System.out.println(String.format("%s saved %s from %s at %s", aladin.getName(), jasmine.getName(), salazar.getName(), palace.getName()));
	}

}
