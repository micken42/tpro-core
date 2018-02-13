package de.htw_berlin.tpro.test_utils;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

public class DeploymentHelper {

	public static JavaArchive createDefaultDeployment() {
	    return ShrinkWrap.create(JavaArchive.class)
	        .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
	        .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
	        .addAsResource("META-INF/test-data.sql", "META-INF/test-data.sql");
	}
}
