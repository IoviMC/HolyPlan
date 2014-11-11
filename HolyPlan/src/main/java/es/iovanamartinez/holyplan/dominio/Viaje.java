package es.iovanamartinez.holyplan.dominio;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "viaje")
public class Viaje {
	//ATRIBUTOS
	@Id
	@Column(name = "idviaje")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(length = 30, nullable = false)
	private String nombreViaje;
	@Column(length = 45, nullable = false)
	private Date fecha;
	private Integer duracion;
	
	// RELACIONES
	@OneToMany(mappedBy = "viaje", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	private Set<ViajeUsuario> usuarios = new HashSet<ViajeUsuario>();
	
	//CONSTRUCTORES
	public Viaje(){
		super();
	}
	
	public Viaje(String nombreViaje, Date fecha, Integer duracion){
		super();
		this.nombreViaje = nombreViaje;
		this.fecha = fecha;
		this.duracion = duracion;
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
	
	public Set<ViajeUsuario> getUsuarios(){
		return usuarios;
	}
	
	public void setUsuarios(Set<ViajeUsuario> usuarios){
		this.usuarios = usuarios;
	}
	
	//METODOS
	public void anadirViajeUsuario(ViajeUsuario viajeUsuario){
		this.usuarios.add(viajeUsuario);
	}
	
	public void eliminarViajeUsuario(ViajeUsuario viajeUsuario){
		this.usuarios.remove(viajeUsuario);
	}
}
