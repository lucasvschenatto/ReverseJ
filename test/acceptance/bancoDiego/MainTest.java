
package acceptance.bancoDiego;

import javax.swing.JFrame;

import reversej.plugin.SimplePlugin;

public class MainTest {

	public static void main(String[] args) {
		SimplePlugin.start();
		
		Gerente gerente1 = new Gerente("Heitor", 31, "Seguro", 85695, 2200.00, "05/05/2015");
		new AtendenteDeCaixa("Virginia", 28, "boletos", 55469, 1200.00, 3);
		System.out.println(gerente1.autentica("gerent123"));
		
		Cliente cliente1 = new Cliente("Antonio", 27);
		Cliente cliente2 = new Cliente("Antonio", 27, 2700.00);
		new Cliente("Antonio", 27);
		
		cliente1.fazerEmprestimo();
		
		Cliente.simulaEmprestimo(cliente2.getSalario());
		
		
//		JFrame janelinha = new JFrame();
//		janelinha.setVisible(true);
//		janelinha.setSize(200,300);
		
		SimplePlugin.stop();
	}

}
