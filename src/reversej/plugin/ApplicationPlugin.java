package reversej.plugin;

import reversej.controller.Controller;
import reversej.tracer.TracerImmunity;
import reversej.view.UserView;

public abstract class ApplicationPlugin implements TracerImmunity {
	private static boolean firstRun = true;
	
	public void run(){
		if (firstRun){
			firstRun = false;
			Controller controller = new Controller();
			controller.start();
			new UserView(controller).run();
		}
	}
}
