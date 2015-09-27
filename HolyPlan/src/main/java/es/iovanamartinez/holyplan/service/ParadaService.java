package es.iovanamartinez.holyplan.service;

import java.util.Date;

import es.iovanamartinez.holyplan.dominio.vo.ParadaVo;

public interface ParadaService {

	ParadaVo crearParada(String nombre, String lugar, Date fecha, Integer idPlaning);

	Integer eliminarParada(Integer idParada);

	ParadaVo getParada(Integer idParada);

}
