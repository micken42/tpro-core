package de.htw_berlin.tpro.user_management.persistence.dao;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import de.htw_berlin.tpro.user_management.model.Context;

@Dependent
@DefaultContextFacade
public class ContextFacadeImpl implements ContextFacade {

	private static final long serialVersionUID = 1L;

	@Inject @DefaultContextDAO
	GenericDAO<Context> contextDAO;

	@Override
	public void updateAllContexts(List<Context> contexts) {
		try {
			contextDAO.beginTransaction();

			contexts.forEach(contextDAO::update);
			contextDAO.flush();

			contextDAO.commit();
		} catch (Exception e) {
			EntityTransaction txn = contextDAO.getEntityManager().getTransaction();
			if (txn != null && txn.isActive())
				contextDAO.rollback();
			throw e;
			// handle the underlying error
		} finally {
			contextDAO.closeTransaction();
		}
	}

	@Override
	public void updateContext(Context context) throws PersistenceException {
		try {
			contextDAO.beginTransaction();

			contextDAO.update(context);

			contextDAO.commit();
		} catch (Exception e) {
			EntityTransaction txn = contextDAO.getEntityManager().getTransaction();
			if (txn != null && txn.isActive())
				contextDAO.rollback();
			throw e;
			// handle the underlying error
		} finally {
			contextDAO.closeTransaction();
		}

	}

	@Override
	public void saveContext(Context context) throws PersistenceException {
		try {
			contextDAO.beginTransaction();

			contextDAO.save(context);

			contextDAO.commit();
		} catch (Exception e) {
			EntityTransaction txn = contextDAO.getEntityManager().getTransaction();
			if (txn != null && txn.isActive())
				contextDAO.rollback();
			throw e;
			// handle the underlying error
		} finally {
			contextDAO.closeTransaction();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Context> getAllContexts() {
		contextDAO.beginTransaction();
		List<Context> contexts;
		try {
			contexts = (List<Context>) contextDAO.getEntityManager().createNamedQuery("Context.findAll")
					.getResultList();
		} catch (NoResultException e) {
			contexts = null;
		}
		contextDAO.commitAndCloseTransaction();
		return contexts;
	}

	@Override
	public Context getContextByName(String name) {
		contextDAO.beginTransaction();
		Context context;
		try {
			context = (Context) contextDAO.getEntityManager().createNamedQuery("Context.findByName")
					.setParameter("name", name).getSingleResult();
		} catch (NoResultException e) {
			context = null;
		}
		contextDAO.commitAndCloseTransaction();
		return context;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllNames() {
		contextDAO.beginTransaction();
		List<String> names;
		try {
			names = (List<String>) contextDAO.getEntityManager().createNamedQuery("Context.findAllNames")
					.getResultList();
		} catch (NoResultException e) {
			names = null;
		}
		contextDAO.commitAndCloseTransaction();
		return names;
	}

	@Override
	public void deleteContext(Context context) {
		Integer id = context.getId();
		try {
			contextDAO.beginTransaction();

			contextDAO.delete(id, Context.class);

			contextDAO.commit();
		} catch (Exception e) {
			EntityTransaction txn = contextDAO.getEntityManager().getTransaction();
			if (txn != null && txn.isActive())
				contextDAO.rollback();
			throw e;
			// handle the underlying error
		} finally {
			contextDAO.closeTransaction();
		}
	}

}