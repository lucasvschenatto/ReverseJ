package acceptance.story;

public class Story {
	public void tellStory(){
		Location place = new Palace("Kings Palace");
		Enemy jafar = new Enemy("Jafar");
		Hero aladin = new Hero("Aladin");
		Princess jasmine = new Princess("Jasmine");
		
		jafar.capture(jasmine);
		aladin.save(jasmine, jafar);
		
		System.out.println(String.format("%s saved %s from %s at %s", aladin.getName(), jasmine.getName(), jafar.getName(), place.getName()));
	}

}
