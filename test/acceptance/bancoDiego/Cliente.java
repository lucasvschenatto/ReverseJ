package acceptance.bancoDiego;



public class Cliente extends Pessoa {
	
	private double salario;
	
	public static int quantClientes;
	
	
	Cliente(String nome, int idade, double salario)
	{
		super(nome, idade);
		this.salario = salario;
		quantClientes ++;
	}
	
	Cliente(String nome, int idade)
	{
		super(nome, idade);
		this.salario = 0;
		quantClientes ++;
	}
	
	public double getSalario()
	{
		return this.salario;
	}
	
	public void fazerEmprestimo()
	{
		if (salario <= 0)
		System.out.println("Empréstimo negado!!");
		else 
			System.out.println("Emprestimo de " + salario * 1.5 + " reais");
	}
	
	public static void simulaEmprestimo(double salario)
	{
		
		System.out.println("Valor simulação emprestimo: " + salario);
		
	}
	
	

}
