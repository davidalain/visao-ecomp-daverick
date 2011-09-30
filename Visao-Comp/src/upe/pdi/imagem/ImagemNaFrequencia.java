package upe.pdi.imagem;

public class ImagemNaFrequencia extends Imagem {

	private double[][] matrizReal;
	private double[][] matrizImaginaria;
	
	public ImagemNaFrequencia(double[][] matrizReal, double[][] matrizImaginaria) {
		super(null);
		this.matrizReal = matrizReal;
		this.matrizImaginaria = matrizImaginaria;
	}
	
	@Override
	public double[][] getMatriz(){
		double[][] retorno = new double[matrizReal.length][matrizReal[0].length];
		
		for (int i = 0; i < retorno.length; i++) {
			for (int j = 0; j < retorno[0].length; j++) {
				retorno[i][j] = Math.sqrt((matrizReal[i][j]*matrizReal[i][j]) + (matrizImaginaria[i][j]*matrizImaginaria[i][j]));
			}
		}		
		
		return retorno;
	}

	public double[][] getMatrizReal() {
		return matrizReal;
	}

	public void setMatrizReal(double[][] matrizReal) {
		this.matrizReal = matrizReal;
	}

	public double[][] getMatrizImaginaria() {
		return matrizImaginaria;
	}

	public void setMatrizImaginaria(double[][] matrizImaginaria) {
		this.matrizImaginaria = matrizImaginaria;
	}
	
	public Imagem getImagemNormal(){
		int M = this.matrizReal.length;
		int N = this.matrizReal[0].length;
		
		double[][] matrizRetorno = new double[M][N];
		
		//double[][] matriz = { {5,3,0,2}, {1,7,8,3}, {4,2,2,2}, {8,5,2,1}  };
		
		for(int x=0; x<M; x++){
			for(int y=0; y<N; y++){
							
				double real = 0;
				double imaginario = 0;
				
				for(int u=0; u<M; u++){
					for(int v=0; v<N; v++){
						double angulo = 2*Math.PI*(u*x+v*y)/M;
						
						double fatorReal = Math.cos( angulo );						
						double fatorImaginario = (Math.sin( angulo));

						real += matrizReal[u][v]*fatorReal;
						imaginario += matrizImaginaria[u][v]*fatorImaginario;
					}
				}
				
				
				matrizRetorno[x][y] = (real - imaginario)/N;
				
			}
			
		}
		
		
		return new Imagem(matrizRetorno);
	}

}
