package it.uniroma3.siw.model;

import java.time.LocalDate;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(uniqueConstraints =@UniqueConstraint(columnNames= {"inizioTesseramento", "fineTesseramento", "giocatore_id"}))
public class Tesseramento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;	
	
	@NotNull
	LocalDate inizioTesseramento;
	
	@NotNull
	LocalDate fineTesseramento;

	@ManyToOne
	public Giocatore giocatore;

	@ManyToOne
	public Squadra squadra;
	
	public LocalDate getInizioTesseramento() {
		return inizioTesseramento;
	}

	public void setInizioTesseramento(LocalDate inizioTesseramento) {
		this.inizioTesseramento = inizioTesseramento;
	}

	public LocalDate getFineTesseramento() {
		return fineTesseramento;
	}

	public void setFineTesseramento(LocalDate fineTesseramento) {
		this.fineTesseramento = fineTesseramento;
	}

	public Giocatore getGiocatore() {
		return giocatore;
	}

	public void setGiocatore(Giocatore giocatore) {
		this.giocatore = giocatore;
	}

	public Squadra getSquadra() {
		return squadra;
	}

	public void setSquadra(Squadra squadra) {
		this.squadra = squadra;
	}

	@Override
	public int hashCode() {
		return Objects.hash(fineTesseramento, giocatore, inizioTesseramento);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tesseramento other = (Tesseramento) obj;
		return Objects.equals(fineTesseramento, other.fineTesseramento) && Objects.equals(giocatore, other.giocatore)
				&& Objects.equals(inizioTesseramento, other.inizioTesseramento);
	}
	

}
