package es.iovanamartinez.holyplan.controlador.formulario;

public class CrearTemaForm {
	
	//ATRIBUTOS
	private String titulo;
	private String descripcion;
	
	//CONTRUCTORES
	public CrearTemaForm() {
		super();
	}

	//GETTERS AND SETTERS
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
