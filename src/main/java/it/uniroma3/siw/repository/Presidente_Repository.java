package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;


import it.uniroma3.siw.model.Presidente;


public interface Presidente_Repository extends CrudRepository<Presidente, Long>{

	boolean existsByCodiceFiscale(String codiceFiscale);

	Presidente findByCodiceFiscale(String codiceFiscale);

	Presidente findByNomeAndCognome(String nome, String cognome);

	Presidente findByUsername(String username);

	boolean existsByUsername(String username);
	
	
}
