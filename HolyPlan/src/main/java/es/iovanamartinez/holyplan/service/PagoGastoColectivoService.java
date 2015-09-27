package es.iovanamartinez.holyplan.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import es.iovanamartinez.holyplan.dominio.vo.PagoGastoColectivoVo;
import es.iovanamartinez.holyplan.dominio.vo.UsuarioVo;



public interface PagoGastoColectivoService {

	List<PagoGastoColectivoVo> obtenerPagos(Integer idViaje);

	HashMap<String, BigDecimal> pagosTotalesPorUsuario(List<PagoGastoColectivoVo> pagos, Integer idViaje);

	void crearPagoGastoColectivoBote(Integer idGasto, Integer idViaje);

	void crearPagoGastoColectivoTodosUsuarios(Integer idGasto, Integer idViaje);

	void crearPagoGastoColectivoPersona(Integer idGasto, Integer idUsuarioPago, Integer idViaje);

	List<UsuarioVo> obtenerUsuariosConDeuda(Integer idViaje);

	void saldarCuenta(Integer idViaje);

	void modificarPagoDeudaPersona(Integer idPago, String[] nuevosPagosIdString);

	List<PagoGastoColectivoVo> obtenerDeudasDeUnPago(Integer idPago);
}
