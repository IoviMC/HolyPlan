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

import org.springframework.format.annotation.DateTimeFormat;


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
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(nullable = false)
	private Date fecha;
	private Integer duracion;
	@Column(length = 250)
	private String descripcion;
	
	// RELACIONES
	@OneToMany(mappedBy = "viaje", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	private Set<ViajeUsuario> usuarios = new HashSet<ViajeUsuario>();
	@OneToMany(mappedBy = "viaje", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	private Set<GastoColectivo> gastosColectivos = new HashSet<GastoColectivo>();
	@OneToMany(mappedBy = "viaje", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	private Set<GastoIndividual> gastosIndividuales = new HashSet<GastoIndividual>();
	@OneToMany(mappedBy = "viaje", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
//	@JoinTable(name="VIAJE_HAS_TEMAS", joinColumns={@JoinColumn(name="idviaje", referencedColumnName="idviaje")}, 
//		inverseJoinColumns={@JoinColumn(name="idtema", referencedColumnName="idtema")})
	private Set<Tema> temas = new HashSet<Tema>();
	
	//CONSTRUCTORES
	public Viaje(){
		super();
	}
	
	public Viaje(String nombreViaje, Date fecha, Integer duracion, String descripcion){
		super();
		this.nombreViaje = nombreViaje;
		this.fecha = fecha;
		this.duracion = duracion;
		this.descripcion = descripcion;
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
	
	public Set<ViajeUsuario> getUsuarios(){
		return usuarios;
	}
	
	public void setUsuarios(Set<ViajeUsuario> usuarios){
		this.usuarios = usuarios;
	}
	
	public Set<GastoColectivo> getGastosColectivos(){
		return gastosColectivos;
	}
	
	public void setGastosColectivos(Set<GastoColectivo> gastosColectivos){
		this.gastosColectivos = gastosColectivos;
	}
	
	public Set<GastoIndividual> getGastosIndividuales(){
		return gastosIndividuales;
	}
	
	public void setGastosIndividuales(Set<GastoIndividual> gastosIndividuales){
		this.gastosIndividuales = gastosIndividuales;
	}
	
	public Set<Tema> getTemas() {
		return temas;
	}

	public void setTemas(Set<Tema> temas) {
		this.temas = temas;
	}
	
	//METODOS
	public void anadirViajeUsuario(ViajeUsuario viajeUsuario){
		this.usuarios.add(viajeUsuario);
	}
	
//	public void anadirTema(Tema tema){
//		this.temas.add(tema);
//	}
	
	public void eliminarViajeUsuario(ViajeUsuario viajeUsuario){
		this.usuarios.remove(viajeUsuario);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Viaje)) {
			return false;
		}
		Viaje other = (Viaje) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}
}
