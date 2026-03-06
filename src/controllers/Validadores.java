package controllers;

import excessoes.NumeroForaEscopoException;
import excessoes.idInexistenteException;
import infra.DAO;

public class Validadores {
	// não quero instanciar, pois só haverá métodos estáticos
	private Validadores() {}
	
	public static boolean verificaValor(int valor, int minVal, int maxVal) { 
		try {
			if(valor > maxVal || valor < minVal) {
				throw new NumeroForaEscopoException();
			}
			
		} catch(NumeroForaEscopoException e) {
			System.err.println("número fora de escopo!");
			return false;
		} 
		
		return true;
	}
	
	
	public static <T> boolean idInexistente(Class<T> classeHeading, long idAveriguar) {
		try {
			DAO<T> dao = new DAO<>();
			T linhaRetornada = dao.lerApenasUm(classeHeading, idAveriguar);
			
			if(linhaRetornada == null) throw new idInexistenteException();
			
			
		}catch(idInexistenteException e) {
			System.err.println(e.getMessage());
			return false;
		}
		
		return true;
	}
}
