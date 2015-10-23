package reversej.plugin;

import reversej.controller.SimpleController;
import reversej.tracer.TracerImmunity;

public abstract class SimplePlugin implements TracerImmunity{	
	public boolean start(){
		return SimpleController.start("diagram");
	}
	public boolean stop(){
		return SimpleController.stop();
	}
	public boolean reset(){
		return SimpleController.reset();
	}
}
