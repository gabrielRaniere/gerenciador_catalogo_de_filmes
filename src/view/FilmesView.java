package view;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import controllers.Validadores;
import infra.DAO;
import infra.DAOatores;
import infra.DAOfilmes;
import models.AutorModel;
import models.FilmeModel;

public class FilmesView {
	
	private static DAOfilmes dao = new DAOfilmes();
	private static DAOatores daoAtor = new DAOatores();
	private static Scanner entrada = new Scanner(System.in);
	
	static public boolean entrarsecaoFilmes() {
		
		while(true) {
			System.out.println("_______________________________________________________");
			System.out.println("OQUE DESEJA FAZER EM FILMES ?");
			System.out.println("[1] inserir filmes");
			System.out.println("[2] atualizar filmes");
			System.out.println("[3] ler filmes");
			System.out.println("[4] apagar filme");
			System.out.println("[5] voltar");
			System.out.print("--> ");
			int valorEscolhido = entrada.nextInt();
			
			if(!Validadores.verificaValor(valorEscolhido, 1, 5)) {
				continue;
			}
			
			switch(valorEscolhido) {
			case 1: {
				inserirFilme();
				continue;
			}
			case 2: {
				atualizaFilme();
				continue;
			}
			case 3: {
				lerFilme();
				continue;
			}
			case 4: {
				apagarFilme();
				continue;
			}
			case 5: {
				return true;
			}
			
			}
			
			entrada.close();
			return false;
		}
		
	}

	private static void atualizaFilme() {
		dao.imprimiFilmesNaoUsados(new HashSet<Long>());
		
		System.out.print("DIGITE O ID DO FILME QUE VOCÊ QUER ALTERAR : ");
		long idSelecionado = entrada.nextLong();
		
		if(!Validadores.idInexistente(FilmeModel.class, idSelecionado)) {
			return;
		}
		
		FilmeModel selecionado = dao.lerApenasUm(FilmeModel.class, idSelecionado);
		
		dao.abrirTrasacao();
		
		System.out.println("[1] título do filme");
		System.out.println("[2] gasto do filme");
		System.out.println("[3] elenco do filme");
		System.out.print("OQUE DESEJA ALTERAR NESSE FILME ?");
		int opcAlterar = entrada.nextInt();
		
		if(!Validadores.verificaValor(opcAlterar, 1, 3)) {
			return;
		}
		
		switch(opcAlterar) {
		case 1: {
			System.out.print("NOVO NOME : ");
			entrada.nextLine();
			String novoNome = entrada.nextLine();
			selecionado.setTitulo(novoNome);
			break;
		}
		case 2: {
			System.out.print("NOVO GASTO : ");
			Double novoGasto = entrada.nextDouble();
			selecionado.setGasto_total(novoGasto);
			break;
		}
		case 3: {
			
			System.out.print("VOCÊ QUER ADICIONAR[1] OU REMOVER[2] ATORES ? ");
			Integer selecionadoOpcAtor = entrada.nextInt();
			
			if(!Validadores.verificaValor(selecionadoOpcAtor, 1, 2)) {
				return;
			}
			
			if(selecionadoOpcAtor == 1) {
				Set<Long> idsAtoresJaNoFilme = new HashSet<>();
				selecionado.getElenco()
				.stream()
				.map(obj -> obj.getId())
				.forEach(id -> idsAtoresJaNoFilme.add(id));
				
				daoAtor.imprimiAtoresNaoUsados(idsAtoresJaNoFilme);
				
				System.out.print("qual ator você quer inserir [id] ? ");
				long atorAinserir = entrada.nextLong();
				
				if(!Validadores.idInexistente(AutorModel.class, atorAinserir)) {
					return;
				}
				
				if(!idsAtoresJaNoFilme.contains(atorAinserir)) {
					selecionado.getElenco().add(
							daoAtor.lerApenasUm(AutorModel.class, atorAinserir)
							);
				}
				
				
			} else if(selecionadoOpcAtor == 2) {
				Set<Long> idsAmostrar = new HashSet<>();
				
				daoAtor.ler(AutorModel.class)
				.stream()
				.forEach(obj -> {
					if(selecionado.getElenco().contains(obj)) {
					} else {
						idsAmostrar.add(obj.getId());
					}
				});
				
				daoAtor.imprimiAtoresNaoUsados(idsAmostrar);
				System.out.print("ID ATOR A REMOVER : ");
				long idRemover = entrada.nextLong();
				
				if(!Validadores.idInexistente(AutorModel.class, idRemover)) {
					return;
				}
				
				AutorModel atorRemover = daoAtor.lerApenasUm(AutorModel.class, idRemover);
				
				selecionado.getElenco().remove(atorRemover);
			}
			
			break;
		}
		}
		
		dao.atualizar(selecionado);
		dao.fecharTransacao();
		
	}

	private static void lerFilme() {
		dao.imprimiFilmesNaoUsados(new HashSet<Long>());
		
	}

	private static void apagarFilme() {
		Set<Long> filmesNaoUsados = new HashSet<>();
		dao.imprimiFilmesNaoUsados(filmesNaoUsados);
		
		System.out.print("ID DO FILME QUE VOCÊ QUER APAGAR : ");
		long idSelecionadoFilme = entrada.nextLong();
		
		if(!Validadores.idInexistente(FilmeModel.class, idSelecionadoFilme)) {
			return;
		}
		
		System.out.print("TEM CERTEZA ? [s/n]: ");
		entrada.nextLine();
		String certeza = entrada.nextLine();
		
		if(certeza.equalsIgnoreCase("s")) {
			
			FilmeModel filmeApagar = dao.lerApenasUm(FilmeModel.class, idSelecionadoFilme);
			
			dao.abrirTrasacao().remove(filmeApagar).fecharTransacao();
			
			filmesNaoUsados.add(idSelecionadoFilme);
			
		} else {
			System.out.println("MISSÃO ABORTADA !");
		}
		
		
		
	}

	private static void inserirFilme() {
		Scanner entradaDados = new Scanner(System.in);
		System.out.print("TITULO : ");
		String titulo = entradaDados.nextLine();
		
		System.out.print("TOTAL GASTO NO FILME : ");
		Double gasto = entradaDados.nextDouble();

		
		FilmeModel filme = new FilmeModel(titulo, gasto);
		
		// aqui eu faço um select de atores puxando do view de atores :
		dao.abrirTrasacao().inserirLinha(filme).fecharTransacao();
		
		System.out.print("Quer inserir atores ? [s/n]");
		entradaDados.nextLine();
		String respostaAtor = entradaDados.nextLine();
		
		if(respostaAtor.equalsIgnoreCase("s")) {
			boolean continuaAdcionando = false;
			Set<Long> autoresIdsJaUsados = new HashSet<>();
			
			do {
				// AQUI QUERO REIMPRIMI AUTORES QUE N USEI AINDA..
				daoAtor.imprimiAtoresNaoUsados(autoresIdsJaUsados);
				
				System.out.print("Selecione o ator para esse filme : ");
				long idSelecionado = entradaDados.nextLong();
				
				AutorModel autorSelecionado = daoAtor.lerApenasUm(AutorModel.class, idSelecionado);
				
				filme.getElenco().add(autorSelecionado);
				
				autoresIdsJaUsados.add(idSelecionado);
				
				System.out.print("inserir outro ? [s/n]: ");
				entradaDados.nextLine();
				String continua = entradaDados.nextLine();
				
				if(continua.equalsIgnoreCase("s")) {
					continuaAdcionando = true;
					
				} else if(continua.equalsIgnoreCase("n")) {
					continuaAdcionando = false;
				} else {
					
				}
				
				if(autoresIdsJaUsados.size() == daoAtor.ler(AutorModel.class).size()) {
					break;
				}
				
				dao.abrirTrasacao().atualizar(filme).fecharTransacao();
			} while(continuaAdcionando);
			
			System.out.println("seção finalizada");
			
			autoresIdsJaUsados.clear();
		} else if(respostaAtor.equalsIgnoreCase("n")) {
			System.out.println("FILME INSERIDO...");
		}
		
		
		entradaDados.close();
	}
}
