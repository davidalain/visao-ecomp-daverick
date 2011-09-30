package upe.pdi.util;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;

public class Util {

	public static String caminho = "img/";
	
	public static String formatoJPG = ".jpg";
	
	public static String formatoBMP = ".bmp";
	
	public static double[][] lerImagem(String caminho) throws Exception {
		File f = new File(caminho);
		BufferedImage image = ImageIO.read(f);
		double[][] retorno = new double[image.getHeight()][image.getWidth()];
		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				double[] tmp = new double[1];
				image.getRaster().getPixel(i, j, tmp);
				retorno[j][i] = tmp[0];
			}
		}
		return retorno;
	}
	
	public static double[][] lerImagem(BufferedImage image) throws Exception {
		double[][] retorno = new double[image.getHeight()][image.getWidth()];
		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				double[] tmp = new double[1];
				image.getRaster().getPixel(i, j, tmp);
				retorno[j][i] = tmp[0];
			}
		}
		return retorno;
	}
	
	public static double[][][] lerImagemColorida(String caminho) throws Exception {
		File f = new File(caminho);
		BufferedImage image = ImageIO.read(f);
		double[][][] retorno = new double[3][image.getHeight()][image.getWidth()];
		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				double[] tmp = new double[3];
				image.getRaster().getPixel(i, j, tmp);
				retorno[0][j][i] = tmp[0];
				retorno[1][j][i] = tmp[1];
				retorno[2][j][i] = tmp[2];
			}
		}
		return retorno;
	}
	
	public static void salvaImagem(String caminho,
			double[][] imagem) throws Exception {
		try {
			BufferedImage image = new BufferedImage(imagem[0].length, imagem.length, BufferedImage.TYPE_BYTE_GRAY);
			for (int y = 0; y < imagem.length; ++y) {
				for (int x = 0; x < imagem[0].length; ++x) {
					double[] tmp = new double[] { imagem[y][x] };
					image.getRaster().setPixel(x, y, tmp);
				}
			}
			ImageIO.write(image, "bmp", new File(caminho));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static double[][] retornaImagemCinza(double[][][] imagem) throws Exception {
		try {
			BufferedImage image = new BufferedImage(imagem[0][0].length, imagem[0].length, BufferedImage.TYPE_BYTE_GRAY);
			for (int y = 0; y < imagem[0].length; ++y) {
				for (int x = 0; x < imagem[0][0].length; ++x) {
					double[] tmp = new double[] { imagem[0][y][x], imagem[1][y][x], imagem[2][y][x] };
					image.getRaster().setPixel(x, y, tmp);
				}
			}
			return lerImagem(image);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void salvaImagem(String caminho,
			double[][][] imagem) throws Exception {
		try {
			BufferedImage image = new BufferedImage(imagem[0][0].length, imagem[0].length, 5);
			for (int y = 0; y < imagem[0].length; ++y) {
				for (int x = 0; x < imagem[0][0].length; ++x) {
					double[] tmp = new double[] { imagem[0][y][x], imagem[1][y][x], imagem[2][y][x] };
					image.getRaster().setPixel(x, y, tmp);
				}
			}
			ImageIO.write(image, "bmp", new File(caminho));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		float[] hsi = new float[3];
		Color.RGBtoHSB(100, 170, 125, hsi);
		DecimalFormat decimalformat = new DecimalFormat("0.000");
		for (int i = 0; i < hsi.length; i++) {
			System.out.println(decimalformat.format(hsi[i]*255));
		}
	}
	
}
