package es.iovanamartinez.holyplan.dao;

import es.iovanamartinez.holyplan.dominio.Usuario;

/**
 * lo que viene a ser un dao
 * 
 * @author Iovana Martínez
 * @version 1.0
 */
public interface UsuarioDao extends GenericDao<Usuario> {
	/** 
	 * Busca un usuario a partir de su nombre único.
	 * 
	 * @return El usuario encontrado, o null si no existe
	 * @see Usuario
	 */
	Usuario buscarPorNombreUsuario(String nombreUsuario);
	Usuario buscarUsuarioPorEmail(String email);
	Usuario buscarUsuarioPorHash(String hash);
}
