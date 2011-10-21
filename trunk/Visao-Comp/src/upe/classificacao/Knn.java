package upe.classificacao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;



public class Knn {


	public static ImageClass[] calcularVizinhos(double[] momentos, int k) throws IOException{

		ArrayList<Double> distancias = new ArrayList<Double>(k);
		ImageClass[] vizinhos = new ImageClass[k];

		HashMap<Double, ArrayList<ImageClass>> mapeamento = new HashMap<Double, ArrayList<ImageClass>>();

		CSVAlturas csvAltras = new CSVAlturas();

		for(int i=0; i<42; i++){
			ArrayList<ImageClass> imgs = csvAltras.retornarImagens();

			for(ImageClass img : imgs){
				double distancia = img.distanciaEuclidiana(momentos);
				//ImageDistance imgDist = new ImageDistance(img, distancia);
				
				/*if(distancias.size() == 0){
					distancias.add(imgDist);
				}
				
				for (int j = 0; j < distancias.size(); j++) {
					if(distancias.get(j).getDistance() > distancia){
						distancias.add(j, imgDist);
					}else{
						distancias.add(imgDist);
					}
					
					distancias.trimToSize();
				}*/
				
				if(mapeamento.get(distancia) == null){
					ArrayList<ImageClass> lista = new ArrayList<ImageClass>();
					mapeamento.put(distancia, lista);
				}else{
					mapeamento.get(distancia).add(img);
				}
				
			}
		}

		int i = 0;
		while(i < k){
			
			i++;
		}

		return vizinhos;
	}

}
