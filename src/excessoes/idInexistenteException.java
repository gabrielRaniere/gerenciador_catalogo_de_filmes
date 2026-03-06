package excessoes;

@SuppressWarnings("serial")
public class idInexistenteException extends RuntimeException{
	public idInexistenteException() {
		super("ID NÃO EXISTE !!");
	}
}
