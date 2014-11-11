package es.iovanamartinez.holyplan.dao;

public interface GenericDao<T> {
	T crear(T t);
	void eliminar(Object id);
	T buscar(Object id);
	T actualizar(T t);
}
