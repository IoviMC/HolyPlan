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

@Entity
@TableGenerator(name = "secuencia", initialValue = 1, allocationSize = 100, table = "tabla_id", pkColumnName = "pk", valueColumnName = "value", pkColumnValue = "parada")
@Table(name = "parada")
public class Parada {
	//ATRIBUTOS
	@Id
	@Column(name = "idparada")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "secuencia")
//	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(length = 45, nullable = false)
	private String nombre;
	@Column(length = 45, nullable = false)
	private String lugar;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	@Column(name = "fecha", nullable = false)
	private Date fecha;
	
	//RELACIONES
	@ManyToOne
	@JoinColumn(name = "idplaning", referencedColumnName = "idplaning")
	private Planing planing;

	//CONSTRUCTORES
	public Parada() {
		super();
	}

	public Parada(String nombre, String lugar, Date fecha) {
		this.nombre = nombre; 
		this.lugar = lugar;
		this.fecha = fecha;
//		this.hora = hora;
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

	public String getLugar() {
		return lugar;
	}


	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Date getFecha() {
		return fecha;
	}

	public Planing getPlaning() {
		return planing;
	}

	public void setPlaning(Planing planing) {
		this.planing = planing;
	}
}
