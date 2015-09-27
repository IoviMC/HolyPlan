package es.iovanamartinez.holyplan.dominio.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import es.iovanamartinez.holyplan.dominio.Viaje;


public class ViajeVo implements Serializable{
	private static final long serialVersionUID = 3602974705536987557L;
	
	//ATRIBUTOS
	private Integer id;
	private String nombreViaje;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecha;
	private Integer duracion;
	private String descripcion;
//	private Set<ViajeUsuarioVo> usuarios;
	private BigDecimal bote;
	private boolean cancelado;
	
	//CONSTRUCTORES
	public ViajeVo(){
		super();
	}
	
	public ViajeVo(String nombre, Date fecha, Integer duracion, String descripcion, BigDecimal bote){
		super();
		this.nombreViaje = nombre;
		this.fecha = fecha;
		this.duracion = duracion;
		this.descripcion = descripcion;
		this.bote = bote;
	}
	
	public ViajeVo(Integer idViaje, String nombre, Date fecha, Integer duracion, String descripcion, BigDecimal bote){
		super();
		this.id = idViaje;
		this.nombreViaje = nombre;
		this.fecha = fecha;
		this.duracion = duracion;
		this.descripcion = descripcion;
		this.bote = bote;
	}
	
	public ViajeVo(Viaje viaje){
		this.id = viaje.getId();
		this.nombreViaje = viaje.getNombreViaje();
		this.fecha = viaje.getFecha();
		this.duracion = viaje.getDuracion();
		this.descripcion = viaje.getDescripcion();
		this.bote = viaje.getBote();
		this.cancelado = viaje.isCancelado();
	}
	
	//METODOS
//	public void anadirViajeUsuario(ViajeUsuarioVo viajeUsuario){
//		usuarios.add(viajeUsuario);
//	}
//	
//	public void eliminarViajeUsuario(ViajeUsuarioVo viajeUsuario){
//		usuarios.remove(viajeUsuario);
//	}
		
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
	
	public String getDescripcion(){
		return descripcion;
	}
	
	public void setDescripcion(String descripcion){
		this.descripcion = descripcion;
	}

	public BigDecimal getBote(){
		return bote;
	}
	
	public void setBote(BigDecimal bote){
		this.bote = bote;
	}
	
	public boolean isCancelado() {
		return cancelado;
	}

	public void setCancelado(boolean cancelado) {
		this.cancelado = cancelado;
	}
}
