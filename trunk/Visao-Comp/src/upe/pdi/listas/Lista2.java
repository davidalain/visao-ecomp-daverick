package upe.pdi.listas;
import upe.pdi.filtros.FiltroRestauracao;
import upe.pdi.imagem.Imagem;
import upe.pdi.util.Util;


public class Lista2 {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		Util util = new Util();
		double[][] matriz = util.lerImagem(util.caminho + "l2/" + "Figura3.jpg");
		Imagem imagem = new Imagem(matriz);
		
		filtroMedianaAdaptativa(imagem);
	}

	
	/**
	 * Questao 1 letra A
	 * @param imagem
	 * @param util
	 * @throws Exception
	 */
	private static void filtroMediaAritmetica(Imagem imagem, Util util) throws Exception{
		Imagem retorno = FiltroRestauracao.mediaAritmetica(imagem, 3);
		Util.salvaImagem(util.caminho + "restauracaoMediaArit3.bmp", retorno.getMatriz() );
		
		retorno = FiltroRestauracao.mediaAritmetica(imagem, 7);
		Util.salvaImagem(util.caminho + "restauracaoMediaArit7.bmp", retorno.getMatriz() );
		
		retorno = FiltroRestauracao.mediaAritmetica(imagem, 9);
		Util.salvaImagem(util.caminho + "restauracaoMediaArit9.bmp", retorno.getMatriz() );
	}
	
	/**
	 * Questao 1 letra B
	 * @param imagem
	 * @param util
	 * @throws Exception
	 */
	private static void filtroMediaGeometrica(Imagem imagem, Util util) throws Exception{
		Imagem retorno = FiltroRestauracao.mediaGeometrica(imagem, 3);
		Util.salvaImagem(util.caminho + "l2/" + "restauracaoMediaGeo3.bmp", retorno.getMatriz() );
		
		retorno = FiltroRestauracao.mediaGeometrica(imagem, 7);
		Util.salvaImagem(util.caminho + "l2/" + "restauracaoMediaGeo7.bmp", retorno.getMatriz() );
		
		retorno = FiltroRestauracao.mediaGeometrica(imagem, 9);
		Util.salvaImagem(util.caminho + "l2/" + "restauracaoMediaGeo9.bmp", retorno.getMatriz() );
	}
	
	/**
	 * Questao 1 letra C
	 * @param imagem
	 * @param util
	 * @throws Exception
	 */
	private static void filtroMediana(Imagem imagem, Util util) throws Exception{
		Imagem retorno = FiltroRestauracao.mediana(imagem, 3);
		Util.salvaImagem(util.caminho + "l2/" + "Figura2_Mediana3.bmp", retorno.getMatriz() );
		
		retorno = FiltroRestauracao.mediana(imagem, 7);
		Util.salvaImagem(util.caminho + "l2/" + "Figura2_Mediana7.bmp", retorno.getMatriz() );
		
		retorno = FiltroRestauracao.mediana(imagem, 9);
		Util.salvaImagem(util.caminho + "l2/" + "Figura2_Mediana9.bmp", retorno.getMatriz() );
	}
	
	/**
	 * Questao 1 letra D
	 * @param imagem
	 * @param util
	 * @throws Exception
	 */
	private static void filtroPontoMedio(Imagem imagem, Util util) throws Exception{
		Imagem retorno = FiltroRestauracao.pontoMedio(imagem, 3);
		Util.salvaImagem(util.caminho + "l2/" + "restauracaoPontoMedio3.bmp", retorno.getMatriz() );
		
		retorno = FiltroRestauracao.pontoMedio(imagem, 7);
		Util.salvaImagem(util.caminho + "l2/" + "restauracaoPontoMedio7.bmp", retorno.getMatriz() );
		
		retorno = FiltroRestauracao.pontoMedio(imagem, 9);
		Util.salvaImagem(util.caminho + "l2/" + "restauracaoPontoMedio9.bmp", retorno.getMatriz() );
	}
	
	private static void filtroContraHarmonico(Imagem imagem, Util util) throws Exception {
		Imagem retorno = FiltroRestauracao.mediaContraHarmonica(imagem, -1, 3);
		Util.salvaImagem(util.caminho + "l2/" + "Figura3_contraHar_-1.bmp", retorno.getMatriz() );
		
		retorno = FiltroRestauracao.mediaContraHarmonica(imagem, 1, 3);
		Util.salvaImagem(util.caminho + "l2/" + "Figura3_contraHar_1.bmp", retorno.getMatriz() );
		
		retorno = FiltroRestauracao.mediaContraHarmonica(imagem, 0, 3);
		Util.salvaImagem(util.caminho + "l2/" + "Figura3_contraHar_0.bmp", retorno.getMatriz() );


	}
	
	private static void filtroMedianaAdaptativa(Imagem imagem) throws Exception {
		Imagem retorno = FiltroRestauracao.medianaAdaptativa(imagem, 3);
		Util.salvaImagem(Util.caminho + "l2/" + "Figura3_medAdaptativa7.bmp", retorno.getMatriz() );
		
	}
}
