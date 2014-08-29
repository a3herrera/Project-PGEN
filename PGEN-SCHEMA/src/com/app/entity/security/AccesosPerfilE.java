package com.app.entity.security;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
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
import javax.persistence.Version;

import com.app.entity.embedded.registroEMB;
import com.app.entity.enums.Acciones;
import com.app.utils.ConstantsEntity;

/**
 * Entity implementation class for Entity: AccesosPerfilE
 *
 * <p>
 * Tiene como fin el controlar los accesos que va a tener un perfil, siempre
 * determinandose sobre los accesos que se le han brindando al grupo al que
 * pertenece el perfil
 * 
 * </p>
 *
 * @author Angel Alfaro
 */
@Entity
@Table(name = "PERFIL_ACCESOS")
@SequenceGenerator(name = "SEQ_PRF_ACC_ID", allocationSize = ConstantsEntity.accesoPerfilAllocation, initialValue = ConstantsEntity.accesoPerfilSecuenciaInit)
public class AccesosPerfilE implements Serializable {
	private static final long serialVersionUID = 1L;

	private long ID;
	private PerfilE perfilID;
	private AccesosGrupoE acceso;

	private List<Acciones> acciones;

	private long version;

	private registroEMB registro;

	public AccesosPerfilE() {
		super();
	}

	@Id
	@Column(name = "ID_PRF_ACC")
	@GeneratedValue(generator = "SEQ_PRF_ACC_ID", strategy = GenerationType.SEQUENCE)
	public long getID() {
		return this.ID;
	}

	public void setID(long ID) {
		this.ID = ID;
	}

	@ManyToOne
	@JoinColumn(name = "ID_PERFIL", nullable = false, insertable = true, updatable = false)
	public PerfilE getPerfilID() {
		return this.perfilID;
	}

	public void setPerfilID(PerfilE perfilID) {
		this.perfilID = perfilID;
	}

	@ManyToOne
	@JoinColumn(name = "ID_ACCESO", nullable = false, insertable = true, updatable = true)
	public AccesosGrupoE getAcceso() {
		return acceso;
	}

	public void setAcceso(AccesosGrupoE acceso) {
		this.acceso = acceso;
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

	@ElementCollection
	@CollectionTable(name = "PERFIL_ACCIONES", joinColumns = @JoinColumn(name = "PERFIL_ID"))
	@Enumerated(EnumType.STRING)
	@Column(name = "ACCION", nullable = false, length=ConstantsEntity.accesoPerfilAccionesSize)
	public List<Acciones> getAcciones() {
		return acciones;
	}

	public void setAcciones(List<Acciones> acciones) {
		this.acciones = acciones;
	}

}
