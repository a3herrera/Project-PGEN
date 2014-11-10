package com.app.entity.transactions;

import com.app.entity.enums.TipoTransaccion;

import java.io.Serializable;
import java.lang.String;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: TransaccionE
 *
 */
@Entity
@Table(name="TRANSACCIONES")
@SequenceGenerator(name = "SEQ_TRANSACCIONES", sequenceName = "SEQ_TRANSACCIONES", allocationSize = 1, initialValue = 1)
public class TransaccionE implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "NO_TRANSACCION")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TRANSACCIONES")
	private long transaccion;

	@Enumerated(EnumType.STRING)
	@Column(name = "TIPO", nullable = false, length = 20)
	private TipoTransaccion tipo;
	
	@Column(name = "MONTO", precision = 18, scale = 2, nullable = false)
	private double monto;
	
	@Column(name = "FUENTE", length = 75)
	private String fuente;
	@Column(name = "NO_DOCUMENTO", length = 75)
	private String documentoNo;
	
	@Column(name = "ID_CUENTA")
	private long idCuenta;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_SOLICITUD")
	private Date fechaSolicitud;

	public TransaccionE() {
		super();
	}   
	public long getTransaccion() {
		return this.transaccion;
	}

	public void setTransaccion(long transaccion) {
		this.transaccion = transaccion;
	}   
	public TipoTransaccion getTipo() {
		return this.tipo;
	}

	public void setTipo(TipoTransaccion tipo) {
		this.tipo = tipo;
	}   
	public double getMonto() {
		return this.monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}   
	public String getFuente() {
		return this.fuente;
	}

	public void setFuente(String fuente) {
		this.fuente = fuente;
	}   
	public String getDocumentoNo() {
		return this.documentoNo;
	}

	public void setDocumentoNo(String documentoNo) {
		this.documentoNo = documentoNo;
	}   
	public long getIdCuenta() {
		return this.idCuenta;
	}

	public void setIdCuenta(long idCuenta) {
		this.idCuenta = idCuenta;
	}   
	public Date getFechaSolicitud() {
		return this.fechaSolicitud;
	}

	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}
   
}
