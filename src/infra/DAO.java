package infra;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class DAO <T>{
	static private EntityManagerFactory emf;
	private EntityManager em;
	
	static {
		try {
			emf = Persistence.createEntityManagerFactory("catalogo-filme");
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}
	
	public DAO() {
		em = emf.createEntityManager();
	}
	
	public DAO<T> abrirTrasacao() {
		em.getTransaction().begin();
		return this;
	}
	
	public DAO<T> fecharTransacao() {
		em.getTransaction().commit();
		return this;
	}
	
	// create
	public DAO<T> inserirLinha(T classeAinserir) {
		
		em.persist(classeAinserir);
		
		return this;
	}
	
	// read
	public List<T> ler(Class<T> classe) {
		
		TypedQuery<T> query = em.createQuery("SELECT x FROM "+ classe.getSimpleName() +" x", classe);
		
		List<T> lista = query.getResultList();
		
		return lista;
	}
	
	public T lerApenasUm(Class<T> classe, long id) {
		try {
			
			this.abrirTrasacao();
			
			T classeRetornada = em.find(classe, id);
			
			this.fecharTransacao();
			return classeRetornada;
		}catch(NullPointerException e) {
			System.out.print("CAIU AQUI");
		}
		
		return null;
	}
	
	// update
	public DAO<T> atualizar(T classe) {
		em.merge(classe);
		System.out.println("ATUALIZADO");
		
		return this;
	}
	
	
	// delete
	public DAO<T> remove(T classe) {
		em.remove(classe);
		System.out.println("APAGADO");
		
		return this;
	}
	
}
