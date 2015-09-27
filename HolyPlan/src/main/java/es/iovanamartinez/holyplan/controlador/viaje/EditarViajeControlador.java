package es.iovanamartinez.holyplan.controlador.viaje;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.iovanamartinez.holyplan.controlador.formulario.EditarViajeForm;
import es.iovanamartinez.holyplan.controlador.validador.EditarViajeValidador;
import es.iovanamartinez.holyplan.dominio.vo.ViajeUsuarioVo;
import es.iovanamartinez.holyplan.dominio.vo.ViajeVo;
import es.iovanamartinez.holyplan.service.ViajeService;

@Controller
public class EditarViajeControlador {
	
	@Autowired
	private EditarViajeValidador editarViajeValidador;
	
	@Autowired
	private ViajeService viajeService;
	
	private static final int ID_ROL_INVITADO = 3;

	
	@RequestMapping(value="/viaje/editarViaje", method=RequestMethod.GET)
	public String mostrarFormEditarViaje(ModelMap model, HttpServletRequest request){
		HttpSession session = request.getSession();
		ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");
		
		if (viajeUsuario.getRol().getId().compareTo(ID_ROL_INVITADO) == 0) {
			model.addAttribute("mensajeError", "No dispone del permiso necesario para acceder a esta secci&oacute;n");
			return "/public/error";
		}
		else {
			EditarViajeForm editarViajeForm = new EditarViajeForm();
			editarViajeForm.setId(viajeUsuario.getViaje().getId());
			editarViajeForm.setNombreViaje(viajeUsuario.getViaje().getNombreViaje());
			editarViajeForm.setFecha(viajeUsuario.getViaje().getFecha());
			editarViajeForm.setDuracion(viajeUsuario.getViaje().getDuracion());
			editarViajeForm.setDescripcion(viajeUsuario.getViaje().getDescripcion());
			if (viajeUsuario.getViaje().getBote() != null)
				editarViajeForm.setBote(viajeUsuario.getViaje().getBote());
			
			model.addAttribute("idViaje", viajeUsuario.getViaje().getId());
			model.addAttribute("idRol", viajeUsuario.getRol().getId());	
			model.addAttribute("nombreUsuario", viajeUsuario.getUsuario().getNombreUsuario());
			model.addAttribute("editarViajeForm", editarViajeForm);
			//model.addAttribute("viaje", viaje);
			return "/private/viaje/editar-viaje";
		}
	}

	
	@RequestMapping(value="/viaje/editarViaje", method=RequestMethod.POST)
	public String editarUsuario(@ModelAttribute @Valid EditarViajeForm editarViajeForm, BindingResult result, 
			ModelMap model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");
		
		editarViajeValidador.validate(editarViajeForm, result);
		if (!result.hasErrors()) {
			ViajeVo viaje = new ViajeVo(editarViajeForm.getId(), editarViajeForm.getNombreViaje(), editarViajeForm.getFecha(), 
					editarViajeForm.getDuracion(), editarViajeForm.getDescripcion(), editarViajeForm.getBote());
			viajeService.editarViaje(viaje);		
			
			model.addAttribute("idViaje", editarViajeForm.getId());
			model.addAttribute("idRol", viajeUsuario.getRol().getId());
			model.addAttribute("nombreUsuario", viajeUsuario.getUsuario().getNombreUsuario());
			model.addAttribute("mensajeConfirmacion", "Cambios realizados con &eacute;xito");
		}
		
		return "/private/viaje/editar-viaje";
	}

}
