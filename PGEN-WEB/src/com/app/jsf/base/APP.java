package com.app.jsf.base;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.app.utils.Constants;

@ApplicationScoped
@ManagedBean(name = "APP")
public class APP {

	private transient EntityManagerFactory emf;

	public EntityManagerFactory getEmf() {
		if (emf == null) {
			emf = Persistence
					.createEntityManagerFactory(Constants.persistencesName);
		}
		return emf;
	}

}
