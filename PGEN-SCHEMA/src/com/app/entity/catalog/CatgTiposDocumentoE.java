package com.app.entity.catalog;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import com.app.entity.embedded.registroEMB;
import com.app.utils.ConstantsEntity;

/**
 * Entity implementation class for Entity: CatgTiposDocumentoE
 *
 */
@Entity
@Table(name = "CATG_DOCUMENTOS")
@SequenceGenerator(name = "seq_catg_documentos", sequenceName = "seq_catg_documentos", initialValue = ConstantsEntity.catgDocumentoSecuenciaInit, allocationSize = ConstantsEntity.catgDocumentoSecuenciaAllocation)
public class CatgTiposDocumentoE implements Serializable {
	
	private long ID;
	private String nombre;
	private String descripcion;
	private String formato;
	private boolean estado;
	private registroEMB registro;
	private long version;
	private static final long serialVersionUID = 1L;

	public CatgTiposDocumentoE() {
		super();
	}

	@Id
	@Column(name = "ID_DOCUMENTO")
	@GeneratedValue(generator="seq_catg_documentos", strategy=GenerationType.SEQUENCE)
	public long getID() {
		return this.ID;
	}

	public void setID(long ID) {
		this.ID = ID;
	}

	@Column(name = "NOMBRE", nullable = false, length = ConstantsEntity.documentoNombreSize)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Lob
	@Column(name = "DESCRIPCION")
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "FORMATO", length = 50)
	public String getFormato() {
		return this.formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	@Column(name = "ESTADO")
	public boolean getEstado() {
		return this.estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	@Embedded
	public registroEMB getRegistro() {
		return this.registro;
	}

	public void setRegistro(registroEMB registro) {
		this.registro = registro;
	}

	@Version
	public long getVersion() {
		return this.version;
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

}
