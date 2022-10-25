package it.manytomanyjpamaven.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import it.manytomanyjpamaven.model.Ruolo;
import it.manytomanyjpamaven.model.Utente;

public class UtenteDAOImpl implements UtenteDAO {

	private EntityManager entityManager;

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<Utente> list() throws Exception {
		// dopo la from bisogna specificare il nome dell'oggetto (lettera maiuscola) e
		// non la tabella
		return entityManager.createQuery("from Utente",Utente.class).getResultList();
	}

	@Override
	public Utente get(Long id) throws Exception {
		return entityManager.find(Utente.class, id);
	}

	@Override
	public void update(Utente utenteInstance) throws Exception {
		if (utenteInstance == null) {
			throw new Exception("Problema valore in input");
		}
		utenteInstance = entityManager.merge(utenteInstance);
	}

	@Override
	public void insert(Utente utenteInstance) throws Exception {
		if (utenteInstance == null) {
			throw new Exception("Problema valore in input");
		}

		entityManager.persist(utenteInstance);
	}

	@Override
	public void delete(Utente utenteInstance) throws Exception {
		if (utenteInstance == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.remove(entityManager.merge(utenteInstance));
	}

	// questo metodo ci torna utile per capire se possiamo rimuovere un ruolo non
	// essendo collegato ad un utente
	public List<Utente> findAllByRuolo(Ruolo ruoloInput) {
		TypedQuery<Utente> query = entityManager.createQuery("select u FROM Utente u join u.ruoli r where r = :ruolo",Utente.class);
		query.setParameter("ruolo", ruoloInput);
		return query.getResultList();
	}

	@Override
	public Utente findByIdFetchingRuoli(Long id) {
		TypedQuery<Utente> query = entityManager.createQuery("select u FROM Utente u left join fetch u.ruoli r where u.id = :idUtente",Utente.class);
		query.setParameter("idUtente", id);
		return query.getResultList().stream().findFirst().orElse(null);
	}

	@Override
	public List<Utente> listAllUsersCreatedInMonthYear(Date data) {
		// TODO Auto-generated method stub
		TypedQuery<Utente> query = entityManager.createQuery("select u from Utente u where year(u.dateCreated) = year(:data) and month(u.dateCreated) = month(:data) ", Utente.class);
		query.setParameter("data", data);
		return query.getResultList();
	}

	@Override
	public int countNumberOfUsersAdministrator() {
		// TODO Auto-generated method stub
		TypedQuery<Long> query = entityManager.createQuery("select distinct count(u) FROM Utente u inner join u.ruoli r where r.descrizione = 'Administrator'", Long.class);
		return query.getSingleResult().intValue();
	}

	@Override
	public List<Utente> listAllUsersWithPasswordLengthUnderEight() {
		// TODO Auto-generated method stub
		TypedQuery<Utente> query = entityManager.createQuery("select u FROM Utente u where length(u.password) < 8 ", Utente.class);
		return query.getResultList();
	}

	@Override
	public boolean existsUserAdminDisabled() {
		// TODO Auto-generated method stub
		TypedQuery<Utente> query = entityManager.createQuery("select u FROM Utente u inner join fetch u.ruoli r where u.stato = 'DISABILITATO' and r.descrizione = 'Administrator'", Utente.class);
		return !query.getResultList().isEmpty();
	}

}
