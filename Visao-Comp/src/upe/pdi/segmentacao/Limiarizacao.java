package upe.pdi.segmentacao;

import java.util.ArrayList;

import upe.pdi.imagem.Imagem;

public class Limiarizacao {

	private static Limiarizacao instance;
	
	public static Limiarizacao getInstance(){
		if(instance == null){
			instance =  new Limiarizacao();
		}
		
		return instance;
	}
	
	public Imagem otsu(Imagem imagem){
		//Passo 1: calcular histograma normalizado pi
		double[] histNormalizado = imagem.histogramaNormalizado();
		//Passo 2: Calcular as somas acumuladas P1(k)
		double[] somasAcumuladas = imagem.distribuicaoAcumulativa();
		
		double[] mediasAcumuladas = new double[somasAcumuladas.length];
		double mediaGlobal = 0;
		
		//Passo 3: calcular as medias acumuladas m(k)
		for (int k = 0; k < mediasAcumuladas.length; k++) {
			for(int i = 0; i <= k; i++){
				mediasAcumuladas[k] += i*histNormalizado[i];
			}
		}		
		
		//Passo 4: calcular a media global
		mediaGlobal = mediasAcumuladas[mediasAcumuladas.length-1];
		
		double[] variancia = new double[somasAcumuladas.length];
		
		ArrayList<Integer> melhoresK = new ArrayList<Integer>();
		double maiorValor = Double.MIN_VALUE;
		
		//Passo 5: calcular a variancia entre as classes
		for (int k = 0; k < variancia.length; k++) {
			variancia[k] = (mediaGlobal*somasAcumuladas[k]-mediasAcumuladas[k])*
				(mediaGlobal*somasAcumuladas[k]-mediasAcumuladas[k]);
			variancia[k] /= somasAcumuladas[k]*(1-somasAcumuladas[k]);
			
			//obter variancia(s) maxima(s)
			if(variancia[k] > maiorValor){
				melhoresK.clear();
				melhoresK.add(k);
				maiorValor = variancia[k];
			}else if(variancia[k] == maiorValor){
				melhoresK.add(k);
				maiorValor = variancia[k];
			}
		}
		
		//Passo 6: obter limiar de otsu
		double kReferencia = 0;
		if(melhoresK.size() > 1){
			double soma = 0;
			for(double k : melhoresK){
				soma += k;
			}
			
			kReferencia = soma/melhoresK.size();
		}else if(melhoresK.size() == 1){
			kReferencia = melhoresK.get(0);
		}
		
		//limiarizando a imagem
		double[][] matriz = imagem.getMatriz();
		double[][] retorno = new double[matriz.length][matriz[0].length];
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[0].length; j++) {
				if(matriz[i][j] > kReferencia){
					retorno[i][j] = 255;
				}
			}
		}
		
		return new Imagem(retorno);
	}

	public Imagem niblack(Imagem imagem){
		double k = -0.2;
		int tamanhoMascara = 7;
		
		double[][] matriz = imagem.getMatriz();
		double[][] retorno = new double[matriz.length][matriz[0].length];
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[0].length; j++) {
				int[] ponto = {i,j};
				Imagem subImagem = imagem.subImagem(ponto, tamanhoMascara);
				double[][] subMatriz = subImagem.getMatriz();
				
				double[] histNormalizado = imagem.histogramaNormalizado();
				
				//calculo da media local
				double media = subImagem.tomCinzaMedio();
				
				//calculo da variancia local
				double variancia = subImagem.standardDeviation();
				
				//variancia = Math.sqrt(variancia);
				double T = media + k*variancia;
				
				//limiarizacao do ponto
				if(matriz[i][j] > T){
					retorno[i][j] = 255;
				}
			}
		}
		
		return new Imagem(retorno);
	}
}
