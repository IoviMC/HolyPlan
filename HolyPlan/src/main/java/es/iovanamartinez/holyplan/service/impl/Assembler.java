package es.iovanamartinez.holyplan.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.iovanamartinez.holyplan.dominio.Usuario;

@Service
public class Assembler {

  @Transactional(readOnly = true)
  User buildUserFromUserEntity(Usuario usuario) {
    String username = usuario.getNombreUsuario();
    String password = usuario.getContrasena();
    boolean enabled = usuario.isActivo();
    boolean accountNonExpired = true;
    boolean credentialsNonExpired = true;
    boolean accountNonLocked = !usuario.isCuentaCerrada();

    Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    authorities.add(new SimpleGrantedAuthority("ROLE_USUARIO"));
    
    User user = new User(username, password, enabled,
      accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    return user;
  }

}
