package reverseJ;

import java.util.List;

public class ClassDiagram implements
		DiagramStrategy {
	private ClassDiagramUtilities generator;
	public ClassDiagram(ClassDiagramUtilities utilities) {
		generator = utilities;
	}

	public ClassDiagram() {
		generator = ClassDiagramUtilities.make();
	}

	@Override
	public Diagram generate(List<Information> informations) {
		ClassDiagramUtilities generator = ClassDiagramUtilities.make();
		for (Information information : informations) {
			generator.createConcreteClass(information.getValue());
		}
		return null;
	}
	@Override
	public ClassDiagramUtilities getUtil() {
		return generator;
	}

}
