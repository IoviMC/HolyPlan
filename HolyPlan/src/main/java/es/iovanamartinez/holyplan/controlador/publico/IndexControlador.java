package es.iovanamartinez.holyplan.controlador.publico;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class IndexControlador {
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String defecto(){
        return "/public/index";
    }
	
	@RequestMapping(value = "/errorLogin", method = RequestMethod.GET)
	public String errorLogin(){
        return "/public/error-login";
    }
}
