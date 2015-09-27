package es.iovanamartinez.holyplan.controlador.parada;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.iovanamartinez.holyplan.dominio.vo.ViajeUsuarioVo;
import es.iovanamartinez.holyplan.service.ParadaService;

@Controller
public class AccionesParadaControlador {
	
	@Autowired
	private ParadaService paradaService;
	
	private static final int ID_ROL_INVITADO = 3;
	
	@RequestMapping(value = "/parada/eliminarParada/{idParada}", method = RequestMethod.GET)
	public String eliminarParada(@PathVariable("idParada") final Integer idParada, HttpServletRequest request, ModelMap model){
		HttpSession session = request.getSession();
		ViajeUsuarioVo viajeUsuario = (ViajeUsuarioVo) session.getAttribute("viajeUsuario");

		if (viajeUsuario.getRol().getId().compareTo(ID_ROL_INVITADO) == 0) {
			model.addAttribute("mensajeError", "No dispone del permiso necesario para acceder a esta secci&oacute;n");
			return "/public/error";	
		}
		else {
			Integer idPlaning = paradaService.eliminarParada(idParada);
	        return "redirect:/planing/modificarPlaning/" + idPlaning;
		}
    }
}
