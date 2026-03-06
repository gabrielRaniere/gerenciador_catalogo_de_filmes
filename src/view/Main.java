package view;

import java.util.Scanner;

import controllers.Validadores;

public class Main {
	public static void main(String[] args) {
		Scanner entrada = new Scanner(System.in);
		
		while(true) {
			System.out.println("_______________________________________________________");
			System.out.println("qual serviço você quer acessar ? ");
			System.out.println("[1] Avaliar filmes");
			System.out.println("[2] acessar filmes");
			System.out.println("[3] acessar autores");
			System.out.print("--> ");
			
			int valorEscolhido = 0;
			boolean digitouNumeroCorreto = false;
			
			while(!digitouNumeroCorreto) {
				
				valorEscolhido = entrada.nextInt();
				
				digitouNumeroCorreto = Validadores.verificaValor(valorEscolhido, 1, 3);
				
			}
			
			// aqui eu já passei por tudo
			
			switch (valorEscolhido) {
				case 1 : {
					boolean goBack = AvaliacoesView.entrarSecaoAvaliacoes();
					
					if(goBack) continue;
					
					break;
				}
				case 2 : {
					boolean goBack = FilmesView.entrarsecaoFilmes();
					
					if(goBack) continue;
					
					break;
				}
				case 3 : {
					boolean goBack = AtoresView.entrarSecaoAtores();
					
					if(goBack) continue;
					break;
					}
			}
		}
		
		}
}
