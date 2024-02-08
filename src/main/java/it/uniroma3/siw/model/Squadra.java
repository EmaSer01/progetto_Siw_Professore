package it.uniroma3.siw.model;



import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(uniqueConstraints =@UniqueConstraint(columnNames= {"nome"}))
public class Squadra {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;

	@NotBlank
	public String nome;
	@NotNull
	public Integer annoFondazione;
	@NotBlank
	public String indirizzoSede;

	@OneToOne(mappedBy = "squadra")
	Presidente presidente;

	
	public Long getId() {
		return id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Integer getAnnoFondazione() {
		return annoFondazione;
	}
	public void setAnnoFondazione(Integer annoFondazione) {
		this.annoFondazione = annoFondazione;
	}
	public String getIndirizzoSede() {
		return indirizzoSede;
	}
	public void setIndirizzoSede(String indirizzoSede) {
		this.indirizzoSede = indirizzoSede;
	}

	public Presidente getPresidente() {
		return presidente;
	}
	public void setPresidente(Presidente presidente) {
		this.presidente = presidente;
	}


	@Override
	public int hashCode() {
		return Objects.hash(nome, this.presidente);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Squadra other = (Squadra) obj;
		return Objects.equals(nome, other.nome) && Objects.equals(presidente, other.presidente);
	}
}
