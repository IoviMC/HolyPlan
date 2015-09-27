package es.iovanamartinez.holyplan.dominio.vo;

import java.io.Serializable;

import es.iovanamartinez.holyplan.dominio.Tema;

public class TemaVo implements Serializable {
	private static final long serialVersionUID = -7225151817625668221L;
	
	//ATRIBUTOS
	private Integer id;
	private String titulo;
	private boolean activo;
	
	//CONSTRUCTORES
	public TemaVo() {
		super();
	}
	
	public TemaVo(String titulo){
		this.titulo = titulo;
		this.activo = true;
	}
	
	public TemaVo(Tema tema) {
		super();
		this.id = tema.getId();
		this.titulo = tema.getTitulo();
		this.activo = tema.isActivo();
	}
	
	//GETTERS AND SETTERS
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
}
