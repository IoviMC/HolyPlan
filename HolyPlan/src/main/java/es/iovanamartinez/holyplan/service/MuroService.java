package es.iovanamartinez.holyplan.service;

import java.util.List;

import es.iovanamartinez.holyplan.dominio.vo.MensajeVo;
import es.iovanamartinez.holyplan.dominio.vo.TemaVo;

public interface MuroService {
	
	TemaVo crearTema(TemaVo tema, MensajeVo mensaje, Integer idViaje, Integer idUsuario);

	MensajeVo crearMensaje(MensajeVo mensajeVo, Integer idTema, Integer idUsuario);

	List<MensajeVo> obtenerMensajes(Integer idTema);

	TemaVo getTema(Integer idTema);

	void eliminarTema(Integer idTema);

	void eliminarMensaje(Integer idMensaje);
}
