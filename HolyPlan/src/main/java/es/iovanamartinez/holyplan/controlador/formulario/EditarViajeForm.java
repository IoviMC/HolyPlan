package es.iovanamartinez.holyplan.controlador.formulario;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class EditarViajeForm {

	//ATRIBUTOS
	private Integer id;
	private String nombreViaje;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecha;
	private Integer duracion;
	private String descripcion;
	private BigDecimal bote;
	
	//CONSTRUCTORES
	public EditarViajeForm(){
		super();
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

	public BigDecimal getBote(){
		return bote;
	}
	
	public void setBote(BigDecimal bote){
		this.bote = bote;
	}
}
