package es.iovanamartinez.holyplan.service;


import java.util.List;

import org.springframework.stereotype.Service;

import es.iovanamartinez.holyplan.dominio.vo.UsuarioVo;
import es.iovanamartinez.holyplan.dominio.vo.ViajeVo;

@Service
public interface UsuarioService {
	UsuarioVo crearUsuario(UsuarioVo usuarioVo);
	UsuarioVo getUsuarioActual();
	UsuarioVo getUsuarioPorNombre(String nombreUsuario);
	UsuarioVo getUsuarioPorEmail(String email);
	UsuarioVo getUsuarioPorHash(String hash);
	void activarUsuario(String hash);
	void desactivarUsuario(Integer idUsuario);
	void modificarNombreUsuario(String nuevoNombre, Integer idUsuario);
	void modificarContrasena(Integer idUsuario, String contrasena);
	void guardarEmailTemp(UsuarioVo usuario, String email);
	void cambiarEmail(Integer idUsuario);
	UsuarioVo anadirAmigo(Integer idUsuario, Integer idAmigo);
	List<UsuarioVo> obtenerAmigos(Integer idUsuario);
//	UsuarioVo buscarAmigoPorNombre(Integer idUsuario, String nombreAmigo);
	List<ViajeVo> obtenerProximosViajes(Integer idUsuario);
	void eliminarAmigo(Integer idAmigo, Integer idUsuario);
	boolean esUsuarioAmigo(Integer idUsuario, Integer idUsuarioAmigo);
	UsuarioVo getUsuario(Integer idUsuario);
	void eliminarUsuario(Integer idUsuario);
	List<Integer> getUsuarioAmigo(Integer idUsuario, Integer idAmigo);
}
