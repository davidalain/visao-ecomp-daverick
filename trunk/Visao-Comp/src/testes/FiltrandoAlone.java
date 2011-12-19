package testes;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Random;

import upe.pdi.imagem.Imagem;
import upe.pdi.util.Util;

public class FiltrandoAlone {

	//public static final String MASK_PATH = "D:\\UPE\\9 periodo\\Visao\\mask4\\";
	//public static final String IMAGE_PATH = "D:\\UPE\\9 periodo\\Visao\\aloi_grey_red4_view\\";
	
	
	public static final String MASK_PATH = "D:\\UPE\\2011.2\\Visão Computacional\\aloi_mask2\\mask2\\";
	public static final String MASKED_IMAGES_PATH = "D:\\UPE\\2011.2\\Visão Computacional\\imagens_mascaradas\\";
	public static final String IMAGE_PATH = "D:\\UPE\\2011.2\\Visão Computacional\\aloi_grey_red2_view\\grey2\\";
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {

		run(6,"arquivos/filtrando_extremos",false);
		run(6,"arquivos/filtrando_extremos_histograma",true);
		
	}

	private static void run(int quantidadeExecucoes, String outputName, boolean extremosApenas) throws Exception {
		
		for(int r=0 ; r < quantidadeExecucoes ; r++){

			System.out.println("*** Run " + (r+1) + " ***");
			
			FileOutputStream fos = new FileOutputStream(outputName + (r+1) + ".csv" );
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			BufferedWriter bw = new BufferedWriter(osw);
			
			double acertoTotal = 0;
			
			//Mesma semente para todas as classes
			int[] angulosRef = gerarAngulos();
			System.out.print("Sementes:");
			bw.write("Sementes:");
			for(int i = 0 ; i < angulosRef.length ; i++){
				System.out.print(" "+angulosRef[i]);
				bw.write(";"+angulosRef[i]);
			}
			System.out.println();
			bw.newLine();
			bw.flush();
			
			for(int i=0; i<1000; i++){
				int classe =i+1;
				double acertoClasse = 0;

				for(int j=0; j<72; j++){

					int angulo = (j+1)*5 % 360;

					Imagem imgFinal = pegarImagemMascarada(classe, angulo);

					int[] angulosFinais = extremosApenas ?  
							angulosProximosExtremos(imgFinal, classe, angulosRef) :
							angulosProximosCaracteristicas(imgFinal, classe, angulosRef);

					if(acertou(angulo, angulosFinais)){
						acertoClasse++;
					}
				}

				acertoClasse /= 72.0;
				
				bw.write(acertoClasse + "");
				bw.newLine();
				bw.flush();
				
				System.out.print((r+1) + "." + classe + " - ");
				System.out.print("Acerto classe = " + acertoClasse*100 + "%");

				acertoTotal += acertoClasse;
				System.out.print("\t Acerto total = " + (acertoTotal/(i+1))*100 + "%");

				System.out.println();
			}
			
			bw.close();
		}
		
	}

	/**
	 * Verifica se o angulo dado esta entre os outros dois passados como parametro
	 * @param anguloRef Angulo dado
	 * @param angulosFinais Angulos de referencia
	 * @return True - Angulo esta entre os outros dois; False - Angulo nao esta entre os outros dois
	 */
	private static boolean acertou(int anguloRef, int[] angulosFinais){

		//Caso especial
		if(angulosFinais[1] < angulosFinais[0]){
			//exemplo:
			//angulosFinais = {350, 80} , anguloRef = 5
			//angulosFinais = {350, 80} , anguloRef = 355

			if(angulosFinais[0] <= anguloRef || angulosFinais[1] >= anguloRef){
				return true;
			}
			
		}else
		if(angulosFinais[0] <= anguloRef && angulosFinais[1] >= anguloRef){
			return true;
		}

		return false;
	}

	/**
	 * Gera os angulos para a comparacao
	 * @return Angulos para comparacao
	 */
//	private static int[] gerarAngulos() {
//
//		//Sorteia um angulo entre 0-355
//		int anguloRand = new Random().nextInt(71)*5;
//
//
//		//Quantos angulos com distancia de 90 graus eu tenho ate chegar em 0
//		int contAngulosAntes = (anguloRand - 0)/90;
//
//		//Quantos angulos com distancia de 90 graus eu tenho ate chegar em 355
//		int contAngulosDepois = (355 - anguloRand)/90;
//
//		//Defino quais angulos estao entre 0-anguloRand com uma diferenca de 90 graus
//		int[] angAntes;
//		if(contAngulosAntes == 0){
//			angAntes = new int[1];
//
//			angAntes[0] = 0;
//		}else{
//			angAntes = new int[contAngulosAntes];
//
//			for (int i = 0; i < angAntes.length; i++) {
//				angAntes[i] = (anguloRand - 90*(i+1));
//			}
//		}
//
//		//Defino quais angulos estao entre anguloRand-355 com uma diferenca de 90 graus
//		int[] angDepois;
//		if(contAngulosDepois == 0){
//			angDepois = new int[1];
//
//			angDepois[0] = 355;
//		}else{
//			angDepois = new int[contAngulosDepois];
//
//			for (int i = 0; i < angDepois.length; i++) {
//				angDepois[i] = (anguloRand + 90*(i+1));
//			}
//		}
//
//		//Organizo os angulos descobertos acima para retorna-los de forma ordenada
//		int[] retorno = new int[angAntes.length + angDepois.length + 1];
//		for (int i = 0; i < retorno.length; i++) {
//			if(i < angAntes.length){
//				retorno[i] = angAntes[i];
//			}else if(i == angAntes.length){
//				retorno[i] = anguloRand;
//			}
//			else{
//				retorno[i] = angDepois[i-angAntes.length-1];
//			}
//		}
//
//		Arrays.sort(retorno);
//
//		return retorno;
//	}
	
	private static int[] gerarAngulos() {

		//Sorteia um angulo entre 0-355
		int anguloRand = new Random().nextInt(71)*5;
		
		//Organizo os angulos descobertos acima para retorna-los de forma ordenada
		int[] retorno = new int[4];

		for(int i = 0 ; i < 4 ; i++){
			retorno[i] = (anguloRand + (90*i)) % 360;
		}

		Arrays.sort(retorno);

		return retorno;
	}

	/**
	 * Dado uma imagem define entre quais dos angulos ela se encontra
	 * @param imagem Imagem de entrada
	 * @param classe Classe da imagem
	 * @param angulos Angulos para comparacao
	 * @return Angulos que a imagem esta no meio 
	 * @throws Exception
	 */
//	private static int[] angulosProximo(Imagem imagem, int classe, int[] angulos) throws Exception{
//		int[] extremosRef = imagem.getExtremos();
//
//		Imagem temp = null;
//		double menorDist = Double.MAX_VALUE;
//		int anguloMaisProx = -1;
//		int anguloMaisProx2 = -1;
//		
//		for (int i = 0; i < angulos.length; i++) {
//			temp = pegarImagem(classe, angulos[i]);
//
//			int[] extremosTemp = temp.getExtremos();
//			double distTemp = distanciaEuclidiana(extremosRef, extremosTemp);
//
//			if( distTemp < menorDist){
//				menorDist = distTemp;
//				anguloMaisProx = angulos[i];
//
//				if(i > 0){
//					anguloMaisProx2 = angulos[i-1];
//				}
//			}
//		}
//
//		System.out.println("Ang1: "+anguloMaisProx + ", Ang2:" + anguloMaisProx2);
//		int[] retorno = {anguloMaisProx2, anguloMaisProx}; 
//		return retorno;
//
//	}
	private static int[] angulosProximosExtremos(Imagem imagem, int classe, int[] angulos) throws Exception{
		int[] extremosRef = imagem.getExtremos();

		Imagem temp = null;
		int indiceMaisProx = -1;
		double menorDistancia = Double.MAX_VALUE;
		double[] distancias = new double[angulos.length];
		int[] retorno = new int[2];
		
		for (int i = 0; i < angulos.length; i++) {
			temp = pegarImagem(classe, angulos[i]);

			int[] extremosTemp = temp.getExtremos();
			distancias[i] = distanciaEuclidiana(extremosRef, extremosTemp);
		}
		
		for(int i = 0 ; i < angulos.length ; i++){
			if(menorDistancia > distancias[i]){
				menorDistancia = distancias[i];
				indiceMaisProx = i;
			}
		}
		
		int anterior = (indiceMaisProx + 3) % 4; //O mais correto seria (indiceMaisProx - 1) % 4, mas o Java resultados de módulo negativos para valores negativos. Blah! 
		int posterior = (indiceMaisProx + 1) % 4;
		if(distancias[anterior] <= distancias[posterior]){
			retorno[0] = angulos[anterior];
			retorno[1] = angulos[indiceMaisProx];
		}else{
			retorno[0] = angulos[indiceMaisProx];
			retorno[1] = angulos[posterior];
		}
		
		return retorno;

	}
	
	private static int[] angulosProximosCaracteristicas(Imagem imagem, int classe, int[] angulos) throws Exception{
		int[] extremosRef = imagem.getCaracteristicas();

		Imagem temp = null;
		int indiceMaisProx = -1;
		double menorDistancia = Double.MAX_VALUE;
		double[] distancias = new double[angulos.length];
		int[] retorno = new int[2];
		
		for (int i = 0; i < angulos.length; i++) {
			temp = pegarImagem(classe, angulos[i]);

			int[] extremosTemp = temp.getCaracteristicas();
			distancias[i] = distanciaEuclidiana(extremosRef, extremosTemp);
		}
		
		for(int i = 0 ; i < angulos.length ; i++){
			if(menorDistancia > distancias[i]){
				menorDistancia = distancias[i];
				indiceMaisProx = i;
			}
		}
		
		int anterior = (indiceMaisProx + 3) % 4; //O mais correto seria (indiceMaisProx - 1) % 4, mas o Java resultados de módulo negativos para valores negativos. Blah! 
		int posterior = (indiceMaisProx + 1) % 4;
		if(distancias[anterior] <= distancias[posterior]){
			retorno[0] = angulos[anterior];
			retorno[1] = angulos[indiceMaisProx];
		}else{
			retorno[0] = angulos[indiceMaisProx];
			retorno[1] = angulos[posterior];
		}
		
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
	
	private static Imagem pegarImagemMascarada(int classe, int angulo){
		String imagemPath = classe + "\\"+ classe +"_r"+ angulo + ".png";
		double[][] imagemMascarada = null;
		try{
			imagemMascarada = Util.lerImagem(MASKED_IMAGES_PATH + imagemPath);
		}catch (Exception e) {
			System.out.println("Tentou abrir: "+MASKED_IMAGES_PATH + imagemPath);
			e.printStackTrace();
		}
		
		return new Imagem(imagemMascarada);
	}

	private static double distanciaEuclidiana(int[] pontos1, int[] pontos2){
		double soma = 0;

		for (int i = 0; i < pontos1.length; i++) {
			soma += (pontos1[i] - pontos2[i])*(pontos1[i] - pontos2[i]);
		}

		return Math.sqrt(soma);
	}
	
	private static double distanciaEuclidianaPonderada(int[] pontos1, int[] pontos2){
		double soma = 0;

		int coeficiente = 2;
		for (int i = 0; i < pontos1.length; i++) {
			if(i > 3){
				coeficiente = 1;
			}
			soma += coeficiente*(pontos1[i] - pontos2[i])*(pontos1[i] - pontos2[i]);
		}

		return Math.sqrt(soma);
	}
}
