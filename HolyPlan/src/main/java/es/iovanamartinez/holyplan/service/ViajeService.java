package es.iovanamartinez.holyplan.service;

import org.springframework.stereotype.Service;

import es.iovanamartinez.holyplan.dominio.vo.UsuarioVo;
import es.iovanamartinez.holyplan.dominio.vo.ViajeVo;

@Service
public interface ViajeService {
	ViajeVo crearViaje(UsuarioVo usuario, ViajeVo viaje);
	void crearViajeUsuario(UsuarioVo usuario, ViajeVo viaje);
}
