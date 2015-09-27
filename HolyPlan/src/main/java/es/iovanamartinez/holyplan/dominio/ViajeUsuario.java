package es.iovanamartinez.holyplan.dominio;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "viaje_usuario")
@IdClass(value = ViajeUsuarioId.class)
public class ViajeUsuario {
	
	// RELACIONES
	@Id
	@ManyToOne
	@JoinColumn(name = "idusuario", referencedColumnName = "idusuario")
	private Usuario usuario;
	@Id
	@ManyToOne
	@JoinColumn(name = "idviaje", referencedColumnName = "idviaje")
	private Viaje viaje;
	@ManyToOne
	@JoinColumn(name = "idrol", referencedColumnName = "idrol")
	private Rol rol;
	@ManyToOne
	@JoinColumn(name = "idestado", referencedColumnName = "idestado")
	private Estado estado;	
	
	// CONSTRUCTORES
	public ViajeUsuario() {
		super();
	}
	
	public ViajeUsuario(Usuario usuario, Viaje viaje, Rol rol, Estado estado){
		this.usuario = usuario;
		this.viaje = viaje;
		this.rol = rol;
		this.estado = estado;
	}
	
	// GETTERS AND SETTERS
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
	
	public Estado getEstado(){
		return estado;
	}
	
	public void setEstado(Estado estado){
		this.estado = estado;
	}
	
	//METODOS
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		result = prime * result + ((viaje == null) ? 0 : viaje.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ViajeUsuario)) {
			return false;
		}
		ViajeUsuario other = (ViajeUsuario) obj;
		if (usuario == null) {
			if (other.usuario != null) {
				return false;
			}
		} else if (!usuario.equals(other.usuario)) {
			return false;
		}
		if (viaje == null) {
			if (other.viaje != null) {
				return false;
			}
		} else if (!viaje.equals(other.viaje)) {
			return false;
		}
		return true;
	}
//	public boolean equals(Object obj) {
//		if (!(obj instanceof ViajeUsuario))
//			return false;	
//		if (obj == this)
//			return true;
//		return this.getViaje().getId().equals(((ViajeUsuario) obj).getViaje().getId()) && this.getRol().getId().equals(((ViajeUsuario) obj).getRol().getId()) && this.getUsuario().getId().equals(((ViajeUsuario) obj).getUsuario().getId());
//	}
// 
//	public int hashCode(){
//		return this.getUsuario().getNombreUsuario().length();
//	}
}
