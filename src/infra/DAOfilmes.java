package infra;

import java.util.Set;

import models.AutorModel;
import models.FilmeModel;

public class DAOfilmes extends DAO<FilmeModel>{
public void imprimiFilmesNaoUsados(Set<Long> ids) {
		
		super.ler(FilmeModel.class)
				.stream()
				.filter(obj -> {
					if(ids.size() > 0L) {
						for(Long id: ids) {
							if(obj.getId() == id) {
								return false;
							}
						}
					}
					
					return true;
				})
				.forEach(obj -> {
					System.out.println(
							obj.getId() + " | " +
							obj.getTitulo() + " | " +
							obj.getGasto_total() 
							);
					
					
					for(AutorModel ator: obj.getElenco()) {
						System.out.println("-> " + ator.getNome() + " - " + ator.getId());
					}
					System.out.println("----------------------------");
				}
				
				);
	}

}
