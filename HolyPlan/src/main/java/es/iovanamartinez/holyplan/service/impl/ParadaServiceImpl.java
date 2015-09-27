package es.iovanamartinez.holyplan.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.iovanamartinez.holyplan.dao.ParadaDao;
import es.iovanamartinez.holyplan.dao.PlaningDao;
import es.iovanamartinez.holyplan.dominio.Parada;
import es.iovanamartinez.holyplan.dominio.Planing;
import es.iovanamartinez.holyplan.dominio.vo.ParadaVo;
import es.iovanamartinez.holyplan.service.ParadaService;

@Service
public class ParadaServiceImpl implements ParadaService {
	
	@Autowired
	ParadaDao paradaDao;
	
	@Autowired
	PlaningDao planingDao;
	
	@Override
	@Transactional
	public ParadaVo crearParada(String nombre, String lugar, Date fecha, Integer idPlaning) {
		Planing planing = planingDao.buscar(idPlaning);

		Parada parada = new Parada(nombre, lugar, fecha);
		parada.setPlaning(planing);
		
		parada = paradaDao.crear(parada);
		return new ParadaVo(parada);
	}

	@Override
	@Transactional
	public Integer eliminarParada(Integer idParada) {
		Parada parada = paradaDao.buscar(idParada);
		Integer idPlaning = parada.getPlaning().getId();
		
		paradaDao.eliminar(parada);
		return idPlaning;
	}
	
	@Override
	@Transactional
	public ParadaVo getParada(Integer idParada) {
		Parada parada = paradaDao.buscar(idParada);

		if (parada == null)
			return null;
		else
			return new ParadaVo(parada);
	}
}
