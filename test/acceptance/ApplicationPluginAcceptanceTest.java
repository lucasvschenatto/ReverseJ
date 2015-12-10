package acceptance;

import reversej.plugin.ApplicationPlugin;
import reversej.tracer.TracerImmunity;

public class ApplicationPluginAcceptanceTest implements TracerImmunity {

	public static void main(String[] args) {
		ApplicationPlugin.run();
	}

}
