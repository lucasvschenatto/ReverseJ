package reverseJ;

import java.util.List;
import org.eclipse.uml2.uml.Package;

public interface DiagramStrategy {

	 Package generate(List<Information> informations);

	AdapterClassToUML2 getUtil();

}
