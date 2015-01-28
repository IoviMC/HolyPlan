package es.iovanamartinez.holyplan.controlador.formulario;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class CrearViajeForm {
	//ATRIBUTOS
	private String nombreViaje;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecha;
	private Integer duracion;
	private String descripcion;
	
	//CONSTRUCTORES
	public CrearViajeForm(){
		super();
	}
	
	//GETTERS AND SETTERS	
	public String getNombreViaje(){
		return nombreViaje;
	}
	
	public void setNombreViaje(String nombreViaje){
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
}
