package view;

import java.util.List;
import java.util.Scanner;

import controllers.Validadores;
import infra.DAO;
import infra.DAOatores;
import models.AutorModel;

public class AtoresView {
	
	private static Scanner entrada = new Scanner(System.in);
	
	static public boolean entrarSecaoAtores() {
		
		while(true) {
			System.out.println("_______________________________________________________");
			System.out.println("OQUE DESEJA FAZER EM FILMES ?");
			System.out.println("[1] inserir atores");
			System.out.println("[2] atualizar atores");
			System.out.println("[3] ler atores");
			System.out.println("[4] excluir atores");
			System.out.println("[5] VOLTAR");
			System.out.print("--> ");
			int valorEscolhido = entrada.nextInt();
			
			if(!Validadores.verificaValor(valorEscolhido, 1, 5)) {
				continue;
			}
			
			// não é um atributo estático pois pode ser executado antes do factorymanager!
			DAOatores dao = new DAOatores();
			
			switch(valorEscolhido) {
			case 1: {
				inserirAtor(dao);
				continue;
			}
			case 2: {
				atualizarAtor(dao);
				continue;
			}
			case 3: {
				lerAtores(dao);
				continue;
			}
			case 4: {
				excluirAtor(dao);
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

	private static void atualizarAtor(DAOatores dao) {
		dao.imprimiAtoresNaoUsados(null);
		System.out.println("-----------------");
		System.out.print("SELECIONE ID DO ATOR A ATUALIZAR: ");
		Long idAtualizar = entrada.nextLong();
		
		if(!Validadores.idInexistente(AutorModel.class, idAtualizar)) {
			return;
		}
		
		AutorModel atorSelecionado = dao.lerApenasUm(AutorModel.class, idAtualizar);
		
		System.out.println("[1] novo nome");
		System.out.println("[2] nova idade");
		System.out.print("DIGITE : ");
		Integer opcSelecionada = entrada.nextInt();
		
		if(opcSelecionada == 1) {
			System.out.print("NOVO NOME: ");
			entrada.next();
			String novoNome = entrada.nextLine();
			
			atorSelecionado.setNome(novoNome);
			
		} else if(opcSelecionada == 2) {
			System.out.print("NOVA IDADE: ");
			Integer novaIdade = entrada.nextInt();
			
			atorSelecionado.setIdade(novaIdade);
		}
		
		dao.abrirTrasacao().atualizar(atorSelecionado).fecharTransacao();
	}

	private static void excluirAtor(DAOatores dao) {
		dao.imprimiAtoresNaoUsados(null);
		System.out.print("ID DO ATOR A REMOVER : ");
		long idRemover = entrada.nextLong();
		
		if(!Validadores.idInexistente(AutorModel.class, idRemover)) {
			return;
		}
		
		AutorModel atorRemover = dao.lerApenasUm(AutorModel.class, idRemover);
		
		dao.abrirTrasacao().remove(atorRemover);
		
		System.out.print("ATOR REMOVIDO !");
	}

	public static void lerAtores(DAOatores dao) {
		List<AutorModel> atores = dao.ler(AutorModel.class);
		
		// ainda vou ajeitar esse print, pois quero imprimir no console algo mais formatadinho
		atores.stream().forEach(obj -> System.out.println(
				obj.getId() + " | " +
				obj.getNome() + " | " +
				obj.getIdade() + " | " + 
				obj.getSexo())
				);
	}

	private static void inserirAtor(DAOatores dao) {
		// fazer aqui !!
		System.out.print("DIGITE O NOME DO ATOR: ");
		entrada.nextLine();
		String atorNome = entrada.nextLine();
		
		System.out.println("DIGITE O SEXO DO ATOR [m/f]: ");
		String atorSexo = entrada.nextLine();
		
		System.out.println("DIGITE A IDADE DO ATOR: ");
		int idadeAtor = entrada.nextInt();

		AutorModel ator = new AutorModel(atorNome, idadeAtor, atorSexo.toUpperCase());
		
		dao
		.abrirTrasacao()
		.inserirLinha(ator)
		.fecharTransacao();
			
		System.out.println("ator inserido !");
	}
	
}
