package es.iovanamartinez.holyplan.dominio.vo;

import java.io.Serializable;

import es.iovanamartinez.holyplan.dominio.Rol;

 public class RolVo implements Serializable{

	private static final long serialVersionUID = -1449827626372578138L;
	
	private Integer id;
	private String rol;
	
	//CONSTRUCTORES
	public RolVo(){
		super();
	}
	
	public RolVo(Rol rol){
		this.id = rol.getId();
		this.rol = rol.getRol();
	}
	
	//GETTERS AND SETTERS
	public Integer getId(){
		return id;
	}
	
	public void setId(Integer id){
		this.id = id;
	}
	
	public String getRol(){
		return rol;
	}
	
	public void setRol(String rol){
		this.rol = rol;
	}
}
