package infra;

import java.util.Set;

import models.AutorModel;

public class DAOatores extends DAO<AutorModel>{
	
	public void imprimiAtoresNaoUsados(Set<Long> ids) {
		
		super.ler(AutorModel.class)
				.stream()
				.filter(obj -> {
					if(ids != null) {
						for(Long id: ids) {
							if(obj.getId() == id) {
								return false;
							}
						}
					}
					
					return true;
				})
				.forEach(obj -> System.out.println(
				obj.getId() + " | " +
				obj.getNome() + " | " +
				obj.getIdade() + " | " + 
				obj.getSexo())
				);
	}
	
}
