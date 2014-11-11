package es.iovanamartinez.holyplan.dao.jpa;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import es.iovanamartinez.holyplan.dao.GenericDao;

@Repository
public abstract class GenericDaoJpaImpl<T> implements GenericDao<T> {

	//@PersistenceUnit
	 //@PersistenceContext(unitName="holyPlanPULocal")
	 protected EntityManagerFactory factory;
	
	 
	 private EntityManager em;
	 
	  @PersistenceContext
	 public void setEntityManager(EntityManager entityManager) {
	     this.em = entityManager;
	 }
	
	 protected Class<T> type;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public GenericDaoJpaImpl() {
		Type t = getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) t;
		type = (Class) pt.getActualTypeArguments()[0];
	}

	//@Transactional(propagation = Propagation.REQUIRED)
	public T crear(T t) {
		//EntityManager em = factory.createEntityManager();
		
		//em.getTransaction().begin();
		em.persist(t);
		//em.flush();
		//em.getTransaction().commit();
		
		return t;
	}
	 
	public void eliminar(Object id) {
		//EntityManager em = factory.createEntityManager();
		
		em.getTransaction().begin();
		em.remove(em.getReference(type, id));
		em.getTransaction().commit();
	}

	public T buscar(Object id) {
		//EntityManager em = factory.createEntityManager();
		
		//em.getTransaction().begin();
		T t = em.find(type, id);
		//em.getTransaction().commit();
		
		return t;
	}

	public T actualizar(T t) {
		//EntityManager em = factory.createEntityManager();
		
		//em.getTransaction().begin();
		t = em.merge(t);
		//em.flush();
		//em.getTransaction().commit();
		
		return t;
	}
}
