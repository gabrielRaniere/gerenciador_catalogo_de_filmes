package excessoes;

@SuppressWarnings("serial")
public class NumeroForaEscopoException extends RuntimeException{
	
	public NumeroForaEscopoException() {
		super("DIGITE UM NÚMERO ENTRE 1 E 3");
	}
	
}
