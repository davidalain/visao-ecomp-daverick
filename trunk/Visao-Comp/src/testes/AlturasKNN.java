package testes;

import java.io.IOException;
import java.util.Random;

import upe.classificacao.CSVAlturas;
import upe.classificacao.ImageClass;
import upe.classificacao.Knn;

public class AlturasKNN {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		int iRandom = new Random().nextInt(1000);
		int jRandom = 54+(new Random().nextInt(18));
		
		System.out.println("Classe = " + (iRandom+1) + " Imagem = r" + (jRandom*5));
		
		CSVAlturas csv = new CSVAlturas();
		ImageClass teste = csv.retornaImagem(iRandom, jRandom);
		
		ImageClass[] vizinhos = Knn.calcularVizinhos(teste.getCaracteristicas(), 100);
		
		System.out.print("Vizinhos = ");
		
		for (int i = 0; i < vizinhos.length; i++) {
			System.out.print(vizinhos[i].getClasse() + ", ");
		}
		
	}

}
