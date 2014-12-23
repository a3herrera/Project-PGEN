package com.app.entity.banks;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.eclipse.persistence.annotations.Customizer;

import com.app.entity.enums.EstadoCheque;
import com.app.entity.history.AuditedEntity;
import com.app.entity.history.ChequesHistory;
import com.app.entity.providers.ComprasPrvE;
import com.app.utils.ConstantsEntity;

@Entity
@Table(name = "CHEQUES")
@Customizer(ChequesHistory.class)
@SequenceGenerator(name = ConstantsEntity.CHEQUE_SEQUENCE_NAME, sequenceName = ConstantsEntity.CHEQUE_SEQUENCE_NAME, allocationSize = ConstantsEntity.chequeraSecuenciaAllocation, initialValue = ConstantsEntity.chequeraSecuenciaInit)
public class ChequeE extends AuditedEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_CHEQUE")
	@GeneratedValue(generator = ConstantsEntity.CHEQUE_SEQUENCE_NAME, strategy = GenerationType.SEQUENCE)
	private long ID;

	@Column(name = "NO_CHEQUE", updatable = false, length = ConstantsEntity.CHEQUE_NO_SIZE)
	private long numero;

	@ManyToOne
	@JoinColumn(name = "ID_CHEQUERA", nullable = false, updatable = false)
	private ChequeraE chequeraID;

	@Column(name = "MONTO", precision = ConstantsEntity.precision, scale = ConstantsEntity.escala)
	private double monto;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_EMISION", nullable = true, updatable = false)
	private Date fechaEmision;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_EJECUCION", nullable = true, insertable = false, updatable = true)
	private Date fechaEjecucion;

	@Enumerated(EnumType.STRING)
	@Column(name = "ESTADO", nullable = false, length = ConstantsEntity.CHEQUE_STATE_SIZE)
	private EstadoCheque estado;

	@Column(name = "SOBRE_SALDO", nullable = true)
	private boolean sobreSaldo;

	@ManyToOne
	@JoinColumn(name = "ID_COMPRA", nullable = true, insertable = true)
	private ComprasPrvE compraID;

	/**
	 * Contructor de la clase
	 */
	public ChequeE() {
	}

	/**
	 * <p>
	 * Constructor, para cuando se va a estar generando el cheque, y ligandose a
	 * una chequera
	 * </p>
	 * 
	 * @param numero
	 * @param chequera
	 */
	public ChequeE(long numero, ChequeraE chequera, EstadoCheque estado) {
		// TODO Auto-generated constructor stub
		setNumero(numero);
		setChequeraID(chequera);
		setEstado(EstadoCheque.GENERADO);
	}

	public long getID() {
		return ID;
	}

	public void setID(long iD) {
		ID = iD;
	}

	public long getNumero() {
		return numero;
	}

	public void setNumero(long numero) {
		this.numero = numero;
	}

	public ChequeraE getChequeraID() {
		return chequeraID;
	}

	public void setChequeraID(ChequeraE chequeraID) {
		this.chequeraID = chequeraID;
	}

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

	public Date getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public EstadoCheque getEstado() {
		return estado;
	}

	public void setEstado(EstadoCheque estado) {
		this.estado = estado;
	}

	public Date getFechaEjecucion() {
		return fechaEjecucion;
	}

	public void setFechaEjecucion(Date fechaEjecucion) {
		this.fechaEjecucion = fechaEjecucion;
	}

	public boolean isSobreSaldo() {
		return sobreSaldo;
	}

	public void setSobreSaldo(boolean sobreSaldo) {
		this.sobreSaldo = sobreSaldo;
	}

	public ComprasPrvE getCompraID() {
		return compraID;
	}

	public void setCompraID(ComprasPrvE compraID) {
		this.compraID = compraID;
	}

}
