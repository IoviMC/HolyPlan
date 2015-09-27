package es.iovanamartinez.holyplan.controlador.parada;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

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

import es.iovanamartinez.holyplan.controlador.formulario.CrearParadaForm;
import es.iovanamartinez.holyplan.controlador.validador.CrearParadaValidador;
import es.iovanamartinez.holyplan.dominio.vo.ViajeUsuarioVo;
import es.iovanamartinez.holyplan.service.ParadaService;

@Controller
public class CrearParadaControlador {
	
	@Autowired
	private CrearParadaValidador crearParadaValidador;
	
	@Autowired
	private ParadaService paradaService;
	
	private static final int ID_ROL_INVITADO = 3;
	
	@RequestMapping(value = "/parada/crearParada/{idPlaning}", method = RequestMethod.GET)
	public String crearParadaForm(@PathVariable("idPlaning") final Integer idPlaning, HttpServletRequest request, ModelMap model){
		HttpSession session = request.getSession();
		ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");

		if (viajeUsuario.getRol().getId().compareTo(ID_ROL_INVITADO) == 0){
			model.addAttribute("mensajeError", "No dispone del permiso necesario para acceder a esta secci&oacute;n");
			return "/public/error";	
		}
		else {
			CrearParadaForm crearParadaForm = new CrearParadaForm();
			
			model.addAttribute("idPlaning", idPlaning);
			Date fechaViaje = viajeUsuario.getViaje().getFecha();
			Instant instant = Instant.ofEpochMilli(fechaViaje.getTime());
			LocalDateTime fechaViajeConvert = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
			
			model.addAttribute("idViaje", viajeUsuario.getViaje().getId());
			model.addAttribute("idRol", viajeUsuario.getRol().getId());
			model.addAttribute("fechaViaje", fechaViajeConvert);
			model.addAttribute("crearParadaForm", crearParadaForm);
			return "private/parada/crear-parada";
		}
	}
	
	@RequestMapping(value = "/parada/crearParada/{idPlaning}", method = RequestMethod.POST)
	public String crearParada(@PathVariable("idPlaning") final Integer idPlaning, @ModelAttribute CrearParadaForm crearParadaForm, 
				BindingResult result,  HttpServletRequest request, ModelMap model){
		
		HttpSession session = request.getSession();
		ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");
		
		String fechaString = crearParadaForm.getFechaString().replace("T", " ");
		Date fecha = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		try {
			fecha = formatter.parse(fechaString);
			crearParadaForm.setFecha(fecha);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}	 
		
		crearParadaValidador.validate(crearParadaForm, result);
		if (!result.hasErrors()) {
			paradaService.crearParada(crearParadaForm.getNombre(), crearParadaForm.getLugar(), fecha, idPlaning);
			
			return "redirect:/viaje/mostrarViaje/" + viajeUsuario.getViaje().getId();
		}
		model.addAttribute("mensajeError", "No se ha podido crear la parada");
		return "private/parada/crear-parada";
	}
}
