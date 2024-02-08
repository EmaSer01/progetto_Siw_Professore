package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Giocatore;


public interface Giocatore_Repository extends CrudRepository<Giocatore, Long>{

	Giocatore findByNomeAndCognomeAndRuolo(String nome, String cognome, String ruolo);

	boolean existsByNomeAndCognomeAndRuoloAndCittaAndAnno(String nome, String cognome, String ruolo, String citta,
			Integer anno);

}
