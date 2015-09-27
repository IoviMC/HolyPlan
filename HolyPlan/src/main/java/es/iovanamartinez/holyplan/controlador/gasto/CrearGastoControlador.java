package es.iovanamartinez.holyplan.controlador.gasto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.iovanamartinez.holyplan.controlador.formulario.GastoForm;
import es.iovanamartinez.holyplan.controlador.validador.GastoValidador;
import es.iovanamartinez.holyplan.dominio.vo.GastoColectivoVo;
import es.iovanamartinez.holyplan.dominio.vo.GastoIndividualVo;
import es.iovanamartinez.holyplan.dominio.vo.ViajeUsuarioVo;
import es.iovanamartinez.holyplan.service.GastoService;
import es.iovanamartinez.holyplan.service.PagoGastoColectivoService;

@Controller
public class CrearGastoControlador {
	
	@Autowired
	private GastoValidador gastoValidador;
	@Autowired
	private GastoService gastoService;
	@Autowired
	private PagoGastoColectivoService pagoGastoService;
	
	private static final int ID_ROL_INVITADO = 3;
	
	@RequestMapping(value = "/gasto/crearGastoColectivo", method = RequestMethod.POST)
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
						pagoGastoService.crearPagoGastoColectivoBote(gastoColectivoVo.getId(), viajeUsuario.getViaje().getId());
					}
				}
				else {
					GastoColectivoVo gastoColectivoVo = new GastoColectivoVo(crearGastoColectivoForm.getConcepto(), 
							crearGastoColectivoForm.getImporte(), crearGastoColectivoForm.getFecha(), false);
					gastoColectivoVo = gastoService.crearGastoColectivo(gastoColectivoVo, viajeUsuario.getViaje().getId());
					if (idUsuarioPago.compareTo(0) == 0){
						pagoGastoService.crearPagoGastoColectivoTodosUsuarios(gastoColectivoVo.getId(), viajeUsuario.getViaje().getId());
					}
					else {
						pagoGastoService.crearPagoGastoColectivoPersona(gastoColectivoVo.getId(), idUsuarioPago, viajeUsuario.getViaje().getId());
//						pagoGastoService.crearPagoGastoColectivoPersona(gastoColectivoVo.getId(), idUsuarioPago);						
					}
				}				
				return "redirect:/gasto/mostrarGastos";
			}
			return "/private/gastos/mostrar-gastos";
		}
	}
	
	@RequestMapping(value = "/gasto/crearGastoIndividual", method = RequestMethod.POST)
	public String crearGastoIndividual(@ModelAttribute("crearGastoIndividualForm") GastoForm crearGastoIndividualForm, ModelMap model, BindingResult result, HttpServletRequest request){
		HttpSession session = request.getSession();
		ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");
		
		gastoValidador.validate(crearGastoIndividualForm, result);
		if (!result.hasErrors()) {
			GastoIndividualVo gastoIndividualVo = new GastoIndividualVo(crearGastoIndividualForm.getConcepto(), 
					crearGastoIndividualForm.getImporte(), crearGastoIndividualForm.getFecha());
			
			gastoService.crearGastoIndividual(gastoIndividualVo, viajeUsuario.getViaje().getId(), viajeUsuario.getUsuario().getId());
			//model.addAttribute("mensajeConfirmacion", "Gasto a&ntilde;adido correctamente");
			return "redirect:/gasto/mostrarGastos";
		}
		return "/private/gastos/mostrar-gastos";
		//return "redirect:/gasto/mostrarGastos";
	}
	
//	@RequestMapping(value = "/gasto/crearGastoColectivo/{id}", method = RequestMethod.POST)
//	public String crearGastoColectivo(@PathVariable("id") final Integer idViaje, @ModelAttribute GastoForm crearGastoColectivoForm, BindingResult result, ModelMap model){
//		
//		gastoValidador.validate(crearGastoColectivoForm, result);
//		if (!result.hasErrors()) {
//			GastoColectivoVo gastoColectivoVo = new GastoColectivoVo(crearGastoColectivoForm.getConcepto(), 
//					crearGastoColectivoForm.getImporte(), crearGastoColectivoForm.getFecha());
//			
//			gastoService.crearGastoColectivo(gastoColectivoVo, idViaje);
//			//model.addAttribute("mensajeConfirmacion", "Gasto a&ntilde;adido correctamente");
//		}
//		//return "/private/gastos/crear-gasto-colectivo";
//		return "redirect:/gasto/mostrarGastos/" + idViaje;
//	}
//	
//	@RequestMapping(value = "/gasto/crearGastoIndividual/{id}", method = RequestMethod.POST)
//	public String crearGastoIndividual(@PathVariable("id") final Integer idViaje, @ModelAttribute GastoForm crearGastoIndividualForm, 
//			BindingResult result, ModelMap model, HttpServletRequest request){
//		HttpSession session = request.getSession();
//		UsuarioVo usuario = (UsuarioVo) session.getAttribute("usuario");
//		
//		gastoValidador.validate(crearGastoIndividualForm, result);
//		if (!result.hasErrors()) {
//			GastoIndividualVo gastoIndividualVo = new GastoIndividualVo(crearGastoIndividualForm.getConcepto(), 
//					crearGastoIndividualForm.getImporte(), crearGastoIndividualForm.getFecha());
//			
//			gastoService.crearGastoIndividual(gastoIndividualVo, idViaje, usuario.getId());
//			//model.addAttribute("mensajeConfirmacion", "Gasto a&ntilde;adido correctamente");
//		}
//		//return "/private/gastos/crear-gasto-individual";
//		return "redirect:/gasto/mostrarGastos/" + idViaje;
//	}
}
