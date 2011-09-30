package upe.pdi.listas;

import upe.pdi.filtros.FiltroRealce;
import upe.pdi.imagem.Imagem;
import upe.pdi.morfologia.Operadores;
import upe.pdi.segmentacao.Limiarizacao;
import upe.pdi.segmentacao.Segmentacao;
import upe.pdi.util.Util;

public class Lista4 {

	private static final String GATO = Util.caminho + "l1/" + "imagens-gato-cinza.bmp";
	private static final String MENINO = Util.caminho + "l4/" + "menino-cinza.bmp";
	private static final String PLACA = Util.caminho + "imagem2.bmp";
	private static final Limiarizacao lim = Limiarizacao.getInstance();
	private static final Segmentacao seg = Segmentacao.getInstance();
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		
		double[][] matriz = Util.lerImagem(PLACA);
				
		Imagem imagem = new Imagem(matriz);
		
		imagem = new Imagem(imagem.imagemEqualizada());
		
		//imagem = FiltroRealce.filtroMedia(imagem, 9);
		
		//otsu(imagem);
		//bordaOtsu(imagem);
		segmentar(imagem);
	}

	
	private static void otsu(Imagem imagem) throws Exception{
		Imagem retorno = lim.otsu(imagem);
		retorno = Operadores.abertura(retorno);
		Util.salvaImagem(Util.caminho + "placa_bin.bmp",retorno.getMatriz());
	}
	
	private static void niblack(Imagem imagem) throws Exception{
		Imagem retorno = lim.niblack(imagem);
		Util.salvaImagem(Util.caminho + "placa_bin_nib.bmp",retorno.getMatriz());
	}
	
	private static void bordaOtsu(Imagem imagem) throws Exception{
		Imagem retorno = lim.otsu(imagem);
		retorno = Operadores.abertura(retorno);
		retorno = Operadores.extrairBorda(retorno);
		Util.salvaImagem(Util.caminho + "placa_borda_otsu.bmp",retorno.getMatriz());
	}
	
	private static void segmentar(Imagem imagem) throws Exception{
		Imagem retorno = seg.kMediasModificado(imagem, 4);
		Util.salvaImagem(Util.caminho + "placa_k_means_modif.bmp",retorno.getMatriz());
	}
	
	//Questao 2 letra c
	public static void filtrosSuavizacaoMedia(Imagem imagem) throws Exception{
		Imagem retorno = null;
		
		retorno = FiltroRealce.filtroMedia(imagem, 3);
		Util.salvaImagem(Util.caminho + "suavizadaMedia3.bmp", retorno.getMatriz());
		
		retorno = FiltroRealce.filtroMedia(imagem, 5);
		Util.salvaImagem(Util.caminho + "suavizadaMedia5.bmp", retorno.getMatriz());

		retorno = FiltroRealce.filtroMedia(imagem, 7);
		Util.salvaImagem(Util.caminho + "suavizadaMedia7.bmp", retorno.getMatriz());
	}
}
