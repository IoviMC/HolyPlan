package es.iovanamartinez.holyplan.dominio;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "pago_gasto_colectivo")
public class PagoGastoColectivo {
	//ATRIBUTOS
	@Id
	@Column(name = "idpago_gasto_colectivo")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(nullable = false, precision = 8, scale = 2)
	private BigDecimal pago;
	@Column(name = "pago_gasto_total")
	private boolean pagoGastoTotal;
	
	//RELACIONES
	@ManyToOne
	@JoinColumn(name = "idusuario", referencedColumnName = "idusuario")
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name = "idgasto_colectivo", referencedColumnName = "idgasto_colectivo")
	private GastoColectivo gasto;
		
	//CONSTRUCTORES
	public PagoGastoColectivo() {
		super();
	}
	
	public PagoGastoColectivo(Usuario usuario, GastoColectivo gasto, BigDecimal pago) {
		this.usuario = usuario;
		this.gasto = gasto;
		this.pago = pago;
	}
	
	public PagoGastoColectivo(Usuario usuario, GastoColectivo gasto, BigDecimal pago, boolean pagoGastoTotal) {
		this.usuario = usuario;
		this.gasto = gasto;
		this.pago = pago;
		this.pagoGastoTotal = pagoGastoTotal;
	}

	//GETTERS AND SETTERS
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getPago() {
		return pago;
	}

	public void setPago(BigDecimal pago) {
		this.pago = pago;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public GastoColectivo getGasto() {
		return gasto;
	}

	public void setGasto(GastoColectivo gasto) {
		this.gasto = gasto;
	}

	public boolean isPagoGastoTotal() {
		return pagoGastoTotal;
	}

	public void setPagoGastoTotal(boolean pagoGastoTotal) {
		this.pagoGastoTotal = pagoGastoTotal;
	}	
}
