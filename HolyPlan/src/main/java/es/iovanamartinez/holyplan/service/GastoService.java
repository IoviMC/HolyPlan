package es.iovanamartinez.holyplan.service;

import java.math.BigDecimal;
import java.util.List;

import es.iovanamartinez.holyplan.dominio.vo.GastoColectivoVo;
import es.iovanamartinez.holyplan.dominio.vo.GastoIndividualVo;

public interface GastoService {

	List<GastoColectivoVo> obtenerGastosColectivos(Integer idViaje);
	void eliminarGastoColectivo(Integer idGasto);
	List<GastoIndividualVo> obtenerGastosIndividuales(Integer idViaje,Integer idUsuario);
	GastoIndividualVo crearGastoIndividual(GastoIndividualVo gastoIndividualVo, Integer idViaje, Integer idUsuario);
	void eliminarGastoIndividual(Integer idGasto);
	GastoColectivoVo getGastoColectivo(Integer idGasto);
	GastoIndividualVo getGastoIndividual(Integer idGasto);
	GastoColectivoVo editarGastoColectivo(GastoColectivoVo gasto);
	void editarGastoIndividual(GastoIndividualVo gasto);
	GastoColectivoVo crearGastoColectivo(GastoColectivoVo gastoColectivoVo,Integer idViaje);
	BigDecimal obtenerGastoTotalPorPersona(Integer idViaje);
	BigDecimal obtenerGastosPagoBote(Integer idViaje);
//	void saldarCuenta(Integer id);
//	List<GastoColectivoVo> obtenerGastosPagoBote(Integer id);

}
