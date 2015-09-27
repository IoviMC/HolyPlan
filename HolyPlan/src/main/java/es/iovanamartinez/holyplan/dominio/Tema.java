package es.iovanamartinez.holyplan.dominio;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import es.iovanamartinez.holyplan.dominio.vo.TemaVo;

@Entity
@TableGenerator(name = "secuencia", initialValue = 1, allocationSize = 100, table = "tabla_id", pkColumnName = "pk", valueColumnName = "value", pkColumnValue = "tema")
@Table(name = "tema")
public class Tema {
	//ATRIBUTOS
	@Id
	@Column(name = "idtema")
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "secuencia")
	private Integer id;
	@Column(length = 50, nullable = false)
	private String titulo;
	@Column(nullable = false)
	private boolean activo;
	
	// RELACIONES
	@OneToMany(mappedBy = "tema", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@OrderBy("fecha ASC")
	private Set<Mensaje> mensajes = new HashSet<Mensaje>();
	
	@ManyToOne
	@JoinColumn(name = "idviaje", referencedColumnName = "idviaje")
	private Viaje viaje;

	
	//CONSTRUCTORES
	public Tema() {
		super();
	}
	
	public Tema(TemaVo tema) {
		super();
		this.titulo = tema.getTitulo();
		this.activo = tema.isActivo();
	}

	//GETTERS AND SETTERS
	public Integer getId(){
		return id;
	}

	public void setId(Integer id){
		this.id = id;
	}

	public String getTitulo(){
		return titulo;
	}
	
	public void setTitulo(String titulo){
		this.titulo = titulo;
	}

//	public Set<Mensaje> getMensajes() {
//		return mensajes;
//	}
//
//	public void setMensajes(Set<Mensaje> mensajes) {
//		this.mensajes = mensajes;
//	}
	
	public Set<Mensaje> getMensajes() {
		return mensajes;
	}

	public void setMensajes(Set<Mensaje> mensajes) {
		this.mensajes = mensajes;
	}
	
	public Viaje getViaje(){
		return viaje;
	}
	
	public void setViaje(Viaje viaje){
		this.viaje = viaje;
	}
	
	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	//METODOS
//	public void anadirMensaje(Mensaje mensaje){
//		this.mensajes.add(mensaje);
//	}
//	public Integer obtenerNumeroMensajes(){
//		return this.mensajes.size();
//	}
//	
//	public Date obtenerFechaUltimoMensaje(){
//		return this.mensajes.get(this.mensajes.size()-1).getFecha();
//	}
}
