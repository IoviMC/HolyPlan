package es.iovanamartinez.holyplan.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
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
import es.iovanamartinez.holyplan.dominio.CheckListViaje;
import es.iovanamartinez.holyplan.dominio.Estado;
import es.iovanamartinez.holyplan.dominio.Planing;
import es.iovanamartinez.holyplan.dominio.Rol;
import es.iovanamartinez.holyplan.dominio.Tema;
import es.iovanamartinez.holyplan.dominio.Usuario;
import es.iovanamartinez.holyplan.dominio.UsuarioAmigo;
import es.iovanamartinez.holyplan.dominio.Viaje;
import es.iovanamartinez.holyplan.dominio.ViajeUsuario;
import es.iovanamartinez.holyplan.dominio.ViajeUsuarioId;
import es.iovanamartinez.holyplan.dominio.vo.CheckListViajeVo;
import es.iovanamartinez.holyplan.dominio.vo.PlaningVo;
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
	private static final int ID_ESTADO_RECHAZADO = 2;
	
	
	@Autowired
	private ViajeDao viajeDao;
	@Autowired
	private RolDao rolDao;
	@Autowired
	private ViajeUsuarioDao viajeUsuarioDao;
	@Autowired
	private UsuarioDao usuarioDao;
	@Autowired 
	EstadoDao estadoDao;
	
	@Transactional
	@Override
	public ViajeUsuarioVo crearViaje(final Integer idUsuario, final ViajeVo viajeVo) {
		
		Viaje nuevoViaje = new Viaje(viajeVo.getNombreViaje(), viajeVo.getFecha(), viajeVo.getDuracion(), viajeVo.getDescripcion(), viajeVo.getBote());
		
		Viaje viajeCreado = viajeDao.crear(nuevoViaje);
		
		Rol rol = rolDao.buscar(ID_ROL_CREADOR);
		
		Estado estado = estadoDao.buscar(ID_ESTADO_ACEPTADO);
		
		Usuario usuario = usuarioDao.buscar(idUsuario);
		
		ViajeUsuario viajeUsuario = new ViajeUsuario(usuario, viajeCreado, rol, estado);
		
		viajeUsuarioDao.crear(viajeUsuario);
		return new ViajeUsuarioVo(viajeUsuario);
	}
	
	@Override
	@Transactional
	public ViajeVo getViaje(Integer idViaje) {
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
		viaje.setBote(viajeVo.getBote());
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
	@Transactional(readOnly = true)
	public List<UsuarioVo> obtenerUsuariosRol(Integer idViaje, Integer idRol) {
		Viaje viaje = viajeDao.buscar(idViaje);
		Set<ViajeUsuario> viajes = viaje.getUsuarios();
		
		List<UsuarioVo> usuariosVo = new ArrayList<UsuarioVo>();
		for(ViajeUsuario viajeUsuario: viajes){
			if (viajeUsuario.getRol().getId().compareTo(idRol) == 0)
					usuariosVo.add(new UsuarioVo(viajeUsuario.getUsuario()));
		}
		
		return usuariosVo;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<UsuarioVo> obtenerAmigosNoInvitados(Integer idUsuario, Integer idViaje) {
		Usuario usuario = usuarioDao.buscar(idUsuario);
		Set<UsuarioAmigo> amigosUsuario = usuario.getAmigos();
		
		Viaje viaje = viajeDao.buscar(idViaje);
		Set<ViajeUsuario> usuariosViaje = viaje.getUsuarios();

		List<Usuario> usuariosInvitados = new ArrayList<Usuario>();
		for(ViajeUsuario usuarioViaje: usuariosViaje){
			usuariosInvitados.add(usuarioViaje.getUsuario());
		}
		
		List<UsuarioVo> amigosVo = new ArrayList<UsuarioVo>();
		for(UsuarioAmigo amigoUsuario: amigosUsuario){
			if (!usuariosInvitados.contains(amigoUsuario.getAmigo()))
				amigosVo.add(new UsuarioVo(amigoUsuario.getAmigo()));
		}
		
		return amigosVo;
	}

	@Override
	@Transactional(readOnly = true)
	public List<UsuarioVo> obtenerUsuariosPorEstado(Integer idViaje, Integer idEstado) {
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
	
	@Override
	@Transactional(readOnly = true)
	public ViajeUsuarioVo getViajeUsuario(String nombreUsuario, Integer idViaje) {
		Usuario usuario = usuarioDao.buscarPorNombreUsuario(nombreUsuario);
		
		ViajeUsuarioId viajeUsuarioId = new ViajeUsuarioId(usuario.getId(), idViaje);
		ViajeUsuario viajeUsuario = viajeUsuarioDao.buscar(viajeUsuarioId);
		
		if (viajeUsuario == null)
			return null;
		else
			return new ViajeUsuarioVo(viajeUsuario);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<ViajeVo> obtenerViajesAntiguos(Integer idUsuario) {
		Usuario usuario = usuarioDao.buscar(idUsuario);
		Set<ViajeUsuario> viajesUsuario = usuario.getViajes();

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH,-1);

		List<ViajeVo> viajesVo = new ArrayList<ViajeVo>();
		for (ViajeUsuario viajeUsuario: viajesUsuario) {
			if (!viajeUsuario.getViaje().isCancelado() && viajeUsuario.getEstado().getId().compareTo(ID_ESTADO_RECHAZADO) != 0 && viajeUsuario.getViaje().getFecha().before(cal.getTime()))
				viajesVo.add(new ViajeVo(viajeUsuario.getViaje()));
		}

		return viajesVo;
	}

	@Override
	@Transactional
	public List<TemaVo> obtenerTemas(Integer idViaje) {
		Viaje viaje = viajeDao.buscar(idViaje);
		
		Set<Tema> temas = viaje.getTemas();
		List<TemaVo> temasVo = new ArrayList<TemaVo>();
		
		for(Tema tema: temas){
			if (tema.isActivo())
				temasVo.add(new TemaVo(tema));
		}
		return temasVo;
	}

	@Override
	@Transactional(readOnly = true)
	public List<CheckListViajeVo> obtenerCheckLists(Integer idViaje) {
		Viaje viaje = viajeDao.buscar(idViaje);
		
		List<CheckListViajeVo> checkLists = new ArrayList<CheckListViajeVo>();
		for(CheckListViaje checkList: viaje.getCheckLists()){
			checkLists.add(new CheckListViajeVo(checkList));
		}
		
		return checkLists;
	}

	@Override
	@Transactional
	public void cancelarViaje(Integer idViaje) {
		Viaje viaje = viajeDao.buscar(idViaje);
		viaje.setCancelado(true);
		viajeDao.actualizar(viaje);
	}

	@Override
	@Transactional(readOnly = true)
	public List<UsuarioVo> obtenerUsuarios(Integer idViaje) {
		Viaje viaje = viajeDao.buscar(idViaje);
		
		List<UsuarioVo> usuarios = new ArrayList<UsuarioVo>();
		for(ViajeUsuario viajeUsuario: viaje.getUsuarios()){
			if (viajeUsuario.getEstado().getId().compareTo(ID_ESTADO_ACEPTADO) == 0) {
				usuarios.add(new UsuarioVo(viajeUsuario.getUsuario()));
			}
		}
		return usuarios;
	}

	@Override
	@Transactional(readOnly = true)
	public List<PlaningVo> getPlanings(Integer idViaje) {
		Viaje viaje = viajeDao.buscar(idViaje);
		
		List<PlaningVo> planings = new ArrayList<PlaningVo>();
		for (Planing planing: viaje.getPlanings()){
			planings.add(new PlaningVo(planing));
		}
		
		return planings;
	}

	@Override
	@Transactional
	public void anadirOrganizador(Integer idOrganizador, Integer idViaje) {
		ViajeUsuarioId viajeUsuarioId = new ViajeUsuarioId(idOrganizador, idViaje);
		
		ViajeUsuario viajeUsuario = viajeUsuarioDao.buscar(viajeUsuarioId);
		Rol rol = rolDao.buscar(ID_ROL_ORGANIZADOR);
		
		viajeUsuario.setRol(rol);
		
		viajeUsuarioDao.actualizar(viajeUsuario);
	}

	@Override
	@Transactional
	public void anadirUsuario(String nombreUsuario, Integer idViaje) {
		Usuario usuario = usuarioDao.buscarPorNombreUsuario(nombreUsuario);
		Viaje viaje = viajeDao.buscar(idViaje);
		Rol rol = rolDao.buscar(ID_ROL_INVITADO);
		Estado estado = estadoDao.buscar(ID_ESTADO_PENDIENTE);
		
		ViajeUsuario viajeUsuario = new ViajeUsuario(usuario, viaje, rol, estado);
			
		viajeUsuarioDao.crear(viajeUsuario);	
	}
}
