package upe.pdi.morfologia;

import upe.pdi.imagem.Imagem;

public class Operadores {


	private static Imagem erosao(Imagem imagem){
		double[][] matriz = imagem.getMatriz();
		double[][] matrizRetorno = new double[matriz.length][matriz[0].length];

		for (int i = 0; i < matrizRetorno.length; i++) {
			for (int j = 0; j < matrizRetorno[0].length; j++) {
				int[] ponto = {i,j};
				double[][] subImagem = imagem.subImagem(ponto, 3).getMatriz();

				double menor = Double.MAX_VALUE;
				for (int k = 0; k < subImagem.length; k++) {
					for (int k2 = 0; k2 < subImagem[0].length; k2++) {
						if(subImagem[k][k2] < menor){
							menor = subImagem[k][k2];
						}
					}
				}

				matrizRetorno[i][j] = menor;
			}
		}		
		return new Imagem(matrizRetorno);
	}

	private static Imagem dilatacao(Imagem imagem){
		double[][] matriz = imagem.getMatriz();
		double[][] matrizRetorno = new double[matriz.length][matriz[0].length];

		for (int i = 0; i < matrizRetorno.length; i++) {
			for (int j = 0; j < matrizRetorno[0].length; j++) {
				int[] ponto = {i,j};
				double[][] subImagem = imagem.subImagem(ponto, 3).getMatriz();

				double maior = Double.MIN_VALUE;
				for (int k = 0; k < subImagem.length; k++) {
					for (int k2 = 0; k2 < subImagem[0].length; k2++) {
						if( subImagem[k][k2] > maior){
							maior = subImagem[k][k2];
						}
					}
				}


				matrizRetorno[i][j] = maior;


			}
		}		
		return new Imagem(matrizRetorno);
	}

	private static Imagem dilatacaoPreenchimento(Imagem imagem){
		double[][] matriz = imagem.getMatriz();
		double[][] matrizRetorno = new double[matriz.length][matriz[0].length];

		for (int i = 0; i < matrizRetorno.length; i++) {
			for (int j = 0; j < matrizRetorno[0].length; j++) {
				int[] ponto = {i,j};
				double[][] subImagem = imagem.subImagem(ponto, 3).getMatriz();

				double maior = 0;
				
				if( subImagem[0][1] == 255 || 
					subImagem[1][0] == 255 || subImagem[1][1] == 255 || subImagem[1][2] == 255 ||
					subImagem[2][1] == 255 ){
					maior = 255;
				}

				if(maior == 0){
					continue;
				}

				matrizRetorno[i][j] = maior;

			}
		}		
		return new Imagem(matrizRetorno);
	}

	private static Imagem transformacao(Imagem imagemBinaria){
		double[][] matriz = imagemBinaria.getMatriz();
		double[][] retorno = new double[matriz.length][matriz[0].length];

		for (int i = 0; i < retorno.length; i++) {
			for (int j = 0; j < retorno[0].length; j++) {
				if(matriz[i][j] == 1 || matriz[i][j] == 255){
					retorno[i][j] = 255;
				}
			}
		}

		return new Imagem(retorno);
	}

	private static Imagem complementar(Imagem imagemBinaria){
		double[][] matriz = imagemBinaria.getMatriz();
		double[][] retorno = new double[matriz.length][matriz[0].length];

		for (int i = 0; i < retorno.length; i++) {
			for (int j = 0; j < retorno[0].length; j++) {
				if(matriz[i][j] == 1){
					retorno[i][j] = 0;
				}else{
					retorno[i][j] = 255;
				}
			}
		}

		return new Imagem(retorno);
	}

	public static Imagem extrairBorda(Imagem imagem){
		double[][] matrizErodida = erosao(imagem).getMatriz();

		double[][] retorno = diferenca(imagem.getMatriz(), matrizErodida);

		return new Imagem(retorno);
	}

	public static Imagem extrairBordaBinaria(Imagem imagem){
		return extrairBorda(transformacao(imagem));
	}

	private static double[][] diferenca(double[][] matrizA, double[][] matrizB){
		double[][] retorno = new double[matrizA.length][matrizA[0].length];

		for (int i = 0; i < matrizA.length; i++) {
			for (int j = 0; j < matrizA[0].length; j++) {

				retorno[i][j] = matrizA[i][j] - matrizB[i][j];


			}
		}

		return retorno;
	}

	public static Imagem gradienteMorfologico(Imagem imagem) throws Exception{

		double[][] matrizErodida = erosao(imagem).getMatriz();
		double[][] matrizDilatada = dilatacao(imagem).getMatriz();

		double[][] retorno = diferenca(matrizDilatada, matrizErodida);

		return new Imagem(retorno);
	}

	public static Imagem preencher(Imagem imagemBinaria) throws Exception{
		double[][] matriz = imagemBinaria.getMatriz();
		int[] ponto = procurarPonto(matriz);

		double[][] complementar = complementar(imagemBinaria).getMatriz();		
		double[][] matrizAtual = new double[matriz.length][matriz[0].length];
		double[][] novaMatriz = new double[matriz.length][matriz[0].length];

		matrizAtual[ponto[0]][ponto[1]] = 255;
		novaMatriz[ponto[0]][ponto[1]] = 255;

		Imagem temp = new Imagem(novaMatriz);

		
		do{
			matrizAtual = novaMatriz.clone();
			
			novaMatriz = dilatacaoPreenchimento(temp).getMatriz();


			for (int k = 0; k < matrizAtual.length; k++) {
				for (int l = 0; l < matrizAtual[0].length; l++) {
					if(novaMatriz[k][l] !=0 && novaMatriz[k][l] == complementar[k][l]){
						novaMatriz[k][l] = 255;
					}else{
						novaMatriz[k][l] = 0;
					}
				}
			}

			temp.setMatriz(novaMatriz);
						
		}while(!ehIgual(matrizAtual, novaMatriz)); //Xk = Xk+1


		return new Imagem(matrizAtual);
	}
	
	public static Imagem abertura(Imagem imagem){
		Imagem retorno = dilatacao(imagem);
		//retorno = dilatacao(imagem);
		
		return retorno;
	}

	/**
	 * Procura um ponto dentro da forma
	 * @param matriz
	 * @return
	 */
	private static int[] procurarPonto(double[][] matriz){
		int[] retorno = new int[2];
		boolean achouBorda = false;
		//varre a imagem na diagonal
		for (int i = 0; i < matriz.length; i++) {
			//achou a borda da imagem
			if(matriz[i][i] != 0){
				achouBorda = true;
				continue;
			}

			//se achou a borda e agora eh preto novamente então entrou na imagem
			if(achouBorda && matriz[i][i] == 0){
				retorno[0] = i;
				retorno[1] = i;
				break;
			}
		}

		return retorno;
	}

	private static boolean ehIgual(double[][] matriz1, double[][] matriz2){
		for (int i = 0; i < matriz2.length; i++) {
			for (int j = 0; j < matriz2[0].length; j++) {
				if(matriz1[i][j] != matriz2[i][j]){
					return false;
				}
			}
		}

		return true;
	}

}
