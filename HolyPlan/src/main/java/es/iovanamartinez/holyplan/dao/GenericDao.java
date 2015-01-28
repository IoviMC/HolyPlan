package es.iovanamartinez.holyplan.dao;

public interface GenericDao<T> {
	T crear(T t);
	void eliminar(T t);
	T buscar(Object id);
	T actualizar(T t);
}
