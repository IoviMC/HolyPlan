package es.iovanamartinez.holyplan.service;

import java.util.List;

import es.iovanamartinez.holyplan.dominio.vo.ParadaVo;
import es.iovanamartinez.holyplan.dominio.vo.PlaningVo;

public interface PlaningService {

	PlaningVo crearPlaning(String nombre, String descripcion, Integer idViaje);

	PlaningVo getPlaning(Integer idPlaning);

//	List<ParadaVo> getParadas(List<PlaningVo> planings);

	void editarPlaning(Integer idPlaning, String nombre, String descripcion);

	void eliminarPlaning(Integer idPlaning, Integer idViaje);

	List<ParadaVo> getParadas(Integer idPlaning);

	ParadaVo proximaParada(List<ParadaVo> paradas);
}
