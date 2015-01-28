package es.iovanamartinez.holyplan.service;

import java.util.List;

import org.springframework.stereotype.Service;

import es.iovanamartinez.holyplan.dominio.vo.TemaVo;
import es.iovanamartinez.holyplan.dominio.vo.UsuarioVo;
import es.iovanamartinez.holyplan.dominio.vo.ViajeUsuarioVo;
import es.iovanamartinez.holyplan.dominio.vo.ViajeVo;

@Service
public interface ViajeService {
	void crearViaje(final Integer idUsuario, final ViajeVo viaje);
	ViajeVo getViaje(final Integer idViaje);
	void editarViaje(final ViajeVo viaje);
	void andadirUsuarios(String[] amigosIdString, Integer idViaje);
	void andadirOrganizadores(String[] amigosIdString, Integer idViaje);
//	RolVo getRolViajeUsuario(Integer idUsuario, Integer idViaje);
	List<UsuarioVo> obtenerUsuariosRol(Integer idUsuario, Integer idViaje, Integer idRol);
//	List<UsuarioVo> obtenerInvitadosNoOrganizadores(Integer id, Integer idViaje);
	List<UsuarioVo> obtenerAmigosNoInvitados(Integer idUsuario, Integer idViaje);
	List<UsuarioVo> obtenerUsuariosPorEstado(Integer idusuario, Integer idViaje, int idEstado);
	void editarEstado(Integer idViaje, Integer idUsuario, Integer idEstado);
	void eliminarUsuario(Integer idInvitado, Integer idViaje);
	ViajeUsuarioVo getViajeUsuario(Integer idUsuario, Integer idViaje);
//	List<UsuarioVo> obtenerUsuariosEstadoRol(Integer idUsuario, Integer idViaje, Integer idEstado, Integer idRol);
	List<TemaVo> obtenerTemas(Integer idViaje);
}
