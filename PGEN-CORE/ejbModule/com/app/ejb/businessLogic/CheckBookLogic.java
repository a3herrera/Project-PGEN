package com.app.ejb.businessLogic;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;

import com.app.bussiness.CheckBookBusinessLogicLocal;
import com.app.entity.banks.ChequeE;
import com.app.entity.banks.ChequeraE;
import com.app.entity.enums.EstadoCheque;
import com.app.entity.enums.EstadoChequera;

@Local(CheckBookBusinessLogicLocal.class)
@Stateless(mappedName = "ejb/CheckBookLogic")
public class CheckBookLogic implements CheckBookBusinessLogicLocal {

	@Override
	public EstadoCheque getNewStateCheck(EstadoChequera stateCheckBook,
			EstadoCheque stateCheck) {
		EstadoCheque newState = null;
		switch (stateCheckBook) {
		case NUEVA:
			newState = null;
			break;
		case LIBERADA:
			newState = EstadoCheque.GENERADO;
			break;
		case HABILITIDA:
			if(stateCheck == EstadoCheque.GENERADO || stateCheck == EstadoCheque.CONGELADO){
				newState = EstadoCheque.DISPONIBLE;	
			}
			else{
				newState= stateCheck;
			}
			break;
		case INHABILITADA:
			newState= stateCheck;
			break;
		case CONGELADA:
			if(stateCheck == EstadoCheque.GENERADO || stateCheck==EstadoCheque.DISPONIBLE){
				newState = EstadoCheque.CONGELADO;
			} else if(stateCheck == EstadoCheque.EMITIDO){
				newState = EstadoCheque.RETENIDO;
			}
			break;
		case CANCELADA:
			newState = EstadoCheque.CANCELADO;
			break;

		}
		return newState;
	}

	@Override
	public EstadoChequera getNewStateCheckBook() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ChequeE> createChecksList(EstadoChequera state, ChequeraE checkBook ) {
		EstadoCheque newState = EstadoCheque.GENERADO;
		switch (state) {
		case NUEVA:
			state = null;
			break;
		case LIBERADA:
			newState = EstadoCheque.GENERADO;
			break;
		case HABILITIDA:
			newState = EstadoCheque.DISPONIBLE;
			break;
		case INHABILITADA:
			newState = EstadoCheque.CANCELADO;
			break;
		case CONGELADA:
			newState = EstadoCheque.CONGELADO;
			break;
		case CANCELADA:
			newState = EstadoCheque.CANCELADO;
			break;

		}
		List<ChequeE> cheques = new ArrayList<ChequeE>();
		long initValue = checkBook.getNumeroInicial();
		long number = checkBook.getTotalCheques();
		if (number > 0 && state != null) {
			for (long x = 1; x <= number; x++) {
				cheques.add(new ChequeE(initValue, checkBook, newState));
				initValue++;
			}
		}
		return cheques;
	}
	
	

}
