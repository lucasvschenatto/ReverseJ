package acceptance.bancoDiego;

import java.util.Date;


public class Funcionario extends Pessoa {
	
	private String setor;
	private int id;
	private double salario;
	
	
	    public Funcionario (String nome, int idade, String setor, int id, double salario) {
		super(nome, idade);
		this.setor = setor;
		this.id = id;
		this.salario = salario;
	}
	    
	    
	
	
	
	
	

}
