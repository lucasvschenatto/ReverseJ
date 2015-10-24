package reversej.diagram;

import java.util.List;

public class DiagramEngine {
	protected RepositoryProvider infoProvider;
	protected List<DiagramStrategy> strategies;
	protected InformationFactory factory;
	
	public DiagramEngine(RepositoryProvider infoProvider, InformationFactory factory, List<DiagramStrategy> diagrams) {
		this.infoProvider = infoProvider;
		this.strategies = diagrams;
		this.factory = factory;
	}

	public Diagram make() {
		for (DiagramStrategy diagram : strategies)
			diagram.generate(infoProvider.getAll(factory));
		return Diagram.getInstance();
	}

	public RepositoryProvider getProvider() {
		return infoProvider;
	}

	public List<DiagramStrategy> getDiagramStrategies() {
		return strategies;
	}

	public InformationFactory getInformationFactory() {
		return factory;
	}
}
