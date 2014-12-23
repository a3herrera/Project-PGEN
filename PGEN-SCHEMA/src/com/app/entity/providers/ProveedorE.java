package com.app.entity.providers;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import com.app.entity.embedded.registroEMB;
import com.app.entity.enums.EstadoProveedor;
import com.app.utils.ConstantsEntity;

@Entity
@Table(name = "PROVEEDORES", uniqueConstraints = { @UniqueConstraint(columnNames = "NOMBRE", name = "CNN_UN_PRV_NOMBRE") })
@SequenceGenerator(name = ConstantsEntity.PROVEEDOR_SEQUENCE_NAME, sequenceName = ConstantsEntity.PROVEEDOR_SEQUENCE_NAME, allocationSize = ConstantsEntity.proveedorSecuenciaAllocation, initialValue = ConstantsEntity.proveedorSecuenciaInit)
public class ProveedorE implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_PROVEEDOR")
	@GeneratedValue(generator=ConstantsEntity.PROVEEDOR_SEQUENCE_NAME, strategy=GenerationType.SEQUENCE)
	private long ID;

	@Column(name = "NOMBRE", nullable = false)
	private String nombre;

	@Column(name = "NIT", nullable = false)
	private String nit;

	@Enumerated(EnumType.STRING)
	@Column(name = "ESTADO", nullable = false)
	private EstadoProveedor estado;

	@OneToMany(mappedBy = "proveedorID", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<ContactoPrvE> contactos;

	@Embedded
	private registroEMB registro;

	@Version
	private long version;

	public long getID() {
		return ID;
	}

	public void setID(long iD) {
		ID = iD;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNit() {
		return nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
	}

	public EstadoProveedor getEstado() {
		return estado;
	}

	public void setEstado(EstadoProveedor estado) {
		this.estado = estado;
	}

	public List<ContactoPrvE> getContactos() {
		return contactos;
	}

	public void setContactos(List<ContactoPrvE> contactos) {
		this.contactos = contactos;
	}

	public registroEMB getRegistro() {
		return registro;
	}

	public void setRegistro(registroEMB registro) {
		this.registro = registro;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

}
