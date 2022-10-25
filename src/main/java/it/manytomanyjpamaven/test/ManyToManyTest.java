package it.manytomanyjpamaven.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import it.manytomanyjpamaven.dao.EntityManagerUtil;
import it.manytomanyjpamaven.exception.RuoloConUtentiAssociatiException;
import it.manytomanyjpamaven.exception.UtenteConRuoliAssociatiException;
import it.manytomanyjpamaven.model.Ruolo;
import it.manytomanyjpamaven.model.StatoUtente;
import it.manytomanyjpamaven.model.Utente;
import it.manytomanyjpamaven.service.MyServiceFactory;
import it.manytomanyjpamaven.service.RuoloService;
import it.manytomanyjpamaven.service.UtenteService;

public class ManyToManyTest {

	public static void main(String[] args) {
		UtenteService utenteServiceInstance = MyServiceFactory.getUtenteServiceInstance();
		RuoloService ruoloServiceInstance = MyServiceFactory.getRuoloServiceInstance();

		// ora passo alle operazioni CRUD
		try {

			// inizializzo i ruoli sul db
			initRuoli(ruoloServiceInstance);

//			System.out.println("In tabella Utente ci sono " + utenteServiceInstance.listAll().size() + " elementi.");
//
//			testInserisciNuovoUtente(utenteServiceInstance);
//			System.out.println("In tabella Utente ci sono " + utenteServiceInstance.listAll().size() + " elementi.");
//
//			testCollegaUtenteARuoloEsistente(ruoloServiceInstance, utenteServiceInstance);
//			System.out.println("In tabella Utente ci sono " + utenteServiceInstance.listAll().size() + " elementi.");
//
//			testModificaStatoUtente(utenteServiceInstance);
//			System.out.println("In tabella Utente ci sono " + utenteServiceInstance.listAll().size() + " elementi.");
//
//			testRimuoviRuoloDaUtente(ruoloServiceInstance, utenteServiceInstance);
//			System.out.println("In tabella Utente ci sono " + utenteServiceInstance.listAll().size() + " elementi.");

			// TEST
			testRimuoviUtente(ruoloServiceInstance, utenteServiceInstance);
			testRimuoviRuolo(ruoloServiceInstance, utenteServiceInstance); 
//			testListAllRuolo(ruoloServiceInstance, utenteServiceInstance);
//			testAggiornaRuolo(ruoloServiceInstance, utenteServiceInstance);

//			// TEST COMPITI PER CASA
//			// Utente
			testListAllCreatiInMeseAnno(ruoloServiceInstance, utenteServiceInstance); 
			testContaNumeroUtentiAmministratori(ruoloServiceInstance, utenteServiceInstance); 
			testListAllUtentiPasswordInferioreAdOtto(ruoloServiceInstance, utenteServiceInstance); 
			testEsisteUtenteDisabilitatoCheEAdmin(ruoloServiceInstance, utenteServiceInstance);
//
//			// Ruolo
			testListAllDescrizioniDistinteAssociatiAdUtenti(ruoloServiceInstance, utenteServiceInstance);

		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			// questa è necessaria per chiudere tutte le connessioni quindi rilasciare il
			// main
			EntityManagerUtil.shutdown();
		}

	}

	private static void testListAllDescrizioniDistinteAssociatiAdUtenti(RuoloService ruoloServiceInstance,
			UtenteService utenteServiceInstance) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(".......testListAllDescrizioniDistinteAssociatiAdUtenti inizio.............");

		if (ruoloServiceInstance.listAllDescrizioniDistinteAssociatiAdUtenti().size() != 2)
			throw new RuntimeException("testListAllDescrizioniDistinteAssociatiAdUtenti: FAILED");

		System.out.println(".......testListAllDescrizioniDistinteAssociatiAdUtenti fine: PASSED.............");

	}

	private static void testEsisteUtenteDisabilitatoCheEAdmin(RuoloService ruoloServiceInstance,
			UtenteService utenteServiceInstance) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(".......testEsisteUtenteDisabilitatoCheEAdmin inizio.............");

		// Creazione Istance Utente con Ruolo
		Utente utenteDisabled = new Utente("disabled.user", "password123459", "user", "withrole", new Date());
		Ruolo ruoloAdministrator = ruoloServiceInstance.cercaPerDescrizioneECodice("Administrator", "ROLE_ADMIN");
		utenteServiceInstance.inserisciNuovo(utenteDisabled);
		utenteServiceInstance.aggiungiRuolo(utenteDisabled, ruoloAdministrator);
		
		utenteDisabled.setStato(StatoUtente.DISABILITATO);
		utenteServiceInstance.aggiorna(utenteDisabled);
		
		//Proper Test
		if(!utenteServiceInstance.esisteUtenteDisabilitatoCheEAdmin())
			throw new RuntimeException("testEsisteUtenteDisabilitatoCheEAdmin: FAILED");

	
		utenteServiceInstance.rimuoviRuoloDaUtente(utenteDisabled.getId(), ruoloAdministrator.getId());
		try {
			utenteServiceInstance.rimuovi(utenteDisabled.getId());
		} catch (UtenteConRuoliAssociatiException e) {
			e.printStackTrace();
		}
		
		System.out.println(".......testEsisteUtenteDisabilitatoCheEAdmin fine: PASSED.............");
	}

	private static void testListAllUtentiPasswordInferioreAdOtto(RuoloService ruoloServiceInstance,
			UtenteService utenteServiceInstance) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(".......testListAllUtentiPasswordInferioreAdOtto inizio.............");

		if (utenteServiceInstance.listAllUtentiPasswordInferioreAdOtto().size() != 4)
			throw new RuntimeException("testContaNumeroUtentiAmministratori: FAILED");

		System.out.println(".......testListAllUtentiPasswordInferioreAdOtto fine: PASSED.............");
	}

	private static void testContaNumeroUtentiAmministratori(RuoloService ruoloServiceInstance,
			UtenteService utenteServiceInstance) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(".......testContaNumeroUtentiAmministratori inizio.............");

		if (utenteServiceInstance.contaNumeroUtentiAmministratori() != 1)
			throw new RuntimeException("testContaNumeroUtentiAmministratori: FAILED");

		System.out.println(".......testContaNumeroUtentiAmministratori fine: PASSED.............");

	}

	private static void testListAllCreatiInMeseAnno(RuoloService ruoloServiceInstance,
			UtenteService utenteServiceInstance) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(".......testListAllCreatiInMeseAnno inizio.............");

		if (utenteServiceInstance.listAllCreatiInMeseAnno(new SimpleDateFormat("dd-MM-yyyy").parse("30-10-2022"))
				.size() != 4)
			throw new RuntimeException("testListAllCreatiInMeseAnno: FAILED");

		System.out.println(".......testListAllCreatiInMeseAnno fine: PASSED.............");
	}

	private static void testRimuoviUtente(RuoloService ruoloServiceInstance, UtenteService utenteServiceInstance)
			throws Exception {
		// TODO Auto-generated method stub

		System.out.println(".......testRimuoviUtente inizio.............");

		// Creazione Istance Utente con Ruolo
		Utente utenteConRuolo = new Utente("withrole.user", "password", "user", "withrole", new Date());
		Ruolo ruoloAdmin = ruoloServiceInstance.cercaPerDescrizioneECodice("Administrator", "ROLE_ADMIN");
		utenteServiceInstance.inserisciNuovo(utenteConRuolo);
		utenteServiceInstance.aggiungiRuolo(utenteConRuolo, ruoloAdmin);

		try {
			utenteServiceInstance.rimuovi(utenteConRuolo.getId());
		} catch (UtenteConRuoliAssociatiException e) {
			e.printStackTrace();
		}

		utenteServiceInstance.rimuoviRuoloDaUtente(utenteConRuolo.getId(), ruoloAdmin.getId());

		try {
			utenteServiceInstance.rimuovi(utenteConRuolo.getId());
		} catch (UtenteConRuoliAssociatiException e) {
			e.printStackTrace();
		}

		System.out.println(".......testRimuoviUtente fine: PASSED.............");

	}

	private static void testRimuoviRuolo(RuoloService ruoloServiceInstance, UtenteService utenteServiceInstance)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println(".......testRimuoviRuolo inizio.............");

		// Creazione Istance Utente con Ruolo
		Utente utenteConRuolo = new Utente("withrole.user", "password", "user", "withrole", new Date());
		Ruolo ruoloConUtente = new Ruolo("Master Admin", "MASTER_ADMIN");
		utenteServiceInstance.inserisciNuovo(utenteConRuolo);
		ruoloServiceInstance.inserisciNuovo(ruoloConUtente);
		utenteServiceInstance.aggiungiRuolo(utenteConRuolo, ruoloConUtente);

		try {
			ruoloServiceInstance.rimuovi(ruoloConUtente.getId());
		} catch (UtenteConRuoliAssociatiException e) {
			e.printStackTrace();
		}

		utenteServiceInstance.rimuoviRuoloDaUtente(utenteConRuolo.getId(), ruoloConUtente.getId());

		try {
			utenteServiceInstance.rimuovi(utenteConRuolo.getId());
		} catch (UtenteConRuoliAssociatiException e) {
			e.printStackTrace();
		}

		try {
			ruoloServiceInstance.rimuovi(ruoloConUtente.getId());
		} catch (UtenteConRuoliAssociatiException e) {
			e.printStackTrace();
		}

		System.out.println(".......testRimuoviRuolo fine: PASSED.............");

	}

	private static void testAggiornaRuolo(RuoloService ruoloServiceInstance, UtenteService utenteServiceInstance)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println(".......testRimuoviUtente inizio.............");

		// Creazione Istance Utente con Ruolo
		Utente testUtente = new Utente("withrole.user", "password", "user", "withrole", new Date());
		Ruolo testRuolo = new Ruolo("Master User", "MASTER_USER");
		ruoloServiceInstance.inserisciNuovo(testRuolo);
		utenteServiceInstance.inserisciNuovo(testUtente);
		utenteServiceInstance.aggiungiRuolo(testUtente, testRuolo);

		// Rimuovi
		try {
			ruoloServiceInstance.rimuovi(testRuolo.getId());
		} catch (UtenteConRuoliAssociatiException e) {
			e.printStackTrace();
		}

		utenteServiceInstance.rimuoviRuoloDaUtente(testUtente.getId(), testRuolo.getId());

		try {
			ruoloServiceInstance.rimuovi(testRuolo.getId());
		} catch (UtenteConRuoliAssociatiException e) {
			e.printStackTrace();
		}

		try {
			utenteServiceInstance.rimuovi(testUtente.getId());
		} catch (UtenteConRuoliAssociatiException e) {
			e.printStackTrace();
		}

		System.out.println(".......testRimuoviUtente fine: PASSED.............");

	}

	private static void testListAllRuolo(RuoloService ruoloServiceInstance, UtenteService utenteServiceInstance)
			throws Exception {
		// TODO Auto-generated method stub

	}

	private static void initRuoli(RuoloService ruoloServiceInstance) throws Exception {
		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Administrator", "ROLE_ADMIN") == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Administrator", "ROLE_ADMIN"));
		}

		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Classic User", "ROLE_CLASSIC_USER") == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Classic User", "ROLE_CLASSIC_USER"));
		}
	}

	private static void testInserisciNuovoUtente(UtenteService utenteServiceInstance) throws Exception {
		System.out.println(".......testInserisciNuovoUtente inizio.............");

		Utente utenteNuovo = new Utente("pippo.rossi", "xxx", "pippo", "rossi", new Date());
		utenteServiceInstance.inserisciNuovo(utenteNuovo);
		if (utenteNuovo.getId() == null)
			throw new RuntimeException("testInserisciNuovoUtente fallito ");

		System.out.println(".......testInserisciNuovoUtente fine: PASSED.............");
	}

	private static void testCollegaUtenteARuoloEsistente(RuoloService ruoloServiceInstance,
			UtenteService utenteServiceInstance) throws Exception {
		System.out.println(".......testCollegaUtenteARuoloEsistente inizio.............");

		Ruolo ruoloEsistenteSuDb = ruoloServiceInstance.cercaPerDescrizioneECodice("Administrator", "ROLE_ADMIN");
		if (ruoloEsistenteSuDb == null)
			throw new RuntimeException("testCollegaUtenteARuoloEsistente fallito: ruolo inesistente ");

		// mi creo un utente inserendolo direttamente su db
		Utente utenteNuovo = new Utente("mario.bianchi", "JJJ", "mario", "bianchi", new Date());
		utenteServiceInstance.inserisciNuovo(utenteNuovo);
		if (utenteNuovo.getId() == null)
			throw new RuntimeException("testInserisciNuovoUtente fallito: utente non inserito ");

		utenteServiceInstance.aggiungiRuolo(utenteNuovo, ruoloEsistenteSuDb);
		// per fare il test ricarico interamente l'oggetto e la relazione
		Utente utenteReloaded = utenteServiceInstance.caricaUtenteSingoloConRuoli(utenteNuovo.getId());
		if (utenteReloaded.getRuoli().size() != 1)
			throw new RuntimeException("testInserisciNuovoUtente fallito: ruoli non aggiunti ");

		System.out.println(".......testCollegaUtenteARuoloEsistente fine: PASSED.............");
	}

	private static void testModificaStatoUtente(UtenteService utenteServiceInstance) throws Exception {
		System.out.println(".......testModificaStatoUtente inizio.............");

		// mi creo un utente inserendolo direttamente su db
		Utente utenteNuovo = new Utente("mario1.bianchi1", "JJJ", "mario1", "bianchi1", new Date());
		utenteServiceInstance.inserisciNuovo(utenteNuovo);
		if (utenteNuovo.getId() == null)
			throw new RuntimeException("testModificaStatoUtente fallito: utente non inserito ");

		// proviamo a passarlo nello stato ATTIVO ma salviamoci il vecchio stato
		StatoUtente vecchioStato = utenteNuovo.getStato();
		utenteNuovo.setStato(StatoUtente.ATTIVO);
		utenteServiceInstance.aggiorna(utenteNuovo);

		if (utenteNuovo.getStato().equals(vecchioStato))
			throw new RuntimeException("testModificaStatoUtente fallito: modifica non avvenuta correttamente ");

		System.out.println(".......testModificaStatoUtente fine: PASSED.............");
	}

	private static void testRimuoviRuoloDaUtente(RuoloService ruoloServiceInstance, UtenteService utenteServiceInstance)
			throws Exception {
		System.out.println(".......testRimuoviRuoloDaUtente inizio.............");

		// carico un ruolo e lo associo ad un nuovo utente
		Ruolo ruoloEsistenteSuDb = ruoloServiceInstance.cercaPerDescrizioneECodice("Administrator", "ROLE_ADMIN");
		if (ruoloEsistenteSuDb == null)
			throw new RuntimeException("testRimuoviRuoloDaUtente fallito: ruolo inesistente ");

		// mi creo un utente inserendolo direttamente su db
		Utente utenteNuovo = new Utente("aldo.manuzzi", "pwd@2", "aldo", "manuzzi", new Date());
		utenteServiceInstance.inserisciNuovo(utenteNuovo);
		if (utenteNuovo.getId() == null)
			throw new RuntimeException("testRimuoviRuoloDaUtente fallito: utente non inserito ");
		utenteServiceInstance.aggiungiRuolo(utenteNuovo, ruoloEsistenteSuDb);

		// ora ricarico il record e provo a disassociare il ruolo
		Utente utenteReloaded = utenteServiceInstance.caricaUtenteSingoloConRuoli(utenteNuovo.getId());
		boolean confermoRuoloPresente = false;
		for (Ruolo ruoloItem : utenteReloaded.getRuoli()) {
			if (ruoloItem.getCodice().equals(ruoloEsistenteSuDb.getCodice())) {
				confermoRuoloPresente = true;
				break;
			}
		}

		if (!confermoRuoloPresente)
			throw new RuntimeException("testRimuoviRuoloDaUtente fallito: utente e ruolo non associati ");

		// ora provo la rimozione vera e propria ma poi forzo il caricamento per fare un
		// confronto 'pulito'
		utenteServiceInstance.rimuoviRuoloDaUtente(utenteReloaded.getId(), ruoloEsistenteSuDb.getId());
		utenteReloaded = utenteServiceInstance.caricaUtenteSingoloConRuoli(utenteNuovo.getId());
		if (!utenteReloaded.getRuoli().isEmpty())
			throw new RuntimeException("testRimuoviRuoloDaUtente fallito: ruolo ancora associato ");

		System.out.println(".......testRimuoviRuoloDaUtente fine: PASSED.............");
	}

}
