package it.uniroma3.siw.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.repository.Squadra_Repository;


@Component
public class Squadra_Validator implements Validator {
	@Autowired
	private Squadra_Repository squadraRepository;

	@Override
	public void validate(Object o, Errors errors) {
		Squadra squadra = (Squadra)o;
		if (squadra.getNome() != null 
				&& squadraRepository.existsByNome(squadra.getNome())) {
			errors.reject("Squadra.duplicate");
		}
	}
	
	@Override
	public boolean supports(Class<?> aClass) {
		return Squadra.class.equals(aClass);
	}
}