package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Squadra;


public interface Squadra_Repository extends CrudRepository<Squadra, Long>{

	Squadra findByNome(String nome);

	boolean existsByNome(String nome);

}
