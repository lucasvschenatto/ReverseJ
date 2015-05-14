package reverseJ;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.UMLFactory;

public class SequenceDiagramGenerator {
	private Context context;
	private List<Lifeline> lifelines;
	public SequenceDiagramGenerator(){
		lifelines = new LinkedList<Lifeline>();
	}
	public Lifeline getLifeline(String name) {
		for (Lifeline lifeline : lifelines) {
			if (lifeline.getName() == name)
				return lifeline;
		}
		return null;
	}

	public boolean createLifeline(String name) {
		if (context != null){
			Lifeline newLifeline = UMLFactory.eINSTANCE.createLifeline();
			newLifeline.setInteraction(context.getInteraction());
			newLifeline.setName(name);
			lifelines.add(newLifeline);
			return true;
		}else
			return false;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}
	public List<Lifeline> getLifelines() {
		return lifelines;
	}

}
