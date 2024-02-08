package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.controller.validator.Giocatore_Validator;
import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.model.Giocatore;
import it.uniroma3.siw.repository.Giocatore_Repository;
import it.uniroma3.siw.service.CredenzialiService;
import jakarta.validation.Valid;

@Controller
public class Giocatore_Controller {
	
	@Autowired Giocatore_Repository giocatoreRepository;
	
	@Autowired Giocatore_Validator giocatoreValidator;
	
	@Autowired
	private CredenzialiService credentialsService;
	
	@GetMapping("/admin/aggiungiGiocatore")
	public String aggiungiGiocatore(Model model) {
		
		//creo nuovo giocatore
		model.addAttribute("giocatore", new Giocatore());
		return "admin/formNewGiocatore.html";	
	}
	
	@PostMapping("/admin/aggiungiGiocatore")
	public String aggiungiGiocatorePost(@Valid @ModelAttribute Giocatore giocatore, BindingResult result, Model model) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credenziali credenziali = credentialsService.getCredentials(userDetails.getUsername());
		
		this.giocatoreValidator.validate(giocatore, result);
		
		if(!result.hasErrors()) {
		//aggiungo all'interno del database
		giocatoreRepository.save(giocatore);
		model.addAttribute("utente", credenziali);
		return "admin/HomePageAdmin.html";	
		}
		
		else {
			model.addAttribute("giocatore", giocatore);
			return "admin/formNewGiocatore.html";
		}
		
	}
	
	
}
