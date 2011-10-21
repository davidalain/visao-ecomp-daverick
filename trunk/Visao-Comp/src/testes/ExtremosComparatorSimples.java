package testes;

import java.util.Comparator;

public class ExtremosComparatorSimples implements Comparator<AnguloExtremos> {

	@Override
	public int compare(AnguloExtremos arg0, AnguloExtremos arg1) {
		int[] extremosArg0 = arg0.getExtremos();
		int[] extremosArg1 = arg1.getExtremos();

		double dist = distanciaSimples(extremosArg0, extremosArg1);

		if(dist < 0){
			return -1; 
		}else if(dist > 0){
			return 1;
		}

		return 0;
	}
	
	private static double distanciaSimples(int[] pontos1, int[] pontos2){
		double soma = 0;

		for (int i = 0; i < pontos1.length; i++) {
			soma += (pontos1[i] - pontos2[i]);
		}

		return soma;
	}

}
