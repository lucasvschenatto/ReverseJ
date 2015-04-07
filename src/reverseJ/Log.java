package reverseJ;
import java.util.LinkedList;
import java.util.List;

public class Log implements RecorderStorage {
	private List <Information> list;
	static final String emptyLogInfo = "**Empty log**";
	public Log(){
		list = new LinkedList<Information>();
	}
	@Override
	public void addInformation(String name, String value) {
		Information info = new Information();
		info.setName(name);
		info.setValue(value);	
		list.add(info);
	}

	@Override
	public String describe (String informationName) throws NotFoundInformationException{
		for (Information information : list)
			if (information.getName() == informationName)
				return information.getValue();
		throw new NotFoundInformationException();
	}
	class Information {
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		private String name;
		private String value;
	}
	@Override
	public String[] describeAll(){
		String [] allInformations = new String [list.size()];
		Information[] toConvert = list.toArray(new Information[0]);
		for (int count = 0; count < toConvert.length; count++)
			allInformations [count] = toConvert[count].getName() + " : "
									+ toConvert[count].getValue();
		if (allInformations.length == 0)
			allInformations = new String[] {emptyLogInfo};
		return allInformations;
		
	}
	class NotFoundInformationException extends Exception{
		private static final long serialVersionUID = 1L;}
	class EmptyLogException extends Exception{
		private static final long serialVersionUID = 1L;}
}