package com.app.mdb;

import java.util.Date;

import javax.ejb.MessageDriven;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import com.app.entity.enums.TipoTransaccion;
import com.app.entity.transactions.CreditosE;
import com.app.entity.transactions.DebitosE;
import com.app.entity.transactions.TransaccionE;
import com.app.utils.ConstantsEntity;

/**
 * Message-Driven Bean implementation class for: MessageWorker
 */
@MessageDriven(mappedName = "transactions", name = "transactions")
@TransactionManagement(TransactionManagementType.BEAN)
public class MessageWorker implements MessageListener {

    /**
     * Default constructor. 
     */
    public MessageWorker() {
        // TODO Auto-generated constructor stub
    }
    
    @PersistenceUnit(unitName =  ConstantsEntity.persistencesName, name =  ConstantsEntity.persistencesName)
    private EntityManagerFactory emf;
    
    
    private EntityManager em;
    
	/**
     * @see MessageListener#onMessage(Message) 
     */
	public void onMessage(Message message) {
		System.out.println("INGRESANDO MENSAJE");
		em = emf.createEntityManager();
		try {
			long id = message.getLongProperty("ID");
			TransaccionE trs = em.find(TransaccionE.class, id);
			transactionHandling(trs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
	}

	
	
	private void transactionHandling(TransaccionE transaction) {
		boolean isOk = false;
		if (transaction != null) {

			if (transaction.getTipo() == TipoTransaccion.Credito) {
				createCredit(transaction);
			}
			if (transaction.getTipo() == TipoTransaccion.Debito) {
				createDebit(transaction);
			}

		}

		if (isOk) {
			// removeRequests(transaction.getTransaccion());
		}
	}
    
    private void createDebit(TransaccionE transaction){
    	DebitosE debito = new DebitosE();
		debito.setDocumentoNo(transaction.getDocumentoNo());
		debito.setEstado(false);
		debito.setFechaAtencion(new Date());
		debito.setFechaSolicitud(transaction.getFechaSolicitud());
		debito.setIdCuenta(transaction.getIdCuenta());
		debito.setMonto(transaction.getMonto());
		debito.setTransaccion(transaction.getTransaccion());
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			try {
				em.persist(debito);
				em.getTransaction().commit();
			} catch (RuntimeException e) {
				em.getTransaction().rollback();
			}

		} finally {
			em.close();
		}
    }
    
    private void createCredit(TransaccionE transaction){
    	CreditosE credit = new CreditosE();
    	credit.setDocumentoNo(transaction.getDocumentoNo());
    	credit.setEstado(false);
    	credit.setFechaAtencion(new Date());
    	credit.setFechaSolicitud(transaction.getFechaSolicitud());
    	credit.setIdCuenta(transaction.getIdCuenta());
    	credit.setMonto(transaction.getMonto());
    	credit.setTransaccion(transaction.getTransaccion());
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			try {
				em.persist(credit);
				em.getTransaction().commit();
			} catch (RuntimeException e) {
				em.getTransaction().rollback();
			}

		} finally {
			em.close();
		}
    }
    
    @SuppressWarnings("unused")
	private void removeRequests(long idRequests){
    	em = emf.createEntityManager();
    	try{
    		em.getTransaction().begin();
    		try{
    			em.remove(em.find(TransaccionE.class, idRequests));
    			em.getTransaction().commit();
    		}catch(RuntimeException e){
    			em.getTransaction().rollback();
    		}
    	}finally{
    		em.close();
    	}
    }

}
