package de.htw_berlin.tpro.user_management.persistence;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.htw_berlin.tpro.test_utils.DeploymentHelper;
import de.htw_berlin.tpro.user_management.model.Group;
import de.htw_berlin.tpro.user_management.persistence.DefaultGroupDAO;
import de.htw_berlin.tpro.user_management.persistence.GenericDAO;
import de.htw_berlin.tpro.user_management.persistence.GroupDAO;
import de.htw_berlin.tpro.user_management.persistence.GroupDAOProducer;

@RunWith(Arquillian.class)
public class GroupDAOProducerTest {

    @Deployment
    public static JavaArchive createDeployment() {
    	return DeploymentHelper.createDefaultDeployment()
    		.addClasses(GenericDAO.class, GroupDAO.class, GroupDAOProducer.class);
    }

    @Inject @DefaultGroupDAO
    GenericDAO<Group> groupDAO;

    @Test
	public void groupDAOProducerShouldProduceRoleDAOInstance() {
		Assert.assertNotEquals(null, groupDAO);
    }

    @Test
    public void producedGroupDAOInstanceShouldBeAbleToCreateEntityManagerInstance() {
    	groupDAO.beginTransaction();
    	EntityManager em = groupDAO.getEntityManager();
    	groupDAO.closeTransaction();

    	Assert.assertNotEquals(null, em);
    }
}