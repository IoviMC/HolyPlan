package es.iovanamartinez.holyplan.controlador.formulario;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class GastoForm {
	//ATRIBUTOS
	private Integer id;
	private String concepto;
	private BigDecimal importe;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecha;
	
	//CONSTRUCTORES
	public GastoForm(){
		super();
	}
	
	public GastoForm(Integer id, String concepto, BigDecimal importe, Date fecha){
		this.id = id;
		this.concepto = concepto;
		this.importe = importe; 
		this.fecha = fecha;
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
