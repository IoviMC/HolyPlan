package es.iovanamartinez.holyplan.controlador.gasto;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.iovanamartinez.holyplan.controlador.formulario.GastoForm;
import es.iovanamartinez.holyplan.controlador.validador.GastoValidador;
import es.iovanamartinez.holyplan.dominio.vo.GastoColectivoVo;
import es.iovanamartinez.holyplan.dominio.vo.GastoIndividualVo;
import es.iovanamartinez.holyplan.dominio.vo.PagoGastoColectivoVo;
import es.iovanamartinez.holyplan.dominio.vo.UsuarioVo;
import es.iovanamartinez.holyplan.dominio.vo.ViajeUsuarioVo;
import es.iovanamartinez.holyplan.service.GastoService;
import es.iovanamartinez.holyplan.service.PagoGastoColectivoService;
import es.iovanamartinez.holyplan.service.ViajeService;

@Controller
public class AccionesGastoControlador {
	
	@Autowired
	private GastoService gastoService;
	
	@Autowired
	private PagoGastoColectivoService pagoGastoColectivoService;
	
	@Autowired
	private ViajeService viajeService;
	
	@Autowired
	private GastoValidador gastoValidador;
	
	private static final int ID_ROL_INVITADO = 3;
	private static final int ID_ROL_CREADOR = 1;
	
	@RequestMapping(value = "/gasto/mostrarGastos", method = RequestMethod.GET)
	public String mostrarGasto(ModelMap model, HttpServletRequest request){
		HttpSession session = request.getSession();
		ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");
		
		List<GastoColectivoVo> gastosColectivos = gastoService.obtenerGastosColectivos(viajeUsuario.getViaje().getId());
		List<GastoIndividualVo> gastosIndividuales = gastoService.obtenerGastosIndividuales(viajeUsuario.getViaje().getId(), viajeUsuario.getUsuario().getId());
		List<UsuarioVo> usuarios = viajeService.obtenerUsuarios(viajeUsuario.getViaje().getId());
		
		GastoForm crearGastoColectivoForm = new GastoForm();
		GastoForm crearGastoIndividualForm = new GastoForm();
		
		model.addAttribute("crearGastoIndividualForm", crearGastoIndividualForm);
		model.addAttribute("crearGastoColectivoForm", crearGastoColectivoForm);
		model.addAttribute("gastosIndividuales", gastosIndividuales);
		model.addAttribute("gastosColectivos", gastosColectivos);
		model.addAttribute("idRol", viajeUsuario.getRol().getId());
		model.addAttribute("usuarios", usuarios);
		model.addAttribute("bote", viajeUsuario.getViaje().getBote());
		model.addAttribute("nombreViaje", viajeUsuario.getViaje().getNombreViaje());
		model.addAttribute("nombreUsuario", viajeUsuario.getUsuario().getNombreUsuario());
		model.addAttribute("idViaje", viajeUsuario.getViaje().getId());
		return "/private/gastos/mostrar-gastos";
	}
	
	@RequestMapping(value = "/gasto/eliminarGastoColectivo/{idGasto}", method = RequestMethod.GET)
	public String eliminarGastoColectivo(HttpServletRequest request, @PathVariable("idGasto") final Integer idGasto, ModelMap model){
		HttpSession session = request.getSession();
		ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");
		
		if (viajeUsuario.getRol().getId().compareTo(ID_ROL_INVITADO) == 0) {
			model.addAttribute("mensajeError", "No dispone del permiso necesario para acceder a esta secci&oacute;n");
			return "/public/error";
		}
		else {
			gastoService.eliminarGastoColectivo(idGasto);
			return "redirect:/gasto/mostrarGastos";
		}
	}
	
	@RequestMapping(value = "/gasto/eliminarGastoIndividual/{idGasto}", method = RequestMethod.GET)
	public String eliminarGastoIndividual(@PathVariable("idGasto") final Integer idGasto){
		gastoService.eliminarGastoIndividual(idGasto);
		
		return "redirect:/gasto/mostrarGastos";
	}
	
	@RequestMapping(value = "/gasto/modificarGastoIndividual/{idGasto}", method = RequestMethod.GET)
	public String modificarGastoIndividual(@PathVariable("idGasto") final Integer idGasto, ModelMap model, HttpServletRequest request){
		HttpSession session = request.getSession();
		ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");
		
		GastoIndividualVo gasto = gastoService.getGastoIndividual(idGasto);
		GastoForm editarGastoForm = new GastoForm(gasto.getId(), gasto.getConcepto(), gasto.getImporte(), gasto.getFecha());
		
		model.addAttribute("nombreUsuario", viajeUsuario.getUsuario().getNombreUsuario());
		model.addAttribute("editarGastoForm", editarGastoForm);
		model.addAttribute("idViaje", viajeUsuario.getViaje().getId());
		model.addAttribute("idRol", viajeUsuario.getRol().getId());
		return "/private/gastos/editar-gasto-individual";
	}
	
	@RequestMapping(value = "/gasto/modificarGastoIndividual", method = RequestMethod.POST)
	public String modificarGastoIndividual(@ModelAttribute @Valid GastoForm editarGastoForm, ModelMap model, BindingResult result){
//		HttpSession session = request.getSession();
//		ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");
		
		gastoValidador.validate(editarGastoForm, result);
		if (!result.hasErrors()) {
			GastoIndividualVo gasto = new GastoIndividualVo(editarGastoForm.getId(), editarGastoForm.getConcepto(), editarGastoForm.getImporte(), 
					editarGastoForm.getFecha());
			gastoService.editarGastoIndividual(gasto);
			
			return "redirect:/gasto/mostrarGastos";

//			model.addAttribute("nombreUsuario", viajeUsuario.getUsuario().getNombreUsuario());
//			model.addAttribute("editarGastoForm", editarGastoForm);
//			model.addAttribute("idViaje", viajeUsuario.getViaje().getId());
//			model.addAttribute("idRol", viajeUsuario.getRol().getId());
//			model.addAttribute("mensajeConfirmacion", "Cambios guardados con &eacute;xito");			
		}		
		return "/private/gastos/editar-gasto-individual";
	}
	
	@RequestMapping(value = "/gasto/modificarGastoColectivo/{idGasto}", method = RequestMethod.GET)
	public String modificarGastoColectivo(HttpServletRequest request, @PathVariable("idGasto") final Integer idGasto, ModelMap model){
		HttpSession session = request.getSession();
		ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");
		
		if (viajeUsuario.getRol().getId().compareTo(ID_ROL_INVITADO) == 0) {
			model.addAttribute("mensajeError", "No dispone del permiso necesario para acceder a esta secci&oacute;n");
			return "/public/error";
		}
		else {
			List<UsuarioVo> usuarios = viajeService.obtenerUsuarios(viajeUsuario.getViaje().getId());			
			
			GastoColectivoVo gasto = gastoService.getGastoColectivo(idGasto);
			GastoForm editarGastoForm = new GastoForm(gasto.getId(), gasto.getConcepto(), gasto.getImporte(), gasto.getFecha());
			
			model.addAttribute("usuarios", usuarios);
			model.addAttribute("idViaje", viajeUsuario.getViaje().getId());
			model.addAttribute("idRol", viajeUsuario.getRol().getId());
			model.addAttribute("editarGastoForm", editarGastoForm);
			model.addAttribute("bote", viajeUsuario.getViaje().getBote());
			model.addAttribute("nombreUsuario", viajeUsuario.getUsuario().getNombreUsuario());
			return "/private/gastos/editar-gasto-colectivo";
		}
	}
	
	@RequestMapping(value = "/gasto/modificarGastoColectivo", method = RequestMethod.POST)
	public String modificarGastoColectivo(HttpServletRequest request, @ModelAttribute @Valid GastoForm editarGastoForm, ModelMap model, BindingResult result){
		String idUsuarioPagoString = request.getParameter("idUsuario");
		Integer idUsuarioPago = Integer.parseInt(idUsuarioPagoString);
		HttpSession session = request.getSession();
		ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");
		
		if (viajeUsuario.getRol().getId().compareTo(ID_ROL_INVITADO) == 0) {
			model.addAttribute("mensajeError", "No dispone del permiso necesario para acceder a esta secci&oacute;n");
			return "/public/error";
		}
		else {
			gastoValidador.validate(editarGastoForm, result);
			if (!result.hasErrors()) {
				if (idUsuarioPago.compareTo(-1) == 0){
					if (viajeUsuario.getViaje().getBote().compareTo(editarGastoForm.getImporte()) == -1){
						model.addAttribute("mensajeError", "no es posible pagar con el bote, es insuficiente para este gasto");
						return "/public/error";
					}
					else {
						GastoColectivoVo gasto = new GastoColectivoVo(editarGastoForm.getId(), editarGastoForm.getConcepto(), 
								editarGastoForm.getImporte(), editarGastoForm.getFecha(), true);
						GastoColectivoVo gastoColectivoVo = gastoService.editarGastoColectivo(gasto);
						pagoGastoColectivoService.crearPagoGastoColectivoBote(gastoColectivoVo.getId(), viajeUsuario.getViaje().getId());
					}
				}
				else {
					GastoColectivoVo gasto = new GastoColectivoVo(editarGastoForm.getId(), editarGastoForm.getConcepto(), 
							editarGastoForm.getImporte(), editarGastoForm.getFecha(), false);
					GastoColectivoVo gastoColectivoVo = gastoService.editarGastoColectivo(gasto);

					if (idUsuarioPago.compareTo(0) == 0){
						pagoGastoColectivoService.crearPagoGastoColectivoTodosUsuarios(gastoColectivoVo.getId(), viajeUsuario.getViaje().getId());
					}
					else {
						pagoGastoColectivoService.crearPagoGastoColectivoPersona(gastoColectivoVo.getId(), idUsuarioPago, viajeUsuario.getViaje().getId());
//						pagoGastoColectivoService.crearPagoGastoColectivoPersona(gastoColectivoVo.getId(), idUsuarioPago);						
					}
				}				
				return "redirect:/gasto/mostrarGastos";
//				List<UsuarioVo> usuarios = viajeService.obtenerUsuarios(viajeUsuario.getViaje().getId());
//				
//				model.addAttribute("idViaje", viajeUsuario.getViaje().getId());
//				model.addAttribute("idRol", viajeUsuario.getRol().getId());
//				model.addAttribute("usuarios", usuarios);
//				model.addAttribute("editarGastoForm", editarGastoForm);
//				model.addAttribute("bote", viajeUsuario.getViaje().getBote());
//				model.addAttribute("nombreUsuario", viajeUsuario.getUsuario().getNombreUsuario());
			}
			return "/private/gastos/mostrar-gastos";
		}
	}
	
	@RequestMapping(value = "/gasto/verPagos", method = RequestMethod.GET)
	public String verPagos(ModelMap model, HttpServletRequest request){
		HttpSession session = request.getSession();
		ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");
		
		List<PagoGastoColectivoVo> pagos = pagoGastoColectivoService.obtenerPagos(viajeUsuario.getViaje().getId());
		HashMap<String, BigDecimal> pagoTotalPorUsuario = pagoGastoColectivoService.pagosTotalesPorUsuario(pagos, viajeUsuario.getViaje().getId());
		
//		Integer numeroUsuarios = viajeService.obtenerNumeroUsuarios(viajeUsuario.getViaje().getId());
		BigDecimal gastoTotalBote = gastoService.obtenerGastosPagoBote(viajeUsuario.getViaje().getId());
//		BigDecimal gastoTotalBoteUsuario = gastoService.importePorUsuario(gastoTotalBote, numeroUsuarios);
		BigDecimal gastoTotalUsuario = gastoService.obtenerGastoTotalPorPersona(viajeUsuario.getViaje().getId());
//		BigDecimal gastoTotalUsuario = gastoService.importePorUsuario(gastoTotalViaje, numeroUsuarios);
		
		model.addAttribute("nombreUsuario", viajeUsuario.getUsuario().getNombreUsuario());
		model.addAttribute("idViaje", viajeUsuario.getViaje().getId());
		model.addAttribute("pagos", pagos);
		model.addAttribute("pagoTotalPorUsuario", pagoTotalPorUsuario);
		model.addAttribute("gastoTotalBote", gastoTotalBote);
		model.addAttribute("bote", viajeUsuario.getViaje().getBote());
		model.addAttribute("gastoTotalUsuario", gastoTotalUsuario);
		model.addAttribute("idRol", viajeUsuario.getRol().getId());
		return "/private/gastos/pagos";
	}
	
	@RequestMapping(value = "/gasto/cobrarGastoColectivo/{idPago}", method = RequestMethod.GET)
	public String cobrarGastoColectivo(@PathVariable("idPago") final Integer idPago, ModelMap model, HttpServletRequest request){
		HttpSession session = request.getSession();
		ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");
		
		List<PagoGastoColectivoVo> deudas = pagoGastoColectivoService.obtenerDeudasDeUnPago(idPago);
		
		model.addAttribute("nombreUsuario", viajeUsuario.getUsuario().getNombreUsuario());
		model.addAttribute("deudas", deudas);
		model.addAttribute("idPago", idPago);
		model.addAttribute("idViaje", viajeUsuario.getViaje().getId());
		model.addAttribute("idRol", viajeUsuario.getRol().getId());
		return "/private/gastos/cobrar_gasto_colectivo";
	}
	
	@RequestMapping(value = "/gasto/cobrarGastoColectivo/{idPago}", method = RequestMethod.POST)
	public String invitarAmigos(@PathVariable("idPago") final Integer idPago, HttpServletRequest request, ModelMap model){
		HttpSession session = request.getSession();
		ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");
		String[] deudasIdString = request.getParameterValues("deudasId");
		
		pagoGastoColectivoService.modificarPagoDeudaPersona(idPago, deudasIdString);
		
		List<PagoGastoColectivoVo> deudas = pagoGastoColectivoService.obtenerDeudasDeUnPago(idPago);
		
		model.addAttribute("mensajeConfirmacion", "Deuda amortizada");
		model.addAttribute("nombreUsuario", viajeUsuario.getUsuario().getNombreUsuario());
		model.addAttribute("deudas", deudas);
		model.addAttribute("idPago", idPago);
		model.addAttribute("idViaje", viajeUsuario.getViaje().getId());
		model.addAttribute("idRol", viajeUsuario.getRol().getId());
		return "/private/gastos/cobrar_gasto_colectivo";
	}
	
	@RequestMapping(value = "/gasto/saldarCuenta", method = RequestMethod.GET)
	public String saldarCuenta(HttpServletRequest request, ModelMap model){
		HttpSession session = request.getSession();
		ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");
		
		if (viajeUsuario.getRol().getId().compareTo(ID_ROL_CREADOR) == 0) {
			pagoGastoColectivoService.saldarCuenta(viajeUsuario.getViaje().getId());
			return "redirect:/gasto/verPagos";
		}
		else {
			model.addAttribute("mensajeError", "No dispone del permiso necesario para acceder a esta secci&oacute;n");
			return "/public/error";
		}
	}
	
//	@RequestMapping(value = "/gasto/cobrarGastoColectivo/{idPago}", method = RequestMethod.POST)
//	public String invitarAmigos(@PathVariable("idPago") final Integer idPago, HttpServletRequest request, ModelMap model){
//		String[] usuariosIdString = request.getParameterValues("usuariosId");
//		
//		pagoGastoColectivoService.modificarPagoDeudaPersona(idPago, usuariosIdString);
//
////		PagoGastoColectivoVo pago = pagoGastoColectivoService.getPago(idPago);
//		
////		for (int x = 0; x < usuariosIdString.length; x++) 
////			pagoGastoColectivoService.modificarPagoDeudaPersona(idPago, Integer.parseInt(usuariosIdString[x]));
//		
////		pagoGastoColectivoService.amortizarDeuda(pago.getId(), usuariosIdString.length);
//		return "redirect:/gasto/verPagos";
//	}
}
