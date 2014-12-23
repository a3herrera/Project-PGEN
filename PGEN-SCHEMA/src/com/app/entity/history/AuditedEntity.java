package com.app.entity.history;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@MappedSuperclass
public class AuditedEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ADD_DATE", updatable = false, insertable = true, nullable=false)
	private Date regCreacion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATE_DATE", updatable = true, insertable = false)
	private Date regModificaci�n;

	@Column(name = "ADDED_BY", length = 64)
	private long creadoPor;

	@Column(name = "UPDATED_BY", length = 64)
	private long modificadoPor;

	@Version
	private long version;

	@PrePersist
	public void prePersist() {
		regCreacion = new Date();
	}

	@PreUpdate
	public void preUpdate() {
		regModificaci�n = new Date();
	}

	public Date getRegCreacion() {
		return regCreacion;
	}

	public void setRegCreacion(Date regCreacion) {
		this.regCreacion = regCreacion;
	}

	public Date getRegModificaci�n() {
		return regModificaci�n;
	}

	public void setRegModificaci�n(Date regModificaci�n) {
		this.regModificaci�n = regModificaci�n;
	}

	public long getCreadoPor() {
		return creadoPor;
	}

	public void setCreadoPor(long creadoPor) {
		this.creadoPor = creadoPor;
	}

	public long getModificadoPor() {
		return modificadoPor;
	}

	public void setModificadoPor(long modificadoPor) {
		this.modificadoPor = modificadoPor;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

}
