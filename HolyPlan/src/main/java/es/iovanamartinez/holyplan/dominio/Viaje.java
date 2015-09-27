package es.iovanamartinez.holyplan.dominio;

import java.math.BigDecimal;
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
import javax.persistence.TableGenerator;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
@TableGenerator(name = "secuencia", initialValue = 1, allocationSize = 100, table = "tabla_id", pkColumnName = "pk", valueColumnName = "value", pkColumnValue = "viaje")
@Table(name = "viaje")
public class Viaje {
	//ATRIBUTOS
	@Id
	@Column(name = "idviaje")
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "secuencia")
	private Integer id;
	@Column(length = 30, nullable = false)
	private String nombreViaje;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(nullable = false)
	private Date fecha;
	private Integer duracion;
	@Column(length = 250)
	private String descripcion;
	private boolean cancelado;
	private BigDecimal bote;
	
	// RELACIONES
	@OneToMany(mappedBy = "viaje", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	private Set<ViajeUsuario> usuarios = new HashSet<ViajeUsuario>();
	
	@OneToMany(mappedBy = "viaje", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	private Set<GastoColectivo> gastosColectivos = new HashSet<GastoColectivo>();
	
	@OneToMany(mappedBy = "viaje", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	private Set<GastoIndividual> gastosIndividuales = new HashSet<GastoIndividual>();
	
	@OneToMany(mappedBy = "viaje", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	private Set<CheckListViaje> checkLists = new HashSet<CheckListViaje>();
	
	@OneToMany(mappedBy = "viaje", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	private Set<Tema> temas = new HashSet<Tema>();
	
	@OneToMany(mappedBy = "viaje", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	private Set<Planing> planings = new HashSet<Planing>();
	
	//CONSTRUCTORES
	public Viaje(){
		super();
	}
	
	public Viaje(String nombreViaje, Date fecha, Integer duracion, String descripcion, BigDecimal bote){
		super();
		this.nombreViaje = nombreViaje;
		this.fecha = fecha;
		this.duracion = duracion;
		this.descripcion = descripcion;
		this.bote = bote;
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
	
	public boolean isCancelado() {
		return cancelado;
	}

	public void setCancelado(boolean cancelado) {
		this.cancelado = cancelado;
	}
	
	public BigDecimal getBote(){
		return bote;
	}
	
	public void setBote(BigDecimal bote){
		this.bote = bote;
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
	
	public Set<CheckListViaje> getCheckLists() {
		return checkLists;
	}

	public void setCheckLists(Set<CheckListViaje> checkLists) {
		this.checkLists = checkLists;
	}
	
	public Set<Planing> getPlanings() {
		return planings;
	}

	public void setPlanings(Set<Planing> planings) {
		this.planings = planings;
	}
	
	//METODOS
//	public void anadirViajeUsuario(ViajeUsuario viajeUsuario){
//		this.usuarios.add(viajeUsuario);
//	}
	
//	public void eliminarViajeUsuario(ViajeUsuario viajeUsuario){
//		this.usuarios.remove(viajeUsuario);
//	}

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
