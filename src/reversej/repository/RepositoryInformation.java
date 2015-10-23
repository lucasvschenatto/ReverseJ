package reversej.repository;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import reversej.diagram.RepositoryProvider;
import reversej.information.Information;
import reversej.information.InformationFactory;
import reversej.information.impl.InformationFactoryImpl;
import reversej.tracer.RepositoryRecorder;

public class RepositoryInformation implements RepositoryRecorder, RepositoryProvider {
	private List <Information> list;
	private Iterator <Information> iterator;
	private InformationFactory factory;
	public RepositoryInformation(){
		list = new LinkedList<Information>();
		factory = new InformationFactoryImpl();
	}
	@Override
	public void addInformation(Information info) {
		list.add(info);
	}
	@Override
	public void addInformation(String type, String value){
		addInformation(factory.create(type, value));
	}
	@Override
	public List<Information> getAll() {
		return list;
	}
	@Override
	public List<String> describeAll(){
		List<String> allInformations = new LinkedList<String>();
		for (Information toConvert : list)
			allInformations.add(toConvert.describe());
		return allInformations;		
	}
	@Override
	public int size() {
		return list.size();
	}
	@Override
	public Information getNext() {
		if (iterator == null)
			iterator = list.iterator();
		return iterator.hasNext()? iterator.next() : null;
	}
	@Override
	public boolean isEmpty() {
		if (list.isEmpty())
			return true;
		return false;
	}
	@Override
	public Information getInfo(String value) {
		for (Information information : list)
			if(information.getValue() == value)
				return information;
		return null;
	}
	
	
	
	
}