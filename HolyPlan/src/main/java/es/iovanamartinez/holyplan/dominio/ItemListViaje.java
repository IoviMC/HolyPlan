package es.iovanamartinez.holyplan.dominio;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import es.iovanamartinez.holyplan.dominio.vo.ItemListViajeVo;

@Entity
@TableGenerator(name = "secuencia", initialValue = 1, allocationSize = 100, table = "tabla_id", pkColumnName = "pk", valueColumnName = "value", pkColumnValue = "itemV")
@Table(name = "itemlist_viaje")
public class ItemListViaje {
	@Id
	@Column(name = "iditemlist_viaje")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "secuencia")
//	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(length = 100, nullable = false)
	private String descripcion;
	@Column(nullable = false)
	private boolean realizado;
	
	//RELACIONES
	@ManyToOne
	@JoinColumn(name = "idchecklist_viaje", referencedColumnName = "idchecklist_viaje")
	@OneToOne(cascade=CascadeType.REMOVE)
	private CheckListViaje checkListViaje;
	
	@ManyToOne
	@JoinColumn(name = "idusuario", referencedColumnName = "idusuario")
	private Usuario usuario;
	
	//CONSTRUCTORES
	public ItemListViaje(){
		super();
	}
	
	public ItemListViaje(String descripcion){
		this.descripcion = descripcion;
	}
	
	public ItemListViaje(ItemListViajeVo itemVo){
		this.id = itemVo.getId();
		this.descripcion = itemVo.getDescripcion();
		this.realizado = itemVo.isRealizado();
	}
	
	//SETTERS AND GETTERS
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

	public boolean isRealizado() {
		return realizado;
	}

	public void setRealizado(boolean realizado) {
		this.realizado = realizado;
	}

	public CheckListViaje getCheckListViaje() {
		return checkListViaje;
	}

	public void setCheckListViaje(CheckListViaje checkListViaje) {
		this.checkListViaje = checkListViaje;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
