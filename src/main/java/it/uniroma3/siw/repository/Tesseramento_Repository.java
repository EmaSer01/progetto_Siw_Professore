package it.uniroma3.siw.repository;



import java.time.LocalDate;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Giocatore;
import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.model.Tesseramento;

public interface Tesseramento_Repository extends CrudRepository<Tesseramento, Long> {

	boolean existsByInizioTesseramentoAndFineTesseramentoAndGiocatore(LocalDate inizioTesseramento,
			LocalDate fineTesseramento, Giocatore giocatore);

	Tesseramento findByGiocatoreAndSquadra(Giocatore giocatore, Squadra squadra);


	

}