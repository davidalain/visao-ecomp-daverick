package testes;

import upe.pdi.imagem.Imagem;
import upe.pdi.util.Util;

public class Filtrando {

	public static final String MASK_PATH = "D:\\UPE\\9 periodo\\Visao\\mask4\\";
	public static final String IMAGE_PATH = "D:\\UPE\\9 periodo\\Visao\\aloi_grey_red4_view\\";
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {

		for(int j=0; j<1000; j++){
			int classe = j+1;
			int acertou = 0;

			for(int i=0; i<72; i++){
				int angulo = 5*i;
				//System.out.print("Sorteado = " + angulo);

				Imagem imgFinal = pegarImagem(classe, angulo);

				int primeiraProximidade = anguloProximo(imgFinal, classe);

				int segundaProximidade = anguloProximo(imgFinal, classe, primeiraProximidade, 25);
								
				int anguloFinal = anguloProximo(imgFinal, classe, segundaProximidade, 5);

				//System.out.println("  Escolhido = " + anguloFinal);

				if(angulo == anguloFinal){
					acertou++;
				}
			}

			double porcentagem = (acertou/72.0)*100;
			System.out.println(porcentagem);
		}
	}

	private static int anguloProximo(Imagem imagem, int classe) throws Exception{
		int[] angulos = {0, 90, 180, 270, 355};
		int[] extremosRef = imagem.getExtremos();
		
		Imagem temp = null;
		double menorDist = Double.MAX_VALUE;
		int anguloMaisProx = 0;
		for (int i = 0; i < angulos.length; i++) {
			temp = pegarImagem(classe, angulos[i]);

			int[] extremosTemp = temp.getExtremos();
			double distTemp = distanciaEuclidiana(extremosRef, extremosTemp);

			if( distTemp < menorDist){
				menorDist = distTemp;
				anguloMaisProx = angulos[i];
			}
		}

		return anguloMaisProx;
	}

	private static int anguloProximo(Imagem imagem, int classe, int primeiroAngulo, int margem) throws Exception{
		int[] angulos = new int[5];

		for(int i=0; i<4; i++){
			if(i == 2)
				margem *= 2;

			angulos[i] = primeiroAngulo-margem;

			if(angulos[i] < 0){
				angulos[i] = 0;
			}

			i++;
			angulos[i] = primeiroAngulo + margem;

			if(angulos[i] > 355){
				angulos[i] = 355;
			}
		}

		angulos[4] = primeiroAngulo;
		int[] extremosRef = imagem.getExtremos();

		Imagem temp = null;
		double menorDist = Double.MAX_VALUE;
		int anguloMaisProx = 0;
		for (int i = 0; i < angulos.length; i++) {
			temp = pegarImagem(classe, angulos[i]);

			int[] extremosTemp = temp.getExtremos();
			double distTemp = distanciaEuclidiana(extremosRef, extremosTemp);

			if( distTemp < menorDist){
				menorDist = distTemp;
				anguloMaisProx = angulos[i];
			}
		}

		return anguloMaisProx;
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
