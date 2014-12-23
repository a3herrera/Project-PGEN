package com.app.entity.transactions;

import com.app.entity.banks.ChequeE;
import com.app.entity.enums.EstadoSolicitud;
import com.app.entity.history.AuditedEntity;

/**
 * Entity implementation class for Entity: SolicitudesE
 *
 */
//@Entity
public class SolicitudesE extends AuditedEntity {

	private static final long serialVersionUID = 1L;

	public SolicitudesE() {
		super();
	}

//	@Id
	private long ID;
	private ChequeE chequeID;

	private EstadoSolicitud estado;

	private TransaccionE transaccion;

	
}
