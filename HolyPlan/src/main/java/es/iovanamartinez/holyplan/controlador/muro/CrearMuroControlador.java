package es.iovanamartinez.holyplan.controlador.muro;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.iovanamartinez.holyplan.controlador.formulario.CrearMensajeForm;
import es.iovanamartinez.holyplan.controlador.formulario.CrearTemaForm;
import es.iovanamartinez.holyplan.controlador.validador.CrearMensajeValidador;
import es.iovanamartinez.holyplan.controlador.validador.CrearTemaValidador;
import es.iovanamartinez.holyplan.dominio.vo.MensajeVo;
import es.iovanamartinez.holyplan.dominio.vo.TemaVo;
import es.iovanamartinez.holyplan.dominio.vo.UsuarioVo;
import es.iovanamartinez.holyplan.dominio.vo.ViajeUsuarioVo;
import es.iovanamartinez.holyplan.service.MuroService;

@Controller
public class CrearMuroControlador {
	
	@Autowired 
	private CrearTemaValidador crearTemaValidador;
	
	
	@Autowired
	private CrearMensajeValidador crearMensajeValidador;
	
	@Autowired 
	private MuroService muroService;
	
	@RequestMapping(value = "/muro/crearTema", method = RequestMethod.GET)
	public String mostrarFormTema(ModelMap model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");
		
		CrearTemaForm crearTemaForm = new CrearTemaForm();
		
		model.addAttribute("nombreUsuario", viajeUsuario.getUsuario().getNombreUsuario());
		model.addAttribute("idViaje", viajeUsuario.getViaje().getId());
		model.addAttribute("idRol", viajeUsuario.getRol().getId());
		model.addAttribute("crearTemaForm", crearTemaForm);
		return "/private/muro/crear-tema";
	}
	
	@RequestMapping(value = "/muro/crearTema", method = RequestMethod.POST)
	public String crearTema(@ModelAttribute CrearTemaForm crearTemaForm, BindingResult result, ModelMap model, HttpServletRequest request){
		
		crearTemaValidador.validate(crearTemaForm, result);
		if (!result.hasErrors()){
			HttpSession session = request.getSession();
			ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");
			
			TemaVo tema = new TemaVo(crearTemaForm.getTitulo());
			MensajeVo mensaje = new MensajeVo(crearTemaForm.getDescripcion());
			
			muroService.crearTema(tema, mensaje, viajeUsuario.getViaje().getId(), viajeUsuario.getUsuario().getId());
			
			model.addAttribute("nombreUsuario", viajeUsuario.getUsuario().getNombreUsuario());
			model.addAttribute("idViaje", viajeUsuario.getViaje().getId());
			model.addAttribute("idRol", viajeUsuario.getRol().getId());
			model.addAttribute("crearTemaForm", crearTemaForm);
			model.addAttribute("mensajeConfirmacion", "Tema creado con &eacute;xito");
		}
		return "/private/muro/crear-tema";
	}
	
	@RequestMapping(value = "/muro/crearMensaje/{id}", method = RequestMethod.POST)
	public String crearMensaje(@PathVariable("id") final Integer idTema, @ModelAttribute CrearMensajeForm crearMensajeForm, BindingResult result, HttpServletRequest request){
		
		crearMensajeValidador.validate(crearMensajeForm, result);
		if (!result.hasErrors()){
			HttpSession session = request.getSession();
			UsuarioVo usuario = (UsuarioVo) session.getAttribute("usuario");
			
			MensajeVo mensaje = new MensajeVo(crearMensajeForm.getDescripcion());
			muroService.crearMensaje(mensaje, idTema, usuario.getId());
		}
		return "redirect:/muro/mostrarMensajes/" + idTema;
	}
}
