package es.iovanamartinez.holyplan.controlador.formulario;

import java.util.List;

import es.iovanamartinez.holyplan.dominio.vo.CheckListPersonaVo;
import es.iovanamartinez.holyplan.dominio.vo.ItemListPersonaVo;

public class ModificarCheckListPersonaForm {
	//ATRIBUTOS
	private Integer id;
	private String nombre;
	private List<ItemListPersonaVo> items;
	
	//CONTRUCTORES
	public ModificarCheckListPersonaForm() {
		super();
	}
	
	public ModificarCheckListPersonaForm(CheckListPersonaVo checkListPersona, List<ItemListPersonaVo> items) {
		this.id = checkListPersona.getId();
		this.nombre = checkListPersona.getNombre();
		this.items = items;
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

	public List<ItemListPersonaVo> getItems() {
		return items;
	}

	public void setItems(List<ItemListPersonaVo> items) {
		this.items = items;
	}
}
