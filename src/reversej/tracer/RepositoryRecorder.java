package reversej.tracer;

import java.util.List;

import reversej.information.Information;

public interface RepositoryRecorder {
	public abstract boolean isEmpty();

	public abstract List<String> describeAll();

	public abstract int size();

	public abstract void addInformation(Information info);
	
	public abstract void addInformation(String type, String content);

	public abstract Information getInfo(String string);
}