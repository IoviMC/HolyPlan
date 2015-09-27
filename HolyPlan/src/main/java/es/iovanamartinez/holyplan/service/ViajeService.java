package es.iovanamartinez.holyplan.service;

import java.util.List;

import org.springframework.stereotype.Service;

import es.iovanamartinez.holyplan.dominio.vo.CheckListViajeVo;
import es.iovanamartinez.holyplan.dominio.vo.PlaningVo;
import es.iovanamartinez.holyplan.dominio.vo.TemaVo;
import es.iovanamartinez.holyplan.dominio.vo.UsuarioVo;
import es.iovanamartinez.holyplan.dominio.vo.ViajeUsuarioVo;
import es.iovanamartinez.holyplan.dominio.vo.ViajeVo;

@Service
public interface ViajeService {
	ViajeUsuarioVo crearViaje(final Integer idUsuario, final ViajeVo viaje);

	void editarViaje(final ViajeVo viaje);
	void andadirUsuarios(String[] amigosIdString, Integer idViaje);
	List<UsuarioVo> obtenerUsuariosRol(Integer idViaje, Integer idRol);
	List<UsuarioVo> obtenerAmigosNoInvitados(Integer idUsuario, Integer idViaje);
	List<UsuarioVo> obtenerUsuariosPorEstado(Integer idViaje, Integer idEstado);
	void editarEstado(Integer idViaje, Integer idUsuario, Integer idEstado);
	void eliminarUsuario(Integer idInvitado, Integer idViaje);
	ViajeUsuarioVo getViajeUsuario(Integer idUsuario, Integer idViaje);
	List<TemaVo> obtenerTemas(Integer idViaje);
	List<CheckListViajeVo> obtenerCheckLists(Integer idViaje);
	void cancelarViaje(Integer idViaje);
	List<UsuarioVo> obtenerUsuarios(Integer idViajes);
	List<PlaningVo> getPlanings(Integer idViaje);
	List<ViajeVo> obtenerViajesAntiguos(Integer idUsuario);
	void anadirOrganizador(Integer idOrganizador, Integer idViaje);
	void anadirUsuario(String nombreUsuario, Integer idViaje);
	ViajeUsuarioVo getViajeUsuario(String nombreUsuario, Integer id);
	ViajeVo getViaje(Integer idViaje);
	
//	void eliminarViaje(Integer idViaje);
//	ViajeVo getViaje(final Integer idViaje);
//	void andadirOrganizadores(String[] amigosIdString, Integer idViaje);
//	RolVo getRolViajeUsuario(Integer idUsuario, Integer idViaje);
//	List<UsuarioVo> obtenerInvitadosNoOrganizadores(Integer id, Integer idViaje);
//	List<UsuarioVo> obtenerUsuariosEstadoRol(Integer idUsuario, Integer idViaje, Integer idEstado, Integer idRol);
//	void editarBote(Integer idViaje, BigDecimal bote);

}
