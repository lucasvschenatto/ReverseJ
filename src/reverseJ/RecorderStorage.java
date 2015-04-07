package reverseJ;

import reverseJ.Log.NotFoundInformationException;

public interface RecorderStorage {

	public abstract void addInformation(String name, String value);

	public abstract String describe(String informationName)
			throws NotFoundInformationException;

	public abstract String[] describeAll();

}