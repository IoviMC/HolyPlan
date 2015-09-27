package es.iovanamartinez.holyplan.dao.jpa;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import es.iovanamartinez.holyplan.dao.UsuarioDao;
import es.iovanamartinez.holyplan.dominio.Usuario;

@Repository(value = "usuarioDao")
public class UsuarioDaoJpaImpl extends GenericDaoJpaImpl<Usuario> implements UsuarioDao {
	
	@Override
	public Usuario buscarPorNombreUsuario(String nombreUsuario) {
		Usuario usuario = null;
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
		TypedQuery<Usuario> query = em.createQuery("SELECT e FROM Usuario e WHERE e.hash = :hash", Usuario.class);
		query.setParameter("hash", hash);
		try {
			usuario = query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		return usuario;
	}
}
