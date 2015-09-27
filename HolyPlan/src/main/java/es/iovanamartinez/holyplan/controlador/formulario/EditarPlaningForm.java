package es.iovanamartinez.holyplan.controlador.formulario;

import es.iovanamartinez.holyplan.dominio.vo.PlaningVo;

public class EditarPlaningForm {
	
	private Integer id;
	private String nombre;
	private String descripcion;
	
	//CONSTRUCTORES
	public EditarPlaningForm() {
		super();
	}
	
	public EditarPlaningForm(PlaningVo planing) {
		this.id = planing.getId();
		this.nombre = planing.getNombre();
		this.descripcion = planing.getDescripcion();
	}
	
	//GETTERS AND SETTERS
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
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
