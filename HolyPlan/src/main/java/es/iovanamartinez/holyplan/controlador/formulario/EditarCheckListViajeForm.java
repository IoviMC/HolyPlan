package es.iovanamartinez.holyplan.controlador.formulario;

import java.util.List;

import es.iovanamartinez.holyplan.dominio.vo.CheckListViajeVo;
import es.iovanamartinez.holyplan.dominio.vo.ItemListViajeVo;

public class EditarCheckListViajeForm {
	//ATRIBUTOS
	private Integer id;
	private String nombre;
	private List<ItemListViajeVo> items;
	
	//CONTRUCTORES
	public EditarCheckListViajeForm() {
		super();
	}
	
	public EditarCheckListViajeForm(CheckListViajeVo checkListViaje, List<ItemListViajeVo> items) {
		this.id = checkListViaje.getId();
		this.nombre = checkListViaje.getNombre();
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

	public List<ItemListViajeVo> getItems() {
		return items;
	}

	public void setItems(List<ItemListViajeVo> items) {
		this.items = items;
	}
}
