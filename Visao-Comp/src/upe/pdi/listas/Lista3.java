package upe.pdi.listas;

import upe.pdi.imagem.Imagem;
import upe.pdi.morfologia.Operadores;
import upe.pdi.util.Util;

public class Lista3 {

	private static final String CENTER = Util.caminho + "suavizadaLaplaciano.bmp";
	private static final String LEFT = Util.caminho + "303_l_equalizado.bmp";
	private static final String RIGHT = Util.caminho + "303_r_equalizado.bmp";
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {

		double[][] matriz = Util.lerImagem(Util.caminho + "1_c1.png");
		Imagem imagem = new Imagem(matriz);
		
		extrairBordas(imagem);
	}

	
	private static void extrairBordas(Imagem imagem) throws Exception{
		Imagem retorno = Operadores.extrairBordaBinaria(imagem);
		
		Util.salvaImagem(Util.caminho + "borda_mask.bmp", retorno.getMatriz());
	}
	
	private static void gradiente(Imagem imagem) throws Exception{
		Imagem retorno = Operadores.gradienteMorfologico(imagem);
		
		Util.salvaImagem(Util.caminho + "l3/" + "gradienteMorfologico.bmp", retorno.getMatriz());
	}
	
	private static void preencher(Imagem imagem) throws Exception{
		Imagem retorno = Operadores.preencher(imagem);
		
		Util.salvaImagem(Util.caminho + "l3/" + "imagemPreenchida.bmp", retorno.getMatriz());

	}
}
