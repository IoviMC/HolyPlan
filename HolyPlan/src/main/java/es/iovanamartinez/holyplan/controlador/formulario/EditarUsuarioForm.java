package es.iovanamartinez.holyplan.controlador.formulario;

public class EditarUsuarioForm {
	private String nombreUsuario;
	private String email;
	private String contrasenaActual;
	private String contrasenaNueva;
	private String contrasenaNuevaConf;

	public EditarUsuarioForm() {
		super();
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
