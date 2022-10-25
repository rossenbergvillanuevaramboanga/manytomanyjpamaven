package it.manytomanyjpamaven.dao;

import java.util.Date;
import java.util.List;

import it.manytomanyjpamaven.model.Ruolo;
import it.manytomanyjpamaven.model.Utente;

public interface UtenteDAO extends IBaseDAO<Utente> {
	
	public List<Utente> findAllByRuolo(Ruolo ruoloInput);
	public Utente findByIdFetchingRuoli(Long id);
	
	public List<Utente> listAllUsersCreatedInMonthYear(Date data);
	public int countNumberOfUsersAdministrator();
	public List<Utente> listAllUsersWithPasswordLengthUnderEight();
	public boolean existsUserAdminDisabled();

}
