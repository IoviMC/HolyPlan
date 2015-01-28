package es.iovanamartinez.holyplan.dao.jpa;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import es.iovanamartinez.holyplan.dao.GenericDao;

@Repository
public abstract class GenericDaoJpaImpl<T> implements GenericDao<T> {


	@PersistenceContext
    protected EntityManager em;
	protected Class<T> type;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public GenericDaoJpaImpl() {
		Type t = getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) t;
		type = (Class) pt.getActualTypeArguments()[0];
	}

	//@Transactional(propagation = Propagation.MANDATORY)
	public T crear(T t) {
		em.persist(t);
		return t;
	}
	
	//@Transactional(propagation = Propagation.MANDATORY)
	public void eliminar(T t) {
		em.remove(t);
	}

	//@Transactional
	public T buscar(Object id) {
		T t = em.find(type, id);
		return t;
	}

	//@Transactional(propagation = Propagation.MANDATORY)
	public T actualizar(T t) {
		t = em.merge(t);		
		return t;
	}
}
