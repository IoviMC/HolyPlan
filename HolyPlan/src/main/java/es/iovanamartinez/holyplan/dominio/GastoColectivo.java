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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.springframework.format.annotation.DateTimeFormat;

import es.iovanamartinez.holyplan.dominio.vo.GastoColectivoVo;

@Entity
@TableGenerator(name = "secuencia", initialValue = 1, allocationSize = 100, table = "tabla_id", pkColumnName = "pk", valueColumnName = "value", pkColumnValue = "gastoC")
@Table(name = "gasto_colectivo")
public class GastoColectivo {
	
	//ATRIBUTOS
	@Id
	@Column(name = "idgasto_colectivo")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "secuencia")
//	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(length = 45, nullable = false)
	private String concepto;
	@Column(nullable = false, precision = 8, scale = 2)
	private BigDecimal importe;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecha;
	private boolean pagoBote;
	
	//RELACIONES
	@ManyToOne
	@JoinColumn(name = "idviaje", referencedColumnName = "idviaje")
	private Viaje viaje;
	
	@OneToMany(mappedBy = "gasto", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	private Set<PagoGastoColectivo> pagos = new HashSet<PagoGastoColectivo>();

	
	//CONSTRUCTORES
	public GastoColectivo(){
		super();
	}
	
	public GastoColectivo(Integer id, String concepto, BigDecimal importe, Date fecha, Viaje viaje, boolean pagoBote){
		super();
		this.id = id;
		this.concepto = concepto;
		this.importe = importe;
		this.fecha = fecha;
		this.viaje = viaje;
		this.pagoBote = pagoBote;
	}
	
	public GastoColectivo(GastoColectivoVo gastoColectivoVo){
		super();
		this.concepto = gastoColectivoVo.getConcepto();
		this.importe = gastoColectivoVo.getImporte();
		this.fecha = gastoColectivoVo.getFecha();
		this.pagoBote = gastoColectivoVo.isPagoBote();
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
	
	public Viaje getViaje() {
		return viaje;
	}
	
	public void setViaje(Viaje viaje) {
		this.viaje = viaje;
	}
	
	public Set<PagoGastoColectivo> getPagos() {
		return pagos;
	}
	
	public void setPagos(Set<PagoGastoColectivo> pagos) {
		this.pagos = pagos;
	}

	public boolean isPagoBote() {
		return pagoBote;
	}

	public void setPagoBote(boolean pagoBote) {
		this.pagoBote = pagoBote;
	}
}
