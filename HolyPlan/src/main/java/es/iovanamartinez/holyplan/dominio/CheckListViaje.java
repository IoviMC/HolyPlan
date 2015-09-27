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
@TableGenerator(name = "secuencia", initialValue = 1, allocationSize = 100, table = "tabla_id", pkColumnName = "pk", valueColumnName = "value", pkColumnValue = "listaV")
@Table(name = "checklist_viaje")
public class CheckListViaje {
	//ATRIBUTOS
	@Id
	@Column(name = "idchecklist_viaje")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "secuencia")
//	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(length = 50, nullable = false)
	private String nombre;
	
	//RELACIONES
	@OneToMany(mappedBy = "checkListViaje", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	private Set<ItemListViaje> items = new HashSet<ItemListViaje>();
	
	@ManyToOne
	@JoinColumn(name = "idviaje", referencedColumnName = "idviaje")
	private Viaje viaje;
	
	
	//CONSTRUCTORES
	public CheckListViaje() {
		super();
	}
	
	public CheckListViaje(String nombre){
		this.nombre = nombre;
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

	public Viaje getViaje() {
		return viaje;
	}

	public void setViaje(Viaje viaje) {
		this.viaje = viaje;
	}
	
	public Set<ItemListViaje> getItems(){
		return items;
	}
	
	public void setItems(Set<ItemListViaje> items){
		this.items = items;
	}
}
