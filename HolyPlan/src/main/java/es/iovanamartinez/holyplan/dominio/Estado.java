package es.iovanamartinez.holyplan.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "estado")
public class Estado {
	
	@Id
	@Column(name = "idestado")
	private Integer id;
	@Column(length = 20, nullable = false)
	private String nombreEstado;
	
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
