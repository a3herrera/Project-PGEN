package com.app.entity.transactions;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class EjecucionesE implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long ID;
	private EjecutoresE ejecutor;
	private Date fechaInicio;
	private Date fechaFin;
	private int totalSolicitudes;

	private List<SolicitudesE> solicitudes;

}
