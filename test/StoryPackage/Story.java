package StoryPackage;

public class Story {
	public void tellStory(){
		Location palace = new Palace("Kings Palace");
		Enemy jafar = new Enemy("Jafar");
		Hero aladin = new Hero("Aladin");
		Princess jasmine = new Princess("Jasmine");
		
		jafar.capture(jasmine);
		aladin.save(jasmine, jafar);
		
		System.out.println(String.format("%s saved %s from %s at %s", aladin.getName(), jasmine.getName(), jafar.getName(), palace.getName()));
	}

}
