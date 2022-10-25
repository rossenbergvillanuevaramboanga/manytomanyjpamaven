package it.manytomanyjpamaven.service;

import java.util.List;

import javax.persistence.EntityManager;

import it.manytomanyjpamaven.dao.EntityManagerUtil;
import it.manytomanyjpamaven.dao.RuoloDAO;
import it.manytomanyjpamaven.dao.UtenteDAO;
import it.manytomanyjpamaven.exception.RuoloConUtentiAssociatiException;
import it.manytomanyjpamaven.exception.UtenteConRuoliAssociatiException;
import it.manytomanyjpamaven.model.Ruolo;
import it.manytomanyjpamaven.model.Utente;

public class RuoloServiceImpl implements RuoloService {

	private RuoloDAO ruoloDAO;
	
	//Prova
	private UtenteDAO utenteDAO;

	@Override
	public List<Ruolo> listAll() throws Exception {
		// TODO Auto-generated method stub
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// uso l'injection per il dao
			ruoloDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			return ruoloDAO.list();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public Ruolo caricaSingoloElemento(Long id) throws Exception {
		// questo è come una connection
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// uso l'injection per il dao
			ruoloDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			return ruoloDAO.get(id);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public void aggiorna(Ruolo ruoloInstance) throws Exception {
		// TODO Auto-generated method stub
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// uso l'injection per il dao
			entityManager.getTransaction().begin();
			ruoloDAO.setEntityManager(entityManager);
			ruoloDAO.update(ruoloInstance);
			entityManager.getTransaction().commit();

		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}

	}

	@Override
	public void inserisciNuovo(Ruolo ruoloInstance) throws Exception {
		// questo è come una connection
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// questo è come il MyConnection.getConnection()
			entityManager.getTransaction().begin();

			// uso l'injection per il dao
			ruoloDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			ruoloDAO.insert(ruoloInstance);

			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		}

	}

	@Override
	public void rimuovi(Long idRuoloToRemove) throws Exception {
		// TODO Auto-generated method stub
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// uso l'injection per il dao
			entityManager.getTransaction().begin();
			
			ruoloDAO.setEntityManager(entityManager);
			utenteDAO.setEntityManager(entityManager);
			
			//Eliminazione
//			if(!utenteDAO.findAllByRuolo(ruoloDAO.get(idRuoloToRemove)).isEmpty()) 
//				throw new RuoloConUtentiAssociatiException("Il ruolo che stai eliminando ha un utente associato");
			for (Utente utente : utenteDAO.findAllByRuolo(ruoloDAO.get(idRuoloToRemove))) {
				if(!utenteDAO.findByIdFetchingRuoli(utente.getId()).getRuoli().isEmpty()) 
					throw new RuoloConUtentiAssociatiException("Il ruolo che stai cercando di eliminare ha un utente.");
			}
			
			//Delete
			ruoloDAO.delete(ruoloDAO.get(idRuoloToRemove));
			
			entityManager.getTransaction().commit();

		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}

	}

	@Override
	public void setRuoloDAO(RuoloDAO ruoloDAO) {
		this.ruoloDAO = ruoloDAO;
	}
	
	
	public void setUtenteDAO(UtenteDAO utenteDAO) {
		this.utenteDAO = utenteDAO;
	}

	@Override
	public Ruolo cercaPerDescrizioneECodice(String descrizione, String codice) throws Exception {
		// questo è come una connection
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// uso l'injection per il dao
			ruoloDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			return ruoloDAO.findByDescrizioneAndCodice(descrizione, codice);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public List<String> listAllDescrizioniDistinteAssociatiAdUtenti() throws Exception {
		// TODO Auto-generated method stub
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// uso l'injection per il dao
			ruoloDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			return ruoloDAO.listAllDistinctDescriptionOfUsers();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

}
