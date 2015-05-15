package reverseJ;

public class InformationHandler {
	private InformationProvider provider;
	private DiagramGenerator diagramGenerator;
	public InformationHandler(InformationProvider provider, DiagramGenerator generator) {
		this.provider = provider;
		this.diagramGenerator = generator;
	}

	public void interpret() {
		
		for (Information information : provider.getAll()) {
			((ClassDiagramGenerator)diagramGenerator).createConcreteClass(information.getValue());
		}
	}

	public InformationProvider getProvider() {
		return provider;
	}

	public DiagramGenerator getDiagramGenerator() {
		return diagramGenerator;
	}

}
