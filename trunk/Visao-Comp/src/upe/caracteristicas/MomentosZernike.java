package upe.caracteristicas;

import java.util.LinkedList;

public class MomentosZernike {

	private double[][] imagem;
	private double xMassa;
	private double yMassa;
	
	public MomentosZernike(LinkedList<Object> lista) {
		super();
		//imagem
		this.imagem = clonarImagem((double[][])lista.get(1)) ;
		//coordenada x do centro de massa
		this.xMassa = momento(1, 0)/momento(0, 0);
		//coordenada y do centro de massa
		this.yMassa = momento(0, 1)/momento(0, 0);
		
	}
	
	public MomentosZernike(double[][] imagem) {
		super();
		//imagem
		this.imagem = clonarImagem(imagem) ;
		//coordenada x do centro de massa
		this.xMassa = momento(1, 0)/momento(0, 0);
		//coordenada y do centro de massa
		this.yMassa = momento(0, 1)/momento(0, 0);
		
	}
	
	private double[][] clonarImagem (double[][] imagemS){
		double retorno[][] = new double[imagemS.length][imagemS[0].length];
		for (int i = 0; i < retorno.length; i++) {
			retorno[i] = imagemS[i].clone();
		}
		return retorno;

	}

	private double momento (int p, int q){
		double momento = 0;
		for (int i = 0; i < this.imagem.length; i++) {
			for (int j = 0; j < this.imagem[0].length; j++) {
				momento += (Math.pow(i, p)) * (Math.pow(j, q)) * this.imagem[i][j]; 
				
			}
		}
		return momento;
	}

	/**
	 * Calcula os nove primeiros momentos de Zernike
	 * @return
	 */
	public double[] momentosDeZernike(){
		double[] retorno = new double[9];
		double xDistancia = 0;
		double yDistancia = 0;
		double raio = 0;
		double angulo = 0;
		
		for (int i = 0; i < imagem.length; i++) {
			
			xDistancia = i - this.xMassa;
			
			for (int j = 0; j < imagem[i].length; j++) {
				
				 yDistancia = j - this.yMassa;
				 
				 raio = Math.sqrt(xDistancia*xDistancia + yDistancia*yDistancia);
				 
				 angulo = Math.atan(yDistancia/xDistancia);
				 
				 for(int l=0; l<retorno.length; l++){
					 retorno[l] += imagem[i][j] * (this.calcularV(raio, angulo, l));
				 }
			}
		}
		
		for (int i = 0; i < retorno.length; i++) {
			if(i == 0 ){
				
				retorno[i] *= 1/Math.PI;
				
			}else if(i == 1){
				
				retorno[i] *= (1+1)/Math.PI;
				
			}else if(i == 2 || i == 3){
				
				retorno[i] *= (2+1)/Math.PI;
				
			}else if(i == 4 || i == 5){
				
				retorno[i] *= (3+1)/Math.PI;
				
			}else if (i == 6 || i == 7 || i == 8){
				
				retorno[i] *= (4+1)/Math.PI; 
				
			}
		}
		
		return retorno;
	}
	
	private double calcularV(double raio, double angulo, int momentIndex){
		double r = this.calcularR(raio, momentIndex);
		
		if(momentIndex == 0 || momentIndex == 2 || momentIndex == 6){
			
			return r*Math.exp(0);
			
		}else if(momentIndex == 1 || momentIndex == 4){
			
			return r*Math.exp(angulo);
			
		}else if(momentIndex == 3 || momentIndex == 7){
			
			return r*Math.exp(angulo*2);
			
		}else if(momentIndex == 5){
			
			return r*Math.exp(angulo*3);
			
		}else{
			
			return r*Math.exp(angulo*4); 
			
		}
		
	}
	
	private double calcularR(double raio, int momentIndex){
		
		switch(momentIndex){
		case 0:
			return 1.0;
		case 1:
			return raio;
		case 2:
			return 2*raio*raio - 1;
		case 3:	
			return raio*raio;
		case 4:
			return 3*raio*raio*raio - 2*raio;
		case 5:
			return raio*raio*raio;
		case 6:
			return 6*Math.pow(raio, 4) - 6*raio*raio;
		case 7:
			return 4*Math.pow(raio, 4) - 3*raio*raio;
		case 8:
			return Math.pow(raio, 4);
		}
		
		return 0;
	}
	
}
