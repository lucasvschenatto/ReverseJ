package reversej.controller;

import java.io.File;
import reversej.tracer.TracerImmunity;

public enum ControllerState implements TracerImmunity {
	INITIAL{
		public boolean start(Controller c){
			c.setState(TRACING);
			c.startTracer();
			return true;
		}
		public boolean stop(Controller c) {
			return false;
		}
		public boolean reset(Controller c) {
			return false;
		}
		public boolean save(Controller c, File fileToSave) {
			return false;
		}
	},
	TRACING{
		public boolean start(Controller c) {
			return false;
		}
		public boolean stop(Controller c) {
			c.setState(TRACED);
			c.stopTracer();
			return true;
		}
		public boolean reset(Controller c) {
			c.setState(INITIAL);
			c.stopTracer();
			c.resetRepository();
			return true;
		}
		public boolean save(Controller c, File fileToSave) {
			return false;
		}},
	TRACED {
		public boolean start(Controller c) {
			return false;
		}
		public boolean stop(Controller c) {
			return false;
		}
		public boolean save(Controller c, File fileToSave) {
			c.setState(SAVED);
			c.saveDiagram(fileToSave);
			return true;
		}
		public boolean reset(Controller c) {
			c.setState(INITIAL);
			c.resetDiagram();
			c.resetRepository();
			return true;
		}

	},
	SAVED {
		public boolean start(Controller c) {
			return false;
		}
		public boolean stop(Controller c) {
			return false;
		}
		public boolean save(Controller c, File fileToSave) {
			c.setState(SAVED);
			c.saveDiagram(fileToSave);
			return true;
		}
		public boolean reset(Controller c) {
			c.setState(INITIAL);
			c.resetDiagram();
			c.resetRepository();
			return true;
		}
	};
	public abstract boolean start(Controller c);
	public abstract boolean stop(Controller c);
	public abstract boolean reset(Controller c);
	public abstract boolean save(Controller c, File fileToSave);
}
