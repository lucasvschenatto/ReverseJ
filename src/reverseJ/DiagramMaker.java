package reverseJ;

import java.util.List;

public class DiagramMaker {
	protected InformationProvider infoProvider;
	protected List<DiagramStrategy> strategies;
	
	public DiagramMaker(InformationProvider infoProvider, List<DiagramStrategy> diagrams) {
		this.infoProvider = infoProvider;
		this.strategies = diagrams;
	}

	public Diagram make() {
		for (DiagramStrategy diagram : strategies)
			diagram.generate(infoProvider.getAll());
		return Diagram.getInstance();
	}

	public InformationProvider getProvider() {
		return infoProvider;
	}

	public List<DiagramStrategy> getDiagramStrategies() {
		return strategies;
	}
}
