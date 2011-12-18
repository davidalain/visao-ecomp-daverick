package testes;

import java.util.ArrayList;
import java.util.Collections;

import upe.pdi.imagem.Imagem;
import upe.pdi.util.Util;

public class CBIR {

	public static final String MASK_PATH = "D:\\UPE\\9 periodo\\Visao\\mask4\\";
	public static final String IMAGE_PATH = "D:\\UPE\\9 periodo\\Visao\\aloi_grey_red4_view\\";

	public static final int CLASS_QNT = 50;
	public static final int ANGLES_QNT = 20;

	public static final int RECALL_QNT = 20;

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static double[] run(int[] classes, int[] angulosRef) throws Exception {

		DistanciaComparator comparator = new DistanciaComparator();

		double[] precisions = new double[RECALL_QNT];
	
		int countImg = 0;
		
		for (int c = 0; c < classes.length; c++) {

			for(int a = 0; a < angulosRef.length; a++){
				
				countImg++;
				
				ArrayList<AnguloDistClass> lista = new ArrayList<AnguloDistClass>();
				//System.out.print("Sorteado = " + anguloRef);

				int[] extremosRef = pegarImagem(classes[c], (angulosRef[a])).getExtremos();
				
				double[] recall = calculateRecall(classes[c], angulosRef[a], extremosRef, angulosRef);

				for(int i=0; i<classes.length; i++){

					for (int j = 0; j < angulosRef.length; j++) {			
						int anguloTemp = angulosRef[j];
						int classeTemp = classes[i];

						if(classeTemp == classes[c] && anguloTemp == angulosRef[a]){
							continue;
						}

						int[] extremosTemp = pegarImagem(classeTemp, anguloTemp).getExtremos();

						double distanciaTemp = distanciaEuclidiana(extremosRef, extremosTemp);

						lista.add(new AnguloDistClass(distanciaTemp, anguloTemp, classeTemp));
					}
				}

				Collections.sort(lista, comparator);

				//Definindo os valores para os intervalos de confianca
				int[] intervaloConf = new int[RECALL_QNT];
				for (int i = 0; i < recall.length; i++) {
					intervaloConf[i] = indiceExtremo(lista, recall[i]);
				}
				
				//System.out.print(countImg + " - ");
				//Calculando a precisao para cada valor de recall
				for (int i = 0; i < precisions.length; i++) {
					int sum = 0;
					for(int j=0; j<intervaloConf[i]; j++){
						if(lista.get(j).getClasse() == classes[c]){
							sum++;
						}
					}
					
					precisions[i] += (sum/(double)intervaloConf[i]);
					
					//System.out.print(precisions[i]/countImg + "  ");
				}
				
				//System.out.println();
			}
		}

		for (int i = 0; i < precisions.length; i++) {
			precisions[i] /= countImg;
		}
		
		return precisions;
	}

	private static int indiceExtremo(ArrayList<AnguloDistClass> lista, double distanciaRef){
		int retorno = 0;
		for(int i=0; i<lista.size(); i++){
			retorno = i;
			
			if(lista.get(i).getDistancia() > distanciaRef){
				break;
			}
		}
		
		if(retorno == 0 || retorno == lista.size()-1){
			retorno += 1;
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

	private static double distanciaEuclidiana(int[] pontos1, int[] pontos2){
		double soma = 0;

		for (int i = 0; i < pontos1.length; i++) {
			soma += (pontos1[i] - pontos2[i])*(pontos1[i] - pontos2[i]);
		}

		return Math.sqrt(soma);
	}

		
	/**
	 * @param args
	 * @throws Exception 
	 * @throws Exception 
	 */
	public static double[] calculateRecall(int classe, int anguloRef, int[] extremosRef, int[] angulos) throws Exception{

		DistanciaComparator comparator = new DistanciaComparator();
		//[5%, 10%, 15%, 20%, ..., 100%]
		double[] precisions = new double[RECALL_QNT];

		ArrayList<AnguloDistancias> lista = new ArrayList<AnguloDistancias>();

		for (int j = 0; j < angulos.length; j++) {
			int anguloTemp = angulos[j];
			int[] extremosTemp = pegarImagem(classe, anguloTemp).getExtremos();

			double distanciaTemp = distanciaEuclidiana(extremosRef, extremosTemp);

			lista.add(new AnguloDistancias(distanciaTemp, anguloTemp));
		}

		Collections.sort(lista, comparator);
		
		for(int i = 0; i<precisions.length; i++){
			precisions[i] = lista.get(i).getDistancia();
		}

		return precisions;
	}
}
