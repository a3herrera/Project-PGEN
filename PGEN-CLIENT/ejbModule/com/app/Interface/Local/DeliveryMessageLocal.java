package com.app.Interface.Local;

import javax.jms.JMSException;

public interface DeliveryMessageLocal {

	void sendMessage(long idTransaction) throws JMSException;
	
}
