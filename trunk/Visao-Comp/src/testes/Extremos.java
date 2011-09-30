package testes;
import upe.pdi.util.Util;


public class Extremos {

	public static final String PATH = "D:\\UPE\\9 periodo\\Visao\\mask4\\";
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {

		Util util = new Util();

		for(int h=0; h<72; h++){

			double[][] matriz;
			if(h == 0){
				matriz = util.lerImagem(PATH +  "\\7\\7_c1.png");
			}else{
				matriz = util.lerImagem(PATH +  "\\7\\7_r" + h*5 + ".png");
			}
			int menorIndiceH = Integer.MAX_VALUE;
			int maiorIndiceH = Integer.MIN_VALUE;

			int menorIndiceV = Integer.MAX_VALUE;
			int maiorIndiceV = Integer.MIN_VALUE;

			for (int i = 0; i < matriz.length; i++) {
				for (int j = 0; j < matriz[i].length; j++) {
					if(matriz[i][j] != 0){
						if(j < menorIndiceH){
							menorIndiceH = j;
						}else if(j > maiorIndiceH){
							maiorIndiceH = j;
						}

						if(i < menorIndiceV){
							menorIndiceV = i;
						}else if(i > maiorIndiceV){
							maiorIndiceV = i;
						}
					}
				}
			}

			/*double[][] retorno = new double[matriz.length][matriz[0].length];
		for (int i = 0; i < retorno.length; i++) {
			for (int j = 0; j < retorno[i].length; j++) {
				if(j == maiorIndiceH || j == menorIndiceH ||
						i == maiorIndiceV || i == menorIndiceV || matriz[i][j] == 1){
					retorno[i][j] = 255;
				}
			}
		}

		Util.salvaImagem(Util.caminho + "extremos.bmp", retorno);*/

			System.out.println("Imagem r" + h*5);
			System.out.println("MenorH = " + menorIndiceH + " MaiorH = " + maiorIndiceH);
			//System.out.println("MenorV = " + menorIndiceV + " MaiorV = " + maiorIndiceV);
		}
	}

}
