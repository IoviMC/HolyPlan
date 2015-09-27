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
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@TableGenerator(name = "secuencia", initialValue = 1, allocationSize = 100, table = "tabla_id", pkColumnName = "pk", valueColumnName = "value", pkColumnValue = "planing")
@Table(name = "planing")
public class Planing {
	//ATRIBUTOS
	@Id
	@Column(name = "idplaning")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "secuencia")
//	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(length = 45, nullable = false)
	private String nombre;
	@Column(length = 500, nullable = false)
	private String descripcion;
	
	//RELACIONES
	@ManyToOne
	@JoinColumn(name = "idviaje", referencedColumnName = "idviaje")
	private Viaje viaje;
	
	@OneToMany(mappedBy = "planing", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	private Set<Parada> paradas = new HashSet<Parada>();

	//CONSTRUCTORES
	public Planing() {
		super();
	}

	public Planing(String nombre, String descripcion) {
		this.nombre = nombre;
		this.descripcion = descripcion;
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Viaje getViaje() {
		return viaje;
	}

	public void setViaje(Viaje viaje) {
		this.viaje = viaje;
	}

	public Set<Parada> getParadas() {
		return paradas;
	}

	public void setParadas(Set<Parada> paradas) {
		this.paradas = paradas;
	}
}
