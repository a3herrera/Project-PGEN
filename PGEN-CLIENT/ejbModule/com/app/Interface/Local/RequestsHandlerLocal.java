package com.app.Interface.Local;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.app.entity.transactions.CreditosE;
import com.app.entity.transactions.DebitosE;

public interface RequestsHandlerLocal extends Serializable {

	long debit(long idAccount, double amount, String noDocument);

	long credit(long idAccount, double amount, String noDocument);

	List<DebitosE> getDebits(long idAccount, Date since, Date until);

	List<CreditosE> getCredist(long idAccount, Date since, Date until);

	
}
