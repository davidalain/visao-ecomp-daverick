package upe.pdi.imagem;

public class Imagem {

	private static final int GRAYLEVEL = 256;
	private double[] histograma;
	private double[][] matriz;

	public double[][] getMatriz() {
		return matriz;
	}

	public void setMatriz(double[][] matriz) {
		this.matriz = matriz.clone();
	}

	private int numeroPixels = 0;

	public Imagem(double[][] matriz){
		this.matriz = matriz;
		this.histograma =  new double[GRAYLEVEL];
	}

	private void calcularHistograma(){
		int tomCinza = 0;
		for (int i=0;i<matriz.length;i++){
			for (int j=0; j < matriz[i].length; j++){
				tomCinza = (int)matriz[i][j];

				if(tomCinza > GRAYLEVEL -1){
					histograma[GRAYLEVEL -1] += 1;
				}else{
					histograma[tomCinza] += 1;
				}

				this.numeroPixels++;
			}
		}

	}

	public double[] histogramaNormalizado(){
		this.calcularHistograma();
		double[] retorno = histograma.clone();
		for (int i=0;i<histograma.length;i++){
			retorno[i] /= this.numeroPixels;
		}

		return retorno;
	}


	public double[] distribuicaoAcumulativa(){
		double[] histNormal = this.histogramaNormalizado();
		double[] acumulador = new double[histNormal.length];
		for(int i=0; i<histNormal.length; i++){			
			for(int j=0; j<=i; j++){
				acumulador[i] += histNormal[j];
			}
		}
		return acumulador;
	}

	public double[][] imagemEqualizada(){
		double[] acumulado = this.distribuicaoAcumulativa();
		int[] novosValores = new int[acumulado.length];

		//definir novos valores
		for(int i=0; i<acumulado.length; i++){
			novosValores[i] = (int) Math.round(acumulado[i] * acumulado.length-1);
		}

		double[][] imagemRetorno = new double[this.matriz.length][this.matriz[0].length];

		//mapear novos valores na imagem
		for(int i=0; i<imagemRetorno.length; i++){
			for(int j=0; j<imagemRetorno[i].length; j++){
				imagemRetorno[i][j] = novosValores[(int)this.matriz[i][j]];
			}
		}

		return imagemRetorno;
	}

	/**
	 * Calcula o tom de cinza medio para toda a imagem
	 * @return A media dos tons de cinza
	 */
	public double tomCinzaMedio(){
		double[] normal = this.histogramaNormalizado();
		double m = 0;

		for(int i=0; i<GRAYLEVEL; i++){
			m += i*normal[i];
		}

		return m;
	}


	public double standardDeviation(){
		double[] normal = this.histogramaNormalizado();
		double retorno = 0;
		double m = this.tomCinzaMedio();

		for(int i=0; i<GRAYLEVEL; i++){
			retorno += ((i - m)*(i - m))*normal[i];
		}

		return Math.sqrt(retorno);
	}

	public Imagem subImagem(int[] ponto, int tamanhoMascara){
		double[][] retorno = new double[tamanhoMascara][tamanhoMascara];

		int tamanho = tamanhoMascara/2;
		int k = 0;
		int h = 0;

		for(int i=ponto[0]-tamanho; i<(ponto[0]-tamanho)+tamanhoMascara; i++){
			h = 0;
			for(int j=ponto[1]-tamanho; j<(ponto[1]-tamanho)+tamanhoMascara; j++){
				if(i<0 || j<0 || i>=this.matriz.length || j>=this.matriz[i].length){
					h+=1;
					continue;
				}

				retorno[k][h] = this.matriz[i][j];

				h++;
			}
			k++;
		}

		return new Imagem(retorno);
	}


	public ImagemNaFrequencia dominioFrequencia(){
		int M = this.matriz.length;
		int N = this.matriz[0].length;

		double[][] matrizReal = new double[M][N];
		double[][] matrizImaginaria = new double[M][N];

		//double[][] matriz = { {5,3,0,2}, {1,7,8,3}, {4,2,2,2}, {8,5,2,1}  };

		for(int u=0; u<M; u++){
			for(int v=0; v<N; v++){

				double real = 0;
				double imaginario = 0;

				for(int x=0; x<M; x++){
					for(int y=0; y<N; y++){
						double angulo = 2*Math.PI*(u*x+v*y)/M;

						double fatorReal = Math.cos( angulo );						
						double fatorImaginario =  ((-1)*Math.sin( angulo));

						real += matriz[x][y]*fatorReal;
						imaginario += matriz[x][y]*fatorImaginario;
					}
				}


				matrizReal[u][v] = real/N;
				matrizImaginaria[u][v] = imaginario/N;

			}
		}


		return new ImagemNaFrequencia(matrizReal, matrizImaginaria);
	}

	public Imagem getInvertida(){
		//double[][] matriz = { {5,3,0,2}, {1,7,8,3}, {4,2,2,2}, {8,5,2,1}  };

		double[][] matrizRetorno = new double[matriz.length][matriz[0].length];

		for (int i = 0; i < matrizRetorno.length; i++) {
			for (int j = 0; j < matrizRetorno[0].length; j++) {
				matrizRetorno[i][j] = Math.pow(-1, i+j)*matriz[i][j];
			}
		}

		return new Imagem(matrizRetorno);
	}

	public Imagem aplicarMascara(double[][] mascara){
		double[][] retorno = new double[matriz.length][matriz[0].length];
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[0].length; j++) {
				if(mascara[i][j] != 0){
					retorno[i][j] = matriz[i][j];
				}
			}
		}

		return new Imagem(retorno);
	}

	public int[] extremosVerticais(){
		int menorIndiceV = Integer.MAX_VALUE;
		int maiorIndiceV = Integer.MIN_VALUE;

		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[i].length; j++) {
				if(matriz[i][j] != 0){

					if(i < menorIndiceV){
						menorIndiceV = i;
					}else if(i > maiorIndiceV){
						maiorIndiceV = i;
					}
				}
			}
		}

		int[] retorno = {menorIndiceV, maiorIndiceV};
		return retorno;
	}

	public int[] extremosHorizontais(){
		int menorIndiceH = Integer.MAX_VALUE;
		int maiorIndiceH = Integer.MIN_VALUE;

		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[i].length; j++) {
				if(matriz[i][j] != 0){
					if(j < menorIndiceH){
						menorIndiceH = j;
					}else if(j > maiorIndiceH){
						maiorIndiceH = j;
					}
				}
			}

		}
		int[] retorno = {menorIndiceH, maiorIndiceH};
		return retorno;
	}
	
	public int[] getExtremos(){
		int[] extremos = new int[4];
		
		int[] extremosV = this.extremosVerticais();
		int[] extremosH = this.extremosHorizontais();
		
		extremos[0] = extremosH[0];
		extremos[1] = extremosH[1];
		extremos[2] = extremosV[0];
		extremos[3] = extremosV[1];
		
		return extremos;
	}
	
	public int[] getCaracteristicas(){
		int[] caracteristicas = new int[4 + 256];
		
		int[] extremosV = this.extremosVerticais();
		int[] extremosH = this.extremosHorizontais();
		
		caracteristicas[0] = extremosH[0];
		caracteristicas[1] = extremosH[1];
		caracteristicas[2] = extremosV[0];
		caracteristicas[3] = extremosV[1];
		
		for(int i = 0 ; i < GRAYLEVEL ; i++){
			caracteristicas[i + 4] = (int)histograma[i];
		}
		
		return caracteristicas;
	}

	public int getAltura(){
		int[] pontos = this.extremosVerticais();

		return pontos[1] - pontos[0];
	}

	public int getLargura(){
		int[] pontos = this.extremosHorizontais();

		return pontos[1] - pontos[0];
	}
}
