package es.iovanamartinez.holyplan.controlador.historicoViaje;

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

import es.iovanamartinez.holyplan.controlador.formulario.CrearMensajeForm;
import es.iovanamartinez.holyplan.controlador.formulario.EditarCheckListViajeForm;
import es.iovanamartinez.holyplan.controlador.formulario.GastoForm;
import es.iovanamartinez.holyplan.controlador.validador.CrearMensajeValidador;
import es.iovanamartinez.holyplan.controlador.validador.GastoValidador;
import es.iovanamartinez.holyplan.dominio.vo.CheckListViajeVo;
import es.iovanamartinez.holyplan.dominio.vo.GastoColectivoVo;
import es.iovanamartinez.holyplan.dominio.vo.GastoIndividualVo;
import es.iovanamartinez.holyplan.dominio.vo.ItemListViajeVo;
import es.iovanamartinez.holyplan.dominio.vo.MensajeVo;
import es.iovanamartinez.holyplan.dominio.vo.PagoGastoColectivoVo;
import es.iovanamartinez.holyplan.dominio.vo.ParadaVo;
import es.iovanamartinez.holyplan.dominio.vo.PlaningVo;
import es.iovanamartinez.holyplan.dominio.vo.TemaVo;
import es.iovanamartinez.holyplan.dominio.vo.UsuarioVo;
import es.iovanamartinez.holyplan.dominio.vo.ViajeUsuarioVo;
import es.iovanamartinez.holyplan.dominio.vo.ViajeVo;
import es.iovanamartinez.holyplan.service.CheckListViajeService;
import es.iovanamartinez.holyplan.service.GastoService;
import es.iovanamartinez.holyplan.service.MuroService;
import es.iovanamartinez.holyplan.service.PagoGastoColectivoService;
import es.iovanamartinez.holyplan.service.PlaningService;
import es.iovanamartinez.holyplan.service.ViajeService;

@Controller
public class AccionesHistoricoViajeControlador {
	@Autowired
	ViajeService viajeService;
	
	@Autowired
	GastoService gastoService;
	
	@Autowired
	MuroService muroService;
	
	@Autowired
	PlaningService planingService;
	
	@Autowired
	CheckListViajeService checkListViajeService;
	
	@Autowired
	PagoGastoColectivoService pagoGastoColectivoService;
	
	@Autowired
	private GastoValidador gastoValidador;
	
	@Autowired
	private CrearMensajeValidador crearMensajeValidador;
	
	private static final int ID_ESTADO_ACEPTADO = 1;
	private static final int ID_ESTADO_PENDIENTE = 0;
	private static final int ID_ROL_INVITADO = 3;
	private static final int ID_ROL_CREADOR = 1;

	@RequestMapping(value = "/viaje/mostrarHistorialViajes", method = RequestMethod.GET)
	public String mostrarHistorialViajes(HttpServletRequest request, ModelMap model){
		HttpSession session = request.getSession(true);
		UsuarioVo usuario = (UsuarioVo) session.getAttribute("usuario");
			
		List<ViajeVo> viajes = viajeService.obtenerViajesAntiguos(usuario.getId());
		
		model.addAttribute("viajes", viajes);
		model.addAttribute("nombreUsuario", usuario.getNombreUsuario());
		return "private/historico/historico-viajes";
	}
	
	@RequestMapping(value = "/historico/mostrarViajeHistorial/{id}", method = RequestMethod.GET)
	public String mostrarViajeHistorico(@PathVariable("id") final Integer idViaje, ModelMap model, HttpServletRequest request){
		HttpSession session = request.getSession();
		UsuarioVo usuario = (UsuarioVo) session.getAttribute("usuario");
		
		ViajeUsuarioVo viajeUsuario = viajeService.getViajeUsuario(usuario.getId(), idViaje);
		
		if (viajeUsuario == null) {
			model.addAttribute("mensajeError", "No dispone del permiso necesario para acceder a esta secci&oacute;n");
			return "/public/error";
		}
		else {
			session.setAttribute("viajeUsuario", viajeUsuario);
			
			List<UsuarioVo> usuariosConfirmados = viajeService.obtenerUsuariosPorEstado(idViaje, ID_ESTADO_ACEPTADO);
			List<UsuarioVo> usuariosPendientes = viajeService.obtenerUsuariosPorEstado(idViaje, ID_ESTADO_PENDIENTE);
			List<TemaVo> temas = viajeService.obtenerTemas(idViaje);
			List<CheckListViajeVo> checkLists = viajeService.obtenerCheckLists(idViaje);
			List<PlaningVo> planings = viajeService.getPlanings(idViaje);
			
			model.addAttribute("checkLists", checkLists);
			model.addAttribute("temas", temas);
			model.addAttribute("usuariosConfirmados", usuariosConfirmados);
			model.addAttribute("usuariosPendientes", usuariosPendientes);
			model.addAttribute("viaje", viajeUsuario.getViaje());
			model.addAttribute("nombreUsuario", usuario.getNombreUsuario());
			model.addAttribute("planings", planings);

			return "/private/historico/historico-detalle-viaje";
		}
	}
	
	@RequestMapping(value = "/historico/mostrarGastos", method = RequestMethod.GET)
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
		return "/private/historico/mostrar-gastos";
	}
	
	@RequestMapping(value = "/historico/crearGastoColectivo", method = RequestMethod.POST)
	public String crearGastoColectivo(HttpServletRequest request, @ModelAttribute("crearGastoColectivoForm") GastoForm crearGastoColectivoForm, BindingResult result, ModelMap model){
		String idUsuarioPagoString = request.getParameter("idUsuario");
		Integer idUsuarioPago = Integer.parseInt(idUsuarioPagoString);
		HttpSession session = request.getSession();
		ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");
		
		if (viajeUsuario.getRol().getId().compareTo(ID_ROL_INVITADO) == 0) {
			model.addAttribute("mensajeError", "No dispone del permiso necesario para acceder a esta secci&oacute;n");
			return "/public/error";
		}
		else {
			gastoValidador.validate(crearGastoColectivoForm, result);
			if (!result.hasErrors()) {
				if (idUsuarioPago.compareTo(-1) == 0){
					if (viajeUsuario.getViaje().getBote().compareTo(crearGastoColectivoForm.getImporte()) == -1){
						model.addAttribute("mensajeError", "No es posible pagar con el bote, es insuficiente para este gasto");
						return "/public/error";
					}
					else {
						GastoColectivoVo gastoColectivoVo = new GastoColectivoVo(crearGastoColectivoForm.getConcepto(), 
								crearGastoColectivoForm.getImporte(), crearGastoColectivoForm.getFecha(), true);
						gastoColectivoVo = gastoService.crearGastoColectivo(gastoColectivoVo, viajeUsuario.getViaje().getId());
						pagoGastoColectivoService.crearPagoGastoColectivoBote(gastoColectivoVo.getId(), viajeUsuario.getViaje().getId());
					}
				}
				else {
					GastoColectivoVo gastoColectivoVo = new GastoColectivoVo(crearGastoColectivoForm.getConcepto(), 
							crearGastoColectivoForm.getImporte(), crearGastoColectivoForm.getFecha(), false);
					gastoColectivoVo = gastoService.crearGastoColectivo(gastoColectivoVo, viajeUsuario.getViaje().getId());
					if (idUsuarioPago.compareTo(0) == 0){
						pagoGastoColectivoService.crearPagoGastoColectivoTodosUsuarios(gastoColectivoVo.getId(), viajeUsuario.getViaje().getId());
					}
					else {
						pagoGastoColectivoService.crearPagoGastoColectivoPersona(gastoColectivoVo.getId(), idUsuarioPago, viajeUsuario.getViaje().getId());
//						pagoGastoService.crearPagoGastoColectivoPersona(gastoColectivoVo.getId(), idUsuarioPago);						
					}
				}				
				return "redirect:/historico/mostrarGastos";
			}
			return "/private/historico/mostrar-gastos";
		}
	}

	
	@RequestMapping(value = "/historico/crearGastoIndividual", method = RequestMethod.POST)
	public String crearGastoIndividual(@ModelAttribute("crearGastoIndividualForm") GastoForm crearGastoIndividualForm, ModelMap model, BindingResult result, HttpServletRequest request){
		HttpSession session = request.getSession();
		ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");
		
		gastoValidador.validate(crearGastoIndividualForm, result);
		if (!result.hasErrors()) {
			GastoIndividualVo gastoIndividualVo = new GastoIndividualVo(crearGastoIndividualForm.getConcepto(), 
					crearGastoIndividualForm.getImporte(), crearGastoIndividualForm.getFecha());
			
			gastoService.crearGastoIndividual(gastoIndividualVo, viajeUsuario.getViaje().getId(), viajeUsuario.getUsuario().getId());
			//model.addAttribute("mensajeConfirmacion", "Gasto a&ntilde;adido correctamente");
			return "redirect:/historico/mostrarGastos";
		}
		return "/private/historico/mostrar-gastos";
		//return "redirect:/gasto/mostrarGastos";
	}
	
	@RequestMapping(value = "/historico/eliminarGastoColectivo/{idGasto}", method = RequestMethod.GET)
	public String eliminarGastoColectivo(HttpServletRequest request, @PathVariable("idGasto") final Integer idGasto, ModelMap model){
		HttpSession session = request.getSession();
		ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");
		
		if (viajeUsuario.getRol().getId().compareTo(ID_ROL_INVITADO) == 0) {
			model.addAttribute("mensajeError", "No dispone del permiso necesario para acceder a esta secci&oacute;n");
			return "/public/error";
		}
		else {
			gastoService.eliminarGastoColectivo(idGasto);
			return "redirect:/historico/mostrarGastos";
		}
	}
	
	@RequestMapping(value = "/historico/eliminarGastoIndividual/{idGasto}", method = RequestMethod.GET)
	public String eliminarGastoIndividual(@PathVariable("idGasto") final Integer idGasto){
		gastoService.eliminarGastoIndividual(idGasto);
		
		return "redirect:/historico/mostrarGastos";
	}
	
	@RequestMapping(value = "/historico/modificarGastoIndividual/{idGasto}", method = RequestMethod.GET)
	public String modificarGastoIndividual(@PathVariable("idGasto") final Integer idGasto, ModelMap model, HttpServletRequest request){
		HttpSession session = request.getSession();
		ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");
		
		GastoIndividualVo gasto = gastoService.getGastoIndividual(idGasto);
		GastoForm editarGastoForm = new GastoForm(gasto.getId(), gasto.getConcepto(), gasto.getImporte(), gasto.getFecha());
		
		model.addAttribute("nombreUsuario", viajeUsuario.getUsuario().getNombreUsuario());
		model.addAttribute("editarGastoForm", editarGastoForm);
		model.addAttribute("idViaje", viajeUsuario.getViaje().getId());
		model.addAttribute("idRol", viajeUsuario.getRol().getId());
		return "/private/historico/editar-gasto-individual";
	}
	
	@RequestMapping(value = "/historico/modificarGastoIndividual", method = RequestMethod.POST)
	public String modificarGastoIndividual(@ModelAttribute @Valid GastoForm editarGastoForm, ModelMap model, BindingResult result){
//		HttpSession session = request.getSession();
//		ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");
		
		gastoValidador.validate(editarGastoForm, result);
		if (!result.hasErrors()) {
			GastoIndividualVo gasto = new GastoIndividualVo(editarGastoForm.getId(), editarGastoForm.getConcepto(), editarGastoForm.getImporte(), 
					editarGastoForm.getFecha());
			gastoService.editarGastoIndividual(gasto);
			
//			model.addAttribute("nombreUsuario", viajeUsuario.getUsuario().getNombreUsuario());
//			model.addAttribute("editarGastoForm", editarGastoForm);
//			model.addAttribute("idViaje", viajeUsuario.getViaje().getId());
//			model.addAttribute("idRol", viajeUsuario.getRol().getId());
//			model.addAttribute("mensajeConfirmacion", "Cambios guardados con &eacute;xito");
			return "redirect:/historico/mostrarGastos";
			
		}		
		return "/private/historico/editar-gasto-individual";
	}
	
	@RequestMapping(value = "/historico/modificarGastoColectivo/{idGasto}", method = RequestMethod.GET)
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
			return "/private/historico/editar-gasto-colectivo";
		}
	}
	
	@RequestMapping(value = "/historico/modificarGastoColectivo", method = RequestMethod.POST)
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
				
				return "redirect:/historico/mostrarGastos";
//				List<UsuarioVo> usuarios = viajeService.obtenerUsuarios(viajeUsuario.getViaje().getId());
//				
//				model.addAttribute("idViaje", viajeUsuario.getViaje().getId());
//				model.addAttribute("idRol", viajeUsuario.getRol().getId());
//				model.addAttribute("usuarios", usuarios);
//				model.addAttribute("editarGastoForm", editarGastoForm);
//				model.addAttribute("bote", viajeUsuario.getViaje().getBote());
//				model.addAttribute("nombreUsuario", viajeUsuario.getUsuario().getNombreUsuario());
			}
			return "/private/historico/mostrar-gastos";
		}
	}
	
	@RequestMapping(value = "/historico/verPagos", method = RequestMethod.GET)
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
		return "/private/historico/pagos";
	}
	
	@RequestMapping(value = "/historico/cobrarGastoColectivo/{idPago}", method = RequestMethod.GET)
	public String cobrarGastoColectivo(@PathVariable("idPago") final Integer idPago, ModelMap model, HttpServletRequest request){
		HttpSession session = request.getSession();
		ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");
		
		List<PagoGastoColectivoVo> deudas = pagoGastoColectivoService.obtenerDeudasDeUnPago(idPago);
		
		model.addAttribute("nombreUsuario", viajeUsuario.getUsuario().getNombreUsuario());
		model.addAttribute("deudas", deudas);
		model.addAttribute("idPago", idPago);
		model.addAttribute("idViaje", viajeUsuario.getViaje().getId());
		model.addAttribute("idRol", viajeUsuario.getRol().getId());
		return "/private/historico/cobrar_gasto_colectivo";
	}
	
	@RequestMapping(value = "/historico/cobrarGastoColectivo/{idPago}", method = RequestMethod.POST)
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
		return "/private/historico/cobrar_gasto_colectivo";
	}
	
	@RequestMapping(value = "/historico/saldarCuenta", method = RequestMethod.GET)
	public String saldarCuenta(HttpServletRequest request, ModelMap model){
		HttpSession session = request.getSession();
		ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");
		
		if (viajeUsuario.getRol().getId().compareTo(ID_ROL_CREADOR) == 0) {
			pagoGastoColectivoService.saldarCuenta(viajeUsuario.getViaje().getId());
			return "redirect:/historico/verPagos";
		}
		else {
			model.addAttribute("mensajeError", "No dispone del permiso necesario para acceder a esta secci&oacute;n");
			return "/public/error";
		}
	}

	@RequestMapping(value = "/historico/mostrarMensajes/{id}", method = RequestMethod.GET)
	public String mostrarMensajeForm(@PathVariable("id") final Integer idTema, ModelMap model, HttpServletRequest request){
		HttpSession session = request.getSession();
		ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");
		
		TemaVo tema = muroService.getTema(idTema);
		if (tema.isActivo()) {
			CrearMensajeForm crearMensajeForm = new CrearMensajeForm();
			
			List<MensajeVo> mensajes = muroService.obtenerMensajes(idTema);
			
			model.addAttribute("tema", tema);
			model.addAttribute("idViaje", viajeUsuario.getViaje().getId());
			model.addAttribute("mensajes", mensajes);
			model.addAttribute("crearMensajeForm", crearMensajeForm);
			model.addAttribute("nombreUsuario", viajeUsuario.getUsuario().getNombreUsuario());
			return "/private/historico/mostrar-mensajes";
		}
		else
			return "/public/error";
	}
	
	@RequestMapping(value = "/historico/crearMensaje/{id}", method = RequestMethod.POST)
	public String crearMensaje(@PathVariable("id") final Integer idTema, @ModelAttribute CrearMensajeForm crearMensajeForm, BindingResult result, HttpServletRequest request){
		
		crearMensajeValidador.validate(crearMensajeForm, result);
		if (!result.hasErrors()){
			HttpSession session = request.getSession();
			UsuarioVo usuario = (UsuarioVo) session.getAttribute("usuario");
			
			MensajeVo mensaje = new MensajeVo(crearMensajeForm.getDescripcion());
			muroService.crearMensaje(mensaje, idTema, usuario.getId());
		}
		return "redirect:/historico/mostrarMensajes/" + idTema;
	}
	
	@RequestMapping(value = "/historico/mostrarPlaning/{idPlaning}", method = RequestMethod.GET)
	public String mostrarPlanings(@PathVariable("idPlaning") final Integer idPlaning, HttpServletRequest request, ModelMap model) {
		HttpSession session = request.getSession();
		ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");
			
		PlaningVo planing = planingService.getPlaning(idPlaning);

		List<ParadaVo> paradas = planingService.getParadas(idPlaning);
		
		model.addAttribute("planing", planing);
		model.addAttribute("paradas", paradas);
		model.addAttribute("idViaje", viajeUsuario.getViaje().getId());
		model.addAttribute("nombreUsuario", viajeUsuario.getUsuario().getNombreUsuario());
        return "/private/historico/mostrar-planing";
    }
	
	@RequestMapping(value = "/historico/modificarCheckList/{id}", method = RequestMethod.GET)
	public String modificarCheckList(@PathVariable("id") final Integer idCheckList, ModelMap model, HttpServletRequest request){
		HttpSession session = request.getSession();
		ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");
			
		CheckListViajeVo checkListViaje = checkListViajeService.getCheckList(idCheckList);
		List<ItemListViajeVo> items = checkListViajeService.getItemsListViaje(idCheckList);
		
		EditarCheckListViajeForm editarCheckListViajeForm = new EditarCheckListViajeForm(checkListViaje, items);
		
		model.addAttribute("modificarCheckListViajeForm", editarCheckListViajeForm);
		model.addAttribute("idViaje", viajeUsuario.getViaje().getId());
		model.addAttribute("nombreUsuario", viajeUsuario.getUsuario().getNombreUsuario());
		return "/private/historico/editar-checklist-viaje";
	}
}
