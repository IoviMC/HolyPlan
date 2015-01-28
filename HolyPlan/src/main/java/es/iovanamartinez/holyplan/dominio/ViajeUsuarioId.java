package es.iovanamartinez.holyplan.dominio;

import java.io.Serializable;

public class ViajeUsuarioId implements Serializable {
	private static final long serialVersionUID = 6844315161302840301L;

	private Integer usuario;
	private Integer viaje;

	public ViajeUsuarioId() {
		super();
	}

	public ViajeUsuarioId(Integer idUsuario, Integer idViaje) {
		this.usuario = idUsuario;
		this.viaje = idViaje;
	}

	public Integer getUsuario() {
		return usuario;
	}

	public void setUsuario(Integer usuario) {
		this.usuario = usuario;
	}

	public Integer getViaje() {
		return viaje;
	}

	public void setViaje(Integer viaje) {
		this.viaje = viaje;
	}

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
		if (!(obj instanceof ViajeUsuarioId)) {
			return false;
		}
		ViajeUsuarioId other = (ViajeUsuarioId) obj;
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
}
