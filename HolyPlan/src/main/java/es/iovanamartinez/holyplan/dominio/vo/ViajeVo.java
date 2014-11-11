package es.iovanamartinez.holyplan.dominio.vo;

import java.util.Date;
import java.util.Set;

import es.iovanamartinez.holyplan.dominio.Viaje;


public class ViajeVo {
	//ATRIBUTOS
	private Integer id;
	private String nombreViaje;
	private Date fecha;
	private Integer duracion;
	private Set<ViajeUsuarioVo> usuarios;
	
	//CONSTRUCTORES
	public ViajeVo(){
		super();
	}
	
	public ViajeVo(String nombre, Date fecha, Integer duracion){
		super();
		this.nombreViaje = nombre;
		this.fecha = fecha;
		this.duracion = duracion;
	}
	
	public ViajeVo(Viaje viaje){
		this.id = viaje.getId();
		this.nombreViaje = viaje.getNombreViaje();
		this.fecha = viaje.getFecha();
		this.duracion = viaje.getDuracion();
	}
	
	//METODOS
	public void anadirViajeUsuario(ViajeUsuarioVo viajeUsuario){
		usuarios.add(viajeUsuario);
	}
	
	public void eliminarViajeUsuario(ViajeUsuarioVo viajeUsuario){
		usuarios.remove(viajeUsuario);
	}
		
	//GETTERS AND SETTERS
	public Integer getId(){
		return id;
	}
	
	public void setId(Integer id){
		this.id = id;
	}
	
	public String getNombreViaje(){
		return nombreViaje;
	}
	
	public void seetNombreViaje(String nombreViaje){
		this.nombreViaje = nombreViaje;
	}
	
	public Date getFecha(){
		return fecha;
	}
	
	public void setFecha(Date fecha){
		this.fecha = fecha;
	}

	public Integer getDuracion(){
		return duracion;
	}
	
	public void setDuracion(Integer duracion){
		this.duracion = duracion;
	}

}
