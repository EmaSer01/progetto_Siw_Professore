package it.uniroma3.siw.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Presidente;
import it.uniroma3.siw.repository.Presidente_Repository;




@Component
public class Presidente_Validator implements Validator {
	@Autowired
	private Presidente_Repository presidenteRepository;

	@Override
	public void validate(Object o, Errors errors) {
		Presidente presidente = (Presidente)o;
		if (presidente.codiceFiscale !=null 
				&& presidenteRepository.existsByCodiceFiscale(presidente.getCodiceFiscale())) {
			errors.reject("Presidente.duplicate");
		}
		if(presidente.getUsername() != null 
				&& presidenteRepository.existsByUsername(presidente.getUsername())) {
			errors.reject("PresUsername.duplicate");
		}
	}
	@Override
	public boolean supports(Class<?> aClass) {
		return Presidente.class.equals(aClass);
	}
}