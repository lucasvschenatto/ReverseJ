package reverseJ;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.uml2.uml.Package;

public class DiagramMaker {
	private InformationProvider infoProvider;
	private List<DiagramStrategy> diagrams;
	
	public DiagramMaker(InformationProvider infoProvider, List<DiagramStrategy> diagrams) {
		this.infoProvider = infoProvider;
		this.diagrams = diagrams;
	}

	public void make() {
		List<Package> packages = new LinkedList<Package>();
		for (DiagramStrategy diagram : diagrams)
			packages.add(diagram.generate(infoProvider.getAll()));
		
	}

	public InformationProvider getProvider() {
		return infoProvider;
	}

	public List<DiagramStrategy> getDiagramStrategies() {
		return diagrams;
	}
}
