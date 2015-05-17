package reverseJ;

import java.util.List;

public interface DiagramStrategy {

	Diagram generate(List<Information> informations);

	ClassDiagramUtilities getUtil();

}
