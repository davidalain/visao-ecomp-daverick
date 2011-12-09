package testes;

public class AnguloDistClass extends AnguloDistancias {

	private int classe;
	
	public int getClasse() {
		return classe;
	}

	public void setClasse(int classe) {
		this.classe = classe;
	}

	public AnguloDistClass(double distancia, int angulo, int classe) {
		super(distancia, angulo);
		
		this.classe = classe;
	}

	@Override
	public String toString() {
		return "" + this.classe;
	}
}
