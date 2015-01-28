package es.iovanamartinez.holyplan.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import es.iovanamartinez.holyplan.dominio.Usuario;

public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  private Assembler assembler;
  
  @PersistenceContext
  private EntityManager em;
 
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String nombreUsuario)
      throws UsernameNotFoundException, DataAccessException {
	  
	Usuario usuario = null;
		
	TypedQuery<Usuario> query = em.createQuery("SELECT e FROM Usuario e WHERE e.nombreUsuario = :nombreUsuario", Usuario.class);
	query.setParameter("nombreUsuario", nombreUsuario);
	try {
		usuario = query.getSingleResult();
	} catch (NoResultException e) {
		throw new UsernameNotFoundException("user not found");
	} catch (Exception e) {
		e.printStackTrace();
		System.out.println("Pos llega");
	}
	
    if (usuario == null) {
    	throw new UsernameNotFoundException("user not found");
    }

    return assembler.buildUserFromUserEntity(usuario);
  }

}
