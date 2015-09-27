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
@TableGenerator(name = "secuencia", initialValue = 1, allocationSize = 100, table = "tabla_id", pkColumnName = "pk", valueColumnName = "value", pkColumnValue = "listaP")
@Table(name = "checklist_persona")
public class CheckListPersona {
	//ATRIBUTOS
	@Id
	@Column(name = "idchecklist_persona")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "secuencia")
//	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(length = 50, nullable = false)
	private String nombre;
	
	@OneToMany(mappedBy = "checkListPersona", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	private Set<ItemListPersona> items = new HashSet<ItemListPersona>();
	
	@ManyToOne
	@JoinColumn(name = "idusuario", referencedColumnName = "idusuario")
	private Usuario usuario;
	
//	@ManyToOne
//	@JoinColumns({
//	    @JoinColumn(name="idusuario", referencedColumnName = "idusuario", insert = "false", update = "falses"),
//	    @JoinColumn(name="idviaje", referencedColumnName = "idviaje")})
//	private ViajeUsuario viajeUsuario;
	
	//CONSTRUCTORES
	public CheckListPersona() {
		super();
	}
	
	public CheckListPersona(String nombre){
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Set<ItemListPersona> getItems(){
		return items;
	}
	
	public void setItems(Set<ItemListPersona> items){
		this.items = items;
	}
}
