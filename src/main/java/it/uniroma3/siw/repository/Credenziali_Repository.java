package it.uniroma3.siw.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Credenziali;

public interface Credenziali_Repository extends CrudRepository<Credenziali, Long> {

	public Optional<Credenziali> findByUsername(String username);

	public boolean existsByUsername(String username);

}