package testes;

import java.util.ArrayList;
import java.util.Random;

import upe.pdi.imagem.Imagem;
import upe.pdi.util.Util;

public class AproximacaoPor4 {

	public static final String MASK_PATH = "D:\\UPE\\9 periodo\\Visao\\mask4\\";
	public static final String IMAGE_PATH = "D:\\UPE\\9 periodo\\Visao\\aloi_grey_red4_view\\";

	public static final DistanciaComparator comparator = new DistanciaComparator();

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {

		int classe = 1;

		for(int r=0; r<10; r++){
			int anguloRef = new Random().nextInt(71)*5;

			ArrayList<AnguloDistancias> lista = new ArrayList<AnguloDistancias>();
			//System.out.print("Sorteado = " + anguloRef);

			int[] extremosRef = pegarImagem(classe, anguloRef).getExtremos();

			System.out.println("Angulo Ref = " + anguloRef);

			int angulos[] = RandomIntGenerator.getAngulos(4);

		
			int indexMenor = -1;
			double proximoMenor = Double.MAX_VALUE;

			int indexMaior = -1;
			double proximoMaior = Double.MIN_VALUE;

			int[] extremosTemp; 
			double distanciaTemp = 0;
			
			for (int i = 0; i < angulos.length; i++) {
				System.out.print(angulos[i] + ", ");
				
				extremosTemp = pegarImagem(classe, angulos[i]).getExtremos();
				
				distanciaTemp = distanciaEuclidiana(extremosRef, extremosTemp);
				
				if(distanciaTemp > proximoMaior){
					proximoMaior = distanciaTemp;
					indexMaior = i;
				}
				
				if(distanciaTemp < proximoMenor){
					proximoMenor = distanciaTemp;
					indexMenor = i;
				}
			}
			System.out.println();
			

			if(indexMenor < 0){
				System.out.print("[ , ");
			}else{
				System.out.print("[" + angulos[indexMenor] + " , ");
			}

			if(indexMaior < 0){
				System.out.print(" ]");
			}else{
				System.out.print(angulos[indexMaior] + "]");
			}
			
			System.out.println();

		}
	}

	private static Imagem pegarImagem(int classe, int angulo) throws Exception{
		String imagemPath = classe + "\\"+ classe +"_r"+ angulo + ".png";

		String mascaraPath = "";
		if(angulo == 0){
			mascaraPath = classe + "\\"+ classe +"_c1.png";
		}else{
			mascaraPath = classe + "\\"+ classe +"_r"+ angulo +".png";
		}

		double[][] mascara = Util.lerImagem(MASK_PATH + mascaraPath);
		double[][] imagem = Util.lerImagem(IMAGE_PATH + imagemPath);

		Imagem img = new Imagem(imagem);

		return img.aplicarMascara(mascara);
	}

	private static double distanciaSimples(int[] pontos1, int[] pontos2){
		double soma = 0;

		for (int i = 0; i < pontos1.length; i++) {
			soma += (pontos1[i] - pontos2[i]);
		}

		return soma;
	}

	private static double distanciaEuclidiana(int[] pontos1, int[] pontos2){
		double soma = 0;

		for (int i = 0; i < pontos1.length; i++) {
			soma += (pontos1[i] - pontos2[i])*(pontos1[i] - pontos2[i]);
		}

		return Math.sqrt(soma);
	}
}
