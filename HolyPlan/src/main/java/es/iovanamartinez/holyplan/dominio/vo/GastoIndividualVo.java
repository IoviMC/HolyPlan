package es.iovanamartinez.holyplan.dominio.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import es.iovanamartinez.holyplan.dominio.GastoIndividual;

public class GastoIndividualVo implements Serializable{

	private static final long serialVersionUID = 2633268765393270727L;
	
	//ATRIBUTOS
	private Integer id;
	private String concepto;
	private BigDecimal importe;
	private Date fecha;
	
	//CONSTRUCTORES
	public GastoIndividualVo() {
		super();
	}
	
	public GastoIndividualVo(String concepto, BigDecimal importe, Date fecha) {
		super();
		this.concepto = concepto;
		this.importe = importe;
		this.fecha = fecha;
	}
	
	public GastoIndividualVo(Integer id, String concepto, BigDecimal importe, Date fecha) {
		super();
		this.id = id;
		this.concepto = concepto;
		this.importe = importe;
		this.fecha = fecha;
	}
	
	public GastoIndividualVo(GastoIndividual gastoIndividual){
		this.id = gastoIndividual.getId();
		this.concepto = gastoIndividual.getConcepto();
		this.importe = gastoIndividual.getImporte();
		this.fecha = gastoIndividual.getFecha();
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

}
