package com.app.bussiness;

import java.util.List;

import com.app.entity.banks.ChequeE;
import com.app.entity.banks.ChequeraE;
import com.app.entity.enums.EstadoCheque;
import com.app.entity.enums.EstadoChequera;

public interface CheckBookBusinessLogicLocal {

	EstadoCheque getNewStateCheck(EstadoChequera stateCheckBook,
			EstadoCheque stateCheck);

	EstadoChequera getNewStateCheckBook();

	List<ChequeE> createChecksList(EstadoChequera state, ChequeraE checkBook );
	
	
}
