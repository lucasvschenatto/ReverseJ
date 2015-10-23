package reversej.plugin;

import reversej.controller.SimpleController;
import reversej.tracer.TracerImmunity;

public abstract class SimplePlugin implements TracerImmunity{	
	public static boolean start(){
		return SimpleController.start("diagram");
	}
	public static boolean stop(){
		return SimpleController.stop();
	}
	public static boolean reset(){
		return SimpleController.reset();
	}
}
