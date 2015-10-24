package reversej.repository;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import reversej.diagram.Information;
import reversej.diagram.InformationFactory;
import reversej.diagram.RepositoryProvider;
import reversej.diagram.informationmodel.InformationFactoryImpl;
import reversej.tracer.RepositoryRecorder;

public class RepositoryInMemory implements RepositoryRecorder, RepositoryProvider {
	private List<TypeValue> list;
	private Iterator<TypeValue> iterator;
	private InformationFactory factory;
	public RepositoryInMemory(){
		list = new LinkedList<TypeValue>();
		factory = new InformationFactoryImpl();
	}
	@Override
	public void addInformation(String type, String value){
		list.add(new TypeValue(type,value));
	}
	@Override
	public List<Information> getAll(InformationFactory factory) {
		List<Information> informations = new LinkedList<Information>();
		for (TypeValue typeValue : list) {
			informations.add(factory.create(typeValue.type, typeValue.value));
		}
		return informations;
	}
	@Override
	public List<String> describeAll(){
		List<String> allInformations = new LinkedList<String>();
		for (TypeValue typeValue : list) {
			allInformations.add(factory.create(typeValue.type, typeValue.value).describe());
		}
		return allInformations;		
	}
	@Override
	public int size() {
		return list.size();
	}
	@Override
	public Information getNext(InformationFactory factory) {
		if (iterator == null)
			iterator = list.iterator();
		TypeValue next = iterator.hasNext()? iterator.next() : null;
		return next==null?null:factory.create(next.type, next.value);
	}
	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}
	@Override
	public String describeInfo(String value) {
		for (TypeValue typeValue : list) {
			if(typeValue.value == value)
				return typeValue.type+" : "+typeValue.value;
		}
		return null;
	}	
	private class TypeValue{
		private String type;
		private String value;
		private TypeValue(String type,String value){
			this.type = type;
			this.value = value;
		}
	}
}