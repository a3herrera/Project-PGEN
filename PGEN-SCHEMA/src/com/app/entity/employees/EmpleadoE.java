package com.app.entity.employees;

import com.app.entity.employees.PersonaE;
import com.app.entity.security.UsuarioE;
import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: EmpleadoE
 *
 */
@Entity
@DiscriminatorValue("EMPLEADO")
public class EmpleadoE extends PersonaE implements Serializable {

	private UsuarioE usuarioID;
	private static final long serialVersionUID = 1L;

	public EmpleadoE() {
		super();
	}

	@OneToOne(mappedBy="empleadoID")
	public UsuarioE getUsuarioID() {
		return this.usuarioID;
	}

	public void setUsuarioID(UsuarioE usuarioID) {
		this.usuarioID = usuarioID;
	}

}
