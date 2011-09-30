package upe.pdi.segmentacao;

import java.util.Arrays;
import java.util.Vector;

import upe.pdi.imagem.Imagem;

public class Segmentacao {

	private static Segmentacao instance;
	
	public static Segmentacao getInstance(){
		if(instance == null){
			instance =  new Segmentacao();
		}
		
		return instance;
	}
		
	public Imagem kMedias(Imagem imagem, int k){
		double[][] matriz = imagem.getMatriz();
		double[] centros = new double[k];
		double[] novosCentros = new double[k];
		
		double[] arrayAux = new double[matriz.length*matriz[0].length]; 
		int sumAux = 0;
		for(int i=0; i<matriz.length; i++){
			for(int l=0; l<matriz[i].length; l++){
				arrayAux[sumAux+l] = matriz[i][l];
			}
			sumAux += matriz[i].length;
		}
		//Arrays.sort(arrayAux);
		
		//calcula os centros
		for (int i = 0; i < centros.length; i++) {
			novosCentros[i] = arrayAux[i];
		}
		Arrays.sort(novosCentros);
		
		Vector<Vector<Double>> grupos = new Vector<Vector<Double>>();
				
		do {
			centros = novosCentros.clone();
			
			grupos.clear();
			for(int i=0; i<k; i++){
				grupos.add(new Vector<Double>());
			}
			
			for (int i = 0; i < arrayAux.length; i++) {
				double[] diferenca = new double[k];

				//calcula distancia desse ponto para os centros
				for (int j = 0; j < centros.length; j++) {
					diferenca[j] = Math.abs(arrayAux[i] - centros[j]);
				}

				//calcula a menor diferenca
				double menorDiferenca = diferenca[0];
				int indiceMenor = 0;
				for (int j = 1; j < diferenca.length; j++) {
					if (diferenca[j] < menorDiferenca) {
						menorDiferenca = diferenca[j];
						indiceMenor = j;
					}
				}

				//coloca esse ponto no grupo relativo ao indice que ele tem a menor distancia
				grupos.get(indiceMenor).add(arrayAux[i]);
			}
			//recalcular os centros
			int grupo = 0;
			for (Vector<Double> array : grupos) {
				Double[] aux = new Double[array.size()];
				array.toArray(aux);
				Arrays.sort(aux);

				novosCentros[grupo] = aux[aux.length / 2];
				grupo++;
			}
		} while (!ehIgual(centros, novosCentros));
		
		double[][] retorno = new double[matriz.length][matriz[0].length];
		for(int i=0; i<retorno.length; i++){
			int grupo = 0;
			for(int j=0; j<retorno[0].length; j++){
				
				for(int l = 0; l<grupos.size(); l++){
					if(grupos.get(l).contains(matriz[i][j])){
						grupo = l;
					}
				}
				
				retorno[i][j] = (grupo*255)/(k-1);
			}
		}
		
		return new Imagem(retorno);
	}
	
	public Imagem kMediasModificado(Imagem imagem, int k){
		double[][] matriz = imagem.getMatriz();
		double[] centros = new double[k];
		double[] novosCentros = new double[k];
		
		double[] arrayAux = new double[matriz.length*matriz[0].length]; 
		int sumAux = 0;
		for(int i=0; i<matriz.length; i++){
			for(int l=0; l<matriz[i].length; l++){
				arrayAux[sumAux+l] = matriz[i][l];
			}
			sumAux += matriz[i].length;
		}
		//Arrays.sort(arrayAux);
		
		//calcula os centros
		for (int i = 0; i < centros.length; i++) {
			novosCentros[i] = arrayAux[i];
		}
		Arrays.sort(novosCentros);
		
		Vector<Vector<Double>> grupos = new Vector<Vector<Double>>();
				
		do {
			centros = novosCentros.clone();
			
			grupos.clear();
			for(int i=0; i<k; i++){
				grupos.add(new Vector<Double>());
			}
			
			for (int i = 0; i < arrayAux.length; i++) {
				double[] diferenca = new double[k];

				//calcula distancia desse ponto para os centros
				for (int j = 0; j < centros.length; j++) {
					diferenca[j] = Math.abs(arrayAux[i] - centros[j]);
				}

				//calcula a menor diferenca
				double menorDiferenca = diferenca[0];
				int indiceMenor = 0;
				for (int j = 1; j < diferenca.length; j++) {
					if (diferenca[j] < menorDiferenca) {
						menorDiferenca = diferenca[j];
						indiceMenor = j;
					}
				}

				//coloca esse ponto no grupo relativo ao indice que ele tem a menor distancia
				grupos.get(indiceMenor).add(arrayAux[i]);
			}
			//recalcular os centros
			int grupo = 0;
			for (Vector<Double> array : grupos) {
				Double[] aux = new Double[array.size()];
				array.toArray(aux);
				Arrays.sort(aux);

				novosCentros[grupo] = aux[aux.length / 2];
				grupo++;
			}
		} while (!ehIgual(centros, novosCentros));
		
		double[][] retorno = new double[matriz.length][matriz[0].length];
		for(int i=0; i<retorno.length; i++){
			int grupo = 0;
			for(int j=0; j<retorno[0].length; j++){
				
				for(int l = 0; l<grupos.size(); l++){
					if(grupos.get(l).contains(matriz[i][j])){
						grupo = l;
					}
				}
				
				if(grupo == 0)
					retorno[i][j] = (grupo*255)/(k-1);
				else
					retorno[i][j] = 255;
			}
		}
		
		return new Imagem(retorno);
	}
	
	private boolean ehIgual(double[] array1, double[] array2){
		for(int i=0; i<array1.length; i++){
			if(array1[i] != array2[i]){
				return false;
			}
		}
		
		return true;
	}
	
}
