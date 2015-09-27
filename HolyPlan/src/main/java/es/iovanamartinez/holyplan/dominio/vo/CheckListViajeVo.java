package es.iovanamartinez.holyplan.dominio.vo;

import java.io.Serializable;

import es.iovanamartinez.holyplan.dominio.CheckListViaje;

public class CheckListViajeVo implements Serializable {
	private static final long serialVersionUID = 8651112420339065745L;
	
	//ATRIBUTOS
	private Integer id;
	private String nombre;
	
	//CONTRUCTORES
	public CheckListViajeVo() {
		super();
	}
	
	public CheckListViajeVo(CheckListViaje checkListViaje) {
		super();
		this.id = checkListViaje.getId();
		this.nombre = checkListViaje.getNombre();
	}
	
	public CheckListViajeVo(String nombre) {
		super();
		this.nombre = nombre;
	}

	//GETTERS AND SETTERSs
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
}
