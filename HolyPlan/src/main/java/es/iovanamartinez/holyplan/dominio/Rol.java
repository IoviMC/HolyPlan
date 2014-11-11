package es.iovanamartinez.holyplan.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "rol")
public class Rol {
	
	@Id
	@Column(name = "idrol")
	private Integer id;
	@Column(length = 15, nullable = false)
	private String rol;
	
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
