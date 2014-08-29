package com.app.entity.security;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.eclipse.persistence.annotations.Index;

import com.app.entity.embedded.registroEMB;
import com.app.utils.ConstantsEntity;

/**
 * Entity implementation class for Entity: AccesosGrupoE
 *
 */
@Entity
@Table(name = "GRUPO_ACCESOS")
@Index(name = "IDX_GRUPO_ACCESOS", columnNames = { "ID_GRUPO","ID_ACCESO" })
@SequenceGenerator(name = "SEQ_GRP_ACC_ID", allocationSize = ConstantsEntity.accesoGrupoAllocation, initialValue = ConstantsEntity.accesoGrupoSecuenciaInit)
public class AccesosGrupoE implements Serializable {
	private static final long serialVersionUID = 1L;

	private long ID;
	private GrupoE grupoID;
	private AccesoE accesoID;
	private long version;

	private registroEMB registro;

	public AccesosGrupoE() {
		super();
	}

	@Id
	@Column(name = "ID_GRP_ACC")
	@GeneratedValue(generator = "SEQ_GRP_ACC_ID", strategy = GenerationType.AUTO)
	public long getID() {
		return this.ID;
	}

	public void setID(long ID) {
		this.ID = ID;
	}

	@ManyToOne
	@JoinColumn(name = "ID_GRUPO", nullable = false, insertable = true, updatable = false)
	public GrupoE getGrupoID() {
		return this.grupoID;
	}

	public void setGrupoID(GrupoE grupoID) {
		this.grupoID = grupoID;
	}

	@ManyToOne
	@JoinColumn(name = "ID_ACCESO", nullable = false, insertable = true, updatable = false)
	public AccesoE getAccesoID() {
		return this.accesoID;
	}

	public void setAccesoID(AccesoE accesoID) {
		this.accesoID = accesoID;
	}

	@Version
	public long getVersion() {
		return this.version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	@Embedded
	public registroEMB getRegistro() {
		return registro;
	}

	public void setRegistro(registroEMB registro) {
		this.registro = registro;
	}

}
