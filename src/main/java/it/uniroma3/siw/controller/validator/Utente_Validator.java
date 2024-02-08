package it.uniroma3.siw.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.Utente_Repository;


@Component
public class Utente_Validator implements Validator {
	@Autowired
	private Utente_Repository utenteRepository;

	@Override
	public void validate(Object o, Errors errors) {
		Utente utente = (Utente)o;
		if (utente.getEmail()!=null 
				&& utenteRepository.existsByEmail(utente.getEmail())) {
			errors.reject("Utente.duplicate");
		}
	}
	@Override
	public boolean supports(Class<?> aClass) {
		return Utente.class.equals(aClass);
	}
}