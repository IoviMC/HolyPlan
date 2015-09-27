package es.iovanamartinez.holyplan.dominio.vo;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import es.iovanamartinez.holyplan.dominio.Mensaje;

public class MensajeVo implements Serializable {
	private static final long serialVersionUID = -3516594477930064289L;
	
	//ATRIBUTOS
	private Integer id;
	private String nombreUsuario;
	private String descripcion;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecha;
	
	//CONSTRUCTORES
	public MensajeVo() {
		super();
	}
	public MensajeVo(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public MensajeVo(Mensaje mensaje) {
		this.id = mensaje.getId();
		this.nombreUsuario = mensaje.getUsuario().getNombreUsuario();
		this.descripcion = mensaje.getDescripcion();
		this.fecha = mensaje.getFecha();
	}
	
	//GETTERS AND SETTERS
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
}
