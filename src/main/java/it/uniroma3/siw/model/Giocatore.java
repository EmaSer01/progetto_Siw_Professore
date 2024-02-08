package it.uniroma3.siw.model;


import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(uniqueConstraints =@UniqueConstraint(columnNames= {"nome", "cognome", "ruolo", "citta", "anno"}))
public class Giocatore {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;
	
	
	@NotBlank
	public String nome;
	
	@NotBlank
	public String cognome;
	
	@Min(1990)
	@Max(2005)
	@NotNull
	public Integer anno;
	
	@NotBlank 
	public String citta;
	
	@NotBlank
	public String ruolo;
	

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public Integer getAnno() {
		return anno;
	}

	public void setAnno(Integer anno) {
		this.anno = anno;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public Long getId() {
		return id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cognome, nome, ruolo, citta, anno);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Giocatore other = (Giocatore) obj;
		return Objects.equals(cognome, other.cognome) && Objects.equals(nome, other.nome)
				&& Objects.equals(ruolo, other.ruolo) && Objects.equals(citta, other.citta)
				&& Objects.equals(anno, other.anno);
	}
	
	
	
}
