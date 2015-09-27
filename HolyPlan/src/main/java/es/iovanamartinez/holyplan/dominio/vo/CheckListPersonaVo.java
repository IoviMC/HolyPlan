package es.iovanamartinez.holyplan.dominio.vo;

import java.io.Serializable;

import es.iovanamartinez.holyplan.dominio.CheckListPersona;

public class CheckListPersonaVo implements Serializable {
	private static final long serialVersionUID = -7782174458645034839L;
	
	//ATRIBUTOS
	private Integer id;
	private String nombre;
	
	//CONTRUCTORES
	public CheckListPersonaVo() {
		super();
	}
	
	public CheckListPersonaVo(CheckListPersona checkListPersona) {
		super();
		this.id = checkListPersona.getId();
		this.nombre = checkListPersona.getNombre();
	}
	
	public CheckListPersonaVo(String nombre) {
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
