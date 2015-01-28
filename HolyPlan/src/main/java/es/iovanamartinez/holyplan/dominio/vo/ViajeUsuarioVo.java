package es.iovanamartinez.holyplan.dominio.vo;

import java.io.Serializable;

import es.iovanamartinez.holyplan.dominio.ViajeUsuario;


public class ViajeUsuarioVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//ATRIBUTOS
	private UsuarioVo usuario;
	private ViajeVo viaje;
	private RolVo rol;
	private EstadoVo estado;
	
	//CONSTRUCTORES
	public ViajeUsuarioVo() {
		super();
	}

	public ViajeUsuarioVo(ViajeUsuario viajeUsuario) {
		this.usuario = new UsuarioVo(viajeUsuario.getUsuario());
		this.viaje = new ViajeVo(viajeUsuario.getViaje());
		this.rol = new RolVo(viajeUsuario.getRol());
		this.estado = new EstadoVo(viajeUsuario.getEstado());
	}	
	
	//GETTERS AND SETTERS
	public UsuarioVo getUsuario(){
		return usuario;
	}

	public void setUsusario(UsuarioVo usuario){
		this.usuario = usuario;
	}
	
	public ViajeVo getViaje(){
		return viaje;
	}
	
	public void setViaje(ViajeVo viaje){
		this.viaje = viaje;
	}
	
	public RolVo getRol(){
		return rol;
	}
	
	public void setRol(RolVo rol){
		this.rol = rol;
	}

	public EstadoVo getEstado() {
		return estado;
	}

	public void setEstado(EstadoVo estado) {
		this.estado = estado;
	}
}
