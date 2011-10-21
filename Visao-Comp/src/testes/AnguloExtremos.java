package testes;

public class AnguloExtremos {

	private int[] extremos;
	private int angulo;
	
		
	public AnguloExtremos(int[] extremos, int angulo) {
		super();
		this.extremos = extremos;
		this.angulo = angulo;
	}

	
	public int[] getExtremos() {
		return extremos;
	}

	public void setExtremos(int[] extremos) {
		this.extremos = extremos;
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
