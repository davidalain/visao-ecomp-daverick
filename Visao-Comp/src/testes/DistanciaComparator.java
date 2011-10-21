package testes;

import java.util.Comparator;

public class DistanciaComparator implements Comparator<AnguloDistancias> {

	@Override
	public int compare(AnguloDistancias arg0, AnguloDistancias arg1) {
		double distanciaArg0 = arg0.getDistancia();
		double distanciaArg1 = arg1.getDistancia();

		if(distanciaArg0 < distanciaArg1){
			return -1; 
		}else if(distanciaArg0 > distanciaArg1){
			return 1;
		}

		return 0;
	}
}
