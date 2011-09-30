package upe.pdi.filtros;

import java.util.Arrays;

import upe.pdi.imagem.Imagem;
import upe.pdi.imagem.ImagemNaFrequencia;

public class FiltroRealce {

	public static Imagem filtroMedia(Imagem imagem, int tamanhoMascara){
		double[][] matriz = imagem.getMatriz();
		double[][] matrizRetorno = new double[matriz.length][matriz[0].length];
		
		
		double[][] subImagem = null;
		for(int i = 0; i<matriz.length; i++){
			for(int j=0; j<matriz[i].length; j++){
				int[] ponto = {i,j};
				subImagem = imagem.subImagem(ponto, tamanhoMascara).getMatriz();
				
				double temp = 0;
				
				for(int k=0; k<subImagem.length; k++){
					for(int l=0; l<subImagem[k].length; l++){
						
						temp += subImagem[k][l];
					}
				}
				
				matrizRetorno[i][j] = temp/(tamanhoMascara*tamanhoMascara);
			}
		}
		
		return new Imagem(matrizRetorno);
		
	}
	
	public static Imagem filtroMediana(Imagem imagem, int tamanhoMascara){
		double[][] matriz = imagem.getMatriz();
		double[][] matrizRetorno = new double[matriz.length][matriz[0].length];
		
		double[][] subImagem = null;
		for(int i = 0; i<matriz.length; i++){
			for(int j=0; j<matriz[i].length; j++){
				int[] ponto = {i,j};
				subImagem = imagem.subImagem(ponto, tamanhoMascara).getMatriz();
				double[] arrayAux = new double[subImagem.length*subImagem[0].length]; 
				
				int sumAux = 0;
				for(int k=0; k<subImagem.length; k++){
					for(int l=0; l<subImagem[k].length; l++){
						arrayAux[sumAux+l] += subImagem[k][l];
					}
					sumAux += subImagem[k].length;
				}
				
				Arrays.sort(arrayAux);
								
				matrizRetorno[i][j] = arrayAux[arrayAux.length/2];
			}
		}
		
		return new Imagem(matrizRetorno);
	}
	
	public static Imagem filtroLaplaciano(Imagem imagem){
		double[][] matriz = imagem.getMatriz();
		double[][] matrizRetorno = new double[matriz.length][matriz[0].length];
		
		
		for(int i = 1; i<matriz.length; i++){
			for(int j=1; j<matriz[i].length; j++){
				int soma = 0;
				
				if(i+1 < matriz.length){
					soma += matriz[i+1][j];
				}
				
				if( j+1 < matriz[i].length ){
					soma += matriz[i][j+1];
				}
				
				if(i-1 > 0){
					soma += matriz[i-1][j];
				}
				
				if(j-1 > 0){
					soma += matriz[i][j-1];
				}
				
				double result = 5*matriz[i][j]-soma;
				if(result < 0 ){
					matrizRetorno[i][j] = Math.abs(result);
				}else if(result > 255){
					matrizRetorno[i][j] = 255;
				}else{
					matrizRetorno[i][j] = result;
				}
			}
		}
		
		return new Imagem(matrizRetorno);
	}
	
	public static Imagem operadorSobel(Imagem imagem){
		double[][] matriz = imagem.getMatriz();
		double[][] matrizRetorno = new double[matriz.length][matriz[0].length];
		
		
		double[][] subImagem = null;
		for(int i = 0; i<matriz.length; i++){
			for(int j=0; j<matriz[i].length; j++){
				int[] ponto = {i,j};
				subImagem = imagem.subImagem(ponto, 3).getMatriz();
				
				//z7+2z8+z9
				double termo1 = subImagem[0][2]+2*subImagem[1][2]+subImagem[2][2];
				
				//z1+2z2+z3
				termo1 -= subImagem[0][0]+2*subImagem[1][0]+subImagem[2][0];
				
				//z3+2z6+z9
				double termo2 = subImagem[2][0]+2*subImagem[1][2]+subImagem[2][2];
				
				//z1+2z4+z7
				termo2 -= subImagem[0][0]+2*subImagem[0][1]+subImagem[0][2];
				
				
				matrizRetorno[i][j] = Math.abs(termo1) + Math.abs(termo2);
				
				if(matrizRetorno[i][j] > 255){
					matrizRetorno[i][j] = 255;
				}
				
			}
		}
		
		return new Imagem(matrizRetorno);
	}

	public static ImagemNaFrequencia filtroNotch(ImagemNaFrequencia imagem){
		double[][] matrizReal = imagem.getMatrizReal();
		double[][] matrizImaginaria = imagem.getMatrizImaginaria();
		
		double[][] matrizRealRet = new double[matrizReal.length][matrizReal[0].length];
		double[][] matrizImaginariaRet = new double[matrizReal.length][matrizReal[0].length];
		
		
		for (int i = 0; i < matrizRealRet.length; i++) {
			for (int j = 0; j < matrizRealRet[0].length; j++) {
				
				if( i != matrizReal.length/2 && j != matrizReal.length/2){
					matrizRealRet[i][j] = matrizReal[i][j];
					matrizImaginariaRet[i][j] = matrizImaginaria[i][j];
				}else{
					matrizRealRet[i][j] = 0;
					matrizImaginariaRet[i][j] = 0;
				}
				
			}
		}
		
		return new ImagemNaFrequencia(matrizRealRet, matrizImaginariaRet);
	}
	
}
