package upe.pdi.filtros;

import java.util.Arrays;

import upe.pdi.imagem.Imagem;

public class FiltroRestauracao {

	private static double somatorio(Imagem subImagem, int q){
		double[][] matriz = subImagem.getMatriz();
		double soma = 0;

		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[0].length; j++) {
				soma += Math.pow(matriz[i][j], q);
			}
		}

		return soma;
	}

	public static Imagem mediaContraHarmonica(Imagem imagem, int q, int tamanhoMascara){
		double[][] matriz = imagem.getMatriz();
		double[][] matrizRetorno = new double[matriz.length][matriz[0].length];
		double novoValor = 0;

		for (int i = 0; i < matrizRetorno.length; i++) {
			for (int j = 0; j < matrizRetorno[0].length; j++) {
				int[] ponto = {i,j};
				Imagem subImagem = imagem.subImagem(ponto, tamanhoMascara);

				novoValor = somatorio(subImagem, q+1);

				novoValor /= somatorio(subImagem, q);

				matrizRetorno[i][j] = novoValor;
			}
		}

		return new Imagem(matrizRetorno);
	}

	public static Imagem mediaAritmetica(Imagem imagem, int tamanhoMascara){
		return mediaContraHarmonica(imagem, 0, tamanhoMascara);
	}

	public static Imagem mediaGeometrica(Imagem imagem, int tamanhoMascara){
		double[][] matriz = imagem.getMatriz();
		double[][] matrizRetorno = new double[matriz.length][matriz[0].length];
		double expoente = 1/(double)(tamanhoMascara*tamanhoMascara);

		for (int i = 0; i < matrizRetorno.length; i++) {
			for (int j = 0; j < matrizRetorno[0].length; j++) {
				int[] ponto = {i,j};
				double[][] subMatriz = imagem.subImagem(ponto, tamanhoMascara).getMatriz();


				double produto = 1.0;

				for (int x = 0; x < subMatriz.length; x++) {
					for (int y = 0; y < subMatriz[0].length; y++) {
						if(subMatriz[x][y] != 0)
							produto *= subMatriz[x][y];
					}
				}

				matrizRetorno[i][j] = Math.pow(produto, expoente);
			}
		}

		return new Imagem(matrizRetorno);

	}

	public static Imagem mediaHarmonica(Imagem imagem, int tamanhoMascara){
		return mediaContraHarmonica(imagem, -1, tamanhoMascara);
	}

	public static Imagem mediana(Imagem imagem, int tamanhoMascara) {
		double[][] matriz = imagem.getMatriz();
		double[][] matrizRetorno = new double[matriz.length][matriz[0].length];

		for (int i = 0; i < matrizRetorno.length; i++) {
			for (int j = 0; j < matrizRetorno[0].length; j++) {
				int[] ponto = {i,j};
				double[][] subMatriz = imagem.subImagem(ponto, tamanhoMascara).getMatriz();

				double[] array = new double[subMatriz.length*subMatriz[0].length];

				int h = 0;
				for (int x = 0; x < subMatriz.length; x++) {
					for (int y = 0; y < subMatriz[0].length; y++) {
						array[h+y] = subMatriz[x][y]; 
					}
					h += subMatriz.length;
				}

				Arrays.sort(array);

				matrizRetorno[i][j] = array[array.length/2];
			}
		}

		return new Imagem(matrizRetorno);
	}

	public static Imagem pontoMedio(Imagem imagem, int tamanhoMascara) {
		double[][] matriz = imagem.getMatriz();
		double[][] matrizRetorno = new double[matriz.length][matriz[0].length];

		for (int i = 0; i < matrizRetorno.length; i++) {
			for (int j = 0; j < matrizRetorno[0].length; j++) {
				int[] ponto = {i,j};
				double[][] subMatriz = imagem.subImagem(ponto, tamanhoMascara).getMatriz();

				double[] array = new double[subMatriz.length*subMatriz[0].length];

				int h = 0;
				for (int x = 0; x < subMatriz.length; x++) {
					for (int y = 0; y < subMatriz[0].length; y++) {
						array[h+y] = subMatriz[x][y]; 
					}
					h += subMatriz.length;
				}

				Arrays.sort(array);

				matrizRetorno[i][j] = 0.5*(array[0]+array[array.length-1]);
			}
		}

		return new Imagem(matrizRetorno);
	}

	public static Imagem medianaAdaptativa(Imagem imagem, int tamanhoMascara){
		double[][] matriz = imagem.getMatriz();
		double[][] matrizRetorno = new double[matriz.length][matriz[0].length];

		for (int i = 0; i < matrizRetorno.length; i++) {
			for (int j = 0; j < matrizRetorno[0].length; j++) {
				int[] ponto = {i,j};

				matrizRetorno[i][j] =  nivelA(imagem, ponto, tamanhoMascara);
			}
		}
		return new Imagem(matrizRetorno);
	}

	private static double nivelA(Imagem imagem, int[] ponto, int tamanhoMascara){
		double zmin = 0;
		double zmax = 0;
		double zmed = 0;
		double sMax = 7;
		double zxy = imagem.getMatriz()[ponto[0]][ponto[1]];

		double[][] subMatriz = imagem.subImagem(ponto, tamanhoMascara).getMatriz();
		
		double[] array = new double[subMatriz.length*subMatriz[0].length];

		int h = 0;
		for (int x = 0; x < subMatriz.length; x++) {
			for (int y = 0; y < subMatriz[0].length; y++) {
				array[h+y] = subMatriz[x][y]; 
			}
			h += subMatriz.length;
		}

		Arrays.sort(array);

		zmin = array[0];
		zmax = array[array.length-1];
		zmed = array[array.length/2];

		double A1 = zmed - zmin;
		double A2 = zmed - zmax;

		if(A1>0 && A2<0){
			double B1 = zxy - zmin;
			double B2 = zxy - zmax;
			if(B1>0 && B2<0){
				return zxy;
			}else{
				return zmed;
			}
		}else{
			tamanhoMascara += 2;
			if(tamanhoMascara <= sMax){
				return nivelA(imagem, ponto, tamanhoMascara);
			}else{
				return zmed;
			}
		}
	}

}

