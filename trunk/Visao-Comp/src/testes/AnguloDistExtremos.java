package testes;

public class AnguloDistExtremos extends AnguloDistancias {

	private int[] extremos;

	public AnguloDistExtremos(double distancia, int angulo, int[] extremos) {
		super(distancia, angulo);
		this.extremos = extremos;
	}

	public int[] getExtremos() {
		return extremos;
	}

	public void setExtremos(int[] extremos) {
		this.extremos = extremos;
	}
	

}
