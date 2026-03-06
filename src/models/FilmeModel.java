package models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "filmes")
public class FilmeModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String titulo;
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Set<AutorModel> elenco = new HashSet<>();
	
	@Column(scale = 1, precision = 3, nullable = true)
	private Double nota;
	
	@Column(scale = 2)
	private Double gasto_total;
	
	private Integer participantes_nota = 0;
	
	@OneToMany(mappedBy = "filme_id", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Set<AvaliacaoModel> avaliacoes = new HashSet<>();
	
	public FilmeModel() {}

	public FilmeModel(String titulo, Double gasto_total) {
		super();
		this.titulo = titulo;
		this.gasto_total = gasto_total;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Set<AutorModel> getElenco() {
		return elenco;
	}

	public void setElenco(Set<AutorModel> elenco) {
		this.elenco = elenco;
	}

	public Double getNota() {
		return nota;
	}

	private void setNota(Double nota) {
		this.nota = nota;
	}

	public Double getGasto_total() {
		return gasto_total;
	}

	public void setGasto_total(Double gasto_total) {
		this.gasto_total = gasto_total;
	}

	public Integer getParticipantes_nota() {
		return participantes_nota;
	}

	public void setParticipantes_nota(Integer participantes_nota) {
		this.participantes_nota = participantes_nota;
	}

	public Set<AvaliacaoModel> getAvaliacoes() {
		return avaliacoes;
	}

	public void calculaMedia(Set<AvaliacaoModel> notas) {
		double soma = 0.0;
		for(AvaliacaoModel nota: notas) {
			soma += nota.getNota();
		}
		
		double resultado = soma / notas.size();
		
		setNota(Math.floor(resultado));
	}

	public void setAvaliacoes(Set<AvaliacaoModel> avaliacoes) {
		this.avaliacoes = avaliacoes;
	}
	
}
