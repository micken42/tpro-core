package de.htw_berlin.tpro.users.mb;

import java.io.Serializable;

import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

@Named
@Stateful
@RequestScoped
public class CredentialsMB implements Serializable {
	private static final long serialVersionUID = 1L;
	@Getter @Setter
	private String username;
	@Getter @Setter
    private String password;

}
