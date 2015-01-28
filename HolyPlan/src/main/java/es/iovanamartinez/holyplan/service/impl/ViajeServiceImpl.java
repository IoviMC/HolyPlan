package es.iovanamartinez.holyplan.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.iovanamartinez.holyplan.dao.EstadoDao;
import es.iovanamartinez.holyplan.dao.RolDao;
import es.iovanamartinez.holyplan.dao.UsuarioDao;
import es.iovanamartinez.holyplan.dao.ViajeDao;
import es.iovanamartinez.holyplan.dao.ViajeUsuarioDao;
import es.iovanamartinez.holyplan.dominio.Estado;
import es.iovanamartinez.holyplan.dominio.Rol;
import es.iovanamartinez.holyplan.dominio.Tema;
import es.iovanamartinez.holyplan.dominio.Usuario;
import es.iovanamartinez.holyplan.dominio.UsuarioAmigo;
import es.iovanamartinez.holyplan.dominio.Viaje;
import es.iovanamartinez.holyplan.dominio.ViajeUsuario;
import es.iovanamartinez.holyplan.dominio.ViajeUsuarioId;
import es.iovanamartinez.holyplan.dominio.vo.TemaVo;
import es.iovanamartinez.holyplan.dominio.vo.UsuarioVo;
import es.iovanamartinez.holyplan.dominio.vo.ViajeUsuarioVo;
import es.iovanamartinez.holyplan.dominio.vo.ViajeVo;
import es.iovanamartinez.holyplan.service.ViajeService;

@Service
public class ViajeServiceImpl implements ViajeService{
	private static final int ID_ROL_CREADOR = 1;
	private static final int ID_ROL_ORGANIZADOR = 2;
	private static final int ID_ROL_INVITADO = 3;
	private static final int ID_ESTADO_ACEPTADO = 1;
	private static final int ID_ESTADO_PENDIENTE = 0;
	
	@Autowired
	private ViajeDao viajeDao;
	@Autowired
	private RolDao rolDao;
	@Autowired
	private ViajeUsuarioDao viajeUsuarioDao;
	@Autowired
	private UsuarioDao usuarioDao;
	@Autowired EstadoDao estadoDao;
	
	@Transactional
	@Override
	public void crearViaje(final Integer idUsuario, final ViajeVo viajeVo) {
		
		Viaje nuevoViaje = new Viaje(viajeVo.getNombreViaje(), viajeVo.getFecha(), viajeVo.getDuracion(), viajeVo.getDescripcion());
		
		Viaje viajeCreado = viajeDao.crear(nuevoViaje);
		
		Rol rol = rolDao.buscar(ID_ROL_CREADOR);
		
		Estado estado = estadoDao.buscar(ID_ESTADO_ACEPTADO);
		
		Usuario usuario = usuarioDao.buscar(idUsuario);
		
		ViajeUsuario viajeUsuario = new ViajeUsuario(usuario, viajeCreado, rol, estado);
		
		viajeUsuarioDao.crear(viajeUsuario);
	}

	@Override
	@Transactional(readOnly = true)
	public ViajeVo getViaje(final Integer idViaje) {
		
		Viaje viaje = viajeDao.buscar(idViaje);
		
		return new ViajeVo(viaje);
	}

	@Override
	@Transactional
	public void editarViaje(final ViajeVo viajeVo) {
		Viaje viaje = viajeDao.buscar(viajeVo.getId());
		viaje.setNombreViaje(viajeVo.getNombreViaje());
		viaje.setFecha(viajeVo.getFecha());
		viaje.setDuracion(viajeVo.getDuracion());
		viaje.setDescripcion(viajeVo.getDescripcion());
		viajeDao.actualizar(viaje);
	}
	
	@Transactional
	@Override
	public void andadirUsuarios(String[] amigosIdString, Integer idViaje){
		Viaje viaje = viajeDao.buscar(idViaje);
		Rol rol = rolDao.buscar(ID_ROL_INVITADO);
		Estado estado = estadoDao.buscar(ID_ESTADO_PENDIENTE);
		
		for(String amigoId: amigosIdString){
			Usuario usuario = usuarioDao.buscar(Integer.parseInt(amigoId));
			ViajeUsuario viajeUsuario = new ViajeUsuario(usuario, viaje, rol, estado);
			
			viajeUsuarioDao.crear(viajeUsuario);
		}	
	}

	@Override
	@Transactional
	public void andadirOrganizadores(String[] amigosIdString, Integer idViaje) {
		Rol rol = rolDao.buscar(ID_ROL_ORGANIZADOR);

		for(String amigoId: amigosIdString){	
			ViajeUsuarioId viajeUsuarioId = new ViajeUsuarioId(Integer.parseInt(amigoId), idViaje);
			ViajeUsuario viajeUsuario = viajeUsuarioDao.buscar(viajeUsuarioId);
			viajeUsuario.setRol(rol);
			
			viajeUsuarioDao.actualizar(viajeUsuario);
		}
		
		
	}
	
//	@Override
//	@Transactional
//	public void eliminarUsuarios(String[] usuariosIdString, Integer idViaje) {
//	
//		for(String usuarioId: usuariosIdString){			
//			Usuario usuario = usuarioDao.buscar(Integer.parseInt(usuarioId));
//			Set <ViajeUsuario> viajesUsuario = usuario.getViajes();
//			
//			Viaje viaje = viajeDao.buscar(idViaje);
//			Set <ViajeUsuario> usuariosViaje = viaje.getUsuarios();
//			
//			ViajeUsuarioId viajeUsuarioId = new ViajeUsuarioId(Integer.parseInt(usuarioId), idViaje);
//			ViajeUsuario viajeUsuario = viajeUsuarioDao.buscar(viajeUsuarioId);
//			
//			usuariosViaje.remove(viajeUsuario);
//			viajesUsuario.remove(viajeUsuario);
//			
//			usuario.setViajes(viajesUsuario);
//			viaje.setUsuarios(usuariosViaje);
//			
//			viajeUsuarioDao.eliminar(viajeUsuario);
//		}			
//	}
	
	//@Override
//	@Transactional(readOnly = true)
//	public RolVo getRolViajeUsuario(Integer idUsuario, Integer idViaje){
//		
//		ViajeUsuarioId viajeUsuarioId = new ViajeUsuarioId(idUsuario, idViaje);
//		
//		ViajeUsuario viajeUsuario = viajeUsuarioDao.buscar(viajeUsuarioId);
//		
//		return new RolVo(viajeUsuario.getRol());
//	}
	
	@Override
	@Transactional(readOnly = true)
	public List<UsuarioVo> obtenerUsuariosRol(Integer idUsuario, Integer idViaje, Integer idRol) {
		Viaje viaje = viajeDao.buscar(idViaje);
		Set<ViajeUsuario> viajes = viaje.getUsuarios();
		
		List<UsuarioVo> usuariosVo = new ArrayList<UsuarioVo>();
		for(ViajeUsuario viajeUsuario: viajes){
			if ((viajeUsuario.getRol().getId().compareTo(idRol)==0) && (viajeUsuario.getEstado().getId().compareTo(ID_ESTADO_ACEPTADO)==0))
				usuariosVo.add(new UsuarioVo(viajeUsuario.getUsuario()));
		}
		
		return usuariosVo;
	}
	
//	@Override
//	@Transactional(readOnly = true)
//	public List<UsuarioVo> obtenerInvitadosNoOrganizadores(Integer idUsuario, Integer idViaje) {
////		Usuario usuario = usuarioDao.buscar(idUsuario);
////		Set<UsuarioAmigo> amigosUsuario = usuario.getAmigos();
////		
////		Viaje viaje = viajeDao.buscar(idViaje);
////		Set<ViajeUsuario> usuariosViaje = viaje.getUsuarios();
////
////		List<Usuario> organizadores = new ArrayList<Usuario>();
////		for(ViajeUsuario usuarioViaje: usuariosViaje){
////			if (usuarioViaje.getRol().getId().compareTo(ID_ROL_ORGANIZADOR) == 0){
////				organizadores.add(usuarioViaje.getUsuario());
////			}
////		}
////		
////		List<UsuarioVo> amigosVo = new ArrayList<UsuarioVo>();
////		for(UsuarioAmigo amigoUsuario: amigosUsuario){
////			if(!organizadores.contains(amigoUsuario.getAmigo())){
////				amigosVo.add(new UsuarioVo(amigoUsuario.getAmigo()));
////			}
////		}
//		
//		Viaje viaje = viajeDao.buscar(idViaje);
//		Set<ViajeUsuario> usuarios= viaje.getUsuarios();
//		List<UsuarioVo> invitadosVo = new ArrayList<UsuarioVo>();
//		for(ViajeUsuario usuario: usuarios){
//			if((usuario.getRol().getId().compareTo(ID_ROL_USUARIO) == 0) && (usuario.getEstado().getId().compareTo(ID_ESTADO_ACEPTADO) == 0)){
//				
//			}
//		}
		
		
//		List<Usuario> amigos = new ArrayList<Usuario>();
//		for(UsuarioAmigo amigoUsuario: amigosUsuario){
//			amigos.add(amigoUsuario.getAmigo());
//		}
//		
//		List<UsuarioVo> amigosVo = new ArrayList<UsuarioVo>();
//		for(Usuario amigo: amigos){
//			if(!organizadores.contains(amigo)){
//				amigosVo.add(new UsuarioVo(amigo));
//			}
//		}
		
//		return amigosVo;
//	}
	
	@Override
	@Transactional(readOnly = true)
	public List<UsuarioVo> obtenerAmigosNoInvitados(Integer idUsuario, Integer idViaje) {
		Usuario usuario = usuarioDao.buscar(idUsuario);
		Set<UsuarioAmigo> amigosUsuario = usuario.getAmigos();
		
		Viaje viaje = viajeDao.buscar(idViaje);
		Set<ViajeUsuario> usuariosViaje = viaje.getUsuarios();

		List<Usuario> usuarios = new ArrayList<Usuario>();
		for(ViajeUsuario usuarioViaje: usuariosViaje){
			usuarios.add(usuarioViaje.getUsuario());
		}
		
//		List<Usuario> amigos = new ArrayList<Usuario>();
//		for(UsuarioAmigo amigoUsuario: amigosUsuario){
//			amigos.add(amigoUsuario.getAmigo());
//		}
		
//		for(Usuario amigo: amigos){
	//		if(!usuarios.contains(amigo)){
	//			amigosVo.add(new UsuarioVo(amigo));
	//		}
	//	}
		
		List<UsuarioVo> amigosVo = new ArrayList<UsuarioVo>();
		for(UsuarioAmigo amigoUsuario: amigosUsuario){
			if (!usuarios.contains(amigoUsuario.getAmigo()))
				amigosVo.add(new UsuarioVo(amigoUsuario.getAmigo()));
		}
		
		
//		Usuario usuario = usuarioDao.buscar(idUsuario);
//		Set<UsuarioAmigo> amigos = usuario.getAmigos();
//
//		Viaje viaje = viajeDao.buscar(idViaje);
//		Set<ViajeUsuario> usuarios = viaje.getUsuarios();
//		
//		for(ViajeUsuario usuarioViaje: usuarios){
//			UsuarioAmigo usuarioAmigo = new UsuarioAmigo(usuario, usuarioViaje.getUsuario());
//			amigos.remove(usuarioAmigo);
//		}
//		
//		List<UsuarioVo> amigosVo = new ArrayList<UsuarioVo>();
//		for(UsuarioAmigo amigoUsuario: amigos)
//			amigosVo.add(new UsuarioVo(amigoUsuario.getAmigo()));
		
		return amigosVo;
	}

	@Override
	@Transactional(readOnly = true)
	public List<UsuarioVo> obtenerUsuariosPorEstado(Integer idusuario, Integer idViaje, int idEstado) {
		Viaje viaje = viajeDao.buscar(idViaje);
		Set<ViajeUsuario> usuariosViaje = viaje.getUsuarios();
		
		List<UsuarioVo> usuarios = new ArrayList<UsuarioVo>();
		for(ViajeUsuario viajeUsuario: usuariosViaje){
			if(viajeUsuario.getEstado().getId().compareTo(idEstado) == 0){
				usuarios.add(new UsuarioVo(viajeUsuario.getUsuario()));
			}
		}
		return usuarios;
	}

	@Override
	@Transactional
	public void editarEstado(Integer idViaje, Integer idUsuario, Integer idEstado) {
		ViajeUsuarioId viajeUsuarioId = new ViajeUsuarioId(idUsuario, idViaje);
		Estado estado = estadoDao.buscar(idEstado);
		ViajeUsuario viajeUsuario = viajeUsuarioDao.buscar(viajeUsuarioId);
		
		viajeUsuario.setEstado(estado);

		viajeUsuarioDao.actualizar(viajeUsuario);
	}

	@Override
	@Transactional
	public void eliminarUsuario(Integer idInvitado, Integer idViaje) {
	
		Usuario usuario = usuarioDao.buscar(idInvitado);
		Set <ViajeUsuario> viajesUsuario = usuario.getViajes();
		
		Viaje viaje = viajeDao.buscar(idViaje);
		Set <ViajeUsuario> usuariosViaje = viaje.getUsuarios();
		
		ViajeUsuarioId viajeUsuarioId = new ViajeUsuarioId(idInvitado, idViaje);
		ViajeUsuario viajeUsuario = viajeUsuarioDao.buscar(viajeUsuarioId);
		
		usuariosViaje.remove(viajeUsuario);
		viajesUsuario.remove(viajeUsuario);
		
//		usuario.setViajes(viajesUsuario);
//		viaje.setUsuarios(usuariosViaje);
		
		viajeUsuarioDao.eliminar(viajeUsuario);
	}

	@Override
	@Transactional(readOnly = true)
	public ViajeUsuarioVo getViajeUsuario(Integer idUsuario, Integer idViaje) {
		ViajeUsuarioId viajeUsuarioId = new ViajeUsuarioId(idUsuario, idViaje);
		ViajeUsuario viajeUsuario = viajeUsuarioDao.buscar(viajeUsuarioId);
		
		if (viajeUsuario == null)
			return null;
		else
			return new ViajeUsuarioVo(viajeUsuario);
	}
/*
	@Override
	@Transactional(readOnly = true)
	public List<UsuarioVo> obtenerUsuariosEstadoRol(Integer idUsuario, Integer idViaje, Integer idEstado, Integer idRol) {
		Viaje viaje = viajeDao.buscar(idViaje);
		Set<ViajeUsuario> usuariosViaje = viaje.getUsuarios();
		
		List<UsuarioVo> usuarios = new ArrayList<UsuarioVo>();
		for(ViajeUsuario viajeUsuario: usuariosViaje){
			if(viajeUsuario.getEstado().getId().compareTo(idEstado) == 0 && viajeUsuario.getRol().getId().compareTo(idRol) == 0){
				usuarios.add(new UsuarioVo(viajeUsuario.getUsuario()));
			}
		}
		return usuarios;
	}*/

	@Override
	@Transactional
	public List<TemaVo> obtenerTemas(Integer idViaje) {
		Viaje viaje = viajeDao.buscar(idViaje);
		
		Set<Tema> temas = viaje.getTemas();
		List<TemaVo> temasVo = new ArrayList<TemaVo>();
		
		for(Tema tema: temas){
			temasVo.add(new TemaVo(tema));
		}
		return temasVo;
	} 
}
