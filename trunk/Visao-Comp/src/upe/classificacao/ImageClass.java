package upe.classificacao;

public class ImageClass {

	private double[] caracteristicas;
	public double[] getCaracteristicas() {
		return caracteristicas;
	}

	public void setCaracteristicas(double[] caracteristicas) {
		this.caracteristicas = caracteristicas;
	}
	private int classe;
	
	public ImageClass(double[] momentosHu, int classe){
		this.caracteristicas = momentosHu.clone();
		this.classe = classe;
	}
	
	public double distanciaEuclidiana(double[] momentosHuReferencia){
		double soma = 0;
		
		for (int i = 0; i < caracteristicas.length; i++) {
			soma += (momentosHuReferencia[i] - this.caracteristicas[i])*(momentosHuReferencia[i] - this.caracteristicas[i]);
		}
		
		return Math.sqrt(soma);
	}
	
	
	public int getClasse() {
		return classe;
	}
	public void setClasse(int classe) {
		this.classe = classe;
	}
}
