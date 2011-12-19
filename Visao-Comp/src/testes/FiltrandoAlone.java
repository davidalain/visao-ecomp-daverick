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

		//run(6,"arquivos/filtrando_extremos",true);
		run(1,"arquivos/filtrando_extremos+histograma",false);
		
	}

	/**
	 * Executa a classificação
	 * @param quantidadeExecucoes
	 * @param outputName
	 * @param extremosApenas
	 * @throws Exception
	 */
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
			
			//Testa para as 1000 classes
			for(int i=0; i<1000; i++){
				int classe =i+1;
				double acertoClasse = 0;

				//Testa para as 72 imagens de cada classe
				for(int j=0; j<72; j++){

					int angulo = (j+1)*5 % 360;

					Imagem imgFinal = pegarImagemMascarada(classe, angulo);

					int[] angulosFinais = extremosApenas ?  
							angulosProximosExtremos(imgFinal, classe, angulosRef) :
							angulosProximosCaracteristicas(imgFinal, classe, angulosRef);

					//Verifica se o angulo dado está entre os angulosFinais obtidos que estão mais próximos
					if(acertou(angulo, angulosFinais)){
						acertoClasse++;
					}
				}

				acertoClasse /= 72.0;
				
				//Escreve no arquivo a taxa de acerto obtida nesta classe.
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
	private static int[] gerarAngulos() {

		//Sorteia um angulo entre 0-355
		int anguloRand = new Random().nextInt(71)*5;
		
		//Ângulos que serão retornados
		int[] retorno = new int[4];

		//Pega os outros 3 ângulos com diferença de 90 graus cada
		for(int i = 0 ; i < 4 ; i++){
			retorno[i] = (anguloRand + (90*i)) % 360;
		}
		
		//Organizo os angulos descobertos acima para retorna-los de forma ordenada
		Arrays.sort(retorno);

		return retorno;
	}

	/**
	 * Dado uma imagem define entre quais dos angulos ela se encontra comparando com os extremos da imagem
	 * @param imagem Imagem de entrada
	 * @param classe Classe da imagem
	 * @param angulos Angulos para comparacao
	 * @return Angulos que a imagem esta no meio 
	 * @throws Exception
	 */
	private static int[] angulosProximosExtremos(Imagem imagem, int classe, int[] angulos) throws Exception{
		
		//Pega os extremos da imagem de query
		int[] extremosRef = imagem.getExtremos();

		Imagem temp = null;
		int indiceMaisProx = -1;
		double menorDistancia = Double.MAX_VALUE;
		double[] distancias = new double[angulos.length];
		int[] retorno = new int[2];
		
		//Calcula as distancias entre a imagem de query e as imagens dos angulos dados
		for (int i = 0; i < angulos.length; i++) {
			temp = pegarImagem(classe, angulos[i]);

			int[] extremosTemp = temp.getExtremos();
			distancias[i] = distanciaEuclidiana(extremosRef, extremosTemp);
		}
		
		//Pega o indice do vetor de ângulos correspondente a imagem que possui a menor distancia
		for(int i = 0 ; i < angulos.length ; i++){
			if(menorDistancia > distancias[i]){
				menorDistancia = distancias[i];
				indiceMaisProx = i;
			}
		}
		
		//Pega a imagem anterior e posterior para comparação.
		//Equivalente a pegar a imagens com +90 graus e -90 graus.
		int anterior = (indiceMaisProx + 3) % 4; //O mais correto seria (indiceMaisProx - 1) % 4, mas o Java dá resultados de módulo negativos para valores negativos. Blah! 
		int posterior = (indiceMaisProx + 1) % 4;
		
		//Coloca no vetor de saida, os angulos que mais se aproximam 
		if(distancias[anterior] <= distancias[posterior]){
			retorno[0] = angulos[anterior];
			retorno[1] = angulos[indiceMaisProx];
		}else{
			retorno[0] = angulos[indiceMaisProx];
			retorno[1] = angulos[posterior];
		}
		
		return retorno;

	}
	
	/**
	 * Dado uma imagem define entre quais dos angulos ela se encontra comparando com as características (extremos+histograma) da imagem
	 * @param imagem Imagem de entrada
	 * @param classe Classe da imagem
	 * @param angulos Angulos para comparacao
	 * @return Angulos que a imagem esta no meio 
	 * @throws Exception
	 */
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
	
	/**
	 * Carrega a imagem do disco e retorna
	 * @param classe
	 * @param angulo
	 * @return
	 */
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
