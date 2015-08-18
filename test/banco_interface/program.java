
package banco_interface;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;

import reverseJ.*;

public class program {

	public static void main(String[] args) {
		InformationStorageProvider i = new InformationStorageProvider();
		RecorderStorage r = i;
		InformationProvider p = i;
		List<DiagramStrategy> lds = new LinkedList<DiagramStrategy>();
		lds.add(new SequenceDiagram());
		lds.add(new ClassDiagram());
		DiagramMaker dM = new MakerAndSaver(p, lds);
		((MakerAndSaver)dM).setFileName("classeESequencia");
		Tracer.start(r);
		
		Gerente gerente1 = new Gerente("Heitor", 31, "Seguro", 85695, 2200.00, "05/05/2015");
		AtendenteDeCaixa atendente1 = new AtendenteDeCaixa("Virginia", 28, "boletos", 55469, 1200.00, 3);
		System.out.println(gerente1.autentica("gerent123"));
		
		Cliente cliente1 = new Cliente("Antonio", 27);
		Cliente cliente2 = new Cliente("Antonio", 27, 2700.00);
		Cliente cliente3 = new Cliente("Antonio", 27);
		
		cliente1.fazerEmprestimo();
		
		Cliente.simulaEmprestimo(cliente2.getSalario()); 
		
		
		JFrame janelinha = new JFrame();
		janelinha.setVisible(true);
		janelinha.setSize(200,300);
		
		Tracer.stop();
		for (String info : r.describeAll()) {
			System.out.println(info);
		}
		dM.make();
		
	}

}
