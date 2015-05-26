package reverseJ;

public class DiagramMaker {
	private InformationProvider infoProvider;
	private DiagramStrategy diagram;
	
	public DiagramMaker(InformationProvider infoProvider, DiagramStrategy diagram) {
		this.infoProvider = infoProvider;
		this.diagram = diagram;
	}

	public void make() {
		diagram.generate(infoProvider.getAll());
	}

	public InformationProvider getProvider() {
		return infoProvider;
	}

	public DiagramStrategy getDiagramStrategies() {
		return diagram;
	}
}
