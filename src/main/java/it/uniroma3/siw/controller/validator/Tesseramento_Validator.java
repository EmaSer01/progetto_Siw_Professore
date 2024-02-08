package it.uniroma3.siw.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Tesseramento;
import it.uniroma3.siw.repository.Tesseramento_Repository;
import it.uniroma3.siw.service.TesseramentoService;


@Component
public class Tesseramento_Validator implements Validator {
	
	
	@Autowired private Tesseramento_Repository tesseramentoRepository;
	
	@Autowired private TesseramentoService tesseramentoService;

	@Override
	public void validate(Object o, Errors errors) {
		Tesseramento tesseramento = (Tesseramento)o;
		
		if(tesseramento.getInizioTesseramento() != null && tesseramento.getFineTesseramento() != null 
			&& tesseramento.getInizioTesseramento().isAfter(tesseramento.getFineTesseramento())	) {
			errors.reject("PeriodoNonValido");
		}
		
		else if (tesseramento.getInizioTesseramento() != null && tesseramento.getFineTesseramento() != null 
				&& tesseramento.getGiocatore() != null
			&& tesseramentoRepository.existsByInizioTesseramentoAndFineTesseramentoAndGiocatore(tesseramento.getInizioTesseramento(), 
					tesseramento.getFineTesseramento(),
					tesseramento.getGiocatore())) {
			errors.reject("Tesseramento.duplicate");
		}
		
		else if(tesseramento.getInizioTesseramento() != null && tesseramento.getFineTesseramento() != null 
				&& tesseramento.getGiocatore() != null && tesseramentoService.tesseramentoIntermedio(tesseramento)) {
			errors.reject("Intermedio");
		}
		
		
		
	}
	
	@Override
	public boolean supports(Class<?> aClass) {
		return Tesseramento.class.equals(aClass);
	}
}