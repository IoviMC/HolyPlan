package es.iovanamartinez.holyplan.dominio;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "viaje_usuario")
@IdClass(value = ViajeUsuarioId.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class ViajeUsuario {
	
	// RELACIONES
	@Id
	@ManyToOne
	@JoinColumn(name = "idusuario", referencedColumnName="idusuario")
	private Usuario usuario;
	@Id
	@ManyToOne
	@JoinColumn(name = "idviaje", referencedColumnName="idviaje")
	private Viaje viaje;
	@ManyToOne
	@JoinColumn(name = "idrol", referencedColumnName="idrol")
	private Rol rol;
	
	
	// CONSTRUCTORES
	public ViajeUsuario() {
		super();
	}
	
	public ViajeUsuario(Usuario usuario, Viaje viaje, Rol rol) {
		this.usuario = usuario;
		this.viaje = viaje;
		this.rol = rol;
	}
	
	//GETTERS AND SETTERS
	public Usuario getUsuario(){
		return usuario;
	}

	public void setUsusario(Usuario usuario){
		this.usuario = usuario;
	}
	
	public Viaje getViaje(){
		return viaje;
	}
	
	public void setViaje(Viaje viaje){
		this.viaje = viaje;
	}
	
	public Rol getRol(){
		return rol;
	}
	
	public void setRol(Rol rol){
		this.rol = rol;
	}
}
