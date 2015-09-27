package es.iovanamartinez.holyplan.dominio;

import java.io.Serializable;

public class UsuarioAmigoId implements Serializable {
	private static final long serialVersionUID = 6844315161302840301L;
	
	private Integer usuario;
	private Integer amigo;

	public UsuarioAmigoId() {
		super();
	}
	
	public UsuarioAmigoId(Integer idUsuario, Integer idAmigo){
		this.usuario = idUsuario;
		this.amigo = idAmigo;
	}

	public Integer getUsuario() {
		return usuario;
	}

	public void setUsuario(Integer usuario) {
		this.usuario = usuario;
	}
	
	public Integer getAmigo() {
		return amigo;
	}

	public void setAmigo(Integer amigo) {
		this.amigo = amigo;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		result = prime * result + ((amigo == null) ? 0 :amigo.hashCode());
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
		if (!(obj instanceof UsuarioAmigoId)) {
			return false;
		}
		UsuarioAmigoId other = (UsuarioAmigoId) obj;
		if (usuario == null) {
			if (other.usuario != null) {
				return false;
			}
		} else if (!usuario.equals(other.usuario)) {
			return false;
		}
		if (amigo == null) {
			if (other.amigo != null) {
				return false;
			}
		} else if (!amigo.equals(other.amigo)) {
			return false;
		}
		return true;
	}

}
