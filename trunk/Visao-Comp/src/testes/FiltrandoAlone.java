package testes;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Random;

import upe.pdi.imagem.Imagem;
import upe.pdi.util.Util;

public class FiltrandoAlone {

	public static final String MASK_PATH = "D:\\UPE\\9 periodo\\Visao\\mask4\\";
	public static final String IMAGE_PATH = "D:\\UPE\\9 periodo\\Visao\\aloi_grey_red4_view\\";
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {

		for(int r=0; r<10; r++){

			System.out.println("*** Run " + (r+1) + " ***");
			
			FileOutputStream fos = new FileOutputStream("arquivos/filtrando_" + (r+1) + ".csv" );
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			BufferedWriter bw = new BufferedWriter(osw);
			
			double acertoTotal = 0;
			
			for(int i=0; i<100; i++){
				int classe =i+1;
				double acertoClasse = 0;

				for(int j=0; j<71; j++){

					int angulo = (j+1)*5;

					Imagem imgFinal = pegarImagem(classe, angulo);

					int[] angulosRef = gerarAngulos();

					int[] angulosFinais = angulosProximo(imgFinal, classe, angulosRef);

					if(acertou(angulo, angulosFinais)){
						acertoClasse++;
					}
				}

				acertoClasse /= 72.0;
				
				bw.write(acertoClasse + "");
				bw.newLine();
				
				System.out.print((r+1) + "." + classe + " - ");
				//System.out.print("Acerto classe = " + acertoClasse*100 + "%");

				acertoTotal += acertoClasse;
				System.out.print("\t Acerto total = " + (acertoTotal/(i+1))*100 + "%");

				System.out.println();
			}
			
			bw.close();
		}

	}

	private static boolean acertou(int anguloRef, int[] angulosFinais){
		if(angulosFinais[0] <= anguloRef && angulosFinais[1] >= anguloRef){
			return true;
		}

		return false;
	}

	private static int[] gerarAngulos() {

		int anguloRand = new Random().nextInt(71)*5;


		//Quantos angulos com distancia de 90 graus eu tenho ate chegar em 0
		int contAngulosAntes = (anguloRand - 0)/90;

		//Quantos angulos com distancia de 90 graus eu tenho ate chegar em 355
		int contAngulosDepois = (355 - anguloRand)/90;

		int[] angAntes;
		if(contAngulosAntes == 0){
			angAntes = new int[1];

			angAntes[0] = 0;
		}else{
			angAntes = new int[contAngulosAntes];

			for (int i = 0; i < angAntes.length; i++) {
				angAntes[i] = (anguloRand - 90*(i+1));
			}
		}

		int[] angDepois;
		if(contAngulosDepois == 0){
			angDepois = new int[1];

			angDepois[0] = 355;
		}else{
			angDepois = new int[contAngulosDepois];

			for (int i = 0; i < angDepois.length; i++) {
				angDepois[i] = (anguloRand + 90*(i+1));
			}
		}


		int[] retorno = new int[angAntes.length + angDepois.length + 1];

		for (int i = 0; i < retorno.length; i++) {
			if(i < angAntes.length){
				retorno[i] = angAntes[i];
			}else if(i == angAntes.length){
				retorno[i] = anguloRand;
			}
			else{
				retorno[i] = angDepois[i-angAntes.length-1];
			}
		}

		Arrays.sort(retorno);

		return retorno;
	}

	private static int[] angulosProximo(Imagem imagem, int classe, int[] angulos) throws Exception{
		int[] extremosRef = imagem.getExtremos();

		Imagem temp = null;
		double menorDist = Double.MAX_VALUE;
		int anguloMaisProx = 0;
		int anguloMaisProx2 = 0;
		for (int i = 0; i < angulos.length; i++) {
			temp = pegarImagem(classe, angulos[i]);

			int[] extremosTemp = temp.getExtremos();
			double distTemp = distanciaEuclidiana(extremosRef, extremosTemp);

			if( distTemp < menorDist){
				menorDist = distTemp;
				anguloMaisProx = angulos[i];

				if(i > 0){
					anguloMaisProx2 = angulos[i-1];
				}
			}
		}

		int[] retorno = {anguloMaisProx2, anguloMaisProx}; 
		return retorno;

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
