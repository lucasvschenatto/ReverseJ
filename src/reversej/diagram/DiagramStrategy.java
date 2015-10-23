package reversej.diagram;

import java.util.List;

import org.eclipse.uml2.uml.Package;

import reversej.diagram.strategies.uml2adapter.AdapterToUml2;
import reversej.information.Information;

public interface DiagramStrategy {

	 Package generate(List<Information> informations);

	AdapterToUml2 getAdapter();

}
