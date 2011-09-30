package testes;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import upe.pdi.imagem.Imagem;
import upe.pdi.util.Util;

public class Mascarando {

	public static final String MASK_PATH = "D:\\UPE\\9 periodo\\Visao\\mask4\\";
	public static final String IMAGE_PATH = "D:\\UPE\\9 periodo\\Visao\\aloi_grey_red4_view\\";


	public static void main(String args[]) throws Exception{

		StringBuffer buffer = new StringBuffer();
		int classe = 1;

		while(classe <= 1000){
			for(int i=0; i<72; i++){
				int valor = i*5;
				String imagemPath = classe + "\\"+ classe +"_r"+ valor + ".png";

				String mascaraPath = "";
				if(i == 0){
					mascaraPath = classe + "\\"+ classe +"_c1.png";
				}else{
					mascaraPath = classe + "\\"+ classe +"_r"+ valor +".png";
				}

				double[][] mascara = Util.lerImagem(MASK_PATH + mascaraPath);
				double[][] imagem = Util.lerImagem(IMAGE_PATH + imagemPath);

				Imagem img = new Imagem(imagem);

				Imagem imgFinal = img.aplicarMascara(mascara);

				/*MomentosZernike mom = new MomentosZernike(imgFinal.getMatriz());

			double[] momentosZernike = mom.momentosDeZernike();

			for (int j = 0; j < momentosZernike.length; j++) {
				buffer.append(momentosZernike[j] + ";");
			}
			buffer.append("\n");*/

				int altura = imgFinal.getAltura();

				buffer.append(altura + ";");
			}
			
			buffer.append("\n");
			classe++;
		}

		FileOutputStream fos = new FileOutputStream(Util.caminho + "alturas.csv");
		OutputStreamWriter osw = new OutputStreamWriter(fos);
		BufferedWriter bw = new BufferedWriter(osw);

		bw.write(buffer.toString());
		bw.close();
	}

}
