package es.iovanamartinez.holyplan.dominio.vo;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import es.iovanamartinez.holyplan.dominio.Parada;


public class ParadaVo implements Serializable{

	private static final long serialVersionUID = 707690497836984313L;
	
	private Integer id;
	private String nombre;
	private String lugar;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date fecha;
	
	//CONSTRUCTORES
	public ParadaVo() {
		super();
	}
	
	public ParadaVo(Parada parada) {
		this.id = parada.getId();
		this.nombre = parada.getNombre();
		this.lugar = parada.getLugar();
		this.fecha = parada.getFecha();
	}

	//GETTERS AND SETTERS
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
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
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
}
