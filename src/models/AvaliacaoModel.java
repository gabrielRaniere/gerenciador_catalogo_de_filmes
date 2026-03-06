package models;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class AvaliacaoModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String email;
	
	private Date data;
	
	@Column(scale = 1, precision = 3)
	private Double nota;
	
	private String comentario;
	
	@ManyToOne
	private FilmeModel filme_id;

	public AvaliacaoModel() {}
	
	public AvaliacaoModel(String email, Double nota, FilmeModel filme_id, String comentario) {
		super();
		this.email = email;
		this.nota = nota;
		this.filme_id = filme_id;
		this.comentario = comentario;
		this.setData(new Date());
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Double getNota() {
		return nota;
	}

	public void setNota(Double nota) {
		this.nota = nota;
	}

	public FilmeModel getFilme_id() {
		return filme_id;
	}

	public void setFilme_id(FilmeModel filme_id) {
		this.filme_id = filme_id;
	}
	
	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AvaliacaoModel other = (AvaliacaoModel) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}
	
	
}
