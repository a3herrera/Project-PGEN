package com.app.ejb;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import com.app.Interface.Local.DeliveryMessageLocal;

/**
 * Session Bean implementation class DeliveryMessage
 */
@Local(DeliveryMessageLocal.class)
@Stateless(mappedName = "ejb/deliveryMessage")
@TransactionManagement(TransactionManagementType.BEAN)
public class DeliveryMessage implements DeliveryMessageLocal {

	/**
	 * Default constructor.
	 */
	public DeliveryMessage() {
		// TODO Auto-generated constructor stub
	}

	@Resource(name = "jms/ConnectionFactory", mappedName = "jms/ConnectionFactory")
	private ConnectionFactory connectionFactory;

	@Resource(name = "transactions", mappedName = "transactions")
	private Destination destination;

	/**
	 * @see DeliveryMessageLocal#sendMessage(long)
	 */
	public void sendMessage(long idTransaction) throws JMSException {
		// TODO Auto-generated method stub
		Connection jmsConnection = connectionFactory.createConnection();
		try {
			Session session = jmsConnection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			try {
				MessageProducer msgProducer = session
						.createProducer(destination);
				try {
					Message jmsMessage = session.createMessage();
					jmsMessage.setLongProperty("ID", idTransaction);
					msgProducer.send(jmsMessage);
				} finally {
					msgProducer.close();
				}
			} finally {
				session.close();
			}
		} finally {
			jmsConnection.close();
		}
	}

}
