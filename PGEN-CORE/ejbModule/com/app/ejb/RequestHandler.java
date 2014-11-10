package com.app.ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jms.JMSException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import com.app.Interface.Local.BaseFacadeLocal;
import com.app.Interface.Local.DeliveryMessageLocal;
import com.app.Interface.Local.RequestsHandlerLocal;
import com.app.entity.enums.TipoTransaccion;
import com.app.entity.transactions.CreditosE;
import com.app.entity.transactions.DebitosE;
import com.app.entity.transactions.TransaccionE;
import com.app.utils.ConstantsEntity;

/**
 * Session Bean implementation class RequestHandler
 */
@Local(RequestsHandlerLocal.class)
@Stateless(mappedName = "ejb/requestHandler")
@TransactionManagement(TransactionManagementType.BEAN)
public class RequestHandler implements RequestsHandlerLocal {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public RequestHandler() {
		// TODO Auto-generated constructor stub
	}

	@EJB
	private DeliveryMessageLocal deliverMsg;

	@EJB
	private BaseFacadeLocal<TransaccionE> transaction;

	@PersistenceUnit(unitName = ConstantsEntity.persistencesName, name = ConstantsEntity.persistencesName)
	private EntityManagerFactory emf;

	/**
	 * @see RequestsHandlerLocal#debit(long, double, String)
	 */
	public long debit(long idAccount, double amount, String noDocument) {
		// TODO Auto-generated method stub
		TransaccionE trans = new TransaccionE();
		trans.setIdCuenta(idAccount);
		trans.setMonto(amount);
		trans.setDocumentoNo(noDocument);
		trans.setFechaSolicitud(new Date());
		trans.setTipo(TipoTransaccion.Debito);
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			try {
				trans = transaction.createE(trans, em);
				em.getTransaction().commit();
			} catch (Exception e) {
				em.getTransaction().rollback();
			}
		} finally {
			em.close();
		}

		if (trans != null && trans.getTransaccion() > 0) {
			try {
				deliverMsg.sendMessage(trans.getTransaccion());
			} catch (JMSException E) {

			}
		}
		return trans.getTransaccion();
	}

	/**
	 * @see RequestsHandlerLocal#credit(long, double, String)
	 */
	public long credit(long idAccount, double amount, String noDocument) {
		TransaccionE trans = new TransaccionE();
		trans.setIdCuenta(idAccount);
		trans.setMonto(amount);
		trans.setDocumentoNo(noDocument);
		trans.setFechaSolicitud(new Date());
		trans.setTipo(TipoTransaccion.Credito);
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			try {
				trans = transaction.createE(trans, em);
				em.getTransaction().commit();
			} catch (Exception e) {
				em.getTransaction().rollback();
			}
		} finally {
			em.close();
		}

		if (trans != null && trans.getTransaccion() > 0) {
			try {
				deliverMsg.sendMessage(trans.getTransaccion());
			} catch (JMSException E) {

			}
		}
		return trans.getTransaccion();
	}

	private Map<String, Object> params;
	
	private static final String QL_DEBITOS = "SELECT e from DebitosE e where e.idCuenta = :idCuenta and e.FechaSolicitud between :since and :until";
	private static final String QL_CREDITOS = "SELECT e from CreditosE e where e.idCuenta = :idCuenta and e.FechaSolicitud between :since and :until";
	
	/**
	 * @see RequestsHandlerLocal#getDebits(long, Date, Date)
	 */
	public List<DebitosE> getDebits(long idAccount, Date since, Date until) {
		// TODO Auto-generated method stub
		EntityManager em = emf.createEntityManager();
		List<DebitosE> debitos = new ArrayList<DebitosE>();
		Date fechaActual = new Date();
		try{
			if((since.after(fechaActual) || since.equals(fechaActual)) &&
				(until.before(since) )){
				
			}
		}finally{
			em.close();
		}
		return debitos;
	}

	/**
	 * @see RequestsHandlerLocal#getCredist(long, Date, Date)
	 */
	public List<CreditosE> getCredist(long idAccount, Date since, Date until) {
		// TODO Auto-generated method stub
		return null;
	}

}
