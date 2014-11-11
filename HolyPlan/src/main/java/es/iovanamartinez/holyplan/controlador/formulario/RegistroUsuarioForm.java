package es.iovanamartinez.holyplan.controlador.formulario;

public class RegistroUsuarioForm {
	private String nombreUsuario;
	private String contrasena;
	private String contrasenaConf;
	private String email;

	public RegistroUsuarioForm() {
		super();
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getContrasenaConf() {
		return contrasenaConf;
	}

	public void setContrasenaConf(String contrasenaForm) {
		this.contrasenaConf = contrasenaForm;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
