package testes;

public class AnguloDistancias {

	private double distancia;
	private int angulo;
	
		
	public AnguloDistancias(double distancia, int angulo) {
		super();
		this.distancia = distancia;
		this.angulo = angulo;
	}

	
	public double getDistancia() {
		return distancia;
	}


	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}


	public int getAngulo() {
		return angulo;
	}

	public void setAngulo(int angulo) {
		this.angulo = angulo;
	}

	@Override
	public String toString() {
		return "" + this.angulo;
	}
}
