package reverseJ;

import java.util.List;

public interface DiagramStrategy {

	void generate(List<Information> informations);

	ClassDiagramUtilities getUtil();

}
