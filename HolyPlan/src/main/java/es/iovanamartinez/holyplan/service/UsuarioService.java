package es.iovanamartinez.holyplan.service;


import org.springframework.stereotype.Service;

import es.iovanamartinez.holyplan.dominio.vo.UsuarioVo;

@Service
public interface UsuarioService {
	void crearUsuario(UsuarioVo usuarioVo);
	UsuarioVo getUsuarioActual();
	UsuarioVo getUsuarioPorNombre(String nombreUsuario);
	UsuarioVo getUsuarioPorEmail(String email);
	UsuarioVo getUsuarioPorHash(String hash);
	void activarUsuario(String hash);
	void desactivarUsuario(Integer id);
	void modificarNombreUsuario(String nuevoNombre, Integer id);
	void modificarContrasena(Integer id, String contrasena);
	void guardarEmailTemp(UsuarioVo usuario, String email);
	void cambiarEmail(Integer id);
}
