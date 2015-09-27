package es.iovanamartinez.holyplan.dominio.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import es.iovanamartinez.holyplan.dominio.GastoColectivo;

public class GastoColectivoVo implements Serializable{
	private static final long serialVersionUID = -6701807322518706008L;
	
	//ATRIBUTOS
	private Integer id;
	private String concepto;
	private BigDecimal importe;
	private Date fecha;
	private boolean pagoBote;
	
	//CONSTRUCTORES
	public GastoColectivoVo(){
		super();
	}
	
	public GastoColectivoVo(String concepto, BigDecimal importe, Date fecha, boolean pagoBote){
		super();
		this.concepto = concepto;
		this.importe = importe;
		this.fecha = fecha;
		this.pagoBote = pagoBote;
	}
	
	public GastoColectivoVo(Integer id, String concepto, BigDecimal importe, Date fecha, boolean pagoBote){
		super();
		this.id = id;
		this.concepto = concepto;
		this.importe = importe;
		this.fecha = fecha;
		this.pagoBote = pagoBote;
	}
	
	public GastoColectivoVo(GastoColectivo gastoColectivo) {
		this.id = gastoColectivo.getId();
		this.concepto = gastoColectivo.getConcepto();
		this.importe = gastoColectivo.getImporte();
		this.fecha = gastoColectivo.getFecha();
		this.pagoBote = gastoColectivo.isPagoBote();
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

	public boolean isPagoBote() {
		return pagoBote;
	}

	public void setPagoBote(boolean pagoBote) {
		this.pagoBote = pagoBote;
	}
}
