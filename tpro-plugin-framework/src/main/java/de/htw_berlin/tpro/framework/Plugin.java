package de.htw_berlin.tpro.framework;

import java.util.Set;

import de.htw_berlin.tpro.user_management.model.Context;
import de.htw_berlin.tpro.user_management.model.Role;
import lombok.Getter;

public abstract class Plugin {
	private @Getter String author;
	private @Getter String name;
	private @Getter String version;
	private @Getter String title;
	private @Getter String description;
	private @Getter String thumbnailResource;

	private @Getter Context context;
	private @Getter Set<Role> roles;
	
	public Plugin(String author, String name, String version, String title, String description, String thumbnailResource,
			Context context, Set<Role> roles) {
		this.author = author;
		this.name = name;
		this.version = version;
		this.title = title;
		this.description = description;
		this.thumbnailResource = thumbnailResource;
		this.context = context;
		this.roles = roles;
	}
}
