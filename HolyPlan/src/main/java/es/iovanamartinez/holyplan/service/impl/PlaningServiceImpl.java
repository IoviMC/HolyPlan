package es.iovanamartinez.holyplan.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.iovanamartinez.holyplan.dao.PlaningDao;
import es.iovanamartinez.holyplan.dao.ViajeDao;
import es.iovanamartinez.holyplan.dominio.Parada;
import es.iovanamartinez.holyplan.dominio.Planing;
import es.iovanamartinez.holyplan.dominio.Viaje;
import es.iovanamartinez.holyplan.dominio.vo.ParadaVo;
import es.iovanamartinez.holyplan.dominio.vo.ParadaVoFechaComparator;
import es.iovanamartinez.holyplan.dominio.vo.PlaningVo;
import es.iovanamartinez.holyplan.service.PlaningService;

@Service
public class PlaningServiceImpl implements PlaningService {
	
	@Autowired
	PlaningDao planingDao;
	
	@Autowired
	ViajeDao viajeDao;
	
	@Override
	@Transactional
	public PlaningVo crearPlaning(String nombre, String descripcion, Integer idViaje) {
		Viaje viaje = viajeDao.buscar(idViaje);
		
		Planing planing = new Planing(nombre, descripcion);
		planing.setViaje(viaje);
		
		planing = planingDao.crear(planing);
		
		return new PlaningVo(planing);
	}
	
	@Override
	@Transactional(readOnly = true)
	public PlaningVo getPlaning(Integer idPlaning) {
		Planing planing = planingDao.buscar(idPlaning);
		
		if(planing == null)
			return null;
		else
			return new PlaningVo(planing);
	}

	@Override
	@Transactional	
	public void editarPlaning(Integer idPlaning, String nombre, String descripcion) {
		Planing planing = planingDao.buscar(idPlaning);
		planing.setNombre(nombre);
		planing.setDescripcion(descripcion);
		
		planingDao.actualizar(planing);		
	}

	@Override
	@Transactional
	public void eliminarPlaning(Integer idPlaning, Integer idViaje) {
		Planing planing = planingDao.buscar(idPlaning);
		Viaje viaje = viajeDao.buscar(idViaje);
		
		viaje.getPlanings().remove(planing);
		
		planingDao.eliminar(planing);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<ParadaVo> getParadas(Integer idPlaning) {
		Planing planing = planingDao.buscar(idPlaning);
		
		List<ParadaVo> paradas = new ArrayList<ParadaVo>();
		for (Parada parada: planing.getParadas()){
			paradas.add(new ParadaVo(parada));
		}
		paradas.sort(new ParadaVoFechaComparator());
		return paradas;
	}

	@Override
	public ParadaVo proximaParada(List<ParadaVo> paradas) {
		ParadaVo proximaParada;
		if (paradas.isEmpty())
			proximaParada = null;
		else {
			proximaParada = paradas.get(0);
			for (ParadaVo parada: paradas) {
				if (!parada.getFecha().before(new Date())) {
					proximaParada = parada;
					break;
				}
			}
		}
		return proximaParada;
	}
	
//	@Override
//	@Transactional(readOnly = true)
//	public List<ParadaVo> getParadas(List<PlaningVo> planings) {
//		Planing planing;
//		List<ParadaVo> paradas = new ArrayList<ParadaVo>();
//		
//		for (PlaningVo planingVo: planings) {
//			planing = planingDao.buscar(planingVo.getId());
//		
//			for (Parada parada: planing.getParadas()){
//				paradas.add(new ParadaVo(parada));
//			}
//		}
//		return paradas;
//	}
}
