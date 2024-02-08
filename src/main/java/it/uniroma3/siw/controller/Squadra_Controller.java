package it.uniroma3.siw.controller;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.controller.validator.Presidente_Validator;
import it.uniroma3.siw.controller.validator.Squadra_Validator;
import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.model.Presidente;
import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.Presidente_Repository;
import it.uniroma3.siw.repository.Squadra_Repository;
import it.uniroma3.siw.service.CredenzialiService;
import jakarta.validation.Valid;

@Controller
public class Squadra_Controller {


	@Autowired Squadra_Repository squadraRepository;

	@Autowired CredenzialiService credentialsService;

	@Autowired Presidente_Repository presidenteRepository;
	
	@Autowired Squadra_Validator squadraValidator;
	
	@Autowired Presidente_Validator presidenteValidator;
	
	@GetMapping("/admin/formGetAggiungiSquadra")
	public String getForm(Model model){
		model.addAttribute("squadra", new Squadra());
		List<Presidente> listaPresidentiSenzaSquadra = new ArrayList<>();

		for(Presidente p : this.presidenteRepository.findAll()){
			if(p!=null && p.getSquadra() == null){
				listaPresidentiSenzaSquadra.add(p);
			}
		}

		model.addAttribute("lista", listaPresidentiSenzaSquadra);
		return "admin/formNewSquadra.html";
	}

	@PostMapping("/admin/formPostAggiungiSquadra")
	public String postForm(@Valid @ModelAttribute("squadra") Squadra squadra,
			BindingResult result,
			@RequestParam("presidenteSquadra") String codiceFiscale,
			Model model){

		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credenziali credenziali= credentialsService.getCredentials(userDetails.getUsername());

		Presidente presidente = this.presidenteRepository.findByCodiceFiscale(codiceFiscale);
		
		this.squadraValidator.validate(squadra, result);
		
		if(!result.hasErrors()){
		squadra.setPresidente(presidente);

		//salvo la squadra
		this.squadraRepository.save(squadra);

		//setto la squadra del presidente
		presidente.setSquadra(squadra);		

		presidenteRepository.save(presidente);
		//poi lo rendo presidente di una squadra
		
		model.addAttribute("utente", credenziali);
		return "admin/HomePageAdmin.html";
		}
		
		else { //c'è stato qualche errore e ritorna alla form
			List<Presidente> listaPresidentiSenzaSquadra = new ArrayList<>();

			for(Presidente p : this.presidenteRepository.findAll()){
				if(p!=null && p.getSquadra() == null){
					listaPresidentiSenzaSquadra.add(p);
				}
			}

			model.addAttribute("lista", listaPresidentiSenzaSquadra);
			model.addAttribute("squadra",squadra);		
			return "admin/formNewSquadra.html";
		}


		
	}

	@GetMapping("/admin/formGetAggiungiPresidente")
	public String getFormPresidente(Model model){
		model.addAttribute("presidente", new Presidente());
		return "admin/formNewPresidente.html";
	}

	@PostMapping("/admin/formPostAggiungiPresidente")
	public String postForm(@Valid @ModelAttribute("presidente") Presidente presidente,
			BindingResult result, Model model){
		
		this.presidenteValidator.validate(presidente, result);
		
		if(!result.hasErrors()) {
		//prima salvo il presidente nel database
		presidenteRepository.save(presidente);

		//creo le credenziali per il presidente
		Utente utente = new Utente();

		utente.setName(presidente.getNome());
		utente.setSurname(presidente.getCognome());
		
		Credenziali credenziali = new Credenziali();
		credenziali.setUsername(presidente.getUsername());

		model.addAttribute("utente", utente);
		model.addAttribute("credenziali", credenziali);
		return "admin/formRegisterPresidente.html";
		}
		
		//altrimenti se  ci sono errori
		else {
			model.addAttribute("presidente", presidente);
			return "admin/formNewPresidente.html";
		}
	}


	@GetMapping("/admin/formGetModificaSquadra")
	public String getUpdateSquadra(Model model){

		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credenziali credenziali= credentialsService.getCredentials(userDetails.getUsername());


		model.addAttribute("utente", credenziali);
		model.addAttribute("squadre", this.squadraRepository.findAll());
		return "admin/formUpdateSquadra.html";
	}

	@GetMapping("/admin/{nome}")
	public String datiUpdateSquadra(@PathVariable("nome") String nome, Model model) {


		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credenziali credenziali= credentialsService.getCredentials(userDetails.getUsername());

		Squadra squadra = this.squadraRepository.findByNome(nome);
		Squadra squadra2 = this.squadraRepository.findByNome(nome);
		List<Presidente> listaPresidentiSenzaSquadra = new ArrayList<>();

		for(Presidente p : this.presidenteRepository.findAll()){
			if(p!=null && p.getSquadra() == null){
				listaPresidentiSenzaSquadra.add(p);
			}
		}

		model.addAttribute("presidente", squadra.getPresidente());
		model.addAttribute("lista", listaPresidentiSenzaSquadra);		
		model.addAttribute("utente", credenziali);
		model.addAttribute("squadra", squadra);
		model.addAttribute("squadra2", squadra2);
		return "admin/updateDatiSquadra.html";
	}

	@PostMapping("/admin/{id}")
	public String postUpdateSquadra(@PathVariable("id") Long id ,
			@Valid @ModelAttribute("squadra") Squadra squadra, 
			BindingResult result,
			@RequestParam("presidenteSquadra") String codiceFiscale,
			Model model) {

		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credenziali credenziali= credentialsService.getCredentials(userDetails.getUsername());
		
		this.squadraValidator.validate(squadra, result);
		
		Squadra squadra2 = this.squadraRepository.findById(id).get();
		
		//aggiunto condizione affinchè se non modifico il nome, non lo conta come errore
		if(!result.hasErrors() || squadra.getNome().equals(squadra2.getNome())) {
		

		Presidente presidente = this.presidenteRepository.findByCodiceFiscale(codiceFiscale);

		//se sono diversi devi mettere a null il vecchio presidente e inserire il nuovo
		if(presidente != null && !presidente.equals(squadra.getPresidente())) {
			Presidente presidenteVecchio = squadra2.getPresidente();
			if(presidenteVecchio != null) {
			presidenteVecchio.setSquadra(null);
			}
			squadra2.setPresidente(presidente);
		
		
		presidente.setSquadra(squadra2);
		
		this.presidenteRepository.save(presidente);
		
		}
		
		if(!squadra.getNome().equals(squadra2.getNome()))
		squadra2.setNome(squadra.getNome());

		if(!squadra.getIndirizzoSede().equals(squadra2.getIndirizzoSede()))
		squadra2.setIndirizzoSede(squadra.getIndirizzoSede());

		if(!squadra.getAnnoFondazione().equals(squadra2.getAnnoFondazione()))
		squadra2.setAnnoFondazione(squadra.getAnnoFondazione());


		//squadra Modificata
		this.squadraRepository.save(squadra2);

		model.addAttribute("utente", credenziali);
		return "admin/HomePageAdmin.html";
		}
		
		//ALTRIMENTI SE CI SONO ERRORI, MI RIMANDI ALLA PAGINA DI MODIFICA
		else {
			
			
//			Squadra squadra3 = this.squadraRepository.findById(id).get();
			Squadra squadra4 = this.squadraRepository.findById(id).get();
			List<Presidente> listaPresidentiSenzaSquadra = new ArrayList<>();

			for(Presidente p : this.presidenteRepository.findAll()){
				if(p!=null && p.getSquadra() == null){
					listaPresidentiSenzaSquadra.add(p);
				}
			}


			model.addAttribute("lista", listaPresidentiSenzaSquadra);		
			model.addAttribute("utente", credenziali);
			model.addAttribute("squadra", squadra);
			model.addAttribute("squadra2", squadra4);
			
			return "admin/updateDatiSquadra.html";
		}
		
	}
	



}
