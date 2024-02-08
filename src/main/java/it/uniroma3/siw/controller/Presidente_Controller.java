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

import it.uniroma3.siw.controller.validator.Tesseramento_Validator;
import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.model.Giocatore;
import it.uniroma3.siw.model.Presidente;
import it.uniroma3.siw.model.Squadra;
import it.uniroma3.siw.model.Tesseramento;
import it.uniroma3.siw.repository.Giocatore_Repository;
import it.uniroma3.siw.repository.Presidente_Repository;
import it.uniroma3.siw.repository.Squadra_Repository;
import it.uniroma3.siw.repository.Tesseramento_Repository;
import it.uniroma3.siw.service.CredenzialiService;
import jakarta.validation.Valid;

@Controller
public class Presidente_Controller {

	@Autowired Tesseramento_Repository tesseramentoRepository;

	@Autowired Squadra_Repository squadraRepository;

	@Autowired Giocatore_Repository giocatoreRepository;

	@Autowired CredenzialiService credenzialiService;

	@Autowired Presidente_Repository presidenteRepository;

	@Autowired Tesseramento_Validator tesseramentoValidator;



	@GetMapping("{id}/{nome}/{cognome}/{ruolo}")
	public String aggListaGiocatori(@PathVariable("id") Long id,
			@PathVariable("nome") String nome,
			@PathVariable("cognome") String cognome, 
			@PathVariable("ruolo") String ruolo, Model model) {

		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credenziali credenziali = credenzialiService.getCredentials(userDetails.getUsername());


		//trovo il giocatore
		Giocatore giocatore = giocatoreRepository.findByNomeAndCognomeAndRuolo(nome ,cognome, ruolo);
		//trovo la squadra alla quale deve essere assegnato
		Squadra squadra = squadraRepository.findById(id).get();
		//dopo devo andare a settare il tesseramento:





		model.addAttribute("tesseramento", new Tesseramento());
		model.addAttribute("utente", credenziali);
		model.addAttribute("giocatore", giocatore);
		model.addAttribute("squadra", squadra);
		return "presidente/tesseramento.html";
	}

	@PostMapping("/presidente/tesseramentoGiocatore/{ids}/{idg}")
	public String aggListaGiocatori(@Valid @ModelAttribute("tesseramento") Tesseramento tesseramento, 
			BindingResult result,
			@PathVariable("ids") Long ids,
			@PathVariable("idg") Long idg,
			Model model) {

		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credenziali credenziali = credenzialiService.getCredentials(userDetails.getUsername());

		Squadra squadra = this.squadraRepository.findById(ids).get();
		Giocatore giocatore = this.giocatoreRepository.findById(idg).get();
		Presidente presidente = this.presidenteRepository.findByUsername(credenziali.getUsername());
		//setto il giocatore perchè nel tesseramento fa parte della chiave, quindi poi faccio la validazione 
		//attraverso il validate
		tesseramento.setGiocatore(giocatore);


		this.tesseramentoValidator.validate(tesseramento, result);

		//se non ci sono errori
		if(!result.hasErrors()){

			//allora vuol dire che il periodo inserito va bene e non c'è un tesseramento uguale nel database
			tesseramento.setSquadra(squadra);


			//se sono qui vuol dire che va bene e non esiste nessun tesseramento di quel giocatore in quel periodo
			//salvo il tesseramento
			this.tesseramentoRepository.save(tesseramento);



			//DEVO AGGIORNARE L'HOME PAGE DEL PRESIDENTE CON IL NUOVO GIOCATORE INSERITO

			//PRENDO LA LISTA DEI GIOCATORI TESSERATI IN QUESTA SQUADRA	

			//TUTTI I TESSERAMENTI CHE CI SONO
			List<Tesseramento> listaTesseramenti = (List<Tesseramento>) this.tesseramentoRepository.findAll();

			List<Tesseramento> listaTesseramentiSquadra = new ArrayList<>();

			//lista di tutti i giocatori
			List<Giocatore> listaGiocatoriNonInSquadra = (List<Giocatore>) this.giocatoreRepository.findAll();

			//se non è vuota
			if(!listaTesseramenti.isEmpty()) {
				//prendo solo quelli che sono di questa squadra
				for(Tesseramento t: listaTesseramenti) {
					if(t.getSquadra().equals(squadra)) {
						listaTesseramentiSquadra.add(t);
					}
				}
				
				//controllo che non sia vuota	
				if(!listaTesseramentiSquadra.isEmpty())
				//mi servono anche i giocatori che non sono in questa squadra
				for(Tesseramento t : listaTesseramentiSquadra) {
					//quindi elimino i giocatori già tesserati nella mia squadra da quelli totali
					listaGiocatoriNonInSquadra.remove(t.getGiocatore());
				}
			
			}
			
			model.addAttribute("giocatoriNonInSquadra", listaGiocatoriNonInSquadra);
			model.addAttribute("listaTesseramenti", listaTesseramentiSquadra);
			model.addAttribute("presidente", presidente);
			model.addAttribute("squadra", tesseramento.getSquadra());
			model.addAttribute("utente", credenziali);

			return "presidente/HomePagePresidente.html";
		}

		else {//altrimenti ritorna alla pagine del tesseramento
			model.addAttribute("utente", credenziali);
			model.addAttribute("giocatore", giocatore);
			model.addAttribute("squadra", squadra);
			return "presidente/tesseramento.html";
		}

	}


	@GetMapping("/presidente/esoneraGiocatore/{nome}/{idG}")
	public String esoneraGiocatore(@PathVariable("nome") String nome,
			@PathVariable("idG") Long id, Model model){

		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credenziali credenziali = credenzialiService.getCredentials(userDetails.getUsername());


		//trovo il giocatore, la squadra
		Giocatore giocatore = this.giocatoreRepository.findById(id).get();
		Squadra squadra = this.squadraRepository.findByNome(nome);

		Presidente presidente = squadra.getPresidente();


		//Trovo il tesseramento in quella squadra di quel giocatore, che è unico
		Tesseramento tesseramento = this.tesseramentoRepository.findByGiocatoreAndSquadra(giocatore, squadra);

		//elimino tesseramento
		this.tesseramentoRepository.delete(tesseramento);

		//RICARICO LA PAGINA DEL PRESIDENTE CON LA SQUADRA AGGIORNATA

		//TUTTI I TESSERAMENTI CHE CI SONO
		List<Tesseramento> listaTesseramenti = (List<Tesseramento>) this.tesseramentoRepository.findAll();

		List<Tesseramento> listaTesseramentiSquadra = new ArrayList<>();

		//lista di tutti i giocatori
		List<Giocatore> listaGiocatoriNonInSquadra = (List<Giocatore>) this.giocatoreRepository.findAll();

		//la lista dei tesseramenti totali non deve essere vuota
		if(!listaTesseramenti.isEmpty()) {	
			//prendo solo quelli che sono di questa squadra
			for(Tesseramento t: listaTesseramenti) {
				if(t.getSquadra().equals(squadra)) {
					listaTesseramentiSquadra.add(t);
				}
			}

		
		//controllo che non sia vuota	
		if(!listaTesseramentiSquadra.isEmpty())
			//mi servono anche i giocatori che non sono in questa squadra
			for(Tesseramento t : listaTesseramentiSquadra) {
				//quindi elimino i giocatori già tesserati nella mia squadra da quelli totali
				listaGiocatoriNonInSquadra.remove(t.getGiocatore());
			}
		
		}


		model.addAttribute("listaTesseramenti", listaTesseramentiSquadra);
		model.addAttribute("presidente", presidente);
		model.addAttribute("giocatoriNonInSquadra", listaGiocatoriNonInSquadra);
		model.addAttribute("squadra", squadra);
		model.addAttribute("utente", credenziali);


		return "presidente/HomePagePresidente.html";
	}




}













