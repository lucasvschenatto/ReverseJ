package acceptance.bancoDiego;


public class Gerente extends Funcionario implements Autentica {

	public Gerente(String nome, int idade, String setor, int id,double salario, String dataPromocao) {
		super(nome, idade, setor, id, salario);
	}


	@Override
	public boolean autentica(String senha) {
		
		if (senha == "gerente123")
			return true;
		else
			return false;
	}
	
}
