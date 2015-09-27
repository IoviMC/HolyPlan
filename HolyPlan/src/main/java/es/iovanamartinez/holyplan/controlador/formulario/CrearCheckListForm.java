package es.iovanamartinez.holyplan.controlador.formulario;



public class CrearCheckListForm {
	
	//ATRIBUTOS
	private String nombre;
	private String[] items;
	
	//CONTRUCTORES
	public CrearCheckListForm() {
		super();
	}

	//GETTERS AND SETTERSs
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String[] getItems() {
		return items;
	}

	public void setItems(String[] items) {
		this.items = items;
	}
}
