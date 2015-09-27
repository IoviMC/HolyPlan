package es.iovanamartinez.holyplan.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import es.iovanamartinez.holyplan.dao.GastoColectivoDao;
import es.iovanamartinez.holyplan.dao.PagoGastoColectivoDao;
import es.iovanamartinez.holyplan.dao.UsuarioDao;
import es.iovanamartinez.holyplan.dao.ViajeDao;
import es.iovanamartinez.holyplan.dominio.GastoColectivo;
import es.iovanamartinez.holyplan.dominio.PagoGastoColectivo;
import es.iovanamartinez.holyplan.dominio.Viaje;
import es.iovanamartinez.holyplan.dominio.ViajeUsuario;
import es.iovanamartinez.holyplan.dominio.vo.PagoGastoColectivoVo;
import es.iovanamartinez.holyplan.dominio.vo.UsuarioVo;
import es.iovanamartinez.holyplan.service.PagoGastoColectivoService;

@Component
public class PagoGastoColectivoServiceImpl implements PagoGastoColectivoService{

	@Autowired
	private ViajeDao viajeDao;
	@Autowired
	private UsuarioDao usuarioDao;
	@Autowired
	private GastoColectivoDao gastoColectivoDao;
	@Autowired
	private PagoGastoColectivoDao pagoGastoColectivoDao;
	
	private static final BigDecimal IMPORTE_CERO = BigDecimal.ZERO;
	private static final boolean PAGO_GASTO_TOTAL = true;
	private static final Integer ID_ESTADO_ACEPTADO = 1;
	
	
	@Override
	@Transactional
	public void crearPagoGastoColectivoBote(Integer idGasto, Integer idViaje) {
		Viaje viaje = viajeDao.buscar(idViaje);
		GastoColectivo gastoColectivo = gastoColectivoDao.buscar(idGasto);
		
		Set<ViajeUsuario> viajeUsuarios = viaje.getUsuarios();
		BigDecimal importeIndividual = gastoColectivo.getImporte().divide(new BigDecimal(numeroUsuariosConfirmados(viaje)), 2, RoundingMode.HALF_UP);
		PagoGastoColectivo pagoGasto;
		
		viaje.setBote(viaje.getBote().subtract(gastoColectivo.getImporte()));
		viajeDao.actualizar(viaje);
		for (ViajeUsuario viajeUsuario: viajeUsuarios){
			if (viajeUsuario.getEstado().getId().compareTo(ID_ESTADO_ACEPTADO) == 0) {
				pagoGasto = new PagoGastoColectivo(viajeUsuario.getUsuario(), gastoColectivo, importeIndividual);
				pagoGastoColectivoDao.crear(pagoGasto);
			}
		}		
	}
	
	@Override
	@Transactional
	public void crearPagoGastoColectivoTodosUsuarios(Integer idGasto, Integer idViaje) {
		Viaje viaje = viajeDao.buscar(idViaje);
		GastoColectivo gastoColectivo = gastoColectivoDao.buscar(idGasto);
		
		Set<ViajeUsuario> viajeUsuarios = viaje.getUsuarios();
		BigDecimal importeIndividual = gastoColectivo.getImporte().divide(new BigDecimal(numeroUsuariosConfirmados(viaje)), 2, RoundingMode.HALF_UP);
		PagoGastoColectivo pagoGasto;
		
		for (ViajeUsuario viajeUsuario: viajeUsuarios){
			if (viajeUsuario.getEstado().getId().compareTo(ID_ESTADO_ACEPTADO) == 0) {
				pagoGasto = new PagoGastoColectivo(viajeUsuario.getUsuario(), gastoColectivo, importeIndividual);
				pagoGastoColectivoDao.crear(pagoGasto);
			}
		}		
	}
	
	@Override
	@Transactional
	public void crearPagoGastoColectivoPersona(Integer idGasto, Integer idUsuarioPago, Integer idViaje) {
		Viaje viaje = viajeDao.buscar(idViaje);
		GastoColectivo gastoColectivo = gastoColectivoDao.buscar(idGasto);
		
		Set<ViajeUsuario> viajeUsuarios = viaje.getUsuarios();
		PagoGastoColectivo pagoGasto;
		
		for (ViajeUsuario viajeUsuario: viajeUsuarios){
			if (viajeUsuario.getEstado().getId().compareTo(ID_ESTADO_ACEPTADO) == 0) {
				if (viajeUsuario.getUsuario().getId().compareTo(idUsuarioPago) == 0)
					pagoGasto = new PagoGastoColectivo(viajeUsuario.getUsuario(), gastoColectivo, gastoColectivo.getImporte(), PAGO_GASTO_TOTAL);
				else
					pagoGasto = new PagoGastoColectivo(viajeUsuario.getUsuario(), gastoColectivo, IMPORTE_CERO);
				pagoGastoColectivoDao.crear(pagoGasto);	
			}
		}		
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<PagoGastoColectivoVo> obtenerPagos(Integer idViaje) {
		Viaje viaje = viajeDao.buscar(idViaje);
		
		Set<PagoGastoColectivo> pagos = new HashSet<PagoGastoColectivo>();
		for(GastoColectivo gasto: viaje.getGastosColectivos()) {
//			if (!gasto.isPagoBote())
				pagos.addAll(gasto.getPagos());
		}
		
		List<PagoGastoColectivoVo> pagosVo = new ArrayList<PagoGastoColectivoVo>();
		for(PagoGastoColectivo pago: pagos) {
			if (pago.getPago().compareTo(IMPORTE_CERO) != 0)
				pagosVo.add(new PagoGastoColectivoVo(pago));
		}
		return pagosVo;
	}	

	@Override
	public HashMap<String, BigDecimal> pagosTotalesPorUsuario(List<PagoGastoColectivoVo> pagos, Integer idViaje) {
		
		BigDecimal importe;
		HashMap<String, BigDecimal> pagoTotalPorUsuario = new HashMap<String, BigDecimal>();
		for(PagoGastoColectivoVo pago: pagos){
			importe = pagoTotalPorUsuario.get(pago.getNombreUsuario());
			if (importe != null){
				pagoTotalPorUsuario.put(pago.getNombreUsuario(), importe.add(pago.getImporte()));
			}
			else {
				pagoTotalPorUsuario.put(pago.getNombreUsuario(), pago.getImporte());
			}
		}
		
		Viaje viaje = viajeDao.buscar(idViaje);
		for (ViajeUsuario viajeUsuario: viaje.getUsuarios()){
			if (!pagoTotalPorUsuario.containsKey(viajeUsuario.getUsuario().getNombreUsuario()) && viajeUsuario.getEstado().getId().compareTo(ID_ESTADO_ACEPTADO) == 0)
				pagoTotalPorUsuario.put(viajeUsuario.getUsuario().getNombreUsuario(), IMPORTE_CERO);
		}
		return pagoTotalPorUsuario;
	}

	@Override
	@Transactional(readOnly = true)
	public List<UsuarioVo> obtenerUsuariosConDeuda(Integer idPago) {
		PagoGastoColectivo pagoUsuario = pagoGastoColectivoDao.buscar(idPago);
		Set<PagoGastoColectivo> pagos = pagoUsuario.getGasto().getPagos();
		
		List<UsuarioVo> usuariosConDeuda = new ArrayList<UsuarioVo>();
		for(PagoGastoColectivo pago: pagos){
			if(pago.getPago().compareTo(IMPORTE_CERO) == 0){
				usuariosConDeuda.add(new UsuarioVo(pago.getUsuario()));
			}
		}
		return usuariosConDeuda;
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
	@Transactional
	public void saldarCuenta(Integer idViaje) {
		Viaje viaje = viajeDao.buscar(idViaje);
		BigDecimal importeIndividual;
		
		for(GastoColectivo gasto: viaje.getGastosColectivos()) {
			if(!gasto.isPagoBote()){
				importeIndividual = gasto.getImporte().divide(new BigDecimal(numeroUsuariosConfirmados(viaje)), 2, RoundingMode.HALF_UP);
				for(PagoGastoColectivo pago: gasto.getPagos()) {
					if (pago.getPago().compareTo(importeIndividual) != 0){
						pago.setPago(importeIndividual);
						pago.setPagoGastoTotal(false);
						pagoGastoColectivoDao.actualizar(pago);
					}		
				}
			}
		}
	}
	

	@Override
	@Transactional
	public void modificarPagoDeudaPersona(Integer idPago, String[] idNuevosPagos) {
		PagoGastoColectivo pagoUsuario = pagoGastoColectivoDao.buscar(idPago);
		BigDecimal importeUsuario = pagoUsuario.getGasto().getImporte().divide(new BigDecimal(pagoUsuario.getGasto().getPagos().size()), 2, RoundingMode.HALF_UP);
		PagoGastoColectivo pagoDeuda;
		
		for(int x = 0; x < idNuevosPagos.length; x++) {
			pagoDeuda = pagoGastoColectivoDao.buscar(Integer.parseInt(idNuevosPagos[x]));
			pagoDeuda.setPago(importeUsuario);
			pagoGastoColectivoDao.actualizar(pagoDeuda);
			pagoUsuario.setPago(pagoUsuario.getPago().subtract(importeUsuario));
		}
		if (pagoUsuario.getPago().compareTo(importeUsuario) == 0)
			pagoUsuario.setPagoGastoTotal(false);
		
		pagoGastoColectivoDao.actualizar(pagoUsuario);
	}

	
	@Override
	public List<PagoGastoColectivoVo> obtenerDeudasDeUnPago(Integer idPago) {
		PagoGastoColectivo pagoUsuario = pagoGastoColectivoDao.buscar(idPago);
		Set<PagoGastoColectivo> pagos = pagoUsuario.getGasto().getPagos();
		
		List<PagoGastoColectivoVo> pagosConDeuda = new ArrayList<PagoGastoColectivoVo>();
		for(PagoGastoColectivo pago: pagos){
			if(pago.getPago().compareTo(IMPORTE_CERO) == 0){
				pagosConDeuda.add(new PagoGastoColectivoVo(pago));
			}
		}
		return pagosConDeuda;
	}
}
