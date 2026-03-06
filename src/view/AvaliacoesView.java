package view;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import controllers.Validadores;
import infra.DAO;
import infra.DAOfilmes;
import models.AvaliacaoModel;
import models.FilmeModel;

public class AvaliacoesView {
	
	static private Scanner entrada = new Scanner(System.in).useLocale(Locale.US);  
	
	static public boolean entrarSecaoAvaliacoes() {
		while(true) {
			DAO<AvaliacaoModel> dao = new DAO<>();
			System.out.println("_______________________________________________________");
			System.out.println("[1] AVALIAR");
			System.out.println("[2] VER AVALIAÇÕES");
			System.out.println("[3] VOLTAR");
			System.out.println("DIGITE : ");
			int servicoSelecionado = entrada.nextInt();
			
			if(!Validadores.verificaValor(servicoSelecionado, 1, 3)) {
				continue;
			}
			
			switch (servicoSelecionado) { 
			case 1: {
				// aqui vou avaliar..
				avaliarFilme(dao);
				continue;
			}
			case 2: {
				verAvaliacoes();
				continue;
			}
			case 3: {
				return true;
			}
			}
			return false;
		}
	}

	private static void verAvaliacoes() {
		DAOfilmes daoFilmes = new DAOfilmes();
		
		List<FilmeModel> filmes = daoFilmes.ler(FilmeModel.class);

		System.out.println("FILME | NOTA MÉDIA | AVALIAÇÕES");
		System.out.println("________________________________");
		
		filmes.stream().forEach(obj -> System.out.println(obj.getTitulo() + "|" + obj.getNota() + "|" + obj.getParticipantes_nota()));
		
	}

	private static void avaliarFilme(DAO<AvaliacaoModel> dao) {
		System.out.print("DIGITE SEU EMAIL: ");
		String emailAvaliador = entrada.next();
		
		List<String> avaliacoes = dao.ler(AvaliacaoModel.class)
				.stream()
				.map(obj -> obj.getEmail())
				.collect(Collectors.toList());
		
		if(avaliacoes.contains(emailAvaliador)) {
			System.out.println("SEJA BEM VINDO DE VOLTA !");
			System.out.println("----------------------------");
			DAOfilmes daoFilme = new DAOfilmes();
			
			Set<Long> idsFilmesJaAvaliados = daoFilme.ler(FilmeModel.class)
					.stream()
					.filter(filme -> {
						
						for(AvaliacaoModel avaliacao: filme.getAvaliacoes()) {
							if(avaliacao.getEmail().equals(emailAvaliador)) {
								return true;
							}
						}
						return false;
					}
					)
					.map(filmesAv -> filmesAv.getId())
					.collect(Collectors.toSet());
					
			
			daoFilme.imprimiFilmesNaoUsados(idsFilmesJaAvaliados);

			inserirAvaliacao(dao, daoFilme, emailAvaliador);
		} else {
			
			DAOfilmes daoFilme = new DAOfilmes();
			daoFilme.imprimiFilmesNaoUsados(new HashSet<Long>());
			
			inserirAvaliacao(dao, daoFilme, emailAvaliador);
		}
	}
	
	static private void inserirAvaliacao(DAO<AvaliacaoModel> dao, DAOfilmes daoFilme, String emailAvaliador) {
		System.out.print("SELECIONE O ID DO FILME A AVALIAR: ");
		Long idFilmeAvaliar = entrada.nextLong();
		
		FilmeModel filme = daoFilme.lerApenasUm(FilmeModel.class, idFilmeAvaliar);
		daoFilme.abrirTrasacao().atualizar(filme).fecharTransacao();
		
		System.out.print("DIGITE A NOTA DO FILME SELECIONADO: ");
		Double notaFilme = entrada.nextDouble();
		
		System.out.print("DIGITE ALGUM COMENTÁRIO(opcional): ");
		String comentarioFilme = entrada.next();
		
		AvaliacaoModel avaliacao = new AvaliacaoModel(emailAvaliador, notaFilme, filme, comentarioFilme);
		
		filme.getAvaliacoes().add(avaliacao);
		filme.calculaMedia(filme.getAvaliacoes());
		daoFilme.abrirTrasacao().atualizar(filme).fecharTransacao();
		
		
		dao.abrirTrasacao().inserirLinha(avaliacao).fecharTransacao();
		
		System.out.print("OBRIGADO PELO FEEDBACK !!");
	}
	
}
