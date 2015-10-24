package reversej.diagram;

import java.util.List;

public interface RepositoryProvider {
	public abstract List<Information> getAll(InformationFactory factory);
	public abstract Information getNext(InformationFactory factory);
}
