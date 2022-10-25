package it.manytomanyjpamaven.service;

import java.util.List;

import it.manytomanyjpamaven.dao.RuoloDAO;
import it.manytomanyjpamaven.dao.UtenteDAO;
import it.manytomanyjpamaven.model.Ruolo;

public interface RuoloService {
	public List<Ruolo> listAll() throws Exception;

	public Ruolo caricaSingoloElemento(Long id) throws Exception;

	public void aggiorna(Ruolo ruoloInstance) throws Exception;

	public void inserisciNuovo(Ruolo ruoloInstance) throws Exception;

	public void rimuovi(Long idRuoloToRemove) throws Exception;

	public Ruolo cercaPerDescrizioneECodice(String descrizione, String codice) throws Exception;
	
	// Metodo Per casa
	public List<String> listAllDescrizioniDistinteAssociatiAdUtenti() throws Exception;

	// per injection
	public void setRuoloDAO(RuoloDAO ruoloDAO);
	public void setUtenteDAO(UtenteDAO utenteDAO);
}
