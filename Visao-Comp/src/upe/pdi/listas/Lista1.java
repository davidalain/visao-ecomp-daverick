package upe.pdi.listas;
import upe.pdi.filtros.FiltroRealce;
import upe.pdi.imagem.Imagem;
import upe.pdi.imagem.ImagemNaFrequencia;
import upe.pdi.util.Util;


public class Lista1 {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		Util util = new Util();
		double[][] matriz = util.lerImagem(util.caminho +  "1/1_r0.png");
		
		
		Imagem imagem = new Imagem(matriz);
		
		//equalizarImagem(imagem, util);
		
		//realcarParteEscura(imagem, util);
		
		//filtrosSuavizacaoMedia(imagem, util);
		
		//filtrosSuavizacaoMediana(imagem, util);
		
		filtrosLaplaciano(imagem, util);
		
		//operadorSobel(imagem, util);
		
		//dominioFrequencia(imagem, util);
	}
	
	//Questao 2 letra a
	public static void equalizarImagem(Imagem imagem, Util util) throws Exception{
		double[][] imagemEqualizada = imagem.imagemEqualizada();		
		util.salvaImagem(Util.caminho + "equalizado.bmp", imagemEqualizada);
	}

	//Questao 2 letra b
	public static void realcarParteEscura(Imagem imagem, Util util) throws Exception{
		double E = 2;
		double k0 = 0.4;
		double k1 = 0.02;
		double k2 = 0.4;
		
		int tamanhoMascara = 5;
		
		double Mg = imagem.tomCinzaMedio();
		double Dg = imagem.standardDeviation();
		double[][] imagemRealcada = imagem.getMatriz();
	
		Imagem subImagem = null;
		
		for(int i=0; i<imagemRealcada.length; i++){
			for(int j=0; j<imagemRealcada[i].length; j++){
				int[] ponto = {i,j};
				subImagem = imagem.subImagem(ponto, tamanhoMascara);
				
				if(subImagem.tomCinzaMedio() <= k0*Mg && subImagem.standardDeviation() >= k1*Dg 
						&& subImagem.standardDeviation() <= k2*Dg ){
					imagemRealcada[i][j] *= E;
				}
			}
		}
		
		util.salvaImagem(Util.caminho + "realceLocal.bmp", imagemRealcada);
	}
	
	//Questao 2 letra c
	public static void filtrosSuavizacaoMedia(Imagem imagem, Util util) throws Exception{
		Imagem retorno = null;
		retorno = FiltroRealce.filtroMedia(imagem, 3);
		util.salvaImagem(Util.caminho + "suavizadaMedia3.bmp", retorno.getMatriz());
		
		retorno = FiltroRealce.filtroMedia(imagem, 5);
		util.salvaImagem(Util.caminho + "suavizadaMedia5.bmp", retorno.getMatriz());

		retorno = FiltroRealce.filtroMedia(imagem, 7);
		util.salvaImagem(Util.caminho + "suavizadaMedia7.bmp", retorno.getMatriz());
	}
	
	//Questao 2 letra c
	public static void filtrosSuavizacaoMediana(Imagem imagem, Util util) throws Exception{
		Imagem retorno = null;
		retorno = FiltroRealce.filtroMediana(imagem, 3);
		util.salvaImagem(Util.caminho + "suavizadaMediana3.bmp", retorno.getMatriz());
		
		retorno = FiltroRealce.filtroMediana(imagem, 5);
		util.salvaImagem(Util.caminho + "suavizadaMediana5.bmp", retorno.getMatriz());

		retorno = FiltroRealce.filtroMediana(imagem, 7);
		util.salvaImagem(Util.caminho + "suavizadaMediana7.bmp", retorno.getMatriz());
	}
	
	//Questao 2 letra d
	public static void filtrosLaplaciano(Imagem imagem, Util util) throws Exception{
		Imagem retorno = null;
		retorno = FiltroRealce.filtroLaplaciano(imagem);
		util.salvaImagem(Util.caminho + "suavizadaLaplaciano_0.bmp", retorno.getMatriz());
	}
	
	//Questao 2 letra d
	public static void operadorSobel(Imagem imagem, Util util) throws Exception{
		Imagem retorno = null;
		retorno = FiltroRealce.operadorSobel(imagem);
		util.salvaImagem(Util.caminho + "operadorSobel_45.bmp", retorno.getMatriz());
	}
	
	//Questao 2 letra e
	public static void dominioFrequencia(Imagem imagem, Util util) throws Exception{
		double[][] retorno = null;
		
		util.salvaImagem(Util.caminho + "l1/espectroFrequencia.bmp", imagem.getMatriz());
		
		Imagem imagemInv = imagem.getInvertida();
		
		ImagemNaFrequencia imagemF = imagemInv.dominioFrequencia();
				
		imagemF = FiltroRealce.filtroNotch(imagemF);
		
		imagem = imagemF.getImagemNormal();
		
		retorno = imagem.getInvertida().getMatriz();
				
		for (int i = 0; i < retorno.length; i++) {
			for (int j = 0; j < retorno.length; j++) {
				if(retorno[i][j] < 0){
					retorno[i][j] = 0;
				}else if(retorno[i][j] > 255){
					retorno[i][j] = 255;
				}
			}
		}
		
		util.salvaImagem(Util.caminho + "l1/filtroNotch.bmp", retorno);

	}
}
