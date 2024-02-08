package it.uniroma3.siw.service;




import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Tesseramento;
import it.uniroma3.siw.repository.Tesseramento_Repository;



@Service
public class TesseramentoService {

    @Autowired
    protected Tesseramento_Repository tesseramentoRepository;

    
    public boolean tesseramentoIntermedio(Tesseramento tesseramento){
    	
    	List<Tesseramento> listaTesseramento = (List<Tesseramento>) this.tesseramentoRepository.findAll();

//    	controllo se ha tesseramenti in quel periodo
    	
    	
    	//    21/12/2000 (Data Inizio Tesseramento)
    	//    21/12/2001 (Data Fine Tesseramento)
    	for(Tesseramento t : listaTesseramento) {
    		
    		//se tesseramento è != NULL e se ho preso il tesseramento del giocatore che mi interessa
    		if(t != null && t.getGiocatore().equals(tesseramento.getGiocatore())){
    		
    		// 21/01/2001	
    	   //  21/12/2001  			
    		if((tesseramento.getInizioTesseramento().isAfter(t.getInizioTesseramento()) 
    			&& tesseramento.getFineTesseramento().isBefore(t.getFineTesseramento()))){
    			//se entro qui dentro vuol dire che il giocatore è gia inserito in un intervallo simile
    			//ritorna indietro alla fase del tesseramento
    			return true;
    		}
    		//21/10/2000
    	   //21/12/2002
    		else if((tesseramento.getInizioTesseramento().isBefore(t.getInizioTesseramento()) 
    				&& tesseramento.getFineTesseramento().isAfter(t.getFineTesseramento()))) {
    			return true;
    		}
    		
    		//20/12/2000
    		//21/03/2001
    		else if((tesseramento.getInizioTesseramento().isBefore(t.getInizioTesseramento()) 
    				&& (tesseramento.getFineTesseramento().isBefore(t.getFineTesseramento()))
    				&&  tesseramento.getFineTesseramento().isAfter(t.getInizioTesseramento()))) {
    			return true;
    		}
    		
    		//21/01/2001
    	   //21//01/2002
    		else if((tesseramento.getFineTesseramento().isAfter(t.getFineTesseramento()) 
    				&& (tesseramento.getInizioTesseramento().isBefore(t.getFineTesseramento()))
    				&&  tesseramento.getInizioTesseramento().isAfter(t.getInizioTesseramento()))) {
    			return true;
    		}
    		
    		//21/01/2001
    		//21/12/2001
    		
    		else if((tesseramento.getInizioTesseramento().isAfter(t.getInizioTesseramento()) 
        			&& tesseramento.getFineTesseramento().isEqual(t.getFineTesseramento()))){
        			
        			return true;
        		}
    		
    		//21/12/2000
    		//21/02/2002
    		else if((tesseramento.getInizioTesseramento().isEqual(t.getInizioTesseramento()) 
        			&& tesseramento.getFineTesseramento().isAfter(t.getFineTesseramento()))){
        			
        			return true;
        		}
    		else if(tesseramento.getFineTesseramento().isEqual(t.getInizioTesseramento()) 
    				|| tesseramento.getInizioTesseramento().isEqual(t.getFineTesseramento())){
    			
    				return true;
    		}
    		
    		}
    		
    }
    return false;
}
    
}
