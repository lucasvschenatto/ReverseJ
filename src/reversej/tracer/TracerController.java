package reversej.tracer;

import static reversej.tracer.Context.*;
import java.util.LinkedList;

public class TracerController {
	
	public static void determineStorage(RepositoryRecorder newStorage){
		REPOSITORY = newStorage;
	}
	public static void start(RepositoryRecorder newStorage){
		determineStorage(newStorage);
		SUB_SUPERS = new LinkedList<SubSuper>();
		RUNNING = true;
	}
	public static void stop(){
		RUNNING = false;
	}
	public static RepositoryRecorder getStorage(){
		return REPOSITORY;
	}
	public static boolean isRunning(){
		return RUNNING;
	}
}
