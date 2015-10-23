package reversej.diagram;

import java.util.List;

import reversej.information.Information;

public interface RepositoryProvider {
	public abstract List<Information> getAll();
	public abstract Information getNext();
}
