package com.app.ejb;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import com.app.Interface.Local.BaseFacadeLocal;
import com.app.Interface.Local.RequestsHandlerLocal;
import com.app.Interface.Local.ServiceLocal;
import com.app.entity.banks.CuentaE;
import com.app.entity.transactions.CreditosE;
import com.app.entity.transactions.DebitosE;
import com.app.utils.ConstantsEntity;

/**
 * Session Bean implementation class TransactionsService
 */
@Local(ServiceLocal.class)
@Stateless(mappedName = "ejb/ServicesLocal")
@WebService(serviceName="transactionsServices")
@TransactionManagement(TransactionManagementType.BEAN)
public class TransactionsService implements ServiceLocal {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public TransactionsService() {
		// TODO Auto-generated constructor stub
	}

	@EJB
	private RequestsHandlerLocal request;

	@EJB
	private BaseFacadeLocal<CuentaE> cuentas;
	
	@PersistenceUnit(unitName = ConstantsEntity.persistencesName, name = ConstantsEntity.persistencesName)
	private EntityManagerFactory emf;
	
	
	/**
	 * @see RequestsHandlerLocal#debit(long, double, String)
	 */
	@WebMethod(operationName = "DEBITS")
	public long debit(@WebParam(name = "ACCOUNT_ID") long idAccount,
			@WebParam(name = "AMOUNT") double amount,
			@WebParam(name = "NO_DOCTO") String noDocument) {
		// TODO Auto-generated method stub
		EntityManager em = emf.createEntityManager();
		try {
			int account = cuentas.countE(
					"SELECT COUNT(e) from CuentaE e where e.ID = " + idAccount,
					em);
			if (account == 0) {
				throw new IllegalArgumentException(
						"El identificador de la cuenta no es valido");
			}
		} catch (Exception e) {
			throw new IllegalArgumentException(
					"El identificador de la cuenta no es valido");
		} finally {
			em.close();
		}
		
		if(amount < 0){
			throw new IllegalArgumentException(
					"El monto no es aceptado");
		}
		long idrequest = request.debit(idAccount, amount, noDocument);
		return idrequest;
	}
	
	/**
	 * @see RequestsHandlerLocal#credit(long, double, String)
	 */
	@WebMethod(operationName = "CREDITS")
	public long credit(@WebParam(name = "ACCOUNT_ID") long idAccount,
			@WebParam(name = "AMOUNT") double amount,
			@WebParam(name = "NO_DOCTO") String noDocument) {
		// TODO Auto-generated method stub
		EntityManager em = emf.createEntityManager();
		try {
			int account = cuentas.countE(
					"SELECT COUNT(e) from CuentaE e where e.ID = " + idAccount,
					em);
			if (account == 0) {
				throw new IllegalArgumentException(
						"El identificador de la cuenta no es valido");
			}
		} catch (Exception e) {
			throw new IllegalArgumentException(
					"El identificador de la cuenta no es valido");
		} finally {
			em.close();
		}
		
		if(amount < 0){
			throw new IllegalArgumentException(
					"El monto no es aceptado");
		}
		long idrequest = request.credit(idAccount, amount, noDocument);
		return idrequest;
	}

	/**
	 * @see RequestsHandlerLocal#getDebits(long, Date, Date)
	 */
	public List<DebitosE> getDebits(long idAccount, Date since, Date until) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see RequestsHandlerLocal#getCredist(long, Date, Date)
	 */
	public List<CreditosE> getCredist(long idAccount, Date since, Date until) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
