package es.iovanamartinez.holyplan.dominio.vo;


public class ViajeUsuarioVo {
	//ATRIBUTOS
	private UsuarioVo usuario;
	private ViajeVo viaje;
	private RolVo rol;
	
	//CONSTRUCTORES
	public ViajeUsuarioVo() {
		super();
	}

	public ViajeUsuarioVo(UsuarioVo usuario, ViajeVo viaje, RolVo rol) {
		this.usuario = usuario;
		this.viaje = viaje;
		this.rol = rol;
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

}
