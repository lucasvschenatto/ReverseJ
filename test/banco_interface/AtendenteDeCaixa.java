package banco_interface;

public class AtendenteDeCaixa extends Funcionario implements Autentica {

	private int guiche;
	
	
	public AtendenteDeCaixa(String nome, int idade, String setor, int id, double salario, int guiche) {
		super(nome, idade, setor, id, salario);
		this.guiche = guiche;
	}






	public boolean autentica(String senha)
	{
		if (senha == "atende123")
			return true;
		else
			return false;
		
	}

	
}
