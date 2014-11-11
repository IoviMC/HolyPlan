package es.iovanamartinez.holyplan.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.iovanamartinez.holyplan.dao.RolDao;
import es.iovanamartinez.holyplan.dao.UsuarioDao;
import es.iovanamartinez.holyplan.dao.ViajeDao;
import es.iovanamartinez.holyplan.dao.ViajeUsuarioDao;
import es.iovanamartinez.holyplan.dominio.Viaje;
import es.iovanamartinez.holyplan.dominio.vo.UsuarioVo;
import es.iovanamartinez.holyplan.dominio.vo.ViajeVo;
import es.iovanamartinez.holyplan.service.ViajeService;

@Service
public class ViajeServiceImpl implements ViajeService{
	//private static final int ID_ROL_CREADOR = 3;
	
	@Autowired
	private ViajeDao viajeDao;
	@Autowired
	private RolDao rolDao;
	@Autowired
	private ViajeUsuarioDao viajeUsuarioDao;
	@Autowired
	private UsuarioDao usuarioDao;
	
	@Transactional(propagation = Propagation.SUPPORTS)
	//@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public ViajeVo crearViaje(final UsuarioVo usuarioVo, final ViajeVo viajeVo) {
		Viaje nuevoViaje = new Viaje(viajeVo.getNombreViaje(), viajeVo.getFecha(), viajeVo.getDuracion());
		
		Viaje viajeCreado = viajeDao.crear(nuevoViaje);
		
//		Rol rol = rolDao.buscar(ID_ROL_CREADOR);
//		Usuario administrador = usuarioDao.buscar(usuarioVo.getId());
//		ViajeUsuario viajeUsuario = new ViajeUsuario(administrador, viajeCreado, rol);
//		
//		administrador.anadirViaje(viajeUsuario);
		//viajeCreado.anadirViajeUsuario(viajeUsuario);
		
		//viajeUsuarioDao.crear(viajeUsuario);
		//usuarioDao.actualizar(administrador);
//		viajeCreado.setDuracion(1);
//		viajeDao.actualizar(viajeCreado);

		//TODO comprobar si esto lo necesito
		//nuevoViaje.anadirViajeUsuario(viajeUsuario);
		ViajeVo nuevoViajeVo = new ViajeVo(viajeCreado);
		
		return nuevoViajeVo;
	}
	
	@Override
	@Transactional
	public void crearViajeUsuario(final UsuarioVo usuario, final ViajeVo viaje) {
		Viaje viajeRecuperado = viajeDao.buscar(viaje.getId());
		viajeRecuperado.setDuracion(1);
		viajeDao.actualizar(viajeRecuperado);
	}
}
