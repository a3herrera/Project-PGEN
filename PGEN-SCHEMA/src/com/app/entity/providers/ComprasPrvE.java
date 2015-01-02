package com.app.entity.providers;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.eclipse.persistence.annotations.Customizer;

import com.app.entity.enums.EstadoCompra;
import com.app.entity.history.AuditedEntity;
import com.app.entity.history.ComprasPrvHistory;
import com.app.utils.ConstantsEntity;

/**
 * Entity implementation class for Entity: ComprasPrvE
 *
 */
@Entity
@Table(name = "COMPRAS_PROVEEDORES")
@Customizer(ComprasPrvHistory.class)
@SequenceGenerator(name = ConstantsEntity.COMPRA_SEQUENCE_NAME, sequenceName = ConstantsEntity.COMPRA_SEQUENCE_NAME, allocationSize = ConstantsEntity.compraSecuenciaAllocation, initialValue = ConstantsEntity.compraSecuenciaInit)
public class ComprasPrvE extends AuditedEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_COMPRA")
	@GeneratedValue(generator = ConstantsEntity.COMPRA_SEQUENCE_NAME)
	private long ID;

	@ManyToOne
	@JoinColumn(name = "ID_PROVEEDOR", nullable = false)
	private ProveedorE proveedorID;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_COMPRA", nullable = false)
	private Date fechaCompra;

	@Column(name = "MONTO", precision = ConstantsEntity.precision, scale = ConstantsEntity.escala)
	private double monto;

	@Column(name = "SALDO", precision = ConstantsEntity.precision, scale = ConstantsEntity.escala)
	private double saldo;

	@Enumerated(EnumType.STRING)
	@Column(name = "ESTADO", nullable = false, length=ConstantsEntity.comprasEstado)
	private EstadoCompra estado;

	
	public long getID() {
		return this.ID;
	}

	public void setID(long ID) {
		this.ID = ID;
	}

	public ProveedorE getProveedorID() {
		return this.proveedorID;
	}

	public void setProveedorID(ProveedorE proveedorID) {
		this.proveedorID = proveedorID;
	}

	public Date getFechaCompra() {
		return this.fechaCompra;
	}

	public void setFechaCompra(Date fechaCompra) {
		this.fechaCompra = fechaCompra;
	}

	public double getMonto() {
		return this.monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

	public double getSaldo() {
		return this.saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public EstadoCompra getEstado() {
		return estado;
	}

	public void setEstado(EstadoCompra estado) {
		this.estado = estado;
	}

}
