package es.iovanamartinez.holyplan.dominio;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "usuario_amigo")
@IdClass(value = UsuarioAmigoId.class)
public class UsuarioAmigo {
	
	// RELACIONES
	@Id
	@ManyToOne
	@JoinColumn(name = "idUsuario", referencedColumnName="idusuario")
	private Usuario usuario;

	@Id
	@ManyToOne
	@JoinColumn(name = "idUsuarioAmigo", referencedColumnName="idusuario")
	private Usuario amigo;

	// CONSTRUCTORES
	public UsuarioAmigo() {
		super();
	}
	
	public UsuarioAmigo(Usuario usuario, Usuario amigo) {
		this.usuario = usuario;
		this.amigo = amigo;
	}
	
	// GETTERS AND SETTERS
	public Usuario getUsuario(){
		return usuario;
	}

	public void setUsuario(Usuario usuario){
		this.usuario = usuario;
	}
	
	public Usuario getAmigo(){
		return amigo;
	}

	public void setAmigo(Usuario amigo){
		this.amigo = amigo;
	}
	
	//METODOS
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * (result + ((amigo == null) ? 0 : amigo.hashCode()));
		result = prime * (result + ((usuario == null) ? 0 : usuario.hashCode()));
		return result; 
	}
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((amigo == null) ? 0 : amigo.getId());
//		result = prime * result + ((usuario == null) ? 0 : usuario.getId());
//		return result;
//	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof UsuarioAmigo)) {
			return false;
		}
		UsuarioAmigo other = (UsuarioAmigo) obj;
		if (amigo == null) {
			if (other.amigo != null) {
				return false;
			}
		} else if (!amigo.equals(other.amigo)) {
			return false;
		}
		if (usuario == null) {
			if (other.usuario != null) {
				return false;
			}
		} else if (!usuario.equals(other.usuario)) {
			return false;
		}
		return true;
	}
}
