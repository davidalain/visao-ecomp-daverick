package testes;

import java.util.ArrayList;
import java.util.Collections;

import upe.pdi.imagem.Imagem;
import upe.pdi.util.Util;

public class Proximidade {

	public static final String MASK_PATH = "D:\\UPE\\9 periodo\\Visao\\mask4\\";
	public static final String IMAGE_PATH = "D:\\UPE\\9 periodo\\Visao\\aloi_grey_red4_view\\";

	private static int[] angulos = {125, 120, 235, 95, 260, 10, 25, 285, 110, 270, 85, 45, 75, 245, 165, 155, 60, 325, 175, 
		50, 100, 220, 65, 340, 215, 80, 35, 130, 265, 185, 115, 320, 70, 280, 275, 315, 160, 20, 15, 205, 345, 335, 240, 
		330, 105, 350, 210, 295, 140, 40, 145, 230, 5, 30, 255, 310, 150, 170, 305, 290, 250, 300, 190, 135, 55, 195, 200, 
		90, 225, 355, 180};
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {


		int classe = 1;
		int acertou = 0;

		DistanciaComparator comparator = new DistanciaComparator();

		for (int i = 0; i < angulos.length; i++) {
			ArrayList<AnguloDistancias> lista = new ArrayList<AnguloDistancias>();
			int anguloRef = angulos[i];
			System.out.print("Sorteado = " + anguloRef);
			int[] extremosRef = pegarImagem(classe, anguloRef).getExtremos();

			for (int j = 0; j < 72; j++) {
				int anguloTemp = j*5;
				int[] extremosTemp = pegarImagem(classe, anguloTemp).getExtremos();

				double distanciaTemp = distanciaEuclidiana(extremosRef, extremosTemp);
				
				lista.add(new AnguloDistancias(distanciaTemp, anguloTemp));
			}
			
			Collections.sort(lista, comparator);
			System.out.println("Fim");
		}




		System.out.println("Fim");
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
	
	private static double distanciaEuclidiana(int[] pontos1, int[] pontos2){
		double soma = 0;

		for (int i = 0; i < pontos1.length; i++) {
			soma += (pontos1[i] - pontos2[i])*(pontos1[i] - pontos2[i]);
		}

		return Math.sqrt(soma);
	}
}
