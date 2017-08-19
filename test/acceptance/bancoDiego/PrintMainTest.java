
package acceptance.bancoDiego;

import java.util.List;

import reversej.repository.RepositoryInMemory;
import reversej.tracer.TracerController;

public class PrintMainTest {

	public static void main(String[] args) {
		RepositoryInMemory r = new RepositoryInMemory();
		TracerController.start(r);
		
		Gerente gerente1 = new Gerente("Heitor", 31, "Seguro", 85695, 2200.00, "05/05/2015");
		new AtendenteDeCaixa("Virginia", 28, "boletos", 55469, 1200.00, 3);
		System.out.println(gerente1.autentica("gerent123"));
		
		Cliente cliente1 = new Cliente("Antonio", 27);
		Cliente cliente2 = new Cliente("Antonio", 27, 2700.00);
		new Cliente("Antonio", 27);
		
		cliente1.fazerEmprestimo();
		
		double d = cliente2.getSalario();
		Cliente.simulaEmprestimo(d);
		
		TracerController.stop();		
		List<String> informations = r.describeAll();
		for (String information : informations)
			System.out.println(information);
	}

}
