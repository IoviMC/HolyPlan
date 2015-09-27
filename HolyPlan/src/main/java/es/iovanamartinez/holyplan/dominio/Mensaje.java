package es.iovanamartinez.holyplan.dominio;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.springframework.format.annotation.DateTimeFormat;

import es.iovanamartinez.holyplan.dominio.vo.MensajeVo;

@Entity
@TableGenerator(name = "secuencia", initialValue = 1, allocationSize = 100, table = "tabla_id", pkColumnName = "pk", valueColumnName = "value", pkColumnValue = "mensaje")
@Table(name = "mensaje")
public class Mensaje {
	
	@Id
	@Column(name = "idmensaje")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "secuencia")
//	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(length = 250, nullable = false)
	private String descripcion;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(nullable = false)
	private Date fecha;
	
	//RELACIONES
	@ManyToOne
	@JoinColumn(name = "idtema", referencedColumnName = "idtema")
	private Tema tema;
	@ManyToOne
	@JoinColumn(name = "idusuario", referencedColumnName = "idusuario")
	private Usuario usuario;
	
	//CONSTRUCTORES
	public Mensaje(){
		super();
	}
	
	public Mensaje(MensajeVo mensajeVo) {
		this.descripcion = mensajeVo.getDescripcion();
		this.fecha = new Date();
	}

	//GETTERS AND SETTERS
	public Integer getId(){
		return id;
	}
	
	public void setId(Integer id){
		this.id = id;
	}
	
	public String getDescripcion(){
		return descripcion;
	}
	
	public void setDescripcion(String descripcion){
		this.descripcion = descripcion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public Tema getTema(){
		return tema;
	}
	
	public void setTema(Tema tema){
		this.tema = tema;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
