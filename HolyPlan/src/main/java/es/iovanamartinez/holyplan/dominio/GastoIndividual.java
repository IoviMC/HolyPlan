package es.iovanamartinez.holyplan.dominio;

import java.math.BigDecimal;
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

import es.iovanamartinez.holyplan.dominio.vo.GastoIndividualVo;

@Entity
@TableGenerator(name = "secuencia", initialValue = 1, allocationSize = 100, table = "tabla_id", pkColumnName = "pk", valueColumnName = "value", pkColumnValue = "gastoI")
@Table(name = "gasto_individual")
public class GastoIndividual {
	
	//ATRIBUTOS
	@Id
	@Column(name = "idgasto_individual")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "secuencia")
//	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(length = 45, nullable = false)
	private String concepto;
	@Column(nullable = false, precision = 8, scale = 2)
	private BigDecimal importe;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecha;
	
	// RELACIONES
//	@ManyToOne
//	@JoinColumn(name = "idviaje", referencedColumnName = "idviaje")
//	private ViajeUsuario viajeUsuario;
	@ManyToOne
	@JoinColumn(name = "idviaje", referencedColumnName = "idviaje")
	private Viaje viaje;
	@ManyToOne
	@JoinColumn(name = "idusuario", referencedColumnName = "idusuario")
	private Usuario usuario;
	
	//CONSTRUCTORES
	public GastoIndividual() {
		super();
	}
	
	public GastoIndividual(Integer id, String concepto, BigDecimal importe, Date fecha) {
		super();
		this.id = id;
		this.concepto = concepto;
		this.importe = importe;
		this.fecha = fecha;
	}
	
	public GastoIndividual(GastoIndividualVo gastoIndividualVo){
		this.concepto = gastoIndividualVo.getConcepto();
		this.importe = gastoIndividualVo.getImporte();
		this.fecha = gastoIndividualVo.getFecha();
	}

	//GETTERS AND SETTERS
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public BigDecimal getImporte() {
		return importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public Usuario getUsuario(){
		return usuario;
	}
	
	public void setUsuario(Usuario usuario){
		this.usuario = usuario;
	}
	
	public Viaje getViaje(){
		return viaje;
	}
	
	public void setViaje(Viaje viaje){
		this.viaje = viaje;
	}
}
