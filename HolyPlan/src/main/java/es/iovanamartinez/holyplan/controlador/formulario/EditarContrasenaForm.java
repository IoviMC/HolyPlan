package es.iovanamartinez.holyplan.controlador.formulario;

public class EditarContrasenaForm {
	private String contrasenaActual;
	private String contrasenaNueva;
	private String contrasenaNuevaConf;

	public EditarContrasenaForm() {
		super();
	}
	
	public String getContrasenaActual() {
		return contrasenaActual;
	}
	
	public void setContrasenaActual(String contrasenaActual) {
		this.contrasenaActual = contrasenaActual;
	}
	
	public String getContrasenaNueva() {
		return contrasenaNueva;
	}
	
	public void setContrasenaNueva(String contrasenaNueva) {
		this.contrasenaNueva = contrasenaNueva;
	}
	
	public String getContrasenaNuevaConf() {
		return contrasenaNuevaConf;
	}
	
	public void setContrasenaNuevaConf(String contrasenaNuevaConf) {
		this.contrasenaNuevaConf = contrasenaNuevaConf;
	}

}
