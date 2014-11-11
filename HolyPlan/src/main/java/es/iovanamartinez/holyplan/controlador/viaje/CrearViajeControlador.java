package es.iovanamartinez.holyplan.controlador.viaje;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.iovanamartinez.holyplan.controlador.formulario.CrearViajeForm;
import es.iovanamartinez.holyplan.controlador.validador.CrearViajeValidador;
import es.iovanamartinez.holyplan.dominio.vo.UsuarioVo;
import es.iovanamartinez.holyplan.dominio.vo.ViajeVo;
import es.iovanamartinez.holyplan.service.ViajeService;

@Controller
public class CrearViajeControlador {

	@Autowired 
	private ViajeService viajeService;
	
	@Autowired
	private CrearViajeValidador crearViajeValidador;
	
	@RequestMapping(value = "/private/crearViaje", method = RequestMethod.GET)
	public String mostrarForm(ModelMap model){
		CrearViajeForm crearViajeForm = new CrearViajeForm();
		model.addAttribute("crearViajeForm", crearViajeForm);
		return "/private/crear-viaje";
	}
	
	@RequestMapping(value = "/private/crearViaje", method = RequestMethod.POST)
	public String procesarFormulario(@ModelAttribute CrearViajeForm crearViajeForm, ModelMap model, BindingResult result, HttpServletRequest request){
		HttpSession session = request.getSession();
		UsuarioVo usuario = (UsuarioVo) session.getAttribute("usuario");
		
		crearViajeValidador.validate(crearViajeForm, result);
		if (!result.hasErrors()) {
			ViajeVo viaje = new ViajeVo(crearViajeForm.getNombreViaje(), crearViajeForm.getFecha(), crearViajeForm.getDuracion());
			//ViajeUsuarioVo viajeUsuario = new ViajeUsuarioVo();
			//ViajeVo viajeCreado = viajeService.crearViaje(usuario, viaje);
			viajeService.crearViaje(usuario, viaje);
			//viajeService.crearViajeUsuario(usuario, viajeCreado);
			model.addAttribute("mensajeConfirmacion", "Viaje creado con &eacute;xito");
		}
		return "/private/crear-viaje";
	}
}
