package de.htw_berlin.tpro.user_management.persistence;

import java.util.HashSet;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;

import de.htw_berlin.tpro.user_management.model.Group;
import de.htw_berlin.tpro.user_management.model.Role;
import de.htw_berlin.tpro.user_management.model.User;

@Dependent
@DefaultGroupFacade
public class GroupFacadeImpl implements GroupFacade {

	private static final long serialVersionUID = 1L;
	
	@Inject @DefaultGroupDAO
	GenericDAO<Group> groupDAO;
	
	@Inject @DefaultUserDAO
	GenericDAO<User> userDAO;
	
	@Override
	public void updateAllGroups(List<Group> groups) {
		try {
			groupDAO.beginTransaction();
			
			groups.forEach(groupDAO::update);
			groupDAO.flush();
			
			groupDAO.commit();
		} catch (Exception e) {
			EntityTransaction txn = groupDAO.getEntityManager().getTransaction();
			if (txn != null && txn.isActive())
				groupDAO.rollback();
			throw e;
			// handle the underlying error
		} finally {
			groupDAO.closeTransaction();
		}
	}
	
	@Override
	public void updateGroup(Group group) {
		try {
			groupDAO.beginTransaction();

			groupDAO.update(group);

			groupDAO.commit();
		} catch (Exception e) {
			EntityTransaction txn = groupDAO.getEntityManager().getTransaction();
			if (txn != null && txn.isActive())
				groupDAO.rollback();
			throw e;
			// handle the underlying error
		} finally {
			groupDAO.closeTransaction();
		}
	}
	
	@Override
	public void saveGroup(Group group) {
		try {
			groupDAO.beginTransaction();

			groupDAO.save(group);

			groupDAO.commit();
		} catch (Exception e) {
			EntityTransaction txn = groupDAO.getEntityManager().getTransaction();
			if (txn != null && txn.isActive())
				groupDAO.rollback();
			throw e;
			// handle the underlying error
		} finally {
			groupDAO.closeTransaction();
		}
	}
	
	@Override
	public List<Group> getAllGroups() {
		groupDAO.beginTransaction();
		List<Group> groups = groupDAO.findAll();
		groupDAO.commitAndCloseTransaction();
		return groups;
	}

	@Override
	public Group getGroupByName(String name) {
		groupDAO.beginTransaction();
		Group group;
		try {
			group = (Group) groupDAO.getEntityManager().createNamedQuery("Group.findByName")
					.setParameter("name", name).getSingleResult();
		} catch (NoResultException e) {
			group = null;
		}
		groupDAO.commitAndCloseTransaction();
		return group;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllNames() {
		groupDAO.beginTransaction();
		List<String> names;
		try {
			names = (List<String>) groupDAO.getEntityManager()
					.createNamedQuery("Group.findAllNames").getResultList();
		} catch (NoResultException e) {
			names = null;
		}
		groupDAO.commitAndCloseTransaction();
		return names;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Group> getGroupsByUsername(String username) {
		groupDAO.beginTransaction();
		List<Group> groups;
		try {
			groups = groupDAO.getEntityManager()
					.createNamedQuery("Group.findAllByUsername")
					.setParameter("username", username)
					.getResultList();
		} catch (NoResultException e) {
			groups = null;
		}
		groupDAO.commitAndCloseTransaction();
		return groups;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public  List<Group> getGroupsByRoleAndContextName(String role, String context) {
		groupDAO.beginTransaction();
		List<Group> groups;
		try {
			groups = groupDAO.getEntityManager()
					.createNamedQuery("Group.findAllByRoleAndContextName")
					.setParameter("role", role)
					.setParameter("context", context)
					.getResultList();
		} catch (NoResultException e) {
			groups = null;
		}
		groupDAO.commitAndCloseTransaction();
		return groups;
	}
	
	@Override
	public void deleteGroupByName(String name) {
		Group group = getGroupByName(name);
		if (group == null) throw new EntityNotFoundException();
		
		group.setRoles(new HashSet<Role>());
		updateGroup(group);
		
		Integer id = group.getId();
		try {
			groupDAO.beginTransaction();
			
			groupDAO.delete(id, Group.class);
			
			groupDAO.commit();
		} catch (Exception e) {
			EntityTransaction txn = groupDAO.getEntityManager().getTransaction();
			if (txn != null && txn.isActive())
				groupDAO.rollback();
			throw e;
			// handle the underlying error
		} finally {
			groupDAO.closeTransaction();
		}
	}
	
	public void updateUser(User user) {
		try {
			userDAO.beginTransaction();
			
			userDAO.update(user);
			
			userDAO.commit();
		} catch (Exception e) {
			EntityTransaction txn = userDAO.getEntityManager().getTransaction();
			if (txn != null && txn.isActive())
				userDAO.rollback();
			
			throw e;
			// handle the underlying error
		} finally {
			userDAO.closeTransaction();
		}
	}

	@Override
	public void deleteAllGroups() {
		List<Group> groups = getAllGroups();
		groups.forEach(group -> deleteGroupByName(group.getName()));
	}

}