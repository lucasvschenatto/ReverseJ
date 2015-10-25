package acceptance.bancoDiego;

public class AtendenteDeCaixa extends Funcionario implements Autentica {

	public AtendenteDeCaixa(String nome, int idade, String setor, int id, double salario, int guiche) {
		super(nome, idade, setor, id, salario);
	}






	public boolean autentica(String senha)
	{
		if (senha == "atende123")
			return true;
		else
			return false;
		
	}

	
}
