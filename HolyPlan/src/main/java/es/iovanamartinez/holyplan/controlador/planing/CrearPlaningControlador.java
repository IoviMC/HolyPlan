package es.iovanamartinez.holyplan.controlador.planing;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.iovanamartinez.holyplan.controlador.formulario.CrearPlaningForm;
import es.iovanamartinez.holyplan.controlador.validador.CrearPlaningValidador;
import es.iovanamartinez.holyplan.dominio.vo.PlaningVo;
import es.iovanamartinez.holyplan.dominio.vo.ViajeUsuarioVo;
import es.iovanamartinez.holyplan.service.PlaningService;

@Controller
public class CrearPlaningControlador {
	
	@Autowired
	private CrearPlaningValidador crearPlaningValidador;
	
	@Autowired
	private PlaningService planingService;
	
	private static final int ID_ROL_INVITADO = 3;
	
	@RequestMapping(value = "/planing/crearPlaning", method = RequestMethod.GET)
	public String crearPlaningForm(HttpServletRequest request, ModelMap model){
		HttpSession session = request.getSession();
		ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");

		if (viajeUsuario.getRol().getId().compareTo(ID_ROL_INVITADO) == 0) {
			model.addAttribute("mensajeError", "No dispone del permiso necesario para acceder a esta secci&oacute;n");
			return "/public/error";	
		}
		else {
			CrearPlaningForm crearPlaningForm = new CrearPlaningForm();
			
			model.addAttribute("idViaje", viajeUsuario.getViaje().getId());
			model.addAttribute("crearPlaningForm", crearPlaningForm);
			model.addAttribute("idRol", viajeUsuario.getRol().getId());
			return "private/planing/crear-planing";
		}
	}
	
	@RequestMapping(value = "/planing/crearPlaning", method = RequestMethod.POST)
	public String crearPlaning(@ModelAttribute CrearPlaningForm crearPlaningForm, BindingResult result, ModelMap model, HttpServletRequest request){
		crearPlaningValidador.validate(crearPlaningForm, result);
		
		if (!result.hasErrors()) {
			HttpSession session = request.getSession();
			ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");
		
			PlaningVo planing = planingService.crearPlaning(crearPlaningForm.getNombre(), crearPlaningForm.getDescripcion(), viajeUsuario.getViaje().getId());
			
//			model.addAttribute("idPlaning", planingVo.getId());
//			model.addAttribute("mensajeConfirmacion", "Planing creado con &eacute;xito");
			return "redirect:/parada/crearParada/" + planing.getId();
		}
		return "private/planing/crear-planing";
	}

}
