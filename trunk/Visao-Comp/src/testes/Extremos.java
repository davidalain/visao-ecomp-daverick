package testes;
import upe.pdi.util.Util;


public class Extremos {

	public static final String PATH = "D:\\UPE\\9 periodo\\Visao\\mask4\\";
	public static final String IMAGE_PATH = "D:\\UPE\\9 periodo\\Visao\\aloi_grey_red4_view\\";

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {

		Util util = new Util();

		//for(int h=0; h<72; h++){

		double[][] matrizMask;
		//if(h == 0){
			matrizMask = util.lerImagem(PATH +  "\\1\\1_c1.png");
			double[][] matrizImg = util.lerImagem(IMAGE_PATH + "\\1\\1_r0.png");
		/*}else{
			matriz = util.lerImagem(PATH +  "\\7\\7_r" + h*5 + ".png");
		}*/
		int menorIndiceH = Integer.MAX_VALUE;
		int maiorIndiceH = Integer.MIN_VALUE;

		int menorIndiceV = Integer.MAX_VALUE;
		int maiorIndiceV = Integer.MIN_VALUE;

		for (int i = 0; i < matrizMask.length; i++) {
			for (int j = 0; j < matrizMask[i].length; j++) {
				if(matrizMask[i][j] != 0){
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

		double[][] retorno = new double[matrizMask.length][matrizMask[0].length];
		for (int i = 0; i < retorno.length; i++) {
			for (int j = 0; j < retorno[i].length; j++) {
				if(j == maiorIndiceH || j == menorIndiceH ||
						i == maiorIndiceV || i == menorIndiceV){
					retorno[i][j] = 255;
				}else if(matrizMask[i][j] == 1){
					retorno[i][j] = matrizImg[i][j];
				}
			}
		}

		Util.salvaImagem(Util.caminho + "extremos.bmp", retorno);

		//System.out.println("Imagem r" + h*5);
		System.out.println("MenorH = " + menorIndiceH + " MaiorH = " + maiorIndiceH);
		//System.out.println("MenorV = " + menorIndiceV + " MaiorV = " + maiorIndiceV);
		//}
	}

}
