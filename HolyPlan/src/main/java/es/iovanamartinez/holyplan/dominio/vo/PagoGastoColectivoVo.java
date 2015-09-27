package es.iovanamartinez.holyplan.dominio.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import es.iovanamartinez.holyplan.dominio.PagoGastoColectivo;

public class PagoGastoColectivoVo  implements Serializable{
	private static final long serialVersionUID = -1102467977983419623L;
	
	//ATRIBUTOS
	private Integer id;
	private BigDecimal importe;
	private String nombreUsuario;
	private String concepto;
	private Integer idGasto;
	private boolean pagoGastoTotal;
	
	
	//CONSTRUCTORES
	public PagoGastoColectivoVo() {
		super();
	}
	
	
//	public PagoGastoColectivoVo(Integer id, BigDecimal importe, String nombreUsuario, String concepto) {
//		this.id = id;
//		this.importe = importe;
//		this.nombreUsuario = nombreUsuario;
//		this.concepto = concepto;
//	}
	
	
	public PagoGastoColectivoVo(PagoGastoColectivo pago){
		this.id = pago.getId();
		this.importe = pago.getPago();
		this.nombreUsuario = pago.getUsuario().getNombreUsuario();
		this.concepto = pago.getGasto().getConcepto();
		this.idGasto = pago.getGasto().getId();
		this.pagoGastoTotal = pago.isPagoGastoTotal();
	}

	//GETTERS AND SETTERS
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getImporte() {
		return importe;
	}
	
	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public Integer getIdGasto() {
		return idGasto;
	}

	public void setIdGasto(Integer idGasto) {
		this.idGasto = idGasto;
	}


	public boolean isPagoGastoTotal() {
		return pagoGastoTotal;
	}


	public void setPagoGastoTotal(boolean pagoGastoTotal) {
		this.pagoGastoTotal = pagoGastoTotal;
	}
}
