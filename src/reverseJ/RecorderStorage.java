package reverseJ;

import java.util.List;

public interface RecorderStorage {
	public abstract boolean isEmpty();

	public abstract List<String> describeAll();

	public abstract int size();

	public abstract void addInformation(Information info);

	public abstract Information getInfo(String string);
}