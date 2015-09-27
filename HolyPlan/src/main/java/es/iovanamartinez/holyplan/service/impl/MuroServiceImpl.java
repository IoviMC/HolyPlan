package es.iovanamartinez.holyplan.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import es.iovanamartinez.holyplan.dao.MensajeDao;
import es.iovanamartinez.holyplan.dao.TemaDao;
import es.iovanamartinez.holyplan.dao.UsuarioDao;
import es.iovanamartinez.holyplan.dao.ViajeDao;
import es.iovanamartinez.holyplan.dominio.Mensaje;
import es.iovanamartinez.holyplan.dominio.Tema;
import es.iovanamartinez.holyplan.dominio.Usuario;
import es.iovanamartinez.holyplan.dominio.Viaje;
import es.iovanamartinez.holyplan.dominio.vo.MensajeVo;
import es.iovanamartinez.holyplan.dominio.vo.TemaVo;
import es.iovanamartinez.holyplan.service.MuroService;

@Component
public class MuroServiceImpl implements MuroService {
	@Autowired
	private TemaDao temaDao;
	
	@Autowired
	private ViajeDao viajeDao;
	
	@Autowired
	private UsuarioDao usuarioDao;
	
	@Autowired
	private MensajeDao mensajeDao;
	
	@Transactional
	@Override
	public TemaVo crearTema(TemaVo temaVo, MensajeVo mensaje, Integer idViaje, Integer idUsuario) {
		Tema tema = new Tema(temaVo);
		Viaje viaje = viajeDao.buscar(idViaje);
		
		tema.setViaje(viaje);
		
		Tema temaCreado = temaDao.crear(tema);
		crearMensaje(mensaje, temaCreado.getId(), idUsuario);
		return new TemaVo(temaCreado);
	}
	
	@Override
	@Transactional(readOnly = true)
	public TemaVo getTema(Integer idTema) {
		Tema tema = temaDao.buscar(idTema);
		
		if (tema == null)
			return null;
		else
			return new TemaVo(tema);
	}
	
	@Override
	@Transactional
	public void eliminarTema(Integer idTema) {
		Tema tema = temaDao.buscar(idTema);
		tema.setActivo(false);
		
		temaDao.actualizar(tema);
	}
	
	@Override
	@Transactional
	public MensajeVo crearMensaje(MensajeVo mensajeVo, Integer idTema, Integer idUsuario) {
		Mensaje mensaje = new Mensaje(mensajeVo);
		Tema tema = temaDao.buscar(idTema);
		Usuario usuario = usuarioDao.buscar(idUsuario);
		
		mensaje.setTema(tema);
		mensaje.setUsuario(usuario);
		mensajeDao.crear(mensaje);
		
		return new MensajeVo(mensaje);
	}

	@Override
	@Transactional(readOnly = true)
	public List<MensajeVo> obtenerMensajes(Integer idTema) {
		Tema tema = temaDao.buscar(idTema);
		//List<Mensaje> mensajes = tema.getMensajes();
		Set<Mensaje> mensajesPrueba = tema.getMensajes();
		
		List<MensajeVo> mensajesVo = new ArrayList<MensajeVo>();
		for(Mensaje mensaje: mensajesPrueba){
			mensajesVo.add(new MensajeVo(mensaje));
		}
		return mensajesVo;
	}

	@Override
	@Transactional
	public void eliminarMensaje(Integer idMensaje) {
		Mensaje mensaje = mensajeDao.buscar(idMensaje);
		Tema tema = mensaje.getTema();
		tema.getMensajes().remove(mensaje);
		
		mensajeDao.eliminar(mensaje);
	}
}
