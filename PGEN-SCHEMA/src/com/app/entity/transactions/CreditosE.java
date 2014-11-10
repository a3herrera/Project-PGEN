package com.app.entity.transactions;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "CREDITOS")
@SequenceGenerator(name = "SEQ_CREDITOS", sequenceName = "SEQ_CREDITOS", allocationSize = 1, initialValue = 1)
public class CreditosE implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CREDITOS")
	private long id;

	@Column(name = "NO_TRANSACCION")
	private long transaccion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SOLICITUD", nullable = false)
	private Date FechaSolicitud;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EJECUCION")
	private Date FechaAtencion;

	@Column(name = "CUENTA_NO", nullable = false)
	private long idCuenta;

	@Column(name = "MONTO", precision = 18, scale = 2)
	private double monto;

	@Column(name = "NO_DOCUMENTO", nullable = false)
	private String documentoNo;

	@Column(name = "MENSAJE")
	private String resultMsg;

	@Column(name = "ESTADO", nullable = false)
	private boolean estado;

	@Column(name = "FUENTE", length = 75)
	private String fuente;

	public long getTransaccion() {
		return transaccion;
	}

	public void setTransaccion(long transaccion) {
		this.transaccion = transaccion;
	}

	public Date getFechaSolicitud() {
		return FechaSolicitud;
	}

	public void setFechaSolicitud(Date fechaSolicitud) {
		FechaSolicitud = fechaSolicitud;
	}

	public Date getFechaAtencion() {
		return FechaAtencion;
	}

	public void setFechaAtencion(Date fechaAtencion) {
		FechaAtencion = fechaAtencion;
	}

	public long getIdCuenta() {
		return idCuenta;
	}

	public void setIdCuenta(long idCuenta) {
		this.idCuenta = idCuenta;
	}

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

	public String getDocumentoNo() {
		return documentoNo;
	}

	public void setDocumentoNo(String documentoNo) {
		this.documentoNo = documentoNo;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFuente() {
		return fuente;
	}

	public void setFuente(String fuente) {
		this.fuente = fuente;
	}

}
