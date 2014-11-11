package es.iovanamartinez.holyplan.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import es.iovanamartinez.holyplan.dao.UsuarioDao;
import es.iovanamartinez.holyplan.dominio.Usuario;

@Repository(value = "usuarioDao")
public class UsuarioDaoJpaImpl extends GenericDaoJpaImpl<Usuario> implements UsuarioDao {
	
	//@PersistenceUnit
	//@PersistenceContext(unitName="holyPlanPULocal")
	//private EntityManagerFactory factory;
	private EntityManager em;
	 
	  @PersistenceContext
	 public void setEntityManager(EntityManager entityManager) {
	     this.em = entityManager;
	 }
	
	@Override
	public Usuario buscarPorNombreUsuario(String nombreUsuario) {
		Usuario usuario = null;
		//EntityManager em = factory.createEntityManager();
		TypedQuery<Usuario> query = em.createQuery("SELECT e FROM Usuario e WHERE e.nombreUsuario = :nombreUsuario", Usuario.class);
		query.setParameter("nombreUsuario", nombreUsuario);
		try {
			usuario = query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		return usuario;
	}
	
	@Override
	public Usuario buscarUsuarioPorEmail(String email) {
		Usuario usuario = null;
		//EntityManager em = factory.createEntityManager();
		TypedQuery<Usuario> query = em.createQuery("SELECT e FROM Usuario e WHERE e.email = :email", Usuario.class);
		query.setParameter("email", email);
		try {
			usuario = query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		return usuario;
	}
	
	@Override
	public Usuario buscarUsuarioPorHash(String hash) {
		Usuario usuario = null;
		//EntityManager em = factory.createEntityManager();
		TypedQuery<Usuario> query = em.createQuery("SELECT e FROM Usuario e WHERE e.hash = :hash", Usuario.class);
		query.setParameter("hash", hash);
		try {
			usuario = query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		return usuario;
	}
	
//	@Override
//	@Transactional
//	public void activarUsuario(String hash) {
//		Usuario usuario = null;
//		EntityManager em = factory.createEntityManager();
//		
//		em.getTransaction().begin();
//		TypedQuery<Usuario> query = em.createQuery("SELECT e FROM Usuario e WHERE e.hash = :hash", Usuario.class);
//		query.setParameter("hash", hash);
//		try {
//			usuario = query.getSingleResult();
//			if (usuario != null) {
//				//em.getTransaction().begin();
//				usuario.setActivo(ACTIVO);
//				//em.getTransaction().commit();
//			}
//		} catch (NoResultException e) {
//			throw new UsernameNotFoundException("hash de usuario no encontrado");
//		}
//		em.getTransaction().commit();
//	}

//	@Override
//	@Transactional
//	public void modificarNombreUsuario(String nuevoNombre, Integer id) {
//		Usuario usuario = null;
//		EntityManager em = factory.createEntityManager();
//		
//		em.getTransaction().begin();
//		usuario = em.find(type, id);
//		usuario.setNombreUsuario(nuevoNombre);
//		em.getTransaction().commit();
//	}
	
//	@Override
//	@Transactional
//	public void modificarContrasena(Integer id, String contrasena) {
//		Usuario usuario = null;
//		EntityManager em = factory.createEntityManager();
//		
//		em.getTransaction().begin();
//		usuario = em.find(type, id);
//		usuario.setContrasena(contrasena);
//		em.getTransaction().commit();
//	}

//	@Override
//	@Transactional
//	public void guadarEmailTemp(Integer id, String email) {
//		Usuario usuario = null;
//		EntityManager em = factory.createEntityManager();
//		
//		em.getTransaction().begin();
//		usuario = em.find(type, id);
//		usuario.setEmailTemp(email);
//		em.getTransaction().commit();
//	}

//	@Override
//	@Transactional
//	public void cambiarEmail(String hash) {
//		Usuario usuario = null;
//		EntityManager em = factory.createEntityManager();
//		em.getTransaction().begin();
//		TypedQuery<Usuario> query = em.createQuery("SELECT e FROM Usuario e WHERE e.hash = :hash", Usuario.class);
//		query.setParameter("hash", hash);
//		try {
//			usuario = query.getSingleResult();
//			if (usuario != null) {
//				if (usuario.getEmailTemp() != null && !usuario.getEmailTemp().isEmpty()) {
//					//em.getTransaction().begin();
//					usuario.setEmail(usuario.getEmailTemp());
//					usuario.setEmailTemp(null);
//					//em.getTransaction().commit();
//				}
//			}
//		} catch (NoResultException e) {
//			throw new UsernameNotFoundException("hash de usuario no encontrado");
//		}
//		em.getTransaction().commit();
//	}
}
