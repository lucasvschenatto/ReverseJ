package reverseJ;

import java.util.List;

public interface DiagramStrategy {

	DiagramObject generate(List<Information> informations);

	ClassDiagramUtilities getUtil();

}
