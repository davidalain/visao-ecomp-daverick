package testes;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;


public class Main_CBIR {

	public static final int CLASS_QNT = 50;
	public static final int ANGLES_QNT = 20;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		for(int j=2; j<10; j++){
			int classes[] = RandomIntGenerator.getClasses(CLASS_QNT);
			int angulosRef[] = RandomIntGenerator.getAngulos(ANGLES_QNT);

			System.out.println("***************************");
			System.out.println("****** CBIR PROPOSTO ******");
			System.out.println("***************************\n\n");

			long time = 0;
			double[] precisionProposta = null;
			double[] precisionSimples = null;


			try {
				time = System.currentTimeMillis();
				precisionProposta = CBIR.run(classes, angulosRef);

				System.out.println("Tempo = " + ((System.currentTimeMillis() - time)/1000)/60 + " min");




				System.out.println("***************************");
				System.out.println("****** CBIR Simples ******");
				System.out.println("***************************\n\n");

				time = System.currentTimeMillis();
				precisionSimples = CBIR_simples.run(classes, angulosRef);

				System.out.println("Tempo = " + ((System.currentTimeMillis() - time)/1000)/60 + " min");

				FileOutputStream fos = new FileOutputStream("arquivos/curvas_" + (j+1) + ".csv" );
				OutputStreamWriter osw = new OutputStreamWriter(fos);
				BufferedWriter bw = new BufferedWriter(osw);

				bw.write("Proposta\t\tSimples");

				for (int i = 0; i < precisionSimples.length; i++) {
					bw.write(precisionProposta[i] + "\t" + precisionSimples[i]);
					bw.newLine();
				}

				bw.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


	}

}
