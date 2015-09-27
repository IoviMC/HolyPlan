package es.iovanamartinez.holyplan.dominio.vo;

import java.io.Serializable;

import es.iovanamartinez.holyplan.dominio.ItemListPersona;


public class ItemListPersonaVo implements Serializable {

	private static final long serialVersionUID = 6639392312305871504L;
	
	//ATRIBUTOS
	private Integer id;
	private String descripcion;
	private boolean realizado;
	
	//CONTRUCTORES
	public ItemListPersonaVo(){
		super();
	}
	
	public ItemListPersonaVo(ItemListPersona itemList){
		this.id = itemList.getId();
		this.descripcion = itemList.getDescripcion();
		this.realizado = itemList.isRealizado();
	}
	
	//GETTERS AND SETTERS
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public boolean isRealizado() {
		return realizado;
	}
	public void setRealizado(boolean realizado) {
		this.realizado = realizado;
	}
}
