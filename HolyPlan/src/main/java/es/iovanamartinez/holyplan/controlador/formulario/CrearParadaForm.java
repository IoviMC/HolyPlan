package es.iovanamartinez.holyplan.controlador.formulario;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;


public class CrearParadaForm {
	
	private String nombre;
	private String lugar;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date fecha;
	private String fechaString;
//	private Date fecha;
//	private Timer hora;
	
	//CONSTRUCTORES
	public CrearParadaForm() {
		super();
	}
	
	//GETTERS AND SETTERS
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getLugar() {
		return lugar;
	}
	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public String getFechaString() {
		return fechaString;
	}

	public void setFechaString(String fechaString) {
		this.fechaString = fechaString;
	}
	
	
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

//	public Timer getHora() {
//		return hora;
//	}
//
//	public void setHora(Timer hora) {
//		this.hora = hora;
//	}
}
