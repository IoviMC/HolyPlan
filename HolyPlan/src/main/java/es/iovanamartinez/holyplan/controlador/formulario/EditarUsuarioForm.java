package es.iovanamartinez.holyplan.controlador.formulario;

public class EditarUsuarioForm {
	private String nombreUsuario;
	private String email;


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
	
}
