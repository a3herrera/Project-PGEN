package com.app.entity.providers;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.app.entity.employees.PersonaE;

/**
 * Entity implementation class for Entity: EmpleadoE
 * <p>
 * Esta entidad va a manejar toda la información de las personas de contacto de
 * los proveedores
 * </p>
 */
@Entity
@DiscriminatorValue("CONTACTO_PRV")
public class ContactoPrvE extends PersonaE {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ProveedorE proveedorID;

	@ManyToOne
	@JoinColumn(name = "ID_PROVEEDOR", nullable = true)
	public ProveedorE getProveedorID() {
		return proveedorID;
	}

	public void setProveedorID(ProveedorE proveedorID) {
		this.proveedorID = proveedorID;
	}

}
