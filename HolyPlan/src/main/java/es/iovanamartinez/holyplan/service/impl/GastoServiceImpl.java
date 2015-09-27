package es.iovanamartinez.holyplan.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import es.iovanamartinez.holyplan.dao.GastoColectivoDao;
import es.iovanamartinez.holyplan.dao.GastoIndividualDao;
import es.iovanamartinez.holyplan.dao.UsuarioDao;
import es.iovanamartinez.holyplan.dao.ViajeDao;
import es.iovanamartinez.holyplan.dominio.GastoColectivo;
import es.iovanamartinez.holyplan.dominio.GastoIndividual;
import es.iovanamartinez.holyplan.dominio.Usuario;
import es.iovanamartinez.holyplan.dominio.Viaje;
import es.iovanamartinez.holyplan.dominio.ViajeUsuario;
import es.iovanamartinez.holyplan.dominio.vo.GastoColectivoVo;
import es.iovanamartinez.holyplan.dominio.vo.GastoIndividualVo;
import es.iovanamartinez.holyplan.service.GastoService;

@Component
public class GastoServiceImpl implements GastoService {
	@Autowired
	private ViajeDao viajeDao;
	@Autowired
	private GastoColectivoDao gastoColectivoDao;
	@Autowired
	private UsuarioDao usuarioDao;
	@Autowired
	private GastoIndividualDao gastoIndividualDao;

	private static final Integer ID_ESTADO_ACEPTADO = 1;
	
	@Override
	@Transactional(readOnly = true)
	public List<GastoColectivoVo> obtenerGastosColectivos(Integer idViaje) {
		Viaje viaje = viajeDao.buscar(idViaje);
		Set<GastoColectivo> gastosColectivos = viaje.getGastosColectivos();
		
		List<GastoColectivoVo> gastosColectivosVo= new ArrayList<GastoColectivoVo>();
		for(GastoColectivo gastoColectivo: gastosColectivos)
			gastosColectivosVo.add(new GastoColectivoVo(gastoColectivo));
		return gastosColectivosVo;
	}
	
	@Transactional
	@Override
	public GastoColectivoVo crearGastoColectivo(GastoColectivoVo gastoColectivoVo, Integer idViaje) {
		Viaje viaje = viajeDao.buscar(idViaje);
		
		GastoColectivo gastoColectivo = new GastoColectivo(gastoColectivoVo);
		gastoColectivo.setViaje(viaje);
		
		gastoColectivo = gastoColectivoDao.crear(gastoColectivo);
		return new GastoColectivoVo(gastoColectivo);
	}
	
	@Override
	@Transactional
	public void eliminarGastoColectivo(Integer idGasto) {
		GastoColectivo gastoColectivo = gastoColectivoDao.buscar(idGasto);
		
		Viaje viaje = gastoColectivo.getViaje();
		if(gastoColectivo.isPagoBote()) {
			viaje.setBote(viaje.getBote().add(gastoColectivo.getImporte()));
			viajeDao.actualizar(viaje);
		}
		
		Set<GastoColectivo> gastos = viaje.getGastosColectivos();
		gastos.remove(gastoColectivo);
		
		gastoColectivoDao.eliminar(gastoColectivo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<GastoIndividualVo> obtenerGastosIndividuales(Integer idViaje, Integer idUsuario) {
		Viaje viaje = viajeDao.buscar(idViaje);
		Set<GastoIndividual> gastosIndividuales = viaje.getGastosIndividuales();
		
		List<GastoIndividualVo> gastosIndividualesVo = new ArrayList<GastoIndividualVo>();
		for(GastoIndividual gastoIndividual: gastosIndividuales){
			if(gastoIndividual.getUsuario().getId().compareTo(idUsuario) == 0)
				gastosIndividualesVo.add(new GastoIndividualVo(gastoIndividual));
		}
		return gastosIndividualesVo;
	}

	@Override
	@Transactional
	public GastoIndividualVo crearGastoIndividual(GastoIndividualVo gastoIndividualVo, Integer idViaje, Integer idUsuario) {
		Viaje viaje = viajeDao.buscar(idViaje);
		Usuario usuario = usuarioDao.buscar(idUsuario);
		
		GastoIndividual gastoIndividual = new GastoIndividual(gastoIndividualVo);
		gastoIndividual.setUsuario(usuario);
		gastoIndividual.setViaje(viaje);
		
		gastoIndividual = gastoIndividualDao.crear(gastoIndividual);
		return new GastoIndividualVo(gastoIndividual);
	}

	@Override
	@Transactional
	public void eliminarGastoIndividual(Integer idGasto) {
		GastoIndividual gastoIndividual = gastoIndividualDao.buscar(idGasto);
		Viaje viaje = gastoIndividual.getViaje();
		
		Set<GastoIndividual> gastos = viaje.getGastosIndividuales();
		gastos.remove(gastoIndividual);
		
		gastoIndividualDao.eliminar(gastoIndividual);
	}

	@Override
	@Transactional(readOnly = true)
	public GastoColectivoVo getGastoColectivo(Integer idGasto) {
		GastoColectivo gasto = gastoColectivoDao.buscar(idGasto);

		if (gasto == null)
			return null;
		else
			return new GastoColectivoVo(gasto);
	}

	@Override
	@Transactional(readOnly = true)
	public GastoIndividualVo getGastoIndividual(Integer idGasto) {
		GastoIndividual gasto = gastoIndividualDao.buscar(idGasto);
		if (gasto == null)
			return null;
		else
			return new GastoIndividualVo(gasto);
	}

	@Override
	@Transactional
	public GastoColectivoVo editarGastoColectivo(final GastoColectivoVo gasto) {
		GastoColectivo gastoColectivo = gastoColectivoDao.buscar(gasto.getId());			
	
		Viaje viaje = gastoColectivo.getViaje();
		if (gastoColectivo.isPagoBote()) {
			viaje.setBote(viaje.getBote().add(gastoColectivo.getImporte()));
			viajeDao.actualizar(viaje);
		}
		
		viaje.getGastosColectivos().remove(gastoColectivo);
		gastoColectivoDao.eliminar(gastoColectivo);
		
		return crearGastoColectivo(gasto, viaje.getId());
	}

	@Override
	@Transactional(readOnly = true)
	public void editarGastoIndividual(final GastoIndividualVo gasto) {
		GastoIndividual gastoIndividual = gastoIndividualDao.buscar(gasto.getId());
		
		gastoIndividual.setConcepto(gasto.getConcepto());
		gastoIndividual.setImporte(gasto.getImporte());
		gastoIndividual.setFecha(gasto.getFecha());
		
		gastoIndividualDao.actualizar(gastoIndividual);
	}
	
	private Integer numeroUsuariosConfirmados(Viaje viaje){
		Integer contador = 0;
		for (ViajeUsuario viajeUsuario: viaje.getUsuarios()){
			if (viajeUsuario.getEstado().getId().compareTo(ID_ESTADO_ACEPTADO) == 0)
				contador ++;
		}
		return contador;
	}
	
	@Override
	@Transactional(readOnly = true)
	public BigDecimal obtenerGastoTotalPorPersona(Integer idViaje) {
		Viaje viaje = viajeDao.buscar(idViaje);
		
		BigDecimal importeTotal = BigDecimal.ZERO;
		for(GastoColectivo gasto: viaje.getGastosColectivos()) {
			importeTotal = importeTotal.add(gasto.getImporte());
		}
		
		return importeTotal.divide(new BigDecimal(numeroUsuariosConfirmados(viaje)), 2, RoundingMode.HALF_UP);
	}

	@Override
	@Transactional(readOnly = true)
	public BigDecimal obtenerGastosPagoBote(Integer idViaje) {
		Viaje viaje = viajeDao.buscar(idViaje);
		
		BigDecimal importeTotal = BigDecimal.ZERO;
		for(GastoColectivo gasto: viaje.getGastosColectivos()) {
			if (gasto.isPagoBote())
				importeTotal = importeTotal.add(gasto.getImporte());
		}
	
//		BigDecimal numUsuariosPersona = new BigDecimal(viaje.getUsuarios().size());
//		return importeTotal.divide(numUsuariosPersona, 2, RoundingMode.HALF_UP);
		return importeTotal;
	}
}
