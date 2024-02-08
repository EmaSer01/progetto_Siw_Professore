package it.uniroma3.siw.model;

import java.time.LocalDate;
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
@Table(uniqueConstraints =@UniqueConstraint(columnNames= {"codiceFiscale"}))
public class Presidente {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;
	
	
	@NotBlank
	public String nome;
	
	@NotBlank
	public String cognome;
	
	@NotBlank
	public String username;

	@NotBlank
	public String codiceFiscale;
	
	@NotNull
	public LocalDate anno;
	
	@NotBlank
	public String citta;
	
	@OneToOne
	Squadra squadra;
	
		
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

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public LocalDate getAnno() {
		return anno;
	}

	public void setAnno(LocalDate anno) {
		this.anno = anno;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	public Long getId() {
		return id;
	}
	
	
	public Squadra getSquadra() {
		return squadra;
	}

	public void setSquadra(Squadra squadra) {
		this.squadra = squadra;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(codiceFiscale);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Presidente other = (Presidente) obj;
		return Objects.equals(codiceFiscale, other.codiceFiscale);
	}
	
	
}
