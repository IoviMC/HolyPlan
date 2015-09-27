package es.iovanamartinez.holyplan.dominio.vo;

import java.io.Serializable;

import es.iovanamartinez.holyplan.dominio.Estado;

public class EstadoVo implements Serializable {
	private static final long serialVersionUID = 3133683595428104481L;
	
	//ATRIBUTOS
	private Integer id;
	private String nombreEstado;
	
	//CONSTRUCTORES
	public EstadoVo(){
		super();
	}
	
	public EstadoVo(Estado estado){
		this.id = estado.getId();
		this.nombreEstado = estado.getNombreEstado();
	}
	
	//GETTERS AND SETTERS
	public Integer getId(){
		return id;
	}
	
	public void setId(Integer id){
		this.id = id;
	}
	
	public String getNombreEstado(){
		return nombreEstado;
	}
	
	public void setNombreEstado(String nombreEstado){
		this.nombreEstado = nombreEstado;
	}
}
