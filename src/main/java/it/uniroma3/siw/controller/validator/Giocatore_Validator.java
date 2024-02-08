package it.uniroma3.siw.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Giocatore;
import it.uniroma3.siw.repository.Giocatore_Repository;


@Component
public class Giocatore_Validator implements Validator {
	
	@Autowired
	private Giocatore_Repository giocatoreRepository;

	@Override
	public void validate(Object o, Errors errors) {
		Giocatore giocatore = (Giocatore)o;
		if ((giocatore.getNome() != null && giocatore.getCognome() != null && giocatore.getRuolo() != null 
			&& giocatore.getCitta() != null && giocatore.getAnno() != null)
			&& (giocatoreRepository.existsByNomeAndCognomeAndRuoloAndCittaAndAnno(giocatore.getNome(), 
						giocatore.getCognome() ,giocatore.getRuolo(),
						giocatore.getCitta(), giocatore.getAnno()))) {
			errors.reject("Giocatore.duplicate");
		}
	}
	
	@Override
	public boolean supports(Class<?> aClass) {
		return Giocatore.class.equals(aClass);
	}
}