package es.iovanamartinez.holyplan.controlador.formulario;

import java.util.List;

import es.iovanamartinez.holyplan.dominio.vo.CheckListPersonaVo;
import es.iovanamartinez.holyplan.dominio.vo.ItemListPersonaVo;

public class EditarItemsPersonaForm {
	//ATRIBUTOS
	private Integer id;
	private String nombre;
	private String[] itemsNuevos;
	private List<ItemListPersonaVo> items;
	
	//CONTRUCTORES
	public EditarItemsPersonaForm() {
		super();
	}
	
	public EditarItemsPersonaForm(CheckListPersonaVo checkList, List<ItemListPersonaVo> items) {
		this.id = checkList.getId();
		this.nombre = checkList.getNombre();
		this.items = items;
	}

	//GETTERS AND SETTERSs
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

	public String[] getItemsNuevos() {
		return itemsNuevos;
	}

	public void setItemsNuevos(String[] itemsNuevos) {
		this.itemsNuevos = itemsNuevos;
	}
}
