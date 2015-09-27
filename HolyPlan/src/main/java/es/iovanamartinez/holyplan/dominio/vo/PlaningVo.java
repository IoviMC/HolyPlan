package es.iovanamartinez.holyplan.dominio.vo;

import java.io.Serializable;

import es.iovanamartinez.holyplan.dominio.Planing;

public class PlaningVo implements Serializable{

	private static final long serialVersionUID = -2158043042927462720L;

	private Integer id;
	private String nombre;
	private String descripcion;
	
	//CONSTRUCTORES
	public PlaningVo() {
		super();
	}
	
	public PlaningVo(Planing planing) {
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
