package es.iovanamartinez.holyplan.dominio.vo;

import java.io.Serializable;

import es.iovanamartinez.holyplan.dominio.ItemListViaje;

public class ItemListViajeVo implements Serializable {

	private static final long serialVersionUID = 2788503389426558990L;
	
	//ATRIBUTOS
	private Integer id;
	private String descripcion;
	private boolean realizado;
	private String nombreUsuario;
	
	//CONTRUCTORES
	public ItemListViajeVo(){
		super();
	}
	
	public ItemListViajeVo(ItemListViaje itemList){
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

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
}
