package upe.classificacao;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

public class CSVAlturas {

	private List<String[]> valores;
	
	public CSVAlturas()throws IOException{
		CSVReader reader = new CSVReader(new FileReader("arquivos" + 
				File.separator + "alturas.csv"), ';');
		
		valores = reader.readAll();
	}
	
	/**
	 * Retorna 75% das imagens para serem usadas como "treino" do Knn
	 * @return 
	 * @throws IOException
	 */
	public ArrayList<ImageClass> retornarImagens(){
		
		ArrayList<ImageClass> retorno = new ArrayList<ImageClass>();
		
		for(int i=0; i<valores.size(); i++){
			//54 = 75% das 72 imagens
			for(int j=0; j<54; j++){
				double[] temp = {Double.parseDouble(valores.get(i)[j])};
				retorno.add(new ImageClass(temp, (i+1)));
			}
		}
		
		return retorno;
	}
	
	public ImageClass retornaImagem(int i, int j){
		double[] temp = {Double.parseDouble( valores.get(i)[j] )};
		return new ImageClass(temp, (i+1));
	}

}
