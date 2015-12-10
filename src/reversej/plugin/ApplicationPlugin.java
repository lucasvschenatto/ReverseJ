package reversej.plugin;

import reversej.controller.Controller;
import reversej.controller.UserView;
import reversej.tracer.TracerImmunity;

public abstract class ApplicationPlugin implements TracerImmunity {
	private static boolean firstRun = true;
	
	public static void run(){
		if (firstRun){
			firstRun = false;
			Controller controller = new Controller();
//			controller.start();
			new UserView(controller).run();
		}
	}
}
