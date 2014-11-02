package com.app.entity.catalog;

import java.io.Serializable;
import java.lang.String;
import java.util.Date;

import javax.persistence.*;

import com.app.entity.embedded.registroEMB;
import com.app.utils.ConstantsEntity;

/**
 * Entity implementation class for Entity: CatgTipoMoneda
 *
 */
@Entity
@Table(name = "CATG_MONEDAS")
@SequenceGenerator(name = "seq_catg_monedas", sequenceName = "seq_catg_monedas", initialValue = ConstantsEntity.catgMonedaSecuenciaInit, allocationSize = ConstantsEntity.catgMonedaSecuenciaAllocation)
public class CatgTipoMoneda implements Serializable {

	private long ID;
	private String moneda;
	private String abreviatura;
	private String simbolo;
	private boolean estado;

	private registroEMB registro;

	private long version;

	private static final long serialVersionUID = 1L;

	public CatgTipoMoneda() {
		super();
	}

	@Id
	@Column(name = "ID_MONEDA")
	@GeneratedValue(generator = "seq_catg_monedas", strategy = GenerationType.SEQUENCE)
	public long getID() {
		return this.ID;
	}

	public void setID(long ID) {
		this.ID = ID;
	}

	@Column(name = "MONEDA", nullable = false)
	public String getMoneda() {
		return this.moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	@Column(name = "ABREVIATURA", nullable = true)
	public String getAbreviatura() {
		return this.abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	@Column(name = "SIMBOLO", nullable = true)
	public String getSimbolo() {
		return this.simbolo;
	}

	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}

	@Embedded
	public registroEMB getRegistro() {
		return registro;
	}

	public void setRegistro(registroEMB registro) {
		this.registro = registro;
	}

	@Version
	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	@PrePersist
	private void per() {
		setRegistro(new registroEMB());
		getRegistro().setRegCreacion(new Date());
	}

	@PreUpdate
	private void upd() {
		getRegistro().setRegModificación(new Date());
	}

	@Column(name = "ESTADO")
	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

}
