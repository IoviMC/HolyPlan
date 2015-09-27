package es.iovanamartinez.holyplan.controlador.formulario;

public class CrearPlaningForm {
	
	private String nombre;
	private String descripcion;
	
	//CONSTRUCTORES
	public CrearPlaningForm() {
		super();
	}
	
	//GETTERS AND SETTERS
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
